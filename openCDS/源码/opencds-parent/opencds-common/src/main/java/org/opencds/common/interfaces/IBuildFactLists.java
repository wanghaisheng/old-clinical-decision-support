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

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

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
 * Interface to a class that will accept the payload from the DSS message as a base64 string, 
 * and convert it into a structured JaxB element for the VMR.  
 * The output of this class can then be further processed by another method  to produce 
 * the internal VMR structure for processing by a rules engine.
 * 
 * @author David Shields
 *
 * @version 1.0
 */
public interface IBuildFactLists {

	/**
	 * @param payloadJaxB
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
	public String buildFactLists ( 
			JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput>	payloadJaxB, 
			java.util.Date 				evalTime,
			Map<String, List<?>> 	allFactLists,
			TimingData					timingData
			) 
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
