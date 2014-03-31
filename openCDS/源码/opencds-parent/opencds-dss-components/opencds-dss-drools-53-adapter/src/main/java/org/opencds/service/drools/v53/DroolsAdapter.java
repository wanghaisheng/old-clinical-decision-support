/**
 * Copyright 2011 OpenCDS.org
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *	
 */

package org.opencds.service.drools.v53;
 
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.audit.WorkingMemoryInMemoryLogger;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.EvaluationExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.InvalidTimeZoneOffsetExceptionFault;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.omg.dss.UnrecognizedLanguageExceptionFault;
import org.omg.dss.UnrecognizedScopedEntityExceptionFault;
import org.omg.dss.UnsupportedLanguageExceptionFault;
import org.opencds.common.structures.DSSRequestItem;
import org.opencds.common.structures.TimingData;
import org.opencds.common.utilities.FileUtility;
import org.opencds.dss.evaluate.Evaluater;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.opencds.service.evaluate.DSSEvaluateAdapterShell;
import org.opencds.service.evaluate.PayloadInboundProcessorFactory;
import org.opencds.vmr.v1_0.mappings.interfaces.IPayloadInboundProcessor;
import org.opencds.vmr.v1_0.mappings.out.MarshalVMR2VMRSchemaPayload;


/**
 * DroolsAdapter.java
 * 
 * <p>Adapter to use Drools to process the evaluate operation of the DSS web service:
 * This class is designed to use data input in standard Java classes, to facilitate its use from various settings.
 * Mapping of the input data to the internal Java classes is done by input mappers and output mappers, with a set 
 * each for every external data format to be processed. 
 * 
 * 
 * Simply stated, input messages contain a list of rules (Knowledge Modules, or KMs) to run, and structured data
 * to run against those KMs.  The submitted data can be in any structure for which there is a mapper.  Currently,
 * OpenCDS supports the HL7 balloted VMR structure.
 * 
 * Additional structures for the submitted data may be developed, possibly including the CDA / CCD schema structure
 * <p/>
 * <p>Copyright 2011 OpenCDS.org</p>
 * <p>Company: OpenCDS</p>
 *
 * @author David Shields
 * @version 2.1 for Drools 5.3
 * @date 11-09-2011
 * 
 */
public class DroolsAdapter extends Evaluater
{		
    private static 	DroolsAdapter 	instance;  //singleton instance
	private static 	Log 				log 					= LogFactory.getLog(DroolsAdapter.class);
        
    public DroolsAdapter()
    {
        initialize();
    }

	protected void initialize()
    {
        log.info(" beginning OpenCDS Drools 5.3 DSSEvaluateAdapter");
		
        //KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
//      config.setOption( AssertBehaviorOption.EQUALITY );
//      config.setOption( RemoveIdentitiesOption.YES );    
//    	config.setOption( MultithreadEvaluationOption.YES );
//    	config.setOption( MaxThreadsOption.get(20) );
//    	
//    	ResourceFactory.getResourceChangeScannerService().start();

    }
	
    // public functions
    public static DroolsAdapter getInstance()
    {
        if (instance == null)
        {
            instance = new DroolsAdapter();
        }
        return instance;
    }
    

	/** 
	 * big picture pseudo code for following method:
	 * 
	 * 		for each requestedKmId { 
	 * 			getResponse:
	 * 				load KM into KBase
     *  			load globals
     *				load data from inputPayloadString 
     *  			KBase.execute (calls Drools)
     *  			unload results to outputString
     * 		} 
	 * 
	 * This means that we are considering the OMG-CDSS concept of KnowledgeModule equivalent to
	 * the Drools concept of KnowledgeBase.
	 */	
	public String getResponse(	DSSRequestItem dSSRequestItem
	) throws
		InvalidDriDataFormatExceptionFault, 
		RequiredDataNotProvidedExceptionFault, 
		EvaluationExceptionFault, 
		InvalidTimeZoneOffsetExceptionFault, 
		UnrecognizedScopedEntityExceptionFault, 
		UnrecognizedLanguageExceptionFault, 
		UnsupportedLanguageExceptionFault,
		DSSRuntimeExceptionFault
	{
		SimpleKnowledgeRepository 	simpleKR 	= SimpleKnowledgeRepository.getInstance();
		
		String 	requestedKmId			= dSSRequestItem.getRequestedKmId();
		String	requestedKmPrimaryProcessName	= simpleKR.getRequiredKMPrimaryProcessNameForKMId(requestedKmId);
		String 	externalFactModelSSId	= dSSRequestItem.getExternalFactModelSSId();
		String 	inputPayloadString		= dSSRequestItem.getInputPayloadString();
		Date 	evalTime				= dSSRequestItem.getEvalTime();
		String 	clientLanguage			= dSSRequestItem.getClientLanguage();
		String 	clientTimeZoneOffset 	= dSSRequestItem.getClientTimeZoneOffset();
		String	interactionId			= dSSRequestItem.getInteractionId();
		
		log.info("begin eval Id: " + interactionId 
				+ ", KmId: "+ requestedKmId + " (" + requestedKmPrimaryProcessName + ")"
				+ ", SSId: " + externalFactModelSSId 
				+ ", evalTime: " + evalTime + ", clTimeZone: " + clientTimeZoneOffset 
				+ ", clLang: " + clientLanguage);
		
		//setup list for facts
		HashMap<String, List<?>>		allFactLists			= new HashMap<String, List<?>>();
		org.opencds.vmr.v1_0.internal.FocalPersonId	focalPersonId = new org.opencds.vmr.v1_0.internal.FocalPersonId();
										
		
/** 
 * Load fact map from specific externalFactModels, as specified in externalFactModel SSId...
 * 
 * Every separately identified SSId, by definition, specifies separate input and output mappings.
 * Input mappings are used here, and then output mappings are used following the session.execute.
 */
		String 			requiredSSID 			= simpleKR.getRequiredSSIdForKMId(requestedKmId);
		if (( requiredSSID == null ) || ( "".equals(requiredSSID) )) {
			throw new InvalidDriDataFormatExceptionFault( "OpenCDS does not recognize SSId='" 
					+ externalFactModelSSId + "', and cannot continue" );
		}

		String 			requiredUnmarshaller 	= simpleKR.getRequiredOpenCDSUnmarshallerClassNameForSSID(requiredSSID);
		if (( requiredUnmarshaller == null ) || ( "".equals(requiredUnmarshaller) )) {
			throw new InvalidDriDataFormatExceptionFault( "OpenCDS does not recognize unmarshaller class '" 
					+ requiredUnmarshaller + "' for SSId='" 
					+ externalFactModelSSId + "', and cannot continue" );
		}
		
		dSSRequestItem.getTimingData().setStartUnmarshalTime(System.currentTimeMillis());		
		TimingData 				timingData 			= dSSRequestItem.getTimingData();
		
		IPayloadInboundProcessor 	payloadInboundProcessor = PayloadInboundProcessorFactory.getPayloadInboundProcessor(requiredUnmarshaller, externalFactModelSSId);
		focalPersonId.setId( ((IPayloadInboundProcessor) payloadInboundProcessor).mappingInbound( inputPayloadString, evalTime, allFactLists, timingData ) );
		
		dSSRequestItem.setTimingData(timingData);
		log.debug("unmarshalling completed for: " + requestedKmId + ", focalPersonId: " + focalPersonId);

		dSSRequestItem.getTimingData().setStartInferenceEngineAdapterTime(System.currentTimeMillis());		
		
/** 
 * Get the KMs and Load them into a stateless session
 * 
 * Currently, assumption is made that each requested knowledge module will be run separately
 *  (i.e., as part of a separate distinct knowledge base)
 * 
 */
		File			drlFile		= null;
		File			bpmnFile	= null;
		File			pkgFile		= null;

		KnowledgeBase	knowledgeBase;
		
		if (simpleKR.getKnowledgeBase(requestedKmId) != null) {
			knowledgeBase 		= (KnowledgeBase)simpleKR.getKnowledgeBase(requestedKmId);
		} else {
								knowledgeBase		= KnowledgeBaseFactory.newKnowledgeBase();
			KnowledgeBuilder 	knowledgeBuilder	= KnowledgeBuilderFactory.newKnowledgeBuilder();
			
			drlFile		= simpleKR.getResourceAsFileWithoutException("knowledgeModules", requestedKmId + ".drl");
			bpmnFile	= simpleKR.getResourceAsFileWithoutException("knowledgeModules", requestedKmId + ".bpmn");
			pkgFile		= simpleKR.getResourceAsFileWithoutException("knowledgeModules", requestedKmId + ".pkg");

			if (drlFile  != null) knowledgeBuilder.add( ResourceFactory.newFileResource(drlFile), ResourceType.DRL );
			if (bpmnFile != null) knowledgeBuilder.add( ResourceFactory.newFileResource(bpmnFile), ResourceType.BPMN2 );
			if (pkgFile  != null) knowledgeBuilder.add( ResourceFactory.newFileResource(pkgFile), ResourceType.PKG );
			if ( knowledgeBuilder.hasErrors() ) {
				throw new DSSRuntimeExceptionFault( "KnowledgeBuilder had errors on build of: '" + requestedKmId + "', "+ knowledgeBuilder.getErrors().toString() );
			}	
			if (knowledgeBuilder.getKnowledgePackages().size() == 0) {
				throw new DSSRuntimeExceptionFault( "KnowledgeBuilder did not find any VALID '.drl', '.bpmn' or '.pkg' files for: '" + requestedKmId + "', "+ knowledgeBuilder.getErrors().toString() );			
			}
			
			knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
			simpleKR.putKnowledgeBase(requestedKmId, knowledgeBase);
		}
		dSSRequestItem.getTimingData().setFinishInsertKnowledgeTime(System.currentTimeMillis());		
		
	   	StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
	   	
        // create a new Working Memory Logger
//      	WorkingMemoryFileLogger 	sessionLogger 	= new WorkingMemoryFileLogger (statelessKnowledgeSession);	
      	WorkingMemoryInMemoryLogger sessionLogger	= new WorkingMemoryInMemoryLogger (statelessKnowledgeSession);
		dSSRequestItem.getTimingData().setFinishStartKnowledgeSessionTime(System.currentTimeMillis());		
	   	
      	// Set the log file that we will be using to log Working Memory (aka session)      	
//      	sessionLogger.setFileName("C:/opencds-logs/OpenCDS-Drools-event-log"); // TODO: make this based on configuration
      	log.debug("knowledgeBase built for: " + requestedKmId);
	    
/**
 * Load the Globals and Fact lists:  evalTime, language, timezoneOffset
 * 
 */

		@SuppressWarnings("rawtypes")
		List<Command> cmds = new ArrayList<Command>();
		
		/**
		 * Load the Globals:  evalTime, language, timezoneOffset, focalPersonId, assertions, namedObjects
		 * 
		 */
//		EvalTime globalEvalTime = new EvalTime();
//		globalEvalTime.setEvalTimeValue(evalTime);
//		cmds.add( CommandFactory.newSetGlobal("evalTime", globalEvalTime));
		cmds.add( CommandFactory.newSetGlobal("evalTime", evalTime));
		cmds.add( CommandFactory.newSetGlobal("clientLanguage", clientLanguage));
		cmds.add( CommandFactory.newSetGlobal("clientTimeZoneOffset", clientTimeZoneOffset));
//		FocalPersonId globalFocalPersonId = new FocalPersonId();
//		globalFocalPersonId.setId(focalPersonId.toString());
//		cmds.add( CommandFactory.newSetGlobal("focalPersonId", focalPersonId));
		cmds.add( CommandFactory.newSetGlobal("focalPersonId", focalPersonId.getId()));

		//following global used to store flags for inter-task communication
		java.util.HashSet<String> assertions = new java.util.HashSet<String>();
		cmds.add( CommandFactory.newSetGlobal("assertions", assertions));

		//following global used to return facts added by rules, such as new observationResults
		java.util.HashMap<String, Object> namedObjects = new java.util.HashMap<String, Object>();
		cmds.add( CommandFactory.newSetGlobal("namedObjects", namedObjects));
		dSSRequestItem.getTimingData().setFinishLoadGlobalsTime(System.currentTimeMillis());		
		
		
		/**
		 * Load the FactLists:  Only ones that are not empty...
		 */
		
		for ( String oneName : allFactLists.keySet() ) {
			@SuppressWarnings("unchecked")
			List<Object> oneFactList = (List<Object>)allFactLists.get(oneName);
			String oneTypeName = "";
			for ( Object oneFact : (List<Object>)oneFactList ) {
				oneTypeName = oneFact.getClass().getSimpleName();
			}
			if (oneFactList.size() > 0)
			{
				cmds.add( CommandFactory.newInsertElements((List<?>)oneFactList, oneTypeName, true, null));
			}
		}
		dSSRequestItem.getTimingData().setFinishLoadFactListsTime(System.currentTimeMillis());	  	
		
		/**	
		 * If this is a PKG (for package with process, initiate PrimaryProcess.
		 */			

		if (((pkgFile  != null) && (requestedKmPrimaryProcessName != null) && ( !"".equals(requestedKmPrimaryProcessName) ) ) 
				|| (bpmnFile != null))
		{		
			if ( "".equals(requestedKmPrimaryProcessName) ) { 
				throw new DSSRuntimeExceptionFault("DroolsAdapter found improperly configured KM: " + requestedKmId
						+ ".  This KM includes a BPMN file, but does not have a value "
						+ "for 'knowledgeModulePrimaryProcessName' in its configuration.");
			}
			cmds.add (CommandFactory.newStartProcess(requestedKmPrimaryProcessName));
		}
		
		dSSRequestItem.getTimingData().setStartInferenceEngine(System.currentTimeMillis());
/**	
 * Use Drools to process everything			
 ********************************************************************************
 */				
		
		ExecutionResults results = statelessKnowledgeSession.execute((CommandFactory.newBatchExecution((cmds))));	
		
/**					
 ********************************************************************************
 */				
		dSSRequestItem.getTimingData().setFinishInferenceEngine(System.currentTimeMillis());
      	//grab session logging
//		sessionLogger.writeToDisk();
      	log.trace(sessionLogger.getEvents());
      	
      	//update original entries in allFactLists to capture any new or updated elements
        //** need to look for every possible fact list, because rules may have created new ones...
        
        Collection<String> 		allResultNames 		= results.getIdentifiers();  //including concepts and globals?

        Map<String, List<?>> 	resultFactLists 	= new HashMap<String, List<?>>();
		for ( String oneName : allResultNames ) {
			if (!("evalTime".equals(oneName)) && !("clientLanguage".equals(oneName)) && !("clientTimeZoneOffset".equals(oneName)))
			{
				Object oneList = results.getValue(oneName);
				resultFactLists.put(oneName, (List<?>)oneList );
			}
        }
		
		for ( String key : namedObjects.keySet()) {
			if ( namedObjects.get(key) != null ) {
				Object oneNamedObject = namedObjects.get(key);
				String className = oneNamedObject.getClass().getSimpleName();
				@SuppressWarnings("unchecked")
				List<Object> oneList = (List<Object>)resultFactLists.get(className);
				if (oneList == null) {
					oneList = new ArrayList<Object>();
					oneList.add(oneNamedObject);
				}
				else {
					oneList.add(oneNamedObject);
				}
				resultFactLists.put(className, oneList);
			}
		}
        
		/** 
		 * Retrieve the Results for this requested KM and stack them in the DSS fkmResponse
		 * NOTE: Each additional requested KM will have a separate output payload
		 */
		log.debug("stacking results for " + requestedKmId );
        dSSRequestItem.getTimingData().setFinishInferenceEngineAdapterTime(System.currentTimeMillis());
		
//    	MarshalVMR2VMRSchemaPayload payloadCreator		= MarshalVMR2VMRSchemaPayload.getInstance();
        String						payloadCreatorName	= simpleKR.getRequiredPayloadCreatorForSSID(requiredSSID);
        MarshalVMR2VMRSchemaPayload payloadCreator;
    	if (simpleKR.getPayloadCreatorInstanceForClassName(payloadCreatorName) == null) {
    		payloadCreator		= MarshalVMR2VMRSchemaPayload.getInstance();
    		simpleKR.putPayloadCreatorInstanceForClassName(payloadCreatorName, payloadCreator);
    	} else {
    		payloadCreator		= (MarshalVMR2VMRSchemaPayload)simpleKR.getPayloadCreatorInstanceForClassName(payloadCreatorName);
    	}
        String						outputString	= payloadCreator.mappingOutbound( 
        															resultFactLists,
        															evalTime,
        															focalPersonId,
        															clientLanguage,
        															clientTimeZoneOffset,
        															dSSRequestItem.getTimingData());
        log.debug("completed Drools dss engine for: " + requestedKmId);
        return outputString;
	}
	
	public static void main(String [ ] args) 
	{
		String 				payloadString 	= null;
		String				filePath		= null;
		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/ConstructedExampleRecursiveNesting.xml";
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/ConstructedExampleNestedLabsFromCCD.xml";
		//following test file loops, because all the ID values are the same...  need to do a little manual enhancement before it is useful...
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/GeneratedCDSInputWithAllOptionalContent.xml"; 
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/sample_testcase-output.xml";
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/ConstructedExampleNestedInputs.xml"; 
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/sample_testcase-output-variation.xml";
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/ConstructedExampleNestedLabsForBeaconProjectForTesting.xml";
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/Ice3VMRSampleInput-correct.xml";
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/ConstructedExample42yo_OutptEnc20110101_Mammogram20100601.xml";
//		filePath = "C:/OpenCDS/opencds-knowledge-repository-data/src/main/resources/samples/ConstructedExample42yo_OutptEnc20110101_PapTest20100601.xml";
		try {
			payloadString = FileUtility.getInstance().getFileContentsAsString(new File (filePath));
		} catch (DSSRuntimeExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		DSSRequestItem	dSSRequestItem = new DSSRequestItem(); 
		
		dSSRequestItem.setRequestedKmId("org.opencds^bounce^1.5.3");
//		dSSRequestItem.setRequestedKmId("org.opencds^Reportable_Disease^1.0.0");
//		dSSRequestItem.setRequestedKmId("org.opencds^NQF_0031_v1^1.0.0");
//		dSSRequestItem.setRequestedKmId("org.opencds^NQF_0032_v1^1.0.0");
		dSSRequestItem.setExternalFactModelSSId("org.opencds.vmr^VMR^1.0");
		dSSRequestItem.setInputPayloadString(payloadString);
		dSSRequestItem.setEvalTime(new Date());
		dSSRequestItem.setClientLanguage("");
		dSSRequestItem.setClientTimeZoneOffset("");
		TimingData timingData = new TimingData();
		dSSRequestItem.setTimingData(timingData);
		timingData.setStartInferenceEngineAdapterTime(System.currentTimeMillis());
		
		try {
			DSSEvaluateAdapterShell.getInstance();  //initializes KR
			String structuredDroolsResult = DroolsAdapter.getInstance().getResponse( 
					dSSRequestItem);
			
			System.out.println(structuredDroolsResult);
			System.out.println("finished");
			
		} catch (InvalidDriDataFormatExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (UnrecognizedLanguageExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (RequiredDataNotProvidedExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (UnsupportedLanguageExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (UnrecognizedScopedEntityExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (EvaluationExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (InvalidTimeZoneOffsetExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (DSSRuntimeExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}		
	}
}
			
		


