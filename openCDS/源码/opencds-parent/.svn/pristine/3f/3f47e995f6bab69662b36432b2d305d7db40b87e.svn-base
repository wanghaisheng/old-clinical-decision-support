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

package org.opencds.service.evaluate;

import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

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
import org.opencds.common.interfaces.IPayloadInboundProcessor;
import org.opencds.common.structures.TimingData;
import org.opencds.common.utilities.FileUtility;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;

/**
 * @author David Shields
 *
 */
public class UnmarshalVMRSchemaPayload2VMR implements IPayloadInboundProcessor, Cloneable {

	/* (non-Javadoc)
	 * @see org.opencds.drools.mappings.in.PayloadUnmarshaller#mappingInbound(java.lang.String, java.util.Date, java.util.Collection)
	 */
	
	private static 	Log 							log 	= LogFactory.getLog(UnmarshalVMRSchemaPayload2VMR.class);	
    private static UnmarshalVMRSchemaPayload2VMR 	instance;  //singleton instance
	private static FileUtility						fileU	= FileUtility.getInstance();

    public UnmarshalVMRSchemaPayload2VMR() throws DSSRuntimeExceptionFault
    {
    }

    public static synchronized UnmarshalVMRSchemaPayload2VMR getInstance() throws DSSRuntimeExceptionFault
    {
        if (instance == null)
        {
            instance = new UnmarshalVMRSchemaPayload2VMR();
        }
        return instance;
    }

	@Override
	public synchronized JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> mappingInbound(
			String 						payloadString, 
			Date 						evalTime,
			TimingData					timingData) 
		throws InvalidDriDataFormatExceptionFault,
			UnrecognizedLanguageExceptionFault,
			RequiredDataNotProvidedExceptionFault,
			UnsupportedLanguageExceptionFault,
			UnrecognizedScopedEntityExceptionFault, 
			EvaluationExceptionFault,
			InvalidTimeZoneOffsetExceptionFault, 
			DSSRuntimeExceptionFault {


		// this begins the guts for building fact lists for input data based on one particular fact model (opencds-vmr-1_0)			
		log.debug("starting UnmarshalVMRSchemaPayload2VMR");
		JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> 	cdsInput		= null;
		StreamSource 										payloadStream 	= new StreamSource( fileU.getInputStreamFromString( payloadString ));

		try {
			Unmarshaller unmarshaller = SimpleKnowledgeRepository.getRequiredUnmarshallerInstanceForUnmarshallerClassCache("org.opencds.vmr^VMR^1.0");
			cdsInput = unmarshaller.unmarshal( payloadStream, org.opencds.vmr.v1_0.schema.CDSInput.class );
			
		} catch (JAXBException e) {
			String jaxBError = e.getLinkedException().fillInStackTrace().getMessage();
			e.printStackTrace();
			throw new InvalidDriDataFormatExceptionFault(jaxBError + ", therefore unable to unmarshal input Semantic Payload xml string: " + payloadString );
		}
		
		log.debug("finished UnmarshalVMRSchemaPayload2VMR");
		
		return cdsInput;
	}
	
}
