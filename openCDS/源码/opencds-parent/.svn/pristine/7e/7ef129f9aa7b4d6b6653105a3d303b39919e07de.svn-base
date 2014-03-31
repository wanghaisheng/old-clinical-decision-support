/**
 * Copyright 2011, 2012 OpenCDS.org
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

package org.opencds.service.drools.v55;
 
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
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
import org.opencds.common.interfaces.IOutboundPayloadProcessor;
import org.opencds.common.structures.DSSRequestDataItem;
import org.opencds.common.structures.DSSRequestKMItem;
import org.opencds.common.structures.TimingDataKM;
import org.opencds.dss.evaluate.Evaluater;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.opencds.vmr.v1_0.mappings.in.BuildCDSInputFactLists;


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
 * @version 2.1 for Drools 5.3 / 5.4
 * @date 11-09-2011
 * 
 */
public class DroolsAdapter extends Evaluater 
{		
//    private static 	DroolsAdapter 				instance;  //singleton instance
	private static 	Log 						log 		= LogFactory.getLog(DroolsAdapter.class);   
        
	protected void initialize()
    {
//        log.info("beginning OpenCDS Drools 5.5 DSSEvaluateAdapter");
		
      //KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
//      config.setOption( AssertBehaviorOption.EQUALITY );
//      config.setOption( RemoveIdentitiesOption.YES );    
//    	config.setOption( MultithreadEvaluationOption.YES );
//    	config.setOption( MaxThreadsOption.get(20) );
//    	
//    	ResourceFactory.getResourceChangeScannerService().start();

    }

	
    // public functions
//    public static DroolsAdapter getInstance()
//    {
//        if (instance == null)
//        {
//            instance = new DroolsAdapter();
//        }
//        return instance;
//    }
    

    public DroolsAdapter()
    {
        initialize();
    }

   
    /** 
	 * big picture pseudo code for following method:
	 * 
	 * 		for this requestedKmId { 
	 * 			getResponse:
	 * 				create Drools session
	 * 				load KM into session
     *  			load globals into session
     *				load data from allFactLists into session 
     *  			KBase.execute (calls Drools)
     *  			unload result from KM to outputString
     * 		} 
	 * 
	 * This means that we are considering the OMG-CDSS concept of KnowledgeModule equivalent to
	 * the Drools concept of KnowledgeBase.
	 */	
    
    
	public String getOneResponse(
			DSSRequestKMItem dssRequestKMItem
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
		
		String 	requestedKmId			= dssRequestKMItem.getRequestedKmId();
		String	requestedKmPrimaryProcessName	= SimpleKnowledgeRepository.getRequiredKMPrimaryProcessNameForKMId(requestedKmId);
		TimingDataKM timingDataKM		= dssRequestKMItem.getKmTimingData();
		
		@SuppressWarnings("unchecked")
		JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> cdsInput = 
			(JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput>)dssRequestKMItem.getDssRequestDataItem().getCdsInput();
		
		DSSRequestDataItem dssRequestDataItem = dssRequestKMItem.getDssRequestDataItem();
		
		String 	externalFactModelSSId	= dssRequestDataItem.getExternalFactModelSSId();
		Date 	evalTime				= dssRequestDataItem.getEvalTime();
		String 	clientLanguage			= dssRequestDataItem.getClientLanguage();
		String 	clientTimeZoneOffset 	= dssRequestDataItem.getClientTimeZoneOffset();
		String	interactionId			= dssRequestDataItem.getInteractionId();
		
		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " (" + requestedKmPrimaryProcessName + ")"
				+ ", SSId: " + externalFactModelSSId 
				+ ", evalTime: " + evalTime + ", clTimeZone: " + clientTimeZoneOffset 
				+ ", clLang: " + clientLanguage);
		
/** 
 * Load fact map from specific externalFactModels, as specified in externalFactModel SSId...
 * 
 * Every separately identified SSId, by definition, specifies separate input and output mappings.
 * Input mappings are used here, and then output mappings are used following the session.execute.
 */

		Map<String, List<?>> 					allFactLists 			= Collections.synchronizedMap(new WeakHashMap<String, List<?>>());
		
/** 
 * allFactLists are updated in place by the following call, including both facts and concepts...
 * ==================================================================
 */
			
		String focalPersonId = BuildCDSInputFactLists.buildFactLists( cdsInput, evalTime, allFactLists, timingDataKM );
		
/** 
 * ==================================================================
 */
		dssRequestKMItem.setFocalPersonId(focalPersonId);
		
		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " built fact/concept lists for " + focalPersonId);
		
		timingDataKM.setFinishBuildConceptListsTime(new AtomicLong(System.nanoTime()));		
		
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

		KnowledgeBase	knowledgeBase = (KnowledgeBase)SimpleKnowledgeRepository.getKnowledgeBaseCache(requestedKmId);	
		
		if (knowledgeBase != null) {
			log.debug("II: " + interactionId + " KMId: " + requestedKmId + " knowledgeBase from cache");
			
		} else {
								knowledgeBase		= KnowledgeBaseFactory.newKnowledgeBase();
			KnowledgeBuilder 	knowledgeBuilder	= KnowledgeBuilderFactory.newKnowledgeBuilder();
			
			drlFile		= SimpleKnowledgeRepository.getResourceAsFileWithoutException("knowledgeModules", requestedKmId + ".drl");
			bpmnFile	= SimpleKnowledgeRepository.getResourceAsFileWithoutException("knowledgeModules", requestedKmId + ".bpmn");
			pkgFile		= SimpleKnowledgeRepository.getResourceAsFileWithoutException("knowledgeModules", requestedKmId + ".pkg");

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
			SimpleKnowledgeRepository.putKnowledgeBaseCache(requestedKmId, knowledgeBase);
			log.debug("II: " + interactionId + " KMId: " + requestedKmId + " knowledgeBase built");
		}
		
		dssRequestKMItem.getKmTimingData().setFinishInsertKnowledgeTime(new AtomicLong(System.nanoTime()));		
		
	   	StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
	   	
/**
 *  to create a new Drools Working Memory Logger for in depth Drools debugging - Use either the InMemoryLogger
 *  to record logs on all input, or use the FileLogger for debugging of one input at a time in Drools and JBPM
 */

//      	WorkingMemoryInMemoryLogger memoryLogger	= new WorkingMemoryInMemoryLogger (statelessKnowledgeSession);
//      	WorkingMemoryFileLogger 	fileLogger 		= new WorkingMemoryFileLogger (statelessKnowledgeSession);	
//      	// If using the FileLogger, Set the log file that we will be using to log Working Memory (aka session)      	
//      	fileLogger.setFileName("C:/opencds-logs/OpenCDS-Drools-event-log"); 
//TODO: 	make the above choice based on configuration settings
      	
		dssRequestKMItem.getKmTimingData().setFinishStartKnowledgeSessionTime(new AtomicLong(System.nanoTime()));	

/**
 * Load the Globals and Fact lists:  evalTime, language, timezoneOffset
 * 
 */

		@SuppressWarnings("rawtypes")
		List<Command> cmds = Collections.synchronizedList(new ArrayList<Command>());
		
		/**
		 * Load the Globals:  evalTime, language, timezoneOffset, focalPersonId, assertions, namedObjects
		 * 
		 */
		cmds.add( CommandFactory.newSetGlobal("evalTime", evalTime));
		cmds.add( CommandFactory.newSetGlobal("clientLanguage", clientLanguage));
		cmds.add( CommandFactory.newSetGlobal("clientTimeZoneOffset", clientTimeZoneOffset));
		cmds.add( CommandFactory.newSetGlobal("focalPersonId", dssRequestKMItem.getFocalPersonId()));

		//following global used to store flags for inter-task communication in a JBPM Process
		java.util.Set<String> assertions = new java.util.HashSet<String>();
		cmds.add( CommandFactory.newSetGlobal("assertions", assertions));

		//following global used to return facts added by rules, such as new observationResults
		java.util.Map<String, Object> namedObjects = new java.util.HashMap<String, Object>();
		cmds.add( CommandFactory.newSetGlobal("namedObjects", namedObjects));
		
		dssRequestKMItem.getKmTimingData().setFinishLoadGlobalsTime(new AtomicLong(System.nanoTime()));		

		
/**
 * Load the FactLists into Commands:  Only ones that are not empty...
 * 
 */
		
		//does this whole thing needs to be made concurrent safe ?? Will this do it??
		synchronized (allFactLists){
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
				} else {
					allFactLists.remove(oneTypeName);
				}
			}
		}
		
		dssRequestKMItem.getKmTimingData().setFinishLoadFactListsTime(new AtomicLong(System.nanoTime()));	  	

		
/**	
 * If this is a PKG (for package with process, initiate the configured Primary Process for JBPM.
 * 
 */			

		if ( (requestedKmPrimaryProcessName != null) && ( !"".equals(requestedKmPrimaryProcessName) ) )
		{		
			if ( "".equals(requestedKmPrimaryProcessName) ) { 
				throw new DSSRuntimeExceptionFault("DroolsAdapter found improperly configured KM: " + requestedKmId
						+ ".  This KM includes a BPMN file, but does not have a value "
						+ "for 'knowledgeModulePrimaryProcessName' in its configuration.");
			}
			cmds.add (CommandFactory.newStartProcess(requestedKmPrimaryProcessName));
	      	log.debug("II: " + interactionId + " KMId: " + requestedKmId + " knowledgeBase Primary Process: " + requestedKmPrimaryProcessName);	
	    }
		
		dssRequestKMItem.getKmTimingData().setStartInferenceEngine(new AtomicLong(System.nanoTime()));

/**	
 * Use Drools to process everything	
 * Added try/catch around stateless session. because Drools has an unhandled exception
 * when a JBPM Process improperly re-declares a global that is constraining a gateway
 * and the resultant global is null - des 20120727		
 ********************************************************************************
 */				
		ExecutionResults results = null;
		try {
			
			results = statelessKnowledgeSession.execute(CommandFactory.newBatchExecution((cmds)));	
			
		} catch (Exception e) {
			String err = "OpenCDS call to Drools.execute failed with error: " + e.getMessage();
			log.error(err);
		    StackTraceElement elements[] = e.getStackTrace();
		    for (int i = 0, n = elements.length; i < n; i++) {
		    	String detail = elements[i].getClassName() + ":" + elements[i].getMethodName() + ":" + elements[i].getLineNumber();
		        log.error(detail);
		        err += "\n" + elements[i].getMethodName();
		    }
			throw new DSSRuntimeExceptionFault(err);
		}
		
/**					
 ********************************************************************************
 *  END Drools
 *  
 */				
		dssRequestKMItem.getKmTimingData().setFinishInferenceEngine(new AtomicLong(System.nanoTime()));
      	//grab session logging of whichever type was started...
//      log.trace(memoryLogger.getEvents());
//		fileLogger.writeToDisk();
      	
      	//update original entries from allFactLists to capture any new or updated elements
        //** need to look for every possible fact list, because rules may have created new ones...
		//NOTE that results contains the original objects passed in via CMD structure, with any 
		//changes introduced by rules.
        
		Map<String, List<?>> 	resultFactLists 	= Collections.synchronizedMap(new WeakHashMap<String, List<?>>());
        
		synchronized (results)
		{
	        Collection<String> 		allResultNames 		= results.getIdentifiers();  //includes concepts but not globals?
			for ( String oneName : allResultNames ) {
				if (!("evalTime".equals(oneName)) && !("clientLanguage".equals(oneName)) && !("clientTimeZoneOffset".equals(oneName))) {
					// ignore these submitted globals, they should not have been changed by rules, and look at everything else
				
					Object oneList = results.getValue(oneName);
					resultFactLists.put(oneName, (List<?>)oneList );
				}
		    }
		}
		
/**
 * now process the returned namedObjects and add them to the resultFactLists
 * 
 */
		synchronized (namedObjects)
		{
			for ( String key : namedObjects.keySet()) {
				if ( namedObjects.get(key) != null ) {
					Object oneNamedObject = namedObjects.get(key);
	//				String className = oneNamedObject.getClass().getSimpleName();
					@SuppressWarnings("unchecked")
					List<Object> oneList = (List<Object>)resultFactLists.get(oneNamedObject.getClass().getSimpleName());
					if (oneList == null) {
						oneList = new ArrayList<Object>();
						oneList.add(oneNamedObject);
					}
					else {
						oneList.add(oneNamedObject);
					}
					resultFactLists.put(oneNamedObject.getClass().getSimpleName(), oneList);
				}
			}
		}
       
/** 
 * Retrieve the Results for this requested KM and stack them in the DSS fkmResponse
 * NOTE: Each additional requested KM will have a separate output payload
 */

        dssRequestKMItem.getKmTimingData().setFinishInferenceEngineAdapterTime(new AtomicLong(System.nanoTime()));
		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " begin marshalling results to external VMR ");
		
		//FIXME probably need to create static final string to identify output SSID, probably always as VMR
//        String						payloadCreatorName	= SimpleKnowledgeRepository.getRequiredPayloadCreatorForSSID(externalFactModelSSId);
        IOutboundPayloadProcessor 	payloadCreator 		=  
			(IOutboundPayloadProcessor) SimpleKnowledgeRepository.getPayloadCreatorInstanceForClassNameCache(
					SimpleKnowledgeRepository.getRequiredPayloadCreatorForSSID(externalFactModelSSId));
        
/**
 * following is normally instantiated as MarshalVMR2VMRSchemaPayload.getInstance().mappingOutbound( resultFactLists, dssRequestKMItem );
 * 
 */
        String	outputString	= payloadCreator.mappingOutbound( 
										resultFactLists,
										dssRequestKMItem);
        
		log.trace("II: " + interactionId + " KMId: " + requestedKmId + " finished marshalling results to external VMR, " 
				+ outputString.length() + " chars.");
		
/**
 * clear out maps and arrays    
 *    
 */
//        BuildCDSInputFactLists.clearAllFactLists(allFactLists);
//		synchronized (allFactLists) {
		log.debug("clearAllFactLists");
		
//			for (String eachKey : ((Map<String, List<?>>)allFactLists).keySet()) {
//				if (allFactLists.get(eachKey) != null) {
//					List<?> eachList = allFactLists.get(eachKey);
//					eachList.clear();
//					allFactLists.remove(eachKey);
//				}
//			}
//		}	
		
//		long usedMemory4 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		// this is not needed, but it will make it easier to see the leak
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter before clear: " + usedMemory4 / 1000  + "KB");
			
        allFactLists.clear();
//		long usedMemory5 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after allFactLists.clear(): " + usedMemory5 / 1000  + "KB, diff = " + (usedMemory5 - usedMemory4) / 1000 + "KB");
        
		allFactLists = null;
//		long usedMemory6 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after allFactLists = null: " + usedMemory6 / 1000  + "KB, diff = " + (usedMemory6 - usedMemory5) / 1000 + "KB");
        
        cmds.clear();
//		long usedMemory7 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after cmds.clear(): " + usedMemory7 / 1000  + "KB, diff = " + (usedMemory7 - usedMemory6) / 1000 + "KB");

        cmds = null;
//		long usedMemory8 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after cmds = null: " + usedMemory8 / 1000  + "KB, diff = " + (usedMemory8 - usedMemory7) / 1000 + "KB");

		assertions.clear();
//		long usedMemory9 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after assertions.clear(): " + usedMemory9 / 1000  + "KB, diff = " + (usedMemory9 - usedMemory8) / 1000 + "KB");

        assertions = null;
//		long usedMemory10 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after assertions = null;: " + usedMemory10 / 1000  + "KB, diff = " + (usedMemory10 - usedMemory9) / 1000 + "KB");

        namedObjects.clear();
//		long usedMemory11 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after namedObjects.clear(): " + usedMemory11 / 1000  + "KB, diff = " + (usedMemory11 - usedMemory10) / 1000 + "KB");

        namedObjects = null;
//		long usedMemory12 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after namedObjects = null: " + usedMemory12 / 1000  + "KB, diff = " + (usedMemory12 - usedMemory11) / 1000 + "KB");

        for (String oneId : results.getIdentifiers()) {
        	//results.getFactHandle(oneId)
        	results.getIdentifiers().remove(results.getValue(oneId));
        }
        results = null;
//		long usedMemory13 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after results = null: " + usedMemory13 / 1000  + "KB, diff = " + (usedMemory13 - usedMemory12) / 1000 + "KB");

        resultFactLists.values().clear();
		resultFactLists.clear();
//		long usedMemory14 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after resultFactLists.clear(): " + usedMemory14 / 1000  + "KB, diff = " + (usedMemory14 - usedMemory13) / 1000 + "KB");

        resultFactLists = null;
//		long usedMemory15 = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//		System.gc(); 
//		System.out.println("KMId: " + requestedKmId + " used memory at end of DroolsAdapter after resultFactLists = null: " + usedMemory15 / 1000  + "KB, diff = " + (usedMemory15 - usedMemory14) / 1000 + "KB");

        
//        dssRequestKMItem = null;
        
//        statelessKnowledgeSession = null;
        
        log.debug("II: " + interactionId + " KMId: " + requestedKmId + " completed Drools inferencing engine");
        
       return outputString;
	}

}
			
		


