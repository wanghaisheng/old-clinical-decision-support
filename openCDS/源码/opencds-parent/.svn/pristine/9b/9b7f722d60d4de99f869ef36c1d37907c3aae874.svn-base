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

package org.opencds.vmr.v1_0.mappings.mappers;

import org.opencds.vmr.v1_0.internal.ClinicalStatementRelationship;
import org.opencds.vmr.v1_0.internal.datatypes.CD;
import org.opencds.vmr.v1_0.mappings.in.FactLists;
import org.opencds.vmr.v1_0.mappings.utilities.MappingUtility;

/**
 * Mapper classes provide mapping in both directions between the external schema structure of the vMR
 * 		and the internal javabeans used by the rules.
 * 
 * @author Daryl Chertcoff
 *
 */
public class ClinicalStatementRelationshipMapper {

	
	// private static Log logger = LogFactory.getLog(ClinicalStatementRelationshipMapper.class);
	
	/**
	 * Populate internal vMR object from nested external vMR object
	 * @param source external vMR object
	 * @param mu MappingUtility instance
	 * @return populated internal vMR structure; null if provided source parameter is null
	 */
	public static void pullIn(org.opencds.vmr.v1_0.schema.ClinicalStatementRelationship source, FactLists factLists) {
		
		// String _METHODNAME = "pullIn(): ";
		if (source == null)
			return;
		
		ClinicalStatementRelationship target = new ClinicalStatementRelationship();
		if ( source.getSourceId() != null ) target.setId(MappingUtility.getUUIDAsII().getValue());
		if ( source.getSourceId() != null ) target.setSourceId(MappingUtility.iI2FlatId(source.getSourceId()));
		if ( source.getTargetId() != null ) target.setTargetId(MappingUtility.iI2FlatId(source.getTargetId()));
		if ( source.getTargetRelationshipToSource() != null ) target.setTargetRelationshipToSource(MappingUtility.cD2CDInternal(source.getTargetRelationshipToSource()));
		factLists.internalClinicalStatementRelationshipList.add(target);
		
		return;
	}
	
	
	/**
	 * Populate internal vMR object from listed external vMR object
	 * @param sourceId
	 * @param targetId
	 * @param targetRelationshipToSource
	 * @param factLists
	 */
	public static void pullIn(String sourceId, String targetId, CD targetRelationshipToSource, FactLists factLists) {
		
		ClinicalStatementRelationship target = new ClinicalStatementRelationship();
		target.setId(MappingUtility.getUUIDAsII().getValue());
		target.setSourceId(sourceId);
		target.setTargetId(targetId);
		target.setTargetRelationshipToSource(targetRelationshipToSource);
		factLists.internalClinicalStatementRelationshipList.add(target);
		
		return;
	}
	
	
	/**
	 * Populate external vMR object from corresponding internal vMR object
	 * @param source internal vMR object
	 * @param mu MappingUtility instance
	 * @return populated external vMR object; null if provided source parameter is null
	 */
	public static org.opencds.vmr.v1_0.schema.ClinicalStatementRelationship pushOut(ClinicalStatementRelationship source) {
		
		// String _METHODNAME = "pushOut(): ";
		
		if (source == null)
			return null;

		org.opencds.vmr.v1_0.schema.ClinicalStatementRelationship target = new org.opencds.vmr.v1_0.schema.ClinicalStatementRelationship();	
		if ( source.getSourceId() != null ) target.setSourceId(MappingUtility.iIFlat2II(source.getSourceId()));
		if ( source.getTargetId() != null ) target.setTargetId(MappingUtility.iIFlat2II(source.getTargetId()));
		if ( source.getTargetRelationshipToSource() != null ) target.setTargetRelationshipToSource(MappingUtility.cDInternal2CD(source.getTargetRelationshipToSource()));
		return target;
	}
}
