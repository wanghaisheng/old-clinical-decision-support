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

package org.opencds.dss.evaluate;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.EvaluationExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.InvalidTimeZoneOffsetExceptionFault;
import org.omg.dss.ObjectFactory;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.omg.dss.UnrecognizedLanguageExceptionFault;
import org.omg.dss.UnrecognizedScopedEntityExceptionFault;
import org.omg.dss.UnsupportedLanguageExceptionFault;
import org.omg.dss.common.EntityIdentifier;
import org.omg.dss.common.ItemIdentifier;
import org.omg.dss.common.SemanticPayload;
import org.omg.dss.evaluation.Evaluate;
import org.omg.dss.evaluation.EvaluateAtSpecifiedTime;
import org.omg.dss.evaluation.EvaluateAtSpecifiedTimeResponse;
import org.omg.dss.evaluation.EvaluateIteratively;
import org.omg.dss.evaluation.EvaluateIterativelyAtSpecifiedTime;
import org.omg.dss.evaluation.EvaluateIterativelyAtSpecifiedTimeResponse;
import org.omg.dss.evaluation.EvaluateIterativelyResponse;
import org.omg.dss.evaluation.EvaluateResponse;
import org.omg.dss.evaluation.requestresponse.EvaluationResponse;
import org.omg.dss.evaluation.requestresponse.FinalKMEvaluationResponse;
import org.omg.dss.evaluation.requestresponse.KMEvaluationRequest;
import org.omg.dss.evaluation.requestresponse.KMEvaluationResultData;
import org.opencds.common.config.OpencdsConfigurator;
import org.opencds.common.structures.DSSRequestDataItem;
import org.opencds.common.structures.DSSRequestKMItem;
import org.opencds.common.structures.TimingData;
import org.opencds.common.structures.TimingDataKM;
import org.opencds.common.utilities.DSSUtility;
import org.opencds.common.utilities.DateUtility;
import org.opencds.common.utilities.XMLDateUtility;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.opencds.service.evaluate.OpenCDSMain;

/**
 * EvaluationImpl is the primary endpoint of the SOAP web service.
 * 
 * It gets a list of the required KMId to process from OpenCDSMain and loops through the following:
 * 		It gets a new instance of requiredEvaluationEngine for each KMId from EvaluationFactory and executes it 
 * 		It gets the evaluationResponse from DSSEvaluationAdapterShell, and stacks it into the FinalEvaluationResponse 
 * Finally it returns the FinalEvaluationResponse to the requestor.	
 * 
 * @author Andrew Iskander, mod by David Shields
 * 
 * @version 1.0
 */
@WebService(endpointInterface = "org.omg.dss.Evaluation")
@BindingType(value = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
@XmlSeeAlso({
    ObjectFactory.class
})

public class EvaluationImpl implements org.omg.dss.Evaluation {

	private static Log log = LogFactory.getLog(EvaluationImpl.class);
	
//	Date rightNow = new Date();
    
	/**
	 * 
	 * @param date as long
	 * @return XMLGregorianCalendar
	 */
    private static XMLGregorianCalendar long2Gregorian(long date) {
    	DatatypeFactory dataTypeFactory;
    	try {
    	dataTypeFactory = DatatypeFactory.newInstance();
    	} catch (DatatypeConfigurationException e) {
    	throw new RuntimeException(e);
    	}
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTimeInMillis(date);
    	return dataTypeFactory.newXMLGregorianCalendar(gc);
    	}

    /**
     * 
     * @param date
     * @return XMLGregorianCalendar
     */
    private static XMLGregorianCalendar date2Gregorian(Date date) {
    	return long2Gregorian(date.getTime());
    	} 

//    XMLGregorianCalendar rightNowTime = date2Gregorian(rightNow);
    XMLGregorianCalendar rightNowTime = date2Gregorian(new Date());
    
   

/* (non-Javadoc)
 * @see org.omg.dss.Evaluation#evaluate(org.omg.dss.evaluation.Evaluate)
 */
@Override  
	public EvaluateResponse evaluate(Evaluate parameters)
		throws InvalidDriDataFormatExceptionFault,
			UnrecognizedLanguageExceptionFault,
			RequiredDataNotProvidedExceptionFault,
			UnsupportedLanguageExceptionFault,
			UnrecognizedScopedEntityExceptionFault, 
			EvaluationExceptionFault,
			InvalidTimeZoneOffsetExceptionFault, 
			DSSRuntimeExceptionFault 
	{
		TimingData timingData = new TimingData();
		Date evalTime = new Date();
		log.debug("II: " + parameters.getInteractionId().getInteractionId().toString() 
				+ "EvaluationImpl.evaluate started asOf " + DateUtility.getInstance().getDateAsString(evalTime, "yyyyMMdd HHmmss").toString());
		if (!SimpleKnowledgeRepository.isKnowledgeRepositoryInitialized()) {
			SimpleKnowledgeRepository.setFullPathToKRData(OpencdsConfigurator.getInstance().getKrFullPath());
		}	
		
		DSSRequestDataItem dssRequestDataItem = new DSSRequestDataItem();
		dssRequestDataItem.setInteractionId(parameters.getInteractionId().getInteractionId().toString());
		
		EvaluateResponse evaluateResponse = new EvaluateResponse();
		evaluateResponse.setEvaluationResponse(new EvaluationResponse());
		
		List<KMEvaluationRequest> listKMERequest = OpenCDSMain.decodeInput(
				parameters.getEvaluationRequest(),
				//XMLDateUtility.xmlGregorian2Date(rightNowTime), 
				evalTime,
				dssRequestDataItem,
				timingData);
		
		for (KMEvaluationRequest oneKMEvaluationRequest : listKMERequest)
		{
			DSSRequestKMItem oneRequest = new DSSRequestKMItem();
			oneRequest.setDssRequestDataItem(dssRequestDataItem);
			oneRequest.setRequestedKmId(DSSUtility.makeEIString(oneKMEvaluationRequest.getKmId()));
			oneRequest.setKmTimingData(new TimingDataKM() );
			initLogTimingsKM(oneRequest.getKmTimingData());
			
			Evaluater evaluater = EvaluationFactory.createEvaluater(oneRequest);
			String structuredDroolsResult = evaluater.getOneResponse(oneRequest);
			
			FinalKMEvaluationResponse	fkmResponse				= new FinalKMEvaluationResponse();
	        SemanticPayload 			semanticPayload			= new SemanticPayload();
	        ItemIdentifier 				itemId 					= new ItemIdentifier();
	        KMEvaluationResultData 		kmerData				= new KMEvaluationResultData();
	        List<byte[]> 				base64EncodedPayload	= new ArrayList<byte[]>();
	        
	        try
			{		
				log.debug("KMId: " + oneRequest.getRequestedKmId().toString() + " EvaluationImpl.evaluate starting one KM");
				log.trace("" + structuredDroolsResult);
				base64EncodedPayload 	= semanticPayload.getBase64EncodedPayload();
				base64EncodedPayload.add( structuredDroolsResult.getBytes() ); 
		        
				itemId.setItemId( oneRequest.getDssRequestDataItem().getInputItemName() + ".EvaluationResult" );	
				itemId.setContainingEntityId(DSSUtility.makeEI(oneRequest.getDssRequestDataItem().getInputContainingEntityId()));
		        
		        String						kmId					= oneRequest.getRequestedKmId();
				String 						ssIdString				= SimpleKnowledgeRepository.getRequiredSSIdForKMId(kmId);
		        EntityIdentifier 			ssId 					= DSSUtility.makeEI(ssIdString);	        
		        semanticPayload.setInformationModelSSId(ssId);
		        
		        kmerData.setData(semanticPayload);
		        kmerData.setEvaluationResultId(itemId);		
		        
		        fkmResponse.setKmId(DSSUtility.makeEI(oneRequest.getRequestedKmId()));
		        fkmResponse.getKmEvaluationResultData().add( kmerData );
		        
		        oneRequest.getKmTimingData().setFinishInferenceEngineAdapterTime(new AtomicLong(System.nanoTime()));
				log.debug("KMId: " + oneRequest.getRequestedKmId().toString() + " EvaluationImpl.evaluate completed one KM");
		        
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw new DSSRuntimeExceptionFault("RuntimeException in EvaluationImpl.evaluate: " + structuredDroolsResult + e.getMessage());
			}
			evaluateResponse.getEvaluationResponse().getFinalKMEvaluationResponse().add(fkmResponse);
			
			logTimingsKM(oneRequest);
			oneRequest 				= null;
			structuredDroolsResult 	= null;
			evaluater 				= null;			
			fkmResponse				= null;
	        semanticPayload			= null;
	        itemId 					= null;
	        kmerData				= null;
	        base64EncodedPayload	= null;
		}
		
		timingData.setFinishInferenceEngineLoopTime(new AtomicLong(System.nanoTime()));
		timingData.setFinishMessageRequestTime(new AtomicLong(System.nanoTime()));
		log.info("II: " + parameters.getInteractionId().getInteractionId().toString() 
				+ " " + logTimings(timingData) + " EvaluationImpl.evaluate completed");	
		
		timingData = null;
		dssRequestDataItem = null;
		listKMERequest.clear();
		listKMERequest = null;
		
		return evaluateResponse;
	}
	


@Override 
	public EvaluateAtSpecifiedTimeResponse evaluateAtSpecifiedTime(EvaluateAtSpecifiedTime parameters)
			throws InvalidDriDataFormatExceptionFault,
			UnrecognizedLanguageExceptionFault,
			RequiredDataNotProvidedExceptionFault,
			UnsupportedLanguageExceptionFault,
			UnrecognizedScopedEntityExceptionFault, EvaluationExceptionFault,
			InvalidTimeZoneOffsetExceptionFault,
			DSSRuntimeExceptionFault 
	{		
		TimingData timingData = new TimingData();
		Date evalTime = XMLDateUtility.xmlGregorian2Date(parameters.getSpecifiedTime());
		log.debug("II: " + parameters.getInteractionId().getInteractionId().toString() 
				+ " EvaluationImpl.evaluateAtSpecifiedTime started asOf " + DateUtility.getInstance().getDateAsString(evalTime, "yyyyMMdd HHmmss").toString());
		if (!SimpleKnowledgeRepository.isKnowledgeRepositoryInitialized()) {
			SimpleKnowledgeRepository.setFullPathToKRData(OpencdsConfigurator.getInstance().getKrFullPath());
		}
			
		DSSRequestDataItem dssRequestDataItem = new DSSRequestDataItem();
		dssRequestDataItem.setInteractionId(parameters.getInteractionId().getInteractionId().toString());
		
		EvaluateAtSpecifiedTimeResponse evalAtSpecTimeResponse = new EvaluateAtSpecifiedTimeResponse();
		evalAtSpecTimeResponse.setEvaluationResponse(new EvaluationResponse());
		
		List<KMEvaluationRequest> listKMERequest = OpenCDSMain.decodeInput(
				parameters.getEvaluationRequest(),
				//XMLDateUtility.xmlGregorian2Date(parameters.getSpecifiedTime()), 
				evalTime,
				dssRequestDataItem,
				timingData);
		
		for (KMEvaluationRequest oneKMEvaluationRequest : listKMERequest)
		{
			DSSRequestKMItem oneRequest = new DSSRequestKMItem();
			oneRequest.setDssRequestDataItem(dssRequestDataItem);
			oneRequest.setRequestedKmId(DSSUtility.makeEIString(oneKMEvaluationRequest.getKmId()));
			oneRequest.setKmTimingData(new TimingDataKM() );
			initLogTimingsKM(oneRequest.getKmTimingData());
			
			Evaluater evaluater = EvaluationFactory.createEvaluater(oneRequest);
			String structuredDroolsResult = evaluater.getOneResponse(oneRequest);
			
			FinalKMEvaluationResponse	fkmResponse				= new FinalKMEvaluationResponse();
	        SemanticPayload 			semanticPayload			= new SemanticPayload();
	        ItemIdentifier 				itemId 					= new ItemIdentifier();
	        KMEvaluationResultData 		kmerData				= new KMEvaluationResultData();
	        List<byte[]> 				base64EncodedPayload	= new ArrayList<byte[]>();
	        
	        try
			{		
				log.debug("KMId: " + oneRequest.getRequestedKmId().toString() + " EvaluationImpl.evaluateAtSpecifiedTime starting one KM");
				log.trace("" + structuredDroolsResult);
				base64EncodedPayload 	= semanticPayload.getBase64EncodedPayload();
				base64EncodedPayload.add( structuredDroolsResult.getBytes() ); 
		        
				itemId.setItemId( oneRequest.getDssRequestDataItem().getInputItemName() + ".EvaluationResult" );	
				itemId.setContainingEntityId(DSSUtility.makeEI(oneRequest.getDssRequestDataItem().getInputContainingEntityId()));
		        
		        String						kmId					= oneRequest.getRequestedKmId();
				String 						ssIdString				= SimpleKnowledgeRepository.getRequiredSSIdForKMId(kmId);
		        EntityIdentifier 			ssId 					= DSSUtility.makeEI(ssIdString);	        
		        semanticPayload.setInformationModelSSId(ssId);
		        
		        kmerData.setData(semanticPayload);
		        kmerData.setEvaluationResultId(itemId);		
		        
		        fkmResponse.setKmId(DSSUtility.makeEI(oneRequest.getRequestedKmId()));
		        fkmResponse.getKmEvaluationResultData().add( kmerData );
		        
		        oneRequest.getKmTimingData().setFinishInferenceEngineAdapterTime(new AtomicLong(System.nanoTime()));
				log.debug("KMId: " + oneRequest.getRequestedKmId().toString() + " EvaluationImpl.evaluateAtSpecifiedTime completed one KM");
		        
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw new DSSRuntimeExceptionFault("RuntimeException in EvaluationImpl.evaluateAtSpecifiedTime: " + structuredDroolsResult + e.getMessage());
			}
			
			evalAtSpecTimeResponse.getEvaluationResponse().getFinalKMEvaluationResponse().add(fkmResponse);
			evaluater = null;
			
			logTimingsKM(oneRequest);
			oneRequest 				= null;
			structuredDroolsResult 	= null;
			evaluater 				= null;			
			fkmResponse				= null;
	        semanticPayload			= null;
	        itemId 					= null;
	        kmerData				= null;
	        base64EncodedPayload	= null;
		}
	
		timingData.setFinishInferenceEngineLoopTime(new AtomicLong(System.nanoTime()));
		timingData.setFinishMessageRequestTime(new AtomicLong(System.nanoTime()));
		log.info("II: " + parameters.getInteractionId().getInteractionId().toString() 
				+ " " + logTimings(timingData) + " EvaluationImpl.evaluateAtSpecifiedTime completed");	
		
		timingData = null;
		dssRequestDataItem = null;
		listKMERequest.clear();
		listKMERequest = null;
		
		return evalAtSpecTimeResponse;
	}


@Override  
	public EvaluateIterativelyResponse evaluateIteratively(
			EvaluateIteratively parameters)
		throws InvalidDriDataFormatExceptionFault,
			UnrecognizedLanguageExceptionFault,
			RequiredDataNotProvidedExceptionFault,
			UnsupportedLanguageExceptionFault,
			UnrecognizedScopedEntityExceptionFault, 
			EvaluationExceptionFault,
			InvalidTimeZoneOffsetExceptionFault,
			DSSRuntimeExceptionFault 
	{
		log.debug("started EvaluationImpl.evaluateIteratively");
		IterativeEvaluater evaluater = EvaluationFactory.createIterativeEvaluater(parameters.getIterativeEvaluationRequest());
		EvaluateIterativelyResponse er = new EvaluateIterativelyResponse();
		er.setIterativeEvaluationResponse( evaluater.getResponse(parameters.getInteractionId(), rightNowTime, parameters.getIterativeEvaluationRequest()) );
		log.debug("completed EvaluationImpl.evaluateIteratively");
		return er;
	}

@Override 
	public EvaluateIterativelyAtSpecifiedTimeResponse evaluateIterativelyAtSpecifiedTime(
			EvaluateIterativelyAtSpecifiedTime parameters)
		throws InvalidDriDataFormatExceptionFault,
			UnrecognizedLanguageExceptionFault,
			RequiredDataNotProvidedExceptionFault,
			UnsupportedLanguageExceptionFault,
			UnrecognizedScopedEntityExceptionFault, 
			EvaluationExceptionFault,
			InvalidTimeZoneOffsetExceptionFault,
			DSSRuntimeExceptionFault 
	{
		log.debug("started EvaluationImpl.evaluateIterativelyAtSpecifiedTime");
		IterativeEvaluater evaluater = EvaluationFactory.createIterativeEvaluater(parameters.getIterativeEvaluationRequest());
		EvaluateIterativelyAtSpecifiedTimeResponse er = new EvaluateIterativelyAtSpecifiedTimeResponse();
		er.setIterativeEvaluationResponse( evaluater.getResponse(parameters.getInteractionId(), parameters.getSpecifiedTime(), parameters.getIterativeEvaluationRequest()) );
		log.debug("completed EvaluationImpl.evaluateIterativelyAtSpecifiedTime");
		return er;
	}


/**
 * initLogTimingsKM -- clears the timing values
 * 
 * @param timingDataKM
 */
private synchronized static void initLogTimingsKM(TimingDataKM timingDataKM)
	{
		timingDataKM.setStartInferenceEngineAdapterTime(new AtomicLong(System.nanoTime()));
		timingDataKM.setFinishBuildFactListsTime(new AtomicLong(0));
		timingDataKM.setFinishBuildConceptListsTime(new AtomicLong(0));
		timingDataKM.setFinishInsertKnowledgeTime(new AtomicLong(0));
		timingDataKM.setFinishStartKnowledgeSessionTime(new AtomicLong(0));
		timingDataKM.setFinishLoadGlobalsTime(new AtomicLong(0));
		timingDataKM.setFinishLoadFactListsTime(new AtomicLong(0));
		timingDataKM.setStartInferenceEngine(new AtomicLong(0));
		timingDataKM.setFinishInferenceEngine(new AtomicLong(0));
		timingDataKM.setFinishBuildOutputTime(new AtomicLong(0));
		timingDataKM.setFinishMarshalTime(new AtomicLong(0));
		timingDataKM.setFinishInferenceEngineAdapterTime(new AtomicLong(0));
	}


/**
 * logTimingsKM -- logs the timings for one KM, and returns a summary to the caller, 
 * 				which can be logged at a different level than the detailed logs
 * 
 * @param timingDataKM
 * @return String
 */
private synchronized static String logTimingsKM(DSSRequestKMItem oneRequest)
	{
		TimingDataKM timingDataKM	= oneRequest.getKmTimingData();
		long buildFactsTime			= timingDataKM.getFinishBuildFactListsTime().get() 			- timingDataKM.getStartInferenceEngineAdapterTime().get();
		long buildConceptsTime		= timingDataKM.getFinishBuildConceptListsTime().get()		- timingDataKM.getFinishBuildFactListsTime().get();
		long knowledgeInsertTime	= timingDataKM.getFinishInsertKnowledgeTime().get() 		- timingDataKM.getFinishBuildConceptListsTime().get();
		long startSessionTime		= timingDataKM.getFinishStartKnowledgeSessionTime().get() 	- timingDataKM.getFinishInsertKnowledgeTime().get();
		long loadGlobalsTime		= timingDataKM.getFinishLoadGlobalsTime().get() 			- timingDataKM.getFinishStartKnowledgeSessionTime().get();
		long loadFactListsTime		= timingDataKM.getFinishLoadFactListsTime().get() 			- timingDataKM.getFinishLoadGlobalsTime().get();
		long startProcessTime		= timingDataKM.getStartInferenceEngine().get() 				- timingDataKM.getFinishLoadFactListsTime().get();
		long evaluationTime			= timingDataKM.getFinishInferenceEngine().get() 			- timingDataKM.getStartInferenceEngine().get();
		long buildOutputTime		= timingDataKM.getFinishBuildOutputTime().get() 			- timingDataKM.getFinishInferenceEngine().get();
		long marshalTime			= timingDataKM.getFinishMarshalTime().get() 				- timingDataKM.getFinishBuildOutputTime().get();
		long finishKMTime			= timingDataKM.getFinishInferenceEngineAdapterTime().get() 	- timingDataKM.getFinishMarshalTime().get();
		
		long evaluationAdapterTime	= timingDataKM.getFinishInferenceEngineAdapterTime().get()	- timingDataKM.getStartInferenceEngineAdapterTime().get();
		long totalPrepTime			= timingDataKM.getStartInferenceEngine().get() 				- timingDataKM.getStartInferenceEngineAdapterTime().get();
		long totalOutputTime		= timingDataKM.getFinishInferenceEngineAdapterTime().get() 	- timingDataKM.getFinishInferenceEngine().get();
		
	    String prepTimings = "km prep: " 	+ totalPrepTime / 1000000   + " ms." 
				  + " + facts=" + buildFactsTime / 1000000 
				  + " + cncpts=" + buildConceptsTime / 1000000 
				  + " + (knowledgeInsert=" + knowledgeInsertTime / 1000000  
				  + " + startSession=" + startSessionTime / 1000000 
				  + " + loadGlobals=" + loadGlobalsTime / 1000000 
				  + " + loadFactLists=" + loadFactListsTime / 1000000 
				  + " + startProcess=" + startProcessTime / 1000000
				  + ")"; 
		String droolsTimings = "km eval: " + evaluationTime / 1000000 + " ms.";
		String postTimings = "km post eval: " 	+ totalOutputTime / 1000000 + " ms. = ("
				  + "output=" + buildOutputTime / 1000000 
				  + " + mrshl=" + marshalTime / 1000000 
				  + " + KMPost=" + finishKMTime / 1000000 
				  + ")"; 
		String kmTimings = " KMId: " + oneRequest.getRequestedKmId()
						+ " priProc: " + oneRequest.getRequestedKmPrimaryProcessName()
						+ " eval total: " + evaluationAdapterTime / 1000000 + " ms.";
		log.debug(prepTimings);
		log.debug(droolsTimings);
		log.debug(postTimings);
		return kmTimings;
	}


/**
 * logTimings -- logs the detailed timings about one message, and passes a summary
 * 				back which can be logged at a different level
 * 
 * @param timingData
 * @return String summary timings
 */
private synchronized static String logTimings(TimingData timingData)
	{
			
		long startKMTime		= timingData.getStartUnmarshalTime().get() 			- timingData.getStartMessageRequestTime().get();
		long unmarshalTime		= timingData.getStartInferenceEngineLoopTime().get()- timingData.getStartUnmarshalTime().get();        
		long totalPrepTime		= timingData.getStartInferenceEngineLoopTime().get()	- timingData.getStartMessageRequestTime().get();
		long evaluationTime		= timingData.getFinishInferenceEngineLoopTime().get()	- timingData.getStartInferenceEngineLoopTime().get();
		long totalOutputTime	= timingData.getFinishMessageRequestTime().get() 		- timingData.getFinishInferenceEngineLoopTime().get();
	
	    String prepTimings = "msg prep: " 	+ totalPrepTime / 1000000   + " ms. = (KMPrep=" + startKMTime / 1000000 
	    												  + " + unMrshl=" + unmarshalTime / 1000000 
	    												  + ") "; 
	    String droolsTimings = "msg eval: " 	+ evaluationTime / 1000000 + " ms. ";
	    String postTimings = "msg out: " 	+ totalOutputTime / 1000000 + " ms. "; 
		log.debug(prepTimings);
		log.debug(droolsTimings);
		log.debug(postTimings);
	    
		long totalMessageTime		= timingData.getFinishMessageRequestTime().get() 		- timingData.getStartMessageRequestTime().get();
	    
	    String totalTiming = ", msg total time: " + totalMessageTime / 1000000 + " ms. (" + prepTimings + droolsTimings + postTimings + ")";
	    
	    return totalTiming;
	}
}
