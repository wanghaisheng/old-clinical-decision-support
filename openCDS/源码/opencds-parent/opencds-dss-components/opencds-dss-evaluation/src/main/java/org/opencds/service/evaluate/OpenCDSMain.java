/**
 * Copyright 2011 - 2013 OpenCDS.org
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

package org.opencds.service.evaluate;
 
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.EvaluationExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.InvalidTimeZoneOffsetExceptionFault;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.omg.dss.UnrecognizedLanguageExceptionFault;
import org.omg.dss.UnrecognizedScopedEntityExceptionFault;
import org.omg.dss.UnsupportedLanguageExceptionFault;
import org.omg.dss.common.ItemIdentifier;
import org.omg.dss.common.SemanticPayload;
import org.omg.dss.evaluation.requestresponse.DataRequirementItemData;
import org.omg.dss.evaluation.requestresponse.EvaluationRequest;
import org.omg.dss.evaluation.requestresponse.KMEvaluationRequest;
import org.opencds.common.interfaces.IPayloadInboundProcessor;
import org.opencds.common.structures.DSSRequestDataItem;
import org.opencds.common.structures.TimingData;
import org.opencds.common.utilities.DSSUtility;
import org.opencds.common.utilities.DateUtility;


/**
 * OpenCDSMain.java
 * 
 * <p>Adapter to process the evaluate operation of the DSS web service:
 * 1. It prepares lists of KMs requested
 * 2. It prepares lists of Data submitted
 * 3. It checks that the submitted data is in the input format required by the requested KM
 * 4. It looks up the evaluation engine required for the requested KM
 * 5. It calls the evaluation engine, passes it the submitted data
 * 6. It feeds the results returned by the evaluation engine back to the requestor
 *  
 * Simply stated, input messages contain a list of rules (Knowledge Modules, or KMs) to run, and structured data
 * to run against those KMs.  The submitted data format is both specified by the submitted SemanticSignifier, and 
 * validated against metadata for the KMs (which are written for one specific SemanticSignifier).
 * 
 * The primary supported data structure at this time is the HL7 vMR as balloted by HL7 in September, 2011.
 * 
 * Additional structures for the submitted data may be developed, and this code is designed to be extended
 * to additional data models, if needed.
 * 
 * This code is also written to support alternate inferencing engines, and more than one can be active at runtime.
 * The correct inferencing engine is specified in metadata for a KM, and cached for rapid invocation.
 * 
 * <p/>
 * <p>Copyright 2010 - 2013 OpenCDS.org</p>
 * <p>Company: OpenCDS</p>
 *
 * @author David Shields
 * @version 1.0
 * @date 09-24-2010
 * 
 */
public class OpenCDSMain //extends Evaluater 
{
	private static 	Log 						log 					= LogFactory.getLog(OpenCDSMain.class);
	private			DateUtility					dateUtility				= DateUtility.getInstance();
	
    protected OpenCDSMain() throws DSSRuntimeExceptionFault
    
    {
        initialize();
    }

	protected void initialize() throws DSSRuntimeExceptionFault
    {
		String startTime = dateUtility.getDateAsString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println();
        System.out.println(startTime + " <<< Initializing OpenCDS OpenCDSMain >>>");
        log.info("Initializing OpenCDS OpenCDSMain");
    }


	private synchronized static void validateDRIDataFormat(List<DataRequirementItemData> listDRID) 
	throws InvalidDriDataFormatExceptionFault 
	{
		boolean driDataFormatBad = false;
		String msg = "";
		
    	//TODO implement validateDRIDataFormat
		
		if ( driDataFormatBad ) {
			throw new InvalidDriDataFormatExceptionFault(msg);	
		}
		return;
	}  
  

	private synchronized static void validateScopedEntityIDRecognized(List<DataRequirementItemData> listDRID) 
	throws UnrecognizedScopedEntityExceptionFault 
	{
		boolean driScopedEntityIDBad = false;
		String msg = "";
		
    	//TODO implement validateScopedEntityIDRecognized
		
		if ( driScopedEntityIDBad ) {
			throw new UnrecognizedScopedEntityExceptionFault(msg);	
		}
		return;
	}  
  

	private synchronized static void validateRequiredDataProvided(
			List<ItemIdentifier> listFactItemTypes, 
			KMEvaluationRequest kmeRequest
			) 
	throws 
		RequiredDataNotProvidedExceptionFault, 
		UnrecognizedScopedEntityExceptionFault 
	{
		boolean 									kmerRequiredDataProvided = true;
		
    	//TODO implement validateRequiredDataProvided, which will fault with a list of all KMDataRequirementItemId values for missing elements
//		EntityIdentifier 							kmId			= kmeRequest.getKmId();
				
		String 										msg 			= "";
		
		if ( kmerRequiredDataProvided ) {
			return;
		} else {
			throw new RequiredDataNotProvidedExceptionFault(msg);	
		}
	}  
	
	
	/**
	 * Note that there is currently a logical constraint that OpenCDS requires all of the list of KMs 
	 * 		(when there is more than one) to use the same inference engine adapter and vmr version.
	 * 
	 * It is also a constraint that iterative and non-iterative KMs cannot be used 
	 * 		in the same request.
	 *
	 * big picture pseudo code for this method:
	 * 
	 * 		unmarshal the input and
	 * 		save the data as JaxB structures
	 * 		for each requestedKmId { 
	 * 			load factLists and ConceptLists from data
	 * 			pass the following to the DecisionEngine specified for the KM:
     *				requestedKmId,
     *  	        externalFactModelSSId,
     *  			inputPayloadString,
     *  			evalTime,
     *  			clientLanguage,
     *  			clientTimeZoneOffset
     *  		}
     *  		stack individual results as Base64 data for each requested KmId
     *  	} 
	 * 		return dSSRequestList containing results from all requestedKM
	 * 
	 * This means that we are considering the OMG-HL7-CDSS concept of KnowledgeModule equivalent to
	 * the Drools concept of KnowledgeBase.
	 * 
	 * It also means that we are only allowing one SSID for a set of one or more KMID.
	 */
	
	public static List<KMEvaluationRequest> decodeInput (	
				EvaluationRequest		evaluationRequest,
				java.util.Date			evalTime,
				DSSRequestDataItem		dssRequestDataItem,
				TimingData				timingData)
		throws
			InvalidDriDataFormatExceptionFault, 
			RequiredDataNotProvidedExceptionFault, 
			EvaluationExceptionFault, 
			InvalidTimeZoneOffsetExceptionFault, 
			UnrecognizedScopedEntityExceptionFault, 
			UnrecognizedLanguageExceptionFault, 
			UnsupportedLanguageExceptionFault,
			DSSRuntimeExceptionFault
	{
		log.debug("II: " + dssRequestDataItem.getInteractionId() + " starting OpenCDSMain.decodeInput");
		timingData.setStartMessageRequestTime(new AtomicLong(System.nanoTime()));
		String 								inputPayloadString		= "";
		
/*
 * get data from dss wrapper
 * 
 */
		List<DataRequirementItemData>	listDRIData					= new 
			CopyOnWriteArrayList<DataRequirementItemData>(evaluationRequest.getDataRequirementItemData());
		
		//FIXME flesh out and implement the following
		validateDRIDataFormat(listDRIData);
		validateScopedEntityIDRecognized(listDRIData);
		
/**
 *  Note that OpenCDS only supports one payload at a time, 
 *  even though the DSS standard supports more than one.
 */
		
		//TODO change this code to stack input payloads, possibly including mixing vMR with CCD and/or HL7v2.x data...
		if (listDRIData.size() != 1) {
			log.warn("OpenCDSMain.getInputPayloadString did not have exactly 1 payload.  It had " 
					+ listDRIData.size() + " payloads, and only the first one can be used.");
		}
		
		dssRequestDataItem.setTimingData(timingData);
		dssRequestDataItem.getTimingData().setStartMessageRequestTime(new AtomicLong(System.nanoTime()));
		//dssRequestDataItem.setInteractionId(ii.getInteractionId());
		dssRequestDataItem.setEvalTime(evalTime);
		dssRequestDataItem.setClientLanguage(evaluationRequest.getClientLanguage());
		dssRequestDataItem.setClientTimeZoneOffset(evaluationRequest.getClientTimeZoneOffset());
		dssRequestDataItem.setInputItemName(listDRIData.get(0).getDriId().getItemId());
		dssRequestDataItem.setInputContainingEntityId(
				DSSUtility.makeEIString(listDRIData.get(0).getDriId().getContainingEntityId()));
		dssRequestDataItem.setExternalFactModelSSId(
				DSSUtility.makeEIString(listDRIData.get(0).getData().getInformationModelSSId()));
		
		try	{
			inputPayloadString = getInputPayloadString( listDRIData.get(0) );
			log.trace(inputPayloadString);
			
			dssRequestDataItem.setInputPayloadString(inputPayloadString);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault(
					"OpenCDSMain.getInputPayloadString had UnsupportedEncoding Exception: "
					+ e.getMessage());
		}
		
		timingData.setStartUnmarshalTime(new AtomicLong(System.nanoTime()));
		
		String			externalFactModelSSId		= DSSUtility.makeEIString(evaluationRequest.getDataRequirementItemData().get(0).getData().getInformationModelSSId());
		
/**
 * IPayloadInboundProcessor is returned from cache, if available, otherwise it is instantiated and cached and then returned
 * 
 */
		
		IPayloadInboundProcessor 	payloadInboundProcessor = PayloadInboundProcessorFactory.getPayloadInboundProcessor(externalFactModelSSId);
		
		dssRequestDataItem.setCdsInput(((IPayloadInboundProcessor) payloadInboundProcessor).mappingInbound( 
				inputPayloadString, 
				evalTime, 
				timingData ));
		
		log.debug("II: " + dssRequestDataItem.getInteractionId() + " unmarshalling completed");
		
/**
 * get List of Rules to run from dss wrapper
 * 
 */
		timingData.setStartInferenceEngineLoopTime(new AtomicLong(System.nanoTime()));	
		
		List<KMEvaluationRequest>	listKMERequest			= new CopyOnWriteArrayList<KMEvaluationRequest>(evaluationRequest.getKmEvaluationRequest());
	
		for (KMEvaluationRequest oneKMEvaluationRequest : listKMERequest) {
			List<ItemIdentifier> listFactItemTypes = new CopyOnWriteArrayList<ItemIdentifier>();
			for (DataRequirementItemData oneDataRequirementItemData : listDRIData) {
				listFactItemTypes.add(oneDataRequirementItemData.getDriId());
			}
			validateRequiredDataProvided(listFactItemTypes, oneKMEvaluationRequest);
		}
			
		log.debug("II: " + dssRequestDataItem.getInteractionId() + " input data validated");

		return listKMERequest;
	}
	
	
	/**
	 * @param driData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getInputPayloadString( DataRequirementItemData driData ) throws UnsupportedEncodingException
	{
		SemanticPayload 	data					= driData.getData();

		List<byte[]> 		base64EncodedPayload	= data.getBase64EncodedPayload();

		// input is automatically decoded from Base64 data by presenting it to us...
		StringBuffer 		payloadStringBuffer		= new StringBuffer();
		for ( byte[] byteList : base64EncodedPayload ) {
			// assembles from chunks of encoded data separated by newline, which is fairly common					
			payloadStringBuffer.append(new String(byteList, "UTF-8"));
		}
		String 				payloadString 			= payloadStringBuffer.toString();
		return payloadString;
	}
	
}
			
		


