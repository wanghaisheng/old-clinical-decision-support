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

package org.opencds.service.evaluate;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.opencds.common.interfaces.IPayloadInboundProcessor;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;


/**
 * 
 * @author David Shields
 * 
 * @version 2.0
 */
public class PayloadInboundProcessorFactory  
{
	/**
	 * 
	 * @param requiredPayloadInboundProcessorName
	 * @param externalFactModelSSId
	 * @return payloadInboundProcessor
	 * @throws DSSRuntimeExceptionFault 
	 * @throws RequiredDataNotProvidedExceptionFault 
	 * @throws InvalidDriDataFormatExceptionFault 
	 */
	public synchronized static IPayloadInboundProcessor getPayloadInboundProcessor (
			String externalFactModelSSId
			) throws DSSRuntimeExceptionFault, RequiredDataNotProvidedExceptionFault, InvalidDriDataFormatExceptionFault 
    {	
//    	SimpleKnowledgeRepository 				simpleKR 		= SimpleKnowledgeRepository.getInstance();
    	
		//simpleKR creates an instance if it doesn't already exist, and caches it.
    	IPayloadInboundProcessor payloadInboundProcessor = 
			(IPayloadInboundProcessor) SimpleKnowledgeRepository.getRequiredInboundPayloadProcessorInstanceCache(externalFactModelSSId);
		
		return payloadInboundProcessor;
	}
}	
