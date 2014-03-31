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

package org.opencds.terminology;

import java.util.HashMap;
import java.util.HashSet;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.structures.ObjectPair;
import org.opencds.common.terminology.CD;

/**
 * Base abstract class for OpenCDS terminology management.
 * 
 * Will be converted into a proper Web service in the future.
 * 
 * @author kawam001
 *
 */
public abstract class TerminologyManager
{
	/**
	 * Returns a HashSet containing an ObjectPair in which object1 is an OpenCDSConcept representing a matched 
	 * OpenCDS concept and object2 is a CD containing the determinationMethod used to 
	 * determine that the code matches the identified concept.
	 * 
	 * Returns null if no matches found.
	 * @param cd 	codeSystem and code must be non-null if a match attempt is to be made
	 * @return
	 */
	public abstract HashSet<ObjectPair> getMatchingOpenCDSConcepts(CD cd);
	
	/**
	 * This method is to return a single OpenCDS Concept for a coordinated pair of concepts, such as BodySite and Laterality
	 * 
	 * Returns a HashSet containing an ObjectPair in which object1 is an OpenCDSConcept representing a matched 
	 * OpenCDS concept and object2 is a CD containing the determinationMethod used to 
	 * determine that the code matches the identified concept.
	 * 
	 * Returns null if no matches found.
	 * @param cdPair 	both sets of codeSystem and code must be non-null if a match attempt is to be made
	 * @return
	 */
	public abstract HashSet<ObjectPair> getMatchingOpenCDSConcepts(ObjectPair cdPair);
	
	/**
	 * Returns a HashMap containing the OpenCDSConcepts supported by the terminology service.
	 * 
	 * Key = supported OpenCDSConcept type enumerated in org.opencds.terminology.OpenCDSConceptTypes.
	 * Target = HashSet of supported OpenCDSConcepts for type.
	 *  
	 * @return
	 * @throws DSSRuntimeExceptionFault 
	 */
	public abstract HashMap<String, HashSet<CD>> getSupportedOpenCDSConcepts() throws DSSRuntimeExceptionFault;
	

	/**
	 * Returns CDs that are equivalent matches to the specified OpenCDSConcept. 
	 * 
	 * @param openCDSConcept
	 * @return
	 */
	public abstract HashSet<CD> getMatchingCodes(CD openCDSConcept);
	
	
	/**
	 * Returns a String containing the OpenCDSConceptType
	 * key = ConceptType name; 
	 * target = ConceptType value.
	 * 
	 * @param conceptTypeName
	 * @return
	 */
	public abstract String getConceptTypeValue(String conceptTypeName);
	

	/**
	 * Returns a String containing the OpenCDS CodeSystem corresponding to the submitted OID
	 * key = CodeSystem OID; 
	 * target = CodeSystem Display name.
	 * 
	 * @param codeSystemOID
	 * @return
	 */
	public abstract String getCodeSystemFromOID(String codeSystemOID);
}