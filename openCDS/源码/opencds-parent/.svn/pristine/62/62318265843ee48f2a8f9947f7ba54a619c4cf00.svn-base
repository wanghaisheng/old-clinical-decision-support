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

package org.opencds.vmr.v1_0.mappings.out;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

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
import org.opencds.common.interfaces.IOutboundPayloadProcessor;
import org.opencds.common.structures.DSSRequestKMItem;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;


/**
 * <p>mapper to go from flat form based on javaBeans in the project opencds-vmr
 * to external XML data described by vmr.schema in project opencds-vmr-jaxb-from-schema.
 * 
 * <p/>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: OpenCDS</p>
 *
 * @author David Shields
 * @version 1.0
 * @date 09-24-2010
 * 
 */

public class MarshalVMR2VMRSchemaPayload implements IOutboundPayloadProcessor
{
	private static 	Log 						log 		= LogFactory.getLog(MarshalVMR2VMRSchemaPayload.class);

	public synchronized String mappingOutbound(
			Map<String,List<?>>		results,
			DSSRequestKMItem		dssRequestKMItem
			)  
	throws 
		InvalidDriDataFormatExceptionFault, 
		UnrecognizedLanguageExceptionFault, 
		RequiredDataNotProvidedExceptionFault, 
		UnsupportedLanguageExceptionFault, 
		UnrecognizedScopedEntityExceptionFault, 
		EvaluationExceptionFault, 
		InvalidTimeZoneOffsetExceptionFault,
		DSSRuntimeExceptionFault
	{
		String										interactionId			= dssRequestKMItem.getDssRequestDataItem().getInteractionId();
		String										requestedKmId			= dssRequestKMItem.getRequestedKmId();
		String										streamResultString		= "";
		
		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " begin buildVMRSchemaResultSet");
        BuildVMRSchemaResultSet						buildVMRSchemaResultSet	= BuildVMRSchemaResultSet.getInstance();
        org.opencds.vmr.v1_0.schema.CDSOutput 		cdsXMLOutput 			= buildVMRSchemaResultSet.buildResultSet( 
				results, 
				dssRequestKMItem);

		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " finish buildVMRSchemaResultSet");
        dssRequestKMItem.getKmTimingData().setFinishBuildOutputTime(new AtomicLong(System.nanoTime()));
        
        JAXBElement<org.opencds.vmr.v1_0.schema.CDSOutput> jaxbCDSOutput;
        
        try {
    		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " begin factory.createCdsOutput");
    		
    		org.opencds.vmr.v1_0.schema.ObjectFactory 	factory 		= new org.opencds.vmr.v1_0.schema.ObjectFactory();
        	jaxbCDSOutput 	= factory.createCdsOutput( cdsXMLOutput );
        	
    		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " finish factory.createCdsOutput");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("RuntimeException in mappingOutbound ObjectFactory cdsOutput: " 
					+ e.getMessage() + ", vmrOutput=" + cdsXMLOutput.getVmrOutput().toString(), e);
		}
        
        if ((null == cdsXMLOutput) || (( null == cdsXMLOutput.getSimpleOutput() ) && ( null == cdsXMLOutput.getVmrOutput() )) ) {          
        	// show the tags, but empty payload
        	streamResultString					= "";
//FIXME    		throw new DSSRuntimeExceptionFault("Marshalled Rules Engine results are empty.");
    		
        }  else {
    		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " begin marshalling results to external VMR: " 
    				+ cdsXMLOutput.getVmrOutput().getTemplateId().toString());
        	
        	ByteArrayOutputStream 					output 			= new ByteArrayOutputStream();
        	StreamResult 							streamResult 	= new StreamResult();
        	streamResult.setOutputStream( output );
        	
	        try {
	        	JAXBContext 						jaxbContext 	= SimpleKnowledgeRepository.getRequiredJAXBContextForMarshallerClassCache(this.getClass().getName());

				Marshaller marshaller = SimpleKnowledgeRepository.getRequiredMarshallerInstanceForMarshallerClassCache(
						this.getClass().getName(), jaxbContext);
				
		        marshaller.marshal(jaxbCDSOutput, streamResult );
		        			
			} catch (JAXBException e) {
				e.printStackTrace();
				throw new EvaluationExceptionFault("JAXBException in mappingOutbound marshalling cdsOutput: " 
						+ e.getMessage(), e);
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw new DSSRuntimeExceptionFault("RuntimeException in mappingOutbound marshalling cdsOutput: " 
						+ e.getMessage(), e);
			}
			
    		log.debug("II: " + interactionId + " KMId: " + requestedKmId + " finished marshalling results to external VMR: " 
    				+ cdsXMLOutput.getVmrOutput().getTemplateId().toString());
    		
			OutputStream 		outputStream 	= streamResult.getOutputStream();			
			streamResultString 					= outputStream.toString();
			
       } 

		dssRequestKMItem.getKmTimingData().setFinishMarshalTime(new AtomicLong(System.nanoTime()));

        return streamResultString;
	}

}


			
		


