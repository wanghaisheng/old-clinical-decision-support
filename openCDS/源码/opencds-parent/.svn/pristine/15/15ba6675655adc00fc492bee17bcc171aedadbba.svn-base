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

package org.opencds.common.interfaces;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.EvaluationExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.InvalidTimeZoneOffsetExceptionFault;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.omg.dss.UnrecognizedLanguageExceptionFault;
import org.omg.dss.UnrecognizedScopedEntityExceptionFault;
import org.omg.dss.UnsupportedLanguageExceptionFault;
import org.opencds.common.structures.TimingData;


/**
 * 
 * @author David Shields
 * 
 * @version 1.0
 */
public abstract interface IPayloadInboundProcessor {

	/**
	 * returns JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> as Object, must be cast
	 * 		to JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> before use
	 * @param payloadString
	 * @param evalTime
	 * @param allFactLists
	 * @throws InvalidDriDataFormatExceptionFault
	 * @throws UnrecognizedLanguageExceptionFault
	 * @throws RequiredDataNotProvidedExceptionFault
	 * @throws UnsupportedLanguageExceptionFault
	 * @throws UnrecognizedScopedEntityExceptionFault
	 * @throws EvaluationExceptionFault
	 * @throws InvalidTimeZoneOffsetExceptionFault
	 * @throws DSSRuntimeExceptionFault
	 */
//	public abstract JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> mappingInbound ( 
	public abstract Object mappingInbound ( 
			String						payloadString, 
			java.util.Date 				evalTime,
			TimingData					timingData) 
		throws
			InvalidDriDataFormatExceptionFault, 
			UnrecognizedLanguageExceptionFault, 
			RequiredDataNotProvidedExceptionFault, 
			UnsupportedLanguageExceptionFault, 
			UnrecognizedScopedEntityExceptionFault, 
			EvaluationExceptionFault, 
			InvalidTimeZoneOffsetExceptionFault,
			DSSRuntimeExceptionFault;

}
