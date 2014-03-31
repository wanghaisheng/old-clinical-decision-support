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

package org.opencds.vmr.v1_0.mappings.in;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.structures.ObjectPair;
import org.opencds.common.terminology.CodeSystems;
import org.opencds.common.utilities.MiscUtility;
import org.opencds.terminology.SimpleTerminologyManager;
import org.opencds.vmr.v1_0.internal.AdverseEvent;
import org.opencds.vmr.v1_0.internal.AppointmentProposal;
import org.opencds.vmr.v1_0.internal.AppointmentRequest;
import org.opencds.vmr.v1_0.internal.BodySite;
import org.opencds.vmr.v1_0.internal.ClinicalStatementRelationship;
import org.opencds.vmr.v1_0.internal.Demographics;
import org.opencds.vmr.v1_0.internal.DeniedAdverseEvent;
import org.opencds.vmr.v1_0.internal.EncounterEvent;
import org.opencds.vmr.v1_0.internal.Entity;
import org.opencds.vmr.v1_0.internal.EntityRelationship;
import org.opencds.vmr.v1_0.internal.EvaluatedPerson;
import org.opencds.vmr.v1_0.internal.EvaluatedPersonRelationship;
import org.opencds.vmr.v1_0.internal.Facility;
import org.opencds.vmr.v1_0.internal.Goal;
import org.opencds.vmr.v1_0.internal.GoalProposal;
import org.opencds.vmr.v1_0.internal.MissedAppointment;
import org.opencds.vmr.v1_0.internal.ObservationOrder;
import org.opencds.vmr.v1_0.internal.ObservationProposal;
import org.opencds.vmr.v1_0.internal.ObservationResult;
import org.opencds.vmr.v1_0.internal.Organization;
import org.opencds.vmr.v1_0.internal.Person;
import org.opencds.vmr.v1_0.internal.Problem;
import org.opencds.vmr.v1_0.internal.ProcedureEvent;
import org.opencds.vmr.v1_0.internal.ProcedureOrder;
import org.opencds.vmr.v1_0.internal.ProcedureProposal;
import org.opencds.vmr.v1_0.internal.ScheduledAppointment;
import org.opencds.vmr.v1_0.internal.ScheduledProcedure;
import org.opencds.vmr.v1_0.internal.Specimen;
import org.opencds.vmr.v1_0.internal.SubstanceAdministrationEvent;
import org.opencds.vmr.v1_0.internal.SubstanceAdministrationOrder;
import org.opencds.vmr.v1_0.internal.SubstanceAdministrationProposal;
import org.opencds.vmr.v1_0.internal.SubstanceDispensationEvent;
import org.opencds.vmr.v1_0.internal.SupplyEvent;
import org.opencds.vmr.v1_0.internal.SupplyOrder;
import org.opencds.vmr.v1_0.internal.SupplyProposal;
import org.opencds.vmr.v1_0.internal.UnconductedObservation;
import org.opencds.vmr.v1_0.internal.UndeliveredProcedure;
import org.opencds.vmr.v1_0.internal.UndeliveredSubstanceAdministration;
import org.opencds.vmr.v1_0.internal.UndeliveredSupply;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventAffectedBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventAffectedBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventAgentConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventSeverityConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventStatusConcept;
import org.opencds.vmr.v1_0.internal.concepts.BrandedMedicationConcept;
import org.opencds.vmr.v1_0.internal.concepts.ClinicalStatementRelationshipConcept;
import org.opencds.vmr.v1_0.internal.concepts.ClinicalStatementTemplateConcept;
import org.opencds.vmr.v1_0.internal.concepts.DataSourceTypeConcept;
import org.opencds.vmr.v1_0.internal.concepts.DoseTypeConcept;
import org.opencds.vmr.v1_0.internal.concepts.DosingSigConcept;
import org.opencds.vmr.v1_0.internal.concepts.EncounterCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.EncounterTypeConcept;
import org.opencds.vmr.v1_0.internal.concepts.EntityRelationshipConcept;
import org.opencds.vmr.v1_0.internal.concepts.EntityTemplateConcept;
import org.opencds.vmr.v1_0.internal.concepts.EntityTypeConcept;
import org.opencds.vmr.v1_0.internal.concepts.EthnicityConcept;
import org.opencds.vmr.v1_0.internal.concepts.EvaluatedPersonRelationshipConcept;
import org.opencds.vmr.v1_0.internal.concepts.GenderConcept;
import org.opencds.vmr.v1_0.internal.concepts.GenericMedicationConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalCodedValueConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalStatusConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalTargetBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalTargetBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ImmunizationConcept;
import org.opencds.vmr.v1_0.internal.concepts.InformationAttestationTypeConcept;
import org.opencds.vmr.v1_0.internal.concepts.ManufacturerConcept;
import org.opencds.vmr.v1_0.internal.concepts.MedicationClassConcept;
import org.opencds.vmr.v1_0.internal.concepts.MedicationConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationCodedValueConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationFocusConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationInterpretationConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationMethodConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationTargetBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationTargetBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ObservationUnconductedReasonConcept;
import org.opencds.vmr.v1_0.internal.concepts.PreferredLanguageConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProblemAffectedBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProblemAffectedBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProblemConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProblemImportanceConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProblemSeverityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProblemStatusConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureApproachBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureApproachBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureTargetBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureTargetBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.RaceConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceAdministrationApproachBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceAdministrationApproachBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceAdministrationCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceAdministrationGeneralPurposeConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceAdministrationTargetBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceAdministrationTargetBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceDeliveryMethodConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceDeliveryRouteConcept;
import org.opencds.vmr.v1_0.internal.concepts.SubstanceFormConcept;
import org.opencds.vmr.v1_0.internal.concepts.SupplyConcept;
import org.opencds.vmr.v1_0.internal.concepts.SupplyCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.SupplyTargetBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.SupplyTargetBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.UndeliveredProcedureReasonConcept;
import org.opencds.vmr.v1_0.internal.concepts.UndeliveredSubstanceAdministrationReasonConcept;
import org.opencds.vmr.v1_0.internal.concepts.VmrOpenCdsConcept;
import org.opencds.vmr.v1_0.internal.datatypes.CD;

/**
 * Build the Collection of ConceptLists for the populated fact lists for input to Drools, moved to this class from BuildCDSInputFactLists.java
 * 
 * @author David Shields
 *
 */
public class BuildOpenCDSConceptLists implements Cloneable 
{

//	private SimpleTerminologyManager myTerminologyManager;
	private static BuildOpenCDSConceptLists instance;  //singleton instance
	private static 	Log 				log 					= LogFactory.getLog(BuildOpenCDSConceptLists.class);

    private BuildOpenCDSConceptLists() throws DSSRuntimeExceptionFault
    {
//    	myTerminologyManager = SimpleTerminologyManager.getInstance();
    }

    public static synchronized BuildOpenCDSConceptLists getInstance() throws DSSRuntimeExceptionFault
    {
        if (instance == null)
        {
            instance = new BuildOpenCDSConceptLists();
        }
        return instance;
    }
 
    public static synchronized void buildConceptLists(FactLists factLists, ConceptLists conceptLists, Map<String, List<?>> allFactLists) 
    	throws DSSRuntimeExceptionFault
    {
    	SimpleTerminologyManager myTerminologyManager = SimpleTerminologyManager.getInstance();
    	
//FIXME - note that references to bodySite-laterality that repeat make use of a new structure for post-coordinated concepts.
//    			This is currently not fully tested, and may be missing some code to correctly populate the combined concept 
//    			mappings from the terminology systems...

//FIXME - note that BodySite.bodySiteCode was changed to make it optional.  This may have ramifications in concepts below that use
//    			bodySiteCode, and they need to be reviewed. They have been reviewed, and they all assume that laterality is only
//    			present when bodySiteCode is present, which is no longer true.  They all need to be changed to allow for a
//    			concept that only has a laterality value populated, and the current code below never looks at laterality if
//    			there is no bodySiteCode.
    	
		//evaluated person
		for (EvaluatedPerson evaluatedPerson: factLists.internalEvaluatedPersonList)
		{
			log.debug("getting concepts for evaluatedPerson=" + evaluatedPerson.getId());
			
			//templateId
			if (evaluatedPerson.getTemplateId() != null){
				for (String oneTemplateId : evaluatedPerson.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(evaluatedPerson.getId(), new EntityTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//entity type
			if (evaluatedPerson.getEntityType() != null){
				HashSet<ObjectPair> matchingOpenCdsConceptsEntityType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						evaluatedPerson.getEntityType().getCodeSystem(), evaluatedPerson.getEntityType().getCode()));
				if (matchingOpenCdsConceptsEntityType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEntityType)
					{
						populateVmrOpenCdsConcept(evaluatedPerson.getId(), new EntityTypeConcept(), conceptLists.entityTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}			
			
			// demographics
			if (evaluatedPerson.getDemographics() != null) {
				Demographics demographics = evaluatedPerson.getDemographics();
				// gender
				if (demographics.getGender() != null) {
					CD gender = demographics.getGender();
					org.opencds.common.terminology.CD genderConcept = new org.opencds.common.terminology.CD(gender.getCodeSystem(), gender.getCode());
					HashSet<ObjectPair> matchingOpenCdsConceptsGender = myTerminologyManager.getMatchingOpenCDSConcepts(genderConcept);
					if (matchingOpenCdsConceptsGender != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsGender)
						{
							populateVmrOpenCdsConcept(evaluatedPerson.getId(), new GenderConcept(), conceptLists.genderConceptList, matchingOpenCdsConcept);						
						}
					}
				}
				
				// race
				if (demographics.getRace() != null) {
					for (CD race : demographics.getRace())
					{
						if (race != null) {
							HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
									race.getCodeSystem(), race.getCode()));
							if (matchingOpenCdsConcepts != null) {
								for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
								{
									populateVmrOpenCdsConcept(evaluatedPerson.getId(), new RaceConcept(), conceptLists.raceConceptList, matchingOpenCdsConcept);						
								}
							}
						}
					}
				}
				
				// ethnicity
				if (demographics.getEthnicity() != null) {
					for (CD ethnicity : demographics.getEthnicity())
					{
						if (ethnicity != null) {
							HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
									ethnicity.getCodeSystem(), ethnicity.getCode()));
							if (matchingOpenCdsConcepts != null) {
								for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
								{
									populateVmrOpenCdsConcept(evaluatedPerson.getId(), new EthnicityConcept(), conceptLists.ethnicityConceptList, matchingOpenCdsConcept);						
								}
							}
						}
					}
				}
				
				// preferred language
				if (demographics.getPreferredLanguage() != null) {
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							evaluatedPerson.getDemographics().getPreferredLanguage().getCodeSystem(), evaluatedPerson.getDemographics().getPreferredLanguage().getCode()));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(evaluatedPerson.getId(), new PreferredLanguageConcept(), conceptLists.preferredLanguageConceptList, matchingOpenCdsConcept);						
						}
					}	
				}
			}
		}
		
		//entity
		for (Entity entity: factLists.internalEntityList)
		{
			//templateId
			if (entity.getTemplateId() != null){
				for (String oneTemplateId : entity.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(entity.getId(), new EntityTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//entity type
			if (entity.getEntityType() != null){
				HashSet<ObjectPair> matchingOpenCdsConceptsEntityType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						entity.getEntityType().getCodeSystem(), entity.getEntityType().getCode()));
				if (matchingOpenCdsConceptsEntityType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEntityType)
					{
						populateVmrOpenCdsConcept(entity.getId(), new EntityTypeConcept(), conceptLists.entityTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}			
		}
		
		//facility 
		for (Facility entity: factLists.internalFacilityList)
		{
			//templateId
			if (entity.getTemplateId() != null){
				for (String oneTemplateId : entity.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(entity.getId(), new EntityTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//entity type
			if (entity.getEntityType() != null){
				HashSet<ObjectPair> matchingOpenCdsConceptsEntityType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						entity.getEntityType().getCodeSystem(), entity.getEntityType().getCode()));
				if (matchingOpenCdsConceptsEntityType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEntityType)
					{
						populateVmrOpenCdsConcept(entity.getId(), new EntityTypeConcept(), conceptLists.entityTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}			
		}
		
		//organization 
		for (Organization entity: factLists.internalOrganizationList)
		{
			//templateId
			if (entity.getTemplateId() != null){
				for (String oneTemplateId : entity.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(entity.getId(), new EntityTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//entity type
			if (entity.getEntityType() != null){
				HashSet<ObjectPair> matchingOpenCdsConceptsEntityType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						entity.getEntityType().getCodeSystem(), entity.getEntityType().getCode()));
				if (matchingOpenCdsConceptsEntityType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEntityType)
					{
						populateVmrOpenCdsConcept(entity.getId(), new EntityTypeConcept(), conceptLists.entityTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}			
		}
		
		//person 
		for (Person entity: factLists.internalPersonList)
		{
			//templateId
			if (entity.getTemplateId() != null){
				for (String oneTemplateId : entity.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(entity.getId(), new EntityTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//entity type
			if (entity.getEntityType() != null){
				HashSet<ObjectPair> matchingOpenCdsConceptsEntityType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						entity.getEntityType().getCodeSystem(), entity.getEntityType().getCode()));
				if (matchingOpenCdsConceptsEntityType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEntityType)
					{
						populateVmrOpenCdsConcept(entity.getId(), new EntityTypeConcept(), conceptLists.entityTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}			
		}
		
		//specimen 
		for (Specimen entity: factLists.internalSpecimenList)
		{
			//templateId
			if (entity.getTemplateId() != null){
				for (String oneTemplateId : entity.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(entity.getId(), new EntityTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//entity type
			if (entity.getEntityType() != null){
				HashSet<ObjectPair> matchingOpenCdsConceptsEntityType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						entity.getEntityType().getCodeSystem(), entity.getEntityType().getCode()));
				if (matchingOpenCdsConceptsEntityType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEntityType)
					{
						populateVmrOpenCdsConcept(entity.getId(), new EntityTypeConcept(), conceptLists.entityTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}			
		}
		
//	deprecated des 2012-02-11	
//		//clinical statement entity in role relationship
//		for (ClinicalStatementEntityInRoleRelationship target : factLists.internalClinicalStatementEntityInRoleRelationshipList)
//		{
//			HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
//					target.getRole().getCodeSystem(), target.getRole().getCode()));
//			if (matchingOpenCdsConcepts != null) {
//				for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
//				{
//					populateVmrOpenCdsConcept(target.getEntityId(), new RoleConcept(), 
//							conceptLists.roleConceptList, matchingOpenCdsConcept);						
//				}
//			}
//		}
		
		//clinical statement relationship
		for (ClinicalStatementRelationship clinicalStatementRelationship : factLists.internalClinicalStatementRelationshipList)
		{
			HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
					clinicalStatementRelationship.getTargetRelationshipToSource().getCodeSystem(), clinicalStatementRelationship.getTargetRelationshipToSource().getCode()));
			if (matchingOpenCdsConcepts != null) {
				for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
				{
//					populateVmrOpenCdsConcept(clinicalStatementRelationship.getTargetId(), new ClinicalStatementRelationshipConcept(), 
					populateVmrOpenCdsConcept(clinicalStatementRelationship.getId(), new ClinicalStatementRelationshipConcept(), 
							conceptLists.clinicalStatementRelationshipConceptList, matchingOpenCdsConcept);						
				}
			}
		}
		
		//entity relationship
		for (EntityRelationship entityRelationship : factLists.internalEntityRelationshipList)
		{
			HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
					entityRelationship.getTargetRole().getCodeSystem(), entityRelationship.getTargetRole().getCode()));
			if (matchingOpenCdsConcepts != null) {
				for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
				{
//					populateVmrOpenCdsConcept(entityRelationship.getTargetEntityId(), new EntityRelationshipConcept(), 
					populateVmrOpenCdsConcept(entityRelationship.getId(), new EntityRelationshipConcept(), 
							conceptLists.entityRelationshipConceptList, matchingOpenCdsConcept);						
				}
			}
		}
		
		//evaluated person relationship
		for (EvaluatedPersonRelationship evaluatedPersonRelationship : factLists.internalEvaluatedPersonRelationshipList)
		{
			HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
					evaluatedPersonRelationship.getTargetRole().getCodeSystem(), evaluatedPersonRelationship.getTargetRole().getCode()));
			if (matchingOpenCdsConcepts != null) {
				for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
				{
//					populateVmrOpenCdsConcept(evaluatedPersonRelationship.getTargetEntityId().getValue(), new EvaluatedPersonRelationshipConcept(), 
					populateVmrOpenCdsConcept(evaluatedPersonRelationship.getId(), new EvaluatedPersonRelationshipConcept(), 
							conceptLists.evaluatedPersonRelationshipConceptList, matchingOpenCdsConcept);						
				}
			}
		}
		
		//cdsInput
		//cdsOutput
		//vmr
		//adverse event 
		for (AdverseEvent adverseEvent : factLists.internalAdverseEventList)
		{
			//templateId
			if (adverseEvent.getTemplateId() != null){
				for (String oneTemplateId : adverseEvent.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(adverseEvent.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (adverseEvent.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						adverseEvent.getDataSourceType().getCodeSystem(), adverseEvent.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(adverseEvent.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//adverse event code
			if (adverseEvent.getAdverseEventCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEvent = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						adverseEvent.getAdverseEventCode().getCodeSystem(), adverseEvent.getAdverseEventCode().getCode()));
				if (matchingOpenCdsConceptsAdverseEvent != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEvent)
					{
						populateVmrOpenCdsConcept(adverseEvent.getId(), new AdverseEventConcept(), 
								conceptLists.adverseEventConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//adverse event agent
			if (adverseEvent.getAdverseEventAgent() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventAgent = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						adverseEvent.getAdverseEventAgent().getCodeSystem(), adverseEvent.getAdverseEventAgent().getCode()));
				if (matchingOpenCdsConceptsAdverseEventAgent != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventAgent)
					{
						populateVmrOpenCdsConcept(adverseEvent.getId(), new AdverseEventAgentConcept(), 
								conceptLists.adverseEventAgentConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//affected body site
			if (adverseEvent.getAffectedBodySite() != null) {
				for (BodySite bodySite : adverseEvent.getAffectedBodySite()) {
					if (bodySite != null) {
						if (bodySite.getBodySiteCode() != null) {
							HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
									bodySite.getBodySiteCode().getCodeSystem(), bodySite.getBodySiteCode().getCode()));
							if (matchingOpenCdsConceptsAffectedBodySiteCode != null) {
								for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteCode)
								{
									populateVmrOpenCdsConcept(adverseEvent.getId(), new AdverseEventAffectedBodySiteConcept(), 
											conceptLists.adverseEventAffectedBodySiteConceptList, matchingOpenCdsConcept);						
								}
							}
							
							//affected body site laterality -- NOTE: this matches to coordinated pair of concepts: bodySite plus laterality
							if (bodySite.getLaterality() != null) {
								HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(
										new ObjectPair(new org.opencds.common.terminology.CD(bodySite.getBodySiteCode().getCodeSystem(), bodySite.getBodySiteCode().getCode()), 
													   new org.opencds.common.terminology.CD(bodySite.getLaterality().getCodeSystem(), bodySite.getLaterality().getCode())));
								if (matchingOpenCdsConceptsAffectedBodySiteLaterality != null) {
									for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteLaterality)
									{
										populateVmrOpenCdsConcept(adverseEvent.getId(), new AdverseEventAffectedBodySiteLateralityConcept(), 
												conceptLists.adverseEventAffectedBodySiteLateralityConceptList, matchingOpenCdsConcept);						
									}
								}
							}
						}
					}
				}
			}
			
			//criticality
			if (adverseEvent.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						adverseEvent.getCriticality().getCodeSystem(), adverseEvent.getCriticality().getCode()));
				if (matchingOpenCdsConceptsAdverseEventCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventCriticality)
					{
						populateVmrOpenCdsConcept(adverseEvent.getId(), new AdverseEventCriticalityConcept(), 
								conceptLists.adverseEventCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//severity
			if (adverseEvent.getSeverity() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventSeverity = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						adverseEvent.getSeverity().getCodeSystem(), adverseEvent.getSeverity().getCode()));
				if (matchingOpenCdsConceptsAdverseEventSeverity != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventSeverity)
					{
						populateVmrOpenCdsConcept(adverseEvent.getId(), new AdverseEventSeverityConcept(), 
								conceptLists.adverseEventSeverityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//status
			if (adverseEvent.getAdverseEventStatus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventStatus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						adverseEvent.getAdverseEventStatus().getCodeSystem(), adverseEvent.getAdverseEventStatus().getCode()));
				if (matchingOpenCdsConceptsAdverseEventStatus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventStatus)
					{
						populateVmrOpenCdsConcept(adverseEvent.getId(), new AdverseEventStatusConcept(), 
								conceptLists.adverseEventStatusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//denied adverse event 	
		for (DeniedAdverseEvent deniedAdverseEvent : factLists.internalDeniedAdverseEventList)
		{
			//templateId
			if (deniedAdverseEvent.getTemplateId() != null){
				for (String oneTemplateId : deniedAdverseEvent.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(deniedAdverseEvent.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (deniedAdverseEvent.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						deniedAdverseEvent.getDataSourceType().getCodeSystem(), deniedAdverseEvent.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(deniedAdverseEvent.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//adverse event code
			if (deniedAdverseEvent.getAdverseEventCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEvent = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						deniedAdverseEvent.getAdverseEventCode().getCodeSystem(), deniedAdverseEvent.getAdverseEventCode().getCode()));
				if (matchingOpenCdsConceptsAdverseEvent != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEvent)
					{
						populateVmrOpenCdsConcept(deniedAdverseEvent.getId(), new AdverseEventConcept(), 
								conceptLists.adverseEventConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//adverse event agent
			if (deniedAdverseEvent.getAdverseEventAgent() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventAgent = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						deniedAdverseEvent.getAdverseEventAgent().getCodeSystem(), deniedAdverseEvent.getAdverseEventAgent().getCode()));
				if (matchingOpenCdsConceptsAdverseEventAgent != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventAgent)
					{
						populateVmrOpenCdsConcept(deniedAdverseEvent.getId(), new AdverseEventAgentConcept(), 
								conceptLists.adverseEventAgentConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//affected body site
			if (deniedAdverseEvent.getAffectedBodySite() != null) {
				for (BodySite bodySite : deniedAdverseEvent.getAffectedBodySite()) {
					if (bodySite != null) {
						if (bodySite.getBodySiteCode() != null) {
							HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
									bodySite.getBodySiteCode().getCodeSystem(), bodySite.getBodySiteCode().getCode()));
							if (matchingOpenCdsConceptsAffectedBodySiteCode != null) {
								for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteCode)
								{
									populateVmrOpenCdsConcept(deniedAdverseEvent.getId(), new AdverseEventAffectedBodySiteConcept(), 
											conceptLists.adverseEventAffectedBodySiteConceptList, matchingOpenCdsConcept);						
								}
							}
							
							//affected body site laterality -- NOTE: this matches to coordinated pair of concepts: bodySite plus laterality
							if (bodySite.getLaterality() != null) {
								HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(
										new ObjectPair(new org.opencds.common.terminology.CD(bodySite.getBodySiteCode().getCodeSystem(), bodySite.getBodySiteCode().getCode()), 
													   new org.opencds.common.terminology.CD(bodySite.getLaterality().getCodeSystem(), bodySite.getLaterality().getCode())));
								if (matchingOpenCdsConceptsAffectedBodySiteLaterality != null) {
									for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteLaterality)
									{
										populateVmrOpenCdsConcept(deniedAdverseEvent.getId(), new AdverseEventAffectedBodySiteLateralityConcept(), 
												conceptLists.adverseEventAffectedBodySiteLateralityConceptList, matchingOpenCdsConcept);						
									}
								}
							}
						}
					}
				}
			}
		}
		
		//appointment proposal 	
		for (AppointmentProposal appointmentProposal : factLists.internalAppointmentProposalList)
		{
			//templateId
			if (appointmentProposal.getTemplateId() != null){
				for (String oneTemplateId : appointmentProposal.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(appointmentProposal.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (appointmentProposal.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						appointmentProposal.getDataSourceType().getCodeSystem(), appointmentProposal.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(appointmentProposal.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//encounter type
			if (appointmentProposal.getEncounterType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsEncounterType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						appointmentProposal.getEncounterType().getCodeSystem(), appointmentProposal.getEncounterType().getCode()));
				if (matchingOpenCdsConceptsEncounterType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEncounterType)
					{
						populateVmrOpenCdsConcept(appointmentProposal.getId(), new EncounterTypeConcept(), 
								conceptLists.encounterTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//criticality
			if (appointmentProposal.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsEncounterCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						appointmentProposal.getCriticality().getCodeSystem(), appointmentProposal.getCriticality().getCode()));
				if (matchingOpenCdsConceptsEncounterCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEncounterCriticality)
					{
						populateVmrOpenCdsConcept(appointmentProposal.getId(), new EncounterCriticalityConcept(), 
								conceptLists.encounterCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//appointment request 	
		for (AppointmentRequest appointmentRequest : factLists.internalAppointmentRequestList)
		{
			//templateId
			if (appointmentRequest.getTemplateId() != null){
				for (String oneTemplateId : appointmentRequest.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(appointmentRequest.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (appointmentRequest.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						appointmentRequest.getDataSourceType().getCodeSystem(), appointmentRequest.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(appointmentRequest.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//encounter type
			if (appointmentRequest.getEncounterType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsEncounterType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						appointmentRequest.getEncounterType().getCodeSystem(), appointmentRequest.getEncounterType().getCode()));
				if (matchingOpenCdsConceptsEncounterType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEncounterType)
					{
						populateVmrOpenCdsConcept(appointmentRequest.getId(), new EncounterTypeConcept(), 
								conceptLists.encounterTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//criticality
			if (appointmentRequest.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsEncounterCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						appointmentRequest.getCriticality().getCodeSystem(), appointmentRequest.getCriticality().getCode()));
				if (matchingOpenCdsConceptsEncounterCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEncounterCriticality)
					{
						populateVmrOpenCdsConcept(appointmentRequest.getId(), new EncounterCriticalityConcept(), 
								conceptLists.encounterCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//encounter event
		for (EncounterEvent encounterEvent : factLists.internalEncounterEventList)
		{
			//templateId
			if (encounterEvent.getTemplateId() != null){
				for (String oneTemplateId : encounterEvent.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(encounterEvent.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (encounterEvent.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						encounterEvent.getDataSourceType().getCodeSystem(), encounterEvent.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(encounterEvent.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//encounter type
			if (encounterEvent.getEncounterType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsEncounterType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						encounterEvent.getEncounterType().getCodeSystem(), encounterEvent.getEncounterType().getCode()));
				if (matchingOpenCdsConceptsEncounterType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEncounterType)
					{
						populateVmrOpenCdsConcept(encounterEvent.getId(), new EncounterTypeConcept(), 
								conceptLists.encounterTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}			
		}
		
		//missed appointment 	
		for (MissedAppointment missedAppointment : factLists.internalMissedAppointmentList)
		{
			//templateId
			if (missedAppointment.getTemplateId() != null){
				for (String oneTemplateId : missedAppointment.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(missedAppointment.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (missedAppointment.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						missedAppointment.getDataSourceType().getCodeSystem(), missedAppointment.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(missedAppointment.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//encounter type
			if (missedAppointment.getEncounterType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsEncounterType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						missedAppointment.getEncounterType().getCodeSystem(), missedAppointment.getEncounterType().getCode()));
				if (matchingOpenCdsConceptsEncounterType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEncounterType)
					{
						populateVmrOpenCdsConcept(missedAppointment.getId(), new EncounterTypeConcept(), 
								conceptLists.encounterTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//scheduled appointment 	
		for (ScheduledAppointment scheduledAppointment : factLists.internalScheduledAppointmentList)
		{
			//templateId
			if (scheduledAppointment.getTemplateId() != null){
				for (String oneTemplateId : scheduledAppointment.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(scheduledAppointment.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (scheduledAppointment.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						scheduledAppointment.getDataSourceType().getCodeSystem(), scheduledAppointment.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(scheduledAppointment.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//encounter type
			if (scheduledAppointment.getEncounterType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsEncounterType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						scheduledAppointment.getEncounterType().getCodeSystem(), scheduledAppointment.getEncounterType().getCode()));
				if (matchingOpenCdsConceptsEncounterType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsEncounterType)
					{
						populateVmrOpenCdsConcept(scheduledAppointment.getId(), new EncounterTypeConcept(), 
								conceptLists.encounterTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//goal 	
		for (Goal goal : factLists.internalGoalList)
		{
			//templateId
			if (goal.getTemplateId() != null){
				for (String oneTemplateId : goal.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(goal.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (goal.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goal.getDataSourceType().getCodeSystem(), goal.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(goal.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//goal focus
			if (goal.getGoalFocus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationFocus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goal.getGoalFocus().getCodeSystem(), goal.getGoalFocus().getCode()));
				if (matchingOpenCdsConceptsObservationFocus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationFocus)
					{
						populateVmrOpenCdsConcept(goal.getId(), new ObservationFocusConcept(), 
								conceptLists.observationFocusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//target body site
			if (goal.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goal.getTargetBodySite().getBodySiteCode().getCodeSystem(), goal.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsObservationResultsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(goal.getId(), new GoalTargetBodySiteConcept(), 
								conceptLists.goalTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
			
			
				//target body site laterality
				if (goal.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							goal.getTargetBodySite().getLaterality().getCodeSystem(), goal.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(goal.getId(), new GoalTargetBodySiteLateralityConcept(), 
									conceptLists.goalTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//goal value (= ANY -- just processing CD here...) //TODO can we create concepts out of any other types???
			if (goal.getTargetGoalValue() != null) {
				if (goal.getTargetGoalValue().getConcept() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetGoalCodedValue = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							goal.getTargetGoalValue().getConcept().getCodeSystem(), goal.getTargetGoalValue().getConcept().getCode()));
					if (matchingOpenCdsConceptsTargetGoalCodedValue != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetGoalCodedValue)
						{
							populateVmrOpenCdsConcept(goal.getId(), new GoalCodedValueConcept(), 
									conceptLists.goalCodedValueConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//goal criticality
			if (goal.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsGoalCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goal.getCriticality().getCodeSystem(), goal.getCriticality().getCode()));
				if (matchingOpenCdsConceptsGoalCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsGoalCriticality)
					{
						populateVmrOpenCdsConcept(goal.getId(), new GoalCriticalityConcept(), 
								conceptLists.goalCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//goal status
			if (goal.getGoalStatus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsGoalStatus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goal.getGoalStatus().getCodeSystem(), goal.getGoalStatus().getCode()));
				if (matchingOpenCdsConceptsGoalStatus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsGoalStatus)
					{
						populateVmrOpenCdsConcept(goal.getId(), new GoalStatusConcept(), 
								conceptLists.goalStatusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//goal proposal 	
		for (GoalProposal goalProposal : factLists.internalGoalProposalList)
		{
			//templateId
			if (goalProposal.getTemplateId() != null){
				for (String oneTemplateId : goalProposal.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(goalProposal.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (goalProposal.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goalProposal.getDataSourceType().getCodeSystem(), goalProposal.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(goalProposal.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//goal focus
			if (goalProposal.getGoalFocus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationFocus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goalProposal.getGoalFocus().getCodeSystem(), goalProposal.getGoalFocus().getCode()));
				if (matchingOpenCdsConceptsObservationFocus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationFocus)
					{
						populateVmrOpenCdsConcept(goalProposal.getId(), new ObservationFocusConcept(), 
								conceptLists.observationFocusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//target body site
			if (goalProposal.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goalProposal.getTargetBodySite().getBodySiteCode().getCodeSystem(), goalProposal.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsObservationResultsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(goalProposal.getId(), new GoalTargetBodySiteConcept(), 
								conceptLists.goalTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (goalProposal.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							goalProposal.getTargetBodySite().getLaterality().getCodeSystem(), goalProposal.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(goalProposal.getId(), new GoalTargetBodySiteLateralityConcept(), 
									conceptLists.goalTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//goal value (= ANY -- just processing CD here...) //TODO can we create concepts out of any other types???
			if (goalProposal.getTargetGoalValue() != null) {
				if (goalProposal.getTargetGoalValue().getConcept() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetGoalCodedValue = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							goalProposal.getTargetGoalValue().getConcept().getCodeSystem(), goalProposal.getTargetGoalValue().getConcept().getCode()));
					if (matchingOpenCdsConceptsTargetGoalCodedValue != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetGoalCodedValue)
						{
							populateVmrOpenCdsConcept(goalProposal.getId(), new GoalCodedValueConcept(), 
									conceptLists.goalCodedValueConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//goal criticality
			if (goalProposal.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsGoalCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						goalProposal.getCriticality().getCodeSystem(), goalProposal.getCriticality().getCode()));
				if (matchingOpenCdsConceptsGoalCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsGoalCriticality)
					{
						populateVmrOpenCdsConcept(goalProposal.getId(), new GoalCriticalityConcept(), 
								conceptLists.goalCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//observation order 	
		for (ObservationOrder observationOrder : factLists.internalObservationOrderList)
		{
			//templateId
			if (observationOrder.getTemplateId() != null){
				for (String oneTemplateId : observationOrder.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(observationOrder.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (observationOrder.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationOrder.getDataSourceType().getCodeSystem(), observationOrder.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(observationOrder.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//observation focus
			if (observationOrder.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationFocus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationOrder.getObservationFocus().getCodeSystem(), observationOrder.getObservationFocus().getCode()));
				if (matchingOpenCdsConceptsObservationFocus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationFocus)
					{
						populateVmrOpenCdsConcept(observationOrder.getId(), new ObservationFocusConcept(), 
								conceptLists.observationFocusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//observation method
			if (observationOrder.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationOrder.getObservationMethod().getCodeSystem(), observationOrder.getObservationMethod().getCode()));
				if (matchingOpenCdsConceptsObservationMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationMethod)
					{
						populateVmrOpenCdsConcept(observationOrder.getId(), new ObservationMethodConcept(), 
								conceptLists.observationMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//target body site
			if (observationOrder.getTargetBodySite() != null) {
				//target body site code
				if ((observationOrder.getTargetBodySite().getBodySiteCode() != null) 
						&& (observationOrder.getTargetBodySite().getBodySiteCode().getCode() != null)) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteCode 
						= myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							observationOrder.getTargetBodySite().getBodySiteCode().getCodeSystem(), observationOrder.getTargetBodySite().getBodySiteCode().getCode()));
					if (matchingOpenCdsConceptsObservationResultsTargetBodySiteCode != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteCode)
						{
							populateVmrOpenCdsConcept(observationOrder.getId(), new ObservationTargetBodySiteConcept(), 
									conceptLists.observationTargetBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//target body site laterality
				if ((observationOrder.getTargetBodySite().getLaterality() != null) 
						&& (observationOrder.getTargetBodySite().getLaterality().getCode() != null)) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality 
						= myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
								observationOrder.getTargetBodySite().getLaterality().getCodeSystem(), observationOrder.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(observationOrder.getId(), new ObservationTargetBodySiteLateralityConcept(), 
									conceptLists.observationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//criticality
			if (observationOrder.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationOrder.getCriticality().getCodeSystem(), observationOrder.getCriticality().getCode()));
				if (matchingOpenCdsConceptsCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsCriticality)
					{
						populateVmrOpenCdsConcept(observationOrder.getId(), new ObservationCriticalityConcept(), 
								conceptLists.observationCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//observation proposal 	
		for (ObservationProposal observationProposal : factLists.internalObservationProposalList)
		{
			//templateId
			if (observationProposal.getTemplateId() != null){
				for (String oneTemplateId : observationProposal.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(observationProposal.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (observationProposal.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationProposal.getDataSourceType().getCodeSystem(), observationProposal.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(observationProposal.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//observation focus
			if (observationProposal.getObservationFocus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationFocus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationProposal.getObservationFocus().getCodeSystem(), observationProposal.getObservationFocus().getCode()));
				if (matchingOpenCdsConceptsObservationFocus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationFocus)
					{
						populateVmrOpenCdsConcept(observationProposal.getId(), new ObservationFocusConcept(), 
								conceptLists.observationFocusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//observation method
			if (observationProposal.getObservationMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationProposal.getObservationMethod().getCodeSystem(), observationProposal.getObservationMethod().getCode()));
				if (matchingOpenCdsConceptsObservationMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationMethod)
					{
						populateVmrOpenCdsConcept(observationProposal.getId(), new ObservationMethodConcept(), 
								conceptLists.observationMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//target body site
			if (observationProposal.getTargetBodySite() != null) {
				//target body site code
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationProposal.getTargetBodySite().getBodySiteCode().getCodeSystem(), observationProposal.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsObservationResultsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(observationProposal.getId(), new ObservationTargetBodySiteConcept(), 
								conceptLists.observationTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
			
				//target body site laterality
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationProposal.getTargetBodySite().getLaterality().getCodeSystem(), observationProposal.getTargetBodySite().getLaterality().getCode()));
				if (matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality)
					{
						populateVmrOpenCdsConcept(observationProposal.getId(), new ObservationTargetBodySiteLateralityConcept(), 
								conceptLists.observationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//criticality
			if (observationProposal.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationProposal.getCriticality().getCodeSystem(), observationProposal.getCriticality().getCode()));
				if (matchingOpenCdsConceptsCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsCriticality)
					{
						populateVmrOpenCdsConcept(observationProposal.getId(), new ObservationCriticalityConcept(), 
								conceptLists.observationCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//observation result 	
		for (ObservationResult observationResult : factLists.internalObservationResultList)
		{
			//templateId
			if (observationResult.getTemplateId() != null){
				for (String oneTemplateId : observationResult.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(observationResult.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (observationResult.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationResult.getDataSourceType().getCodeSystem(), observationResult.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(observationResult.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//observation focus
			if (observationResult.getObservationFocus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationFocus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationResult.getObservationFocus().getCodeSystem(), observationResult.getObservationFocus().getCode()));
				if (matchingOpenCdsConceptsObservationFocus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationFocus)
					{
						//FIXME
						populateVmrOpenCdsConcept(observationResult.getId(), new ObservationFocusConcept(), 
								conceptLists.observationFocusConceptList, matchingOpenCdsConcept);	
//						org.opencds.common.terminology.CD openCdsConcept = (org.opencds.common.terminology.CD) matchingOpenCdsConcept.getObject1();
//						org.opencds.common.terminology.CD determinationMethod = (org.opencds.common.terminology.CD) matchingOpenCdsConcept.getObject2();
//						ObservationFocusConcept obsvFocusC = new ObservationFocusConcept();
//						obsvFocusC.setId(MiscUtility.getUUIDAsString());
//						obsvFocusC.setConceptTargetId(observationResult.getId());
//						obsvFocusC.setOpenCdsConceptCode(openCdsConcept.getCode());
//						obsvFocusC.setDeterminationMethodCode(determinationMethod.getCode());
//						conceptLists.observationFocusConceptList.add( obsvFocusC );	
					}
				}
			}
			
			//observation method
			if (observationResult.getObservationMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						observationResult.getObservationMethod().getCodeSystem(), observationResult.getObservationMethod().getCode()));
				if (matchingOpenCdsConceptsObservationMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationMethod)
					{
						populateVmrOpenCdsConcept(observationResult.getId(), new ObservationMethodConcept(), 
								conceptLists.observationMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//target body site
			if (observationResult.getTargetBodySite() != null) {
				if (observationResult.getTargetBodySite().getBodySiteCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							observationResult.getTargetBodySite().getBodySiteCode().getCodeSystem(), observationResult.getTargetBodySite().getBodySiteCode().getCode()));
					if (matchingOpenCdsConceptsObservationResultsTargetBodySiteCode != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteCode)
						{
							populateVmrOpenCdsConcept(observationResult.getId(), new ObservationTargetBodySiteConcept(), 
									conceptLists.observationTargetBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//target body site laterality
				if (observationResult.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							observationResult.getTargetBodySite().getLaterality().getCodeSystem(), observationResult.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationResultsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(observationResult.getId(), new ObservationTargetBodySiteLateralityConcept(), 
									conceptLists.observationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//observation value (= ANY -- just processing CD here...) //TODO can we create concepts out of any other types???
			if (observationResult.getObservationValue() != null) {
				if (observationResult.getObservationValue().getConcept() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationValueCD = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							observationResult.getObservationValue().getConcept().getCodeSystem(), observationResult.getObservationValue().getConcept().getCode()));
					if (matchingOpenCdsConceptsObservationValueCD != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationValueCD)
						{
							populateVmrOpenCdsConcept(observationResult.getId(), new ObservationCodedValueConcept(), 
									conceptLists.observationCodedValueConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//interpretation
			if (observationResult.getInterpretation() != null) {
				for (CD oneInterpretation : observationResult.getInterpretation()) {
					if (oneInterpretation != null) {
						HashSet<ObjectPair> matchingOpenCdsConceptsInterpretation = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
								oneInterpretation.getCodeSystem(), oneInterpretation.getCode()));
						if (matchingOpenCdsConceptsInterpretation != null) {
							for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsInterpretation)
							{
								populateVmrOpenCdsConcept(observationResult.getId(), new ObservationInterpretationConcept(), 
										conceptLists.observationInterpretationConceptList, matchingOpenCdsConcept);						
							}
						}
					}
				}
			}
		}
		
		//unconducted observation 	
		for (UnconductedObservation unconductedObservation : factLists.internalUnconductedObservationList)
		{
			//templateId
			if (unconductedObservation.getTemplateId() != null){
				for (String oneTemplateId : unconductedObservation.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(unconductedObservation.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (unconductedObservation.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						unconductedObservation.getDataSourceType().getCodeSystem(), unconductedObservation.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(unconductedObservation.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//observation focus
			if (unconductedObservation.getObservationFocus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationFocus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						unconductedObservation.getObservationFocus().getCodeSystem(), unconductedObservation.getObservationFocus().getCode()));
				if (matchingOpenCdsConceptsObservationFocus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationFocus)
					{
						populateVmrOpenCdsConcept(unconductedObservation.getId(), new ObservationFocusConcept(), 
								conceptLists.observationFocusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//observation method
			if (unconductedObservation.getObservationMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						unconductedObservation.getObservationMethod().getCodeSystem(), unconductedObservation.getObservationMethod().getCode()));
				if (matchingOpenCdsConceptsObservationMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationMethod)
					{
						populateVmrOpenCdsConcept(unconductedObservation.getId(), new ObservationMethodConcept(), 
								conceptLists.observationMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//target body site
			if (unconductedObservation.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						unconductedObservation.getTargetBodySite().getBodySiteCode().getCodeSystem(), unconductedObservation.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsObservationTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(unconductedObservation.getId(), new ObservationTargetBodySiteConcept(), 
								conceptLists.observationTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (unconductedObservation.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsObservationTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							unconductedObservation.getTargetBodySite().getLaterality().getCodeSystem(), unconductedObservation.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsObservationTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(unconductedObservation.getId(), new ObservationTargetBodySiteLateralityConcept(), 
									conceptLists.observationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//reason
			if (unconductedObservation.getReason() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsObservationUnconductedReason = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						unconductedObservation.getReason().getCodeSystem(), unconductedObservation.getReason().getCode()));
				if (matchingOpenCdsConceptsObservationUnconductedReason != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsObservationUnconductedReason)
					{
						populateVmrOpenCdsConcept(unconductedObservation.getId(), new ObservationUnconductedReasonConcept(), 
								conceptLists.observationUnconductedReasonConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//denied problem 	
		for (Problem deniedProblem : factLists.internalProblemList)
		{
			//templateId
			if (deniedProblem.getTemplateId() != null){
				for (String oneTemplateId : deniedProblem.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(deniedProblem.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (deniedProblem.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						deniedProblem.getDataSourceType().getCodeSystem(), deniedProblem.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(deniedProblem.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//problem code
			if (deniedProblem.getProblemCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProblem = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						deniedProblem.getProblemCode().getCodeSystem(), deniedProblem.getProblemCode().getCode()));
				if (matchingOpenCdsConceptsProblem != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProblem)
					{
						populateVmrOpenCdsConcept(deniedProblem.getId(), new ProblemConcept(), 
								conceptLists.problemConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//affected body site
			if (deniedProblem.getAffectedBodySite() != null)
			for (BodySite affectedBodySite : deniedProblem.getAffectedBodySite())
			{
				//body site code
				if (affectedBodySite != null) {
					if (affectedBodySite.getBodySiteCode() != null) {
						HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
								affectedBodySite.getBodySiteCode().getCodeSystem(), affectedBodySite.getBodySiteCode().getCode()));
						if (matchingOpenCdsConceptsAffectedBodySiteCode != null) {
							for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteCode)
							{
								populateVmrOpenCdsConcept(deniedProblem.getId(), new ProblemAffectedBodySiteConcept(), 
										conceptLists.problemAffectedBodySiteConceptList, matchingOpenCdsConcept);						
							}
						}
						
						//affected body site laterality -- NOTE: this matches to coordinated pair of concepts: bodySite plus laterality
						if (affectedBodySite.getLaterality() != null) {
							HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(
									new ObjectPair(new org.opencds.common.terminology.CD(affectedBodySite.getBodySiteCode().getCodeSystem(), affectedBodySite.getBodySiteCode().getCode()), 
												   new org.opencds.common.terminology.CD(affectedBodySite.getLaterality().getCodeSystem(), affectedBodySite.getLaterality().getCode())));
							if (matchingOpenCdsConceptsAffectedBodySiteLaterality != null) {
								for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteLaterality)
								{
									populateVmrOpenCdsConcept(deniedProblem.getId(), new ProblemAffectedBodySiteLateralityConcept(), 
											conceptLists.problemAffectedBodySiteLateralityConceptList, matchingOpenCdsConcept);						
								}
							}
						}
					}
				}
			}
		}
		
		//problem 	
		for (Problem problem : factLists.internalProblemList)
		{
			//templateId
			if (problem.getTemplateId() != null){
				for (String oneTemplateId : problem.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(problem.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (problem.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						problem.getDataSourceType().getCodeSystem(), problem.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(problem.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//problem status
			if (problem.getProblemStatus() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProblemStatus = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						problem.getProblemStatus().getCodeSystem(), problem.getProblemStatus().getCode()));
				if (matchingOpenCdsConceptsProblemStatus != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProblemStatus)
					{
						populateVmrOpenCdsConcept(problem.getId(), new ProblemStatusConcept(), 
								conceptLists.problemStatusConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//problem code
			if (problem.getProblemCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProblem = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						problem.getProblemCode().getCodeSystem(), problem.getProblemCode().getCode()));
				if (matchingOpenCdsConceptsProblem != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProblem)
					{
						populateVmrOpenCdsConcept(problem.getId(), new ProblemConcept(), 
								conceptLists.problemConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//problem importance
			if (problem.getImportance() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProblemImportance = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						problem.getImportance().getCodeSystem(), problem.getImportance().getCode()));
				if (matchingOpenCdsConceptsProblemImportance != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProblemImportance)
					{
						populateVmrOpenCdsConcept(problem.getId(), new ProblemImportanceConcept(), 
								conceptLists.problemImportanceConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//severity
			if (problem.getSeverity() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSeverity = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						problem.getSeverity().getCodeSystem(), problem.getSeverity().getCode()));
				if (matchingOpenCdsConceptsSeverity != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSeverity)
					{
						populateVmrOpenCdsConcept(problem.getId(), new ProblemSeverityConcept(), 
								conceptLists.problemSeverityConceptList, matchingOpenCdsConcept);						
					}
				}
			}

			//affected body site
			if (problem.getAffectedBodySite() != null)
			for (BodySite affectedBodySite : problem.getAffectedBodySite())
			{
				//body site code
				if (affectedBodySite != null) {
					if (affectedBodySite.getBodySiteCode() != null) {
						HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
								affectedBodySite.getBodySiteCode().getCodeSystem(), affectedBodySite.getBodySiteCode().getCode()));
						if (matchingOpenCdsConceptsAffectedBodySiteCode != null) {
							for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteCode)
							{
								populateVmrOpenCdsConcept(problem.getId(), new ProblemAffectedBodySiteConcept(), 
										conceptLists.problemAffectedBodySiteConceptList, matchingOpenCdsConcept);						
							}
						}
						
						//affected body site laterality -- NOTE: this matches to coordinated pair of concepts: bodySite plus laterality
						if (affectedBodySite.getLaterality() != null) {
							HashSet<ObjectPair> matchingOpenCdsConceptsAffectedBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(
									new ObjectPair(new org.opencds.common.terminology.CD(affectedBodySite.getBodySiteCode().getCodeSystem(), affectedBodySite.getBodySiteCode().getCode()), 
												   new org.opencds.common.terminology.CD(affectedBodySite.getLaterality().getCodeSystem(), affectedBodySite.getLaterality().getCode())));
							if (matchingOpenCdsConceptsAffectedBodySiteLaterality != null) {
								for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAffectedBodySiteLaterality)
								{
									populateVmrOpenCdsConcept(problem.getId(), new ProblemAffectedBodySiteLateralityConcept(), 
											conceptLists.problemAffectedBodySiteLateralityConceptList, matchingOpenCdsConcept);						
								}
							}
						}
					}
				}
			}
		}
		
		//procedure event 
		for (ProcedureEvent procedureEvent : factLists.internalProcedureEventList)
		{
			//templateId
			if (procedureEvent.getTemplateId() != null){
				for (String oneTemplateId : procedureEvent.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(procedureEvent.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (procedureEvent.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureEvent.getDataSourceType().getCodeSystem(), procedureEvent.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(procedureEvent.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure code
			if (procedureEvent.getProcedureCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedure = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureEvent.getProcedureCode().getCodeSystem(), procedureEvent.getProcedureCode().getCode()));
				if (matchingOpenCdsConceptsProcedure != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedure)
					{
						populateVmrOpenCdsConcept(procedureEvent.getId(), new ProcedureConcept(), 
								conceptLists.procedureConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure method
			if (procedureEvent.getProcedureMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedureMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureEvent.getProcedureMethod().getCodeSystem(), procedureEvent.getProcedureMethod().getCode()));
				if (matchingOpenCdsConceptsProcedureMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedureMethod)
					{
						populateVmrOpenCdsConcept(procedureEvent.getId(), new ProcedureConcept(), 
								conceptLists.procedureMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (procedureEvent.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureEvent.getApproachBodySite().getBodySiteCode().getCodeSystem(), procedureEvent.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(procedureEvent.getId(), new ProcedureApproachBodySiteConcept(), 
								conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (procedureEvent.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							procedureEvent.getApproachBodySite().getLaterality().getCodeSystem(), procedureEvent.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(procedureEvent.getId(), new ProcedureApproachBodySiteLateralityConcept(), 
									conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//target body site
			if (procedureEvent.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureEvent.getTargetBodySite().getBodySiteCode().getCodeSystem(), procedureEvent.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(procedureEvent.getId(), new ProcedureTargetBodySiteConcept(), 
								conceptLists.procedureTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (procedureEvent.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							procedureEvent.getTargetBodySite().getLaterality().getCodeSystem(), procedureEvent.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(procedureEvent.getId(), new ProcedureTargetBodySiteLateralityConcept(), 
									conceptLists.procedureTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//procedure order 	
		for (ProcedureOrder procedureOrder : factLists.internalProcedureOrderList)
		{
			//templateId
			if (procedureOrder.getTemplateId() != null){
				for (String oneTemplateId : procedureOrder.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(procedureOrder.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (procedureOrder.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureOrder.getDataSourceType().getCodeSystem(), procedureOrder.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(procedureOrder.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure code
			if (procedureOrder.getProcedureCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedure = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureOrder.getProcedureCode().getCodeSystem(), procedureOrder.getProcedureCode().getCode()));
				if (matchingOpenCdsConceptsProcedure != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedure)
					{
						populateVmrOpenCdsConcept(procedureOrder.getId(), new ProcedureConcept(), 
								conceptLists.procedureConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure method
			if (procedureOrder.getProcedureMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedureMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureOrder.getProcedureMethod().getCodeSystem(), procedureOrder.getProcedureMethod().getCode()));
				if (matchingOpenCdsConceptsProcedureMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedureMethod)
					{
						populateVmrOpenCdsConcept(procedureOrder.getId(), new ProcedureConcept(), 
								conceptLists.procedureMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (procedureOrder.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureOrder.getApproachBodySite().getBodySiteCode().getCodeSystem(), procedureOrder.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(procedureOrder.getId(), new ProcedureApproachBodySiteConcept(), 
								conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (procedureOrder.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							procedureOrder.getApproachBodySite().getLaterality().getCodeSystem(), procedureOrder.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(procedureOrder.getId(), new ProcedureApproachBodySiteLateralityConcept(), 
									conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//target body site
			if (procedureOrder.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureOrder.getTargetBodySite().getBodySiteCode().getCodeSystem(), procedureOrder.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(procedureOrder.getId(), new ProcedureTargetBodySiteConcept(), 
								conceptLists.procedureTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (procedureOrder.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							procedureOrder.getTargetBodySite().getLaterality().getCodeSystem(), procedureOrder.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(procedureOrder.getId(), new ProcedureTargetBodySiteLateralityConcept(), 
									conceptLists.procedureTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//criticality
			if (procedureOrder.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureOrder.getCriticality().getCodeSystem(), procedureOrder.getCriticality().getCode()));
				if (matchingOpenCdsConceptsCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsCriticality)
					{
						populateVmrOpenCdsConcept(procedureOrder.getId(), new ProcedureCriticalityConcept(), 
								conceptLists.procedureCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//procedure proposal 	
		for (ProcedureProposal procedureProposal : factLists.internalProcedureProposalList)
		{
			//templateId
			if (procedureProposal.getTemplateId() != null){
				for (String oneTemplateId : procedureProposal.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(procedureProposal.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (procedureProposal.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureProposal.getDataSourceType().getCodeSystem(), procedureProposal.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(procedureProposal.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure code
			if (procedureProposal.getProcedureCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedure = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureProposal.getProcedureCode().getCodeSystem(), procedureProposal.getProcedureCode().getCode()));
				if (matchingOpenCdsConceptsProcedure != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedure)
					{
						populateVmrOpenCdsConcept(procedureProposal.getId(), new ProcedureConcept(), 
								conceptLists.procedureConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure method
			if (procedureProposal.getProcedureMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedureMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureProposal.getProcedureMethod().getCodeSystem(), procedureProposal.getProcedureMethod().getCode()));
				if (matchingOpenCdsConceptsProcedureMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedureMethod)
					{
						populateVmrOpenCdsConcept(procedureProposal.getId(), new ProcedureConcept(), 
								conceptLists.procedureMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (procedureProposal.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureProposal.getApproachBodySite().getBodySiteCode().getCodeSystem(), procedureProposal.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(procedureProposal.getId(), new ProcedureApproachBodySiteConcept(), 
								conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (procedureProposal.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							procedureProposal.getApproachBodySite().getLaterality().getCodeSystem(), procedureProposal.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(procedureProposal.getId(), new ProcedureApproachBodySiteLateralityConcept(), 
									conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//target body site
			if (procedureProposal.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureProposal.getTargetBodySite().getBodySiteCode().getCodeSystem(), procedureProposal.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(procedureProposal.getId(), new ProcedureTargetBodySiteConcept(), 
								conceptLists.procedureTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (procedureProposal.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							procedureProposal.getTargetBodySite().getLaterality().getCodeSystem(), procedureProposal.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(procedureProposal.getId(), new ProcedureTargetBodySiteLateralityConcept(), 
									conceptLists.procedureTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//criticality
			if (procedureProposal.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						procedureProposal.getCriticality().getCodeSystem(), procedureProposal.getCriticality().getCode()));
				if (matchingOpenCdsConceptsCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsCriticality)
					{
						populateVmrOpenCdsConcept(procedureProposal.getId(), new ProcedureCriticalityConcept(), 
								conceptLists.procedureCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		}
		
		//scheduled procedure 	
		for (ScheduledProcedure scheduledProcedure : factLists.internalScheduledProcedureList)
		{
			//templateId
			if (scheduledProcedure.getTemplateId() != null){
				for (String oneTemplateId : scheduledProcedure.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(scheduledProcedure.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (scheduledProcedure.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						scheduledProcedure.getDataSourceType().getCodeSystem(), scheduledProcedure.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(scheduledProcedure.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure code
			if (scheduledProcedure.getProcedureCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedure = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						scheduledProcedure.getProcedureCode().getCodeSystem(), scheduledProcedure.getProcedureCode().getCode()));
				if (matchingOpenCdsConceptsProcedure != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedure)
					{
						populateVmrOpenCdsConcept(scheduledProcedure.getId(), new ProcedureConcept(), 
								conceptLists.procedureConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure method
			if (scheduledProcedure.getProcedureMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedureMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						scheduledProcedure.getProcedureMethod().getCodeSystem(), scheduledProcedure.getProcedureMethod().getCode()));
				if (matchingOpenCdsConceptsProcedureMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedureMethod)
					{
						populateVmrOpenCdsConcept(scheduledProcedure.getId(), new ProcedureConcept(), 
								conceptLists.procedureMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (scheduledProcedure.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						scheduledProcedure.getApproachBodySite().getBodySiteCode().getCodeSystem(), scheduledProcedure.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(scheduledProcedure.getId(), new ProcedureApproachBodySiteConcept(), 
								conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (scheduledProcedure.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							scheduledProcedure.getApproachBodySite().getLaterality().getCodeSystem(), scheduledProcedure.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(scheduledProcedure.getId(), new ProcedureApproachBodySiteLateralityConcept(), 
									conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//target body site
			if (scheduledProcedure.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						scheduledProcedure.getTargetBodySite().getBodySiteCode().getCodeSystem(), scheduledProcedure.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(scheduledProcedure.getId(), new ProcedureTargetBodySiteConcept(), 
								conceptLists.procedureTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (scheduledProcedure.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							scheduledProcedure.getTargetBodySite().getLaterality().getCodeSystem(), scheduledProcedure.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(scheduledProcedure.getId(), new ProcedureTargetBodySiteLateralityConcept(), 
									conceptLists.procedureTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//undelivered procedure 	
		for (UndeliveredProcedure undeliveredProcedure : factLists.internalUndeliveredProcedureList)
		{
			//templateId
			if (undeliveredProcedure.getTemplateId() != null){
				for (String oneTemplateId : undeliveredProcedure.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (undeliveredProcedure.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsAdverseEventDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredProcedure.getDataSourceType().getCodeSystem(), undeliveredProcedure.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsAdverseEventDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsAdverseEventDataSource)
					{
						populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure code
			if (undeliveredProcedure.getProcedureCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedure = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredProcedure.getProcedureCode().getCodeSystem(), undeliveredProcedure.getProcedureCode().getCode()));
				if (matchingOpenCdsConceptsProcedure != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedure)
					{
						populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new ProcedureConcept(), 
								conceptLists.procedureConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//procedure method
			if (undeliveredProcedure.getProcedureMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsProcedureMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredProcedure.getProcedureMethod().getCodeSystem(), undeliveredProcedure.getProcedureMethod().getCode()));
				if (matchingOpenCdsConceptsProcedureMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsProcedureMethod)
					{
						populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new ProcedureConcept(), 
								conceptLists.procedureMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (undeliveredProcedure.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredProcedure.getApproachBodySite().getBodySiteCode().getCodeSystem(), undeliveredProcedure.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new ProcedureApproachBodySiteConcept(), 
								conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (undeliveredProcedure.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredProcedure.getApproachBodySite().getLaterality().getCodeSystem(), undeliveredProcedure.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new ProcedureApproachBodySiteLateralityConcept(), 
									conceptLists.procedureApproachBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//target body site
			if (undeliveredProcedure.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredProcedure.getTargetBodySite().getBodySiteCode().getCodeSystem(), undeliveredProcedure.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new ProcedureTargetBodySiteConcept(), 
								conceptLists.procedureTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (undeliveredProcedure.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredProcedure.getTargetBodySite().getLaterality().getCodeSystem(), undeliveredProcedure.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new ProcedureTargetBodySiteLateralityConcept(), 
									conceptLists.procedureTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//undelivered procedure reason
			if (undeliveredProcedure.getReason() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsUndeliveredProcedureReason = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredProcedure.getReason().getCodeSystem(), undeliveredProcedure.getReason().getCode()));
				if (matchingOpenCdsConceptsUndeliveredProcedureReason != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsUndeliveredProcedureReason)
					{
						populateVmrOpenCdsConcept(undeliveredProcedure.getId(), new UndeliveredProcedureReasonConcept(), 
								conceptLists.undeliveredProcedureReasonConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
		}
		
		//substance administration event 	
		for (SubstanceAdministrationEvent substanceAdministrationEvent : factLists.internalSubstanceAdministrationEventList)
		{
			//templateId
			if (substanceAdministrationEvent.getTemplateId() != null){
				for (String oneTemplateId : substanceAdministrationEvent.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (substanceAdministrationEvent.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationEvent.getDataSourceType().getCodeSystem(), substanceAdministrationEvent.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance administration general purpose
			if (substanceAdministrationEvent.getSubstanceAdministrationGeneralPurpose() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationEvent.getSubstanceAdministrationGeneralPurpose().getCodeSystem(), substanceAdministrationEvent.getSubstanceAdministrationGeneralPurpose().getCode()));
				if (matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose)
					{
						populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceAdministrationGeneralPurposeConcept(), 
								conceptLists.substanceAdministrationGeneralPurposeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (substanceAdministrationEvent.getApproachBodySite() != null) {
				if (substanceAdministrationEvent.getApproachBodySite().getBodySiteCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getApproachBodySite().getBodySiteCode().getCodeSystem(), substanceAdministrationEvent.getApproachBodySite().getBodySiteCode().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceAdministrationApproachBodySiteConcept(), 
									conceptLists.substanceAdministrationApproachBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//approach body site laterality
				if (substanceAdministrationEvent.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getApproachBodySite().getLaterality().getCodeSystem(), substanceAdministrationEvent.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceAdministrationApproachBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationApproachBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//delivery method
			if (substanceAdministrationEvent.getDeliveryMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationEvent.getDeliveryMethod().getCodeSystem(), substanceAdministrationEvent.getDeliveryMethod().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryMethod)
					{
						populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceDeliveryMethodConcept(), 
								conceptLists.substanceDeliveryMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//delivery route
			if (substanceAdministrationEvent.getDeliveryRoute() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryRoute = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationEvent.getDeliveryRoute().getCodeSystem(), substanceAdministrationEvent.getDeliveryRoute().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryRoute != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryRoute)
					{
						populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceDeliveryRouteConcept(), 
								conceptLists.substanceDeliveryRouteConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//dose type
			if (substanceAdministrationEvent.getDoseType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDoseType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationEvent.getDoseType().getCodeSystem(), substanceAdministrationEvent.getDoseType().getCode()));
				if (matchingOpenCdsConceptsDoseType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDoseType)
					{
						populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new DoseTypeConcept(), 
								conceptLists.doseTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//form
			if (substanceAdministrationEvent.getSubstance() != null) {
					if (substanceAdministrationEvent.getSubstance().getForm() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceForm = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getSubstance().getForm().getCodeSystem(), substanceAdministrationEvent.getSubstance().getForm().getCode()));
					if (matchingOpenCdsConceptsSubstanceForm != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceForm)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceFormConcept(), 
									conceptLists.substanceFormConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//manufacturer
				if (substanceAdministrationEvent.getSubstance().getManufacturer() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceManufacturer = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getSubstance().getManufacturer().getCodeSystem(), substanceAdministrationEvent.getSubstance().getManufacturer().getCode()));
					if (matchingOpenCdsConceptsSubstanceManufacturer != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceManufacturer)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new ManufacturerConcept(), 
									conceptLists.manufacturerConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance 
				if (substanceAdministrationEvent.getSubstance().getSubstanceCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstance = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getSubstance().getSubstanceCode().getCodeSystem(), substanceAdministrationEvent.getSubstance().getSubstanceCode().getCode()));
					if (matchingOpenCdsConceptsSubstance != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstance)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new ImmunizationConcept(), 
									conceptLists.immunizationConceptList, matchingOpenCdsConcept);						
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new MedicationConcept(), 
									conceptLists.medicationConceptList, matchingOpenCdsConcept);						
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new MedicationClassConcept(), 
									conceptLists.medicationClassConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance brand code
				if (substanceAdministrationEvent.getSubstance().getSubstanceBrandCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceBrand = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getSubstance().getSubstanceBrandCode().getCodeSystem(), substanceAdministrationEvent.getSubstance().getSubstanceBrandCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceBrand != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceBrand)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new BrandedMedicationConcept(), 
									conceptLists.brandedMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance generic code
				if (substanceAdministrationEvent.getSubstance().getSubstanceGenericCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceGeneric = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getSubstance().getSubstanceGenericCode().getCodeSystem(), substanceAdministrationEvent.getSubstance().getSubstanceGenericCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceGeneric != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceGeneric)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new GenericMedicationConcept(), 
									conceptLists.genericMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		
			//information attestation type
			if (substanceAdministrationEvent.getInformationAttestationType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsInformationAttestationType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationEvent.getInformationAttestationType().getCodeSystem(), substanceAdministrationEvent.getInformationAttestationType().getCode()));
				if (matchingOpenCdsConceptsInformationAttestationType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsInformationAttestationType)
					{
						populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new InformationAttestationTypeConcept(), 
								conceptLists.informationAttestationTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//target body site
			if (substanceAdministrationEvent.getTargetBodySite() != null) {
				if (substanceAdministrationEvent.getTargetBodySite().getBodySiteCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getTargetBodySite().getBodySiteCode().getCodeSystem(), substanceAdministrationEvent.getTargetBodySite().getBodySiteCode().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceAdministrationTargetBodySiteConcept(), 
									conceptLists.substanceAdministrationTargetBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//target body site laterality
				if (substanceAdministrationEvent.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationEvent.getTargetBodySite().getLaterality().getCodeSystem(), substanceAdministrationEvent.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceAdministrationEvent.getId(), new SubstanceAdministrationTargetBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//substance administration order 	
		for (SubstanceAdministrationOrder substanceAdministrationOrder : factLists.internalSubstanceAdministrationOrderList)
		{
			//templateId
			if (substanceAdministrationOrder.getTemplateId() != null){
				for (String oneTemplateId : substanceAdministrationOrder.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (substanceAdministrationOrder.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getDataSourceType().getCodeSystem(), substanceAdministrationOrder.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance administration general purpose
			if (substanceAdministrationOrder.getSubstanceAdministrationGeneralPurpose() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getSubstanceAdministrationGeneralPurpose().getCodeSystem(), substanceAdministrationOrder.getSubstanceAdministrationGeneralPurpose().getCode()));
				if (matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceAdministrationGeneralPurposeConcept(), 
								conceptLists.substanceAdministrationGeneralPurposeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (substanceAdministrationOrder.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getApproachBodySite().getBodySiteCode().getCodeSystem(), substanceAdministrationOrder.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceAdministrationApproachBodySiteConcept(), 
								conceptLists.substanceAdministrationApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (substanceAdministrationOrder.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationOrder.getApproachBodySite().getLaterality().getCodeSystem(), substanceAdministrationOrder.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceAdministrationApproachBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationApproachBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//delivery method
			if (substanceAdministrationOrder.getDeliveryMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getDeliveryMethod().getCodeSystem(), substanceAdministrationOrder.getDeliveryMethod().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryMethod)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceDeliveryMethodConcept(), 
								conceptLists.substanceDeliveryMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//delivery route
			if (substanceAdministrationOrder.getDeliveryRoute() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryRoute = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getDeliveryRoute().getCodeSystem(), substanceAdministrationOrder.getDeliveryRoute().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryRoute != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryRoute)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceDeliveryRouteConcept(), 
								conceptLists.substanceDeliveryRouteConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//dose type
			if (substanceAdministrationOrder.getDoseType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDoseType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getDoseType().getCodeSystem(), substanceAdministrationOrder.getDoseType().getCode()));
				if (matchingOpenCdsConceptsDoseType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDoseType)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new DoseTypeConcept(), 
								conceptLists.doseTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance 
			if (substanceAdministrationOrder.getSubstance() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstance = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getSubstance().getSubstanceCode().getCodeSystem(), substanceAdministrationOrder.getSubstance().getSubstanceCode().getCode()));
				if (matchingOpenCdsConceptsSubstance != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstance)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new ImmunizationConcept(), 
								conceptLists.immunizationConceptList, matchingOpenCdsConcept);						
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new MedicationConcept(), 
								conceptLists.medicationConceptList, matchingOpenCdsConcept);						
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new MedicationClassConcept(), 
								conceptLists.medicationClassConceptList, matchingOpenCdsConcept);						
					}
				}
		
				//form
				if (substanceAdministrationOrder.getSubstance().getForm() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceForm = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationOrder.getSubstance().getForm().getCodeSystem(), substanceAdministrationOrder.getSubstance().getForm().getCode()));
					if (matchingOpenCdsConceptsSubstanceForm != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceForm)
						{
							populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceFormConcept(), 
									conceptLists.substanceFormConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//manufacturer
				if (substanceAdministrationOrder.getSubstance().getManufacturer() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceManufacturer = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationOrder.getSubstance().getManufacturer().getCodeSystem(), substanceAdministrationOrder.getSubstance().getManufacturer().getCode()));
					if (matchingOpenCdsConceptsSubstanceManufacturer != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceManufacturer)
						{
							populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new ManufacturerConcept(), 
									conceptLists.manufacturerConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance brand code
				if (substanceAdministrationOrder.getSubstance().getSubstanceBrandCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceBrand = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationOrder.getSubstance().getSubstanceBrandCode().getCodeSystem(), substanceAdministrationOrder.getSubstance().getSubstanceBrandCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceBrand != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceBrand)
						{
							populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new BrandedMedicationConcept(), 
									conceptLists.brandedMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance generic code
				if (substanceAdministrationOrder.getSubstance().getSubstanceGenericCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceGeneric = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationOrder.getSubstance().getSubstanceGenericCode().getCodeSystem(), substanceAdministrationOrder.getSubstance().getSubstanceGenericCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceGeneric != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceGeneric)
						{
							populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new GenericMedicationConcept(), 
									conceptLists.genericMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		
			//criticality
			if (substanceAdministrationOrder.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceAdministrationCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getCriticality().getCodeSystem(), substanceAdministrationOrder.getCriticality().getCode()));
				if (matchingOpenCdsConceptsSubstanceAdministrationCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceAdministrationCriticality)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceAdministrationCriticalityConcept(), 
								conceptLists.substanceAdministrationCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//dosing sig
			if (substanceAdministrationOrder.getDosingSig() != null) {
				for (CD dosingSig : substanceAdministrationOrder.getDosingSig()) {
					if (dosingSig != null) {
						HashSet<ObjectPair> matchingOpenCdsConceptsDosingSig = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
								dosingSig.getCodeSystem(), dosingSig.getCode()));
						if (matchingOpenCdsConceptsDosingSig != null) {
							for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDosingSig)
							{
								populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new DosingSigConcept(), 
										conceptLists.dosingSigConceptList, matchingOpenCdsConcept);						
							}
						}
					}
				}
			}
		
			//target body site
			if (substanceAdministrationOrder.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationOrder.getTargetBodySite().getBodySiteCode().getCodeSystem(), substanceAdministrationOrder.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceAdministrationTargetBodySiteConcept(), 
								conceptLists.substanceAdministrationTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (substanceAdministrationOrder.getTargetBodySite() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationOrder.getTargetBodySite().getLaterality().getCodeSystem(), substanceAdministrationOrder.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceAdministrationOrder.getId(), new SubstanceAdministrationTargetBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//substance administration proposal 	
		for (SubstanceAdministrationProposal substanceAdministrationProposal : factLists.internalSubstanceAdministrationProposalList)
		{
			//templateId
			if (substanceAdministrationProposal.getTemplateId() != null){
				for (String oneTemplateId : substanceAdministrationProposal.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (substanceAdministrationProposal.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationProposal.getDataSourceType().getCodeSystem(), substanceAdministrationProposal.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance administration general purpose
			if (substanceAdministrationProposal.getSubstanceAdministrationGeneralPurpose() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationProposal.getSubstanceAdministrationGeneralPurpose().getCodeSystem(), substanceAdministrationProposal.getSubstanceAdministrationGeneralPurpose().getCode()));
				if (matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose)
					{
						populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceAdministrationGeneralPurposeConcept(), 
								conceptLists.substanceAdministrationGeneralPurposeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (substanceAdministrationProposal.getApproachBodySite() != null) {
				if (substanceAdministrationProposal.getApproachBodySite().getBodySiteCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getApproachBodySite().getBodySiteCode().getCodeSystem(), substanceAdministrationProposal.getApproachBodySite().getBodySiteCode().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceAdministrationApproachBodySiteConcept(), 
									conceptLists.substanceAdministrationApproachBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//approach body site laterality
				if (substanceAdministrationProposal.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getApproachBodySite().getLaterality().getCodeSystem(), substanceAdministrationProposal.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceAdministrationApproachBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationApproachBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//delivery method
			if (substanceAdministrationProposal.getDeliveryMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationProposal.getDeliveryMethod().getCodeSystem(), substanceAdministrationProposal.getDeliveryMethod().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryMethod)
					{
						populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceDeliveryMethodConcept(), 
								conceptLists.substanceDeliveryMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//delivery route
			if (substanceAdministrationProposal.getDeliveryRoute() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryRoute = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationProposal.getDeliveryRoute().getCodeSystem(), substanceAdministrationProposal.getDeliveryRoute().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryRoute != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryRoute)
					{
						populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceDeliveryRouteConcept(), 
								conceptLists.substanceDeliveryRouteConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//dose type
			if (substanceAdministrationProposal.getDoseType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDoseType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationProposal.getDoseType().getCodeSystem(), substanceAdministrationProposal.getDoseType().getCode()));
				if (matchingOpenCdsConceptsDoseType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDoseType)
					{
						populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new DoseTypeConcept(), 
								conceptLists.doseTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//form
			if (substanceAdministrationProposal.getSubstance() != null) {
				if (substanceAdministrationProposal.getSubstance().getForm() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceForm = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getSubstance().getForm().getCodeSystem(), substanceAdministrationProposal.getSubstance().getForm().getCode()));
					if (matchingOpenCdsConceptsSubstanceForm != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceForm)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceFormConcept(), 
									conceptLists.substanceFormConceptList, matchingOpenCdsConcept);						
						}
					}
				}
		
				//manufacturer
				if (substanceAdministrationProposal.getSubstance().getManufacturer() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceManufacturer = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getSubstance().getManufacturer().getCodeSystem(), substanceAdministrationProposal.getSubstance().getManufacturer().getCode()));
					if (matchingOpenCdsConceptsSubstanceManufacturer != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceManufacturer)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new ManufacturerConcept(), 
									conceptLists.manufacturerConceptList, matchingOpenCdsConcept);						
						}
					}
				}
		
				//substance 
				if (substanceAdministrationProposal.getSubstance().getSubstanceCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstance = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getSubstance().getSubstanceCode().getCodeSystem(), substanceAdministrationProposal.getSubstance().getSubstanceCode().getCode()));
					if (matchingOpenCdsConceptsSubstance != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstance)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new ImmunizationConcept(), 
									conceptLists.immunizationConceptList, matchingOpenCdsConcept);						
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new MedicationConcept(), 
									conceptLists.medicationConceptList, matchingOpenCdsConcept);						
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new MedicationClassConcept(), 
									conceptLists.medicationClassConceptList, matchingOpenCdsConcept);						
						}
					}
				}
		
				//substance brand code
				if (substanceAdministrationProposal.getSubstance().getSubstanceBrandCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceBrand = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getSubstance().getSubstanceBrandCode().getCodeSystem(), substanceAdministrationProposal.getSubstance().getSubstanceBrandCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceBrand != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceBrand)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new BrandedMedicationConcept(), 
									conceptLists.brandedMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
		
				//substance generic code
				if (substanceAdministrationProposal.getSubstance().getSubstanceGenericCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceGeneric = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getSubstance().getSubstanceGenericCode().getCodeSystem(), substanceAdministrationProposal.getSubstance().getSubstanceGenericCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceGeneric != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceGeneric)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new GenericMedicationConcept(), 
									conceptLists.genericMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		
			//criticality
			if (substanceAdministrationProposal.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceAdministrationCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceAdministrationProposal.getCriticality().getCodeSystem(), substanceAdministrationProposal.getCriticality().getCode()));
				if (matchingOpenCdsConceptsSubstanceAdministrationCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceAdministrationCriticality)
					{
						populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceAdministrationCriticalityConcept(), 
								conceptLists.substanceAdministrationCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//target body site
			if (substanceAdministrationProposal.getTargetBodySite() != null) {
				if (substanceAdministrationProposal.getTargetBodySite().getBodySiteCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getTargetBodySite().getBodySiteCode().getCodeSystem(), substanceAdministrationProposal.getTargetBodySite().getBodySiteCode().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceAdministrationTargetBodySiteConcept(), 
									conceptLists.substanceAdministrationTargetBodySiteConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//target body site laterality
				if (substanceAdministrationProposal.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceAdministrationProposal.getTargetBodySite().getLaterality().getCodeSystem(), substanceAdministrationProposal.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceAdministrationProposal.getId(), new SubstanceAdministrationTargetBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//substance dispensation event 	
		for (SubstanceDispensationEvent substanceDispensationEvent : factLists.internalSubstanceDispensationEventList)
		{
			//templateId
			if (substanceDispensationEvent.getTemplateId() != null){
				for (String oneTemplateId : substanceDispensationEvent.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (substanceDispensationEvent.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getDataSourceType().getCodeSystem(), substanceDispensationEvent.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance administration general purpose
			if (substanceDispensationEvent.getSubstanceAdministrationGeneralPurpose() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getSubstanceAdministrationGeneralPurpose().getCodeSystem(), substanceDispensationEvent.getSubstanceAdministrationGeneralPurpose().getCode()));
				if (matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceAdministrationGeneralPurposeConcept(), 
								conceptLists.substanceAdministrationGeneralPurposeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (substanceDispensationEvent.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getApproachBodySite().getBodySiteCode().getCodeSystem(), substanceDispensationEvent.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceAdministrationApproachBodySiteConcept(), 
								conceptLists.substanceAdministrationApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (substanceDispensationEvent.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceDispensationEvent.getApproachBodySite().getLaterality().getCodeSystem(), substanceDispensationEvent.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceAdministrationApproachBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationApproachBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//delivery method
			if (substanceDispensationEvent.getDeliveryMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getDeliveryMethod().getCodeSystem(), substanceDispensationEvent.getDeliveryMethod().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryMethod)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceDeliveryMethodConcept(), 
								conceptLists.substanceDeliveryMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//delivery route
			if (substanceDispensationEvent.getDeliveryRoute() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryRoute = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getDeliveryRoute().getCodeSystem(), substanceDispensationEvent.getDeliveryRoute().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryRoute != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryRoute)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceDeliveryRouteConcept(), 
								conceptLists.substanceDeliveryRouteConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//dose type
			if (substanceDispensationEvent.getDoseType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDoseType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getDoseType().getCodeSystem(), substanceDispensationEvent.getDoseType().getCode()));
				if (matchingOpenCdsConceptsDoseType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDoseType)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new DoseTypeConcept(), 
								conceptLists.doseTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance 
			if (substanceDispensationEvent.getSubstance() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstance = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getSubstance().getSubstanceCode().getCodeSystem(), substanceDispensationEvent.getSubstance().getSubstanceCode().getCode()));
				if (matchingOpenCdsConceptsSubstance != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstance)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new ImmunizationConcept(), 
								conceptLists.immunizationConceptList, matchingOpenCdsConcept);						
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new MedicationConcept(), 
								conceptLists.medicationConceptList, matchingOpenCdsConcept);						
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new MedicationClassConcept(), 
								conceptLists.medicationClassConceptList, matchingOpenCdsConcept);						
					}
				}
			
				//form
				if (substanceDispensationEvent.getSubstance().getForm() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceForm = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceDispensationEvent.getSubstance().getForm().getCodeSystem(), substanceDispensationEvent.getSubstance().getForm().getCode()));
					if (matchingOpenCdsConceptsSubstanceForm != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceForm)
						{
							populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceFormConcept(), 
									conceptLists.substanceFormConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//manufacturer
				if (substanceDispensationEvent.getSubstance().getManufacturer() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceManufacturer = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceDispensationEvent.getSubstance().getManufacturer().getCodeSystem(), substanceDispensationEvent.getSubstance().getManufacturer().getCode()));
					if (matchingOpenCdsConceptsSubstanceManufacturer != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceManufacturer)
						{
							populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new ManufacturerConcept(), 
									conceptLists.manufacturerConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance brand code
				if (substanceDispensationEvent.getSubstance().getSubstanceBrandCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceBrand = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceDispensationEvent.getSubstance().getSubstanceBrandCode().getCodeSystem(), substanceDispensationEvent.getSubstance().getSubstanceBrandCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceBrand != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceBrand)
						{
							populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new BrandedMedicationConcept(), 
									conceptLists.brandedMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance generic code
				if (substanceDispensationEvent.getSubstance().getSubstanceGenericCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceGeneric = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceDispensationEvent.getSubstance().getSubstanceGenericCode().getCodeSystem(), substanceDispensationEvent.getSubstance().getSubstanceGenericCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceGeneric != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceGeneric)
						{
							populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new GenericMedicationConcept(), 
									conceptLists.genericMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		
			//target body site
			if (substanceDispensationEvent.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						substanceDispensationEvent.getTargetBodySite().getBodySiteCode().getCodeSystem(), substanceDispensationEvent.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceAdministrationTargetBodySiteConcept(), 
								conceptLists.substanceAdministrationTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (substanceDispensationEvent.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							substanceDispensationEvent.getTargetBodySite().getLaterality().getCodeSystem(), substanceDispensationEvent.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(substanceDispensationEvent.getId(), new SubstanceAdministrationTargetBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//undelivered substance administration 	
		for (UndeliveredSubstanceAdministration undeliveredSubstanceAdministration : factLists.internalUndeliveredSubstanceAdministrationList)
		{
			//templateId
			if (undeliveredSubstanceAdministration.getTemplateId() != null){
				for (String oneTemplateId : undeliveredSubstanceAdministration.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (undeliveredSubstanceAdministration.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getDataSourceType().getCodeSystem(), undeliveredSubstanceAdministration.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance administration general purpose
			if (undeliveredSubstanceAdministration.getSubstanceAdministrationGeneralPurpose() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getSubstanceAdministrationGeneralPurpose().getCodeSystem(), undeliveredSubstanceAdministration.getSubstanceAdministrationGeneralPurpose().getCode()));
				if (matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceAdministrationGeneralPurpose)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceAdministrationGeneralPurposeConcept(), 
								conceptLists.substanceAdministrationGeneralPurposeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//approach body site
			if (undeliveredSubstanceAdministration.getApproachBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getApproachBodySite().getBodySiteCode().getCodeSystem(), undeliveredSubstanceAdministration.getApproachBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsApproachBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteCode)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceAdministrationApproachBodySiteConcept(), 
								conceptLists.substanceAdministrationApproachBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//approach body site laterality
				if (undeliveredSubstanceAdministration.getApproachBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsApproachBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredSubstanceAdministration.getApproachBodySite().getLaterality().getCodeSystem(), undeliveredSubstanceAdministration.getApproachBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsApproachBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsApproachBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceAdministrationApproachBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationApproachBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}

			//delivery method
			if (undeliveredSubstanceAdministration.getDeliveryMethod() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryMethod = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getDeliveryMethod().getCodeSystem(), undeliveredSubstanceAdministration.getDeliveryMethod().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryMethod != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryMethod)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceDeliveryMethodConcept(), 
								conceptLists.substanceDeliveryMethodConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//delivery route
			if (undeliveredSubstanceAdministration.getDeliveryRoute() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceDeliveryRoute = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getDeliveryRoute().getCodeSystem(), undeliveredSubstanceAdministration.getDeliveryRoute().getCode()));
				if (matchingOpenCdsConceptsSubstanceDeliveryRoute != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceDeliveryRoute)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceDeliveryRouteConcept(), 
								conceptLists.substanceDeliveryRouteConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//dose type
			if (undeliveredSubstanceAdministration.getDoseType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDoseType = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getDoseType().getCodeSystem(), undeliveredSubstanceAdministration.getDoseType().getCode()));
				if (matchingOpenCdsConceptsDoseType != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDoseType)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new DoseTypeConcept(), 
								conceptLists.doseTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//substance 
			if (undeliveredSubstanceAdministration.getSubstance() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSubstance = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getSubstance().getSubstanceCode().getCodeSystem(), undeliveredSubstanceAdministration.getSubstance().getSubstanceCode().getCode()));
				if (matchingOpenCdsConceptsSubstance != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstance)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new ImmunizationConcept(), 
								conceptLists.immunizationConceptList, matchingOpenCdsConcept);						
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new MedicationConcept(), 
								conceptLists.medicationConceptList, matchingOpenCdsConcept);						
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new MedicationClassConcept(), 
								conceptLists.medicationClassConceptList, matchingOpenCdsConcept);						
					}
				}
			
				//form
				if (undeliveredSubstanceAdministration.getSubstance().getForm() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceForm = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredSubstanceAdministration.getSubstance().getForm().getCodeSystem(), undeliveredSubstanceAdministration.getSubstance().getForm().getCode()));
					if (matchingOpenCdsConceptsSubstanceForm != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceForm)
						{
							populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceFormConcept(), 
									conceptLists.substanceFormConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//manufacturer
				if (undeliveredSubstanceAdministration.getSubstance().getManufacturer() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceManufacturer = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredSubstanceAdministration.getSubstance().getManufacturer().getCodeSystem(), undeliveredSubstanceAdministration.getSubstance().getManufacturer().getCode()));
					if (matchingOpenCdsConceptsSubstanceManufacturer != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceManufacturer)
						{
							populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new ManufacturerConcept(), 
									conceptLists.manufacturerConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance brand code
				if (undeliveredSubstanceAdministration.getSubstance().getSubstanceBrandCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceBrand = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredSubstanceAdministration.getSubstance().getSubstanceBrandCode().getCodeSystem(), undeliveredSubstanceAdministration.getSubstance().getSubstanceBrandCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceBrand != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceBrand)
						{
							populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new BrandedMedicationConcept(), 
									conceptLists.brandedMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			
				//substance generic code
				if (undeliveredSubstanceAdministration.getSubstance().getSubstanceGenericCode() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsSubstanceGeneric = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredSubstanceAdministration.getSubstance().getSubstanceGenericCode().getCodeSystem(), undeliveredSubstanceAdministration.getSubstance().getSubstanceGenericCode().getCode()));
					if (matchingOpenCdsConceptsSubstanceGeneric != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSubstanceGeneric)
						{
							populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new GenericMedicationConcept(), 
									conceptLists.genericMedicationConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		
			//reason
			if (undeliveredSubstanceAdministration.getReason() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsUndeliveredSubstanceAdministrationReason = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getReason().getCodeSystem(), undeliveredSubstanceAdministration.getReason().getCode()));
				if (matchingOpenCdsConceptsUndeliveredSubstanceAdministrationReason != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsUndeliveredSubstanceAdministrationReason)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new UndeliveredSubstanceAdministrationReasonConcept(), 
								conceptLists.undeliveredSubstanceAdministrationReasonConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//target body site
			if (undeliveredSubstanceAdministration.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSubstanceAdministration.getTargetBodySite().getBodySiteCode().getCodeSystem(), undeliveredSubstanceAdministration.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceAdministrationTargetBodySiteConcept(), 
								conceptLists.substanceAdministrationTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (undeliveredSubstanceAdministration.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredSubstanceAdministration.getTargetBodySite().getLaterality().getCodeSystem(), undeliveredSubstanceAdministration.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(undeliveredSubstanceAdministration.getId(), new SubstanceAdministrationTargetBodySiteLateralityConcept(), 
									conceptLists.substanceAdministrationTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//supply event 	
		for (SupplyEvent supplyEvent : factLists.internalSupplyEventList)
		{
			//templateId
			if (supplyEvent.getTemplateId() != null){
				for (String oneTemplateId : supplyEvent.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(supplyEvent.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (supplyEvent.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyEvent.getDataSourceType().getCodeSystem(), supplyEvent.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(supplyEvent.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//supply
			if (supplyEvent.getSupplyCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSupply = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyEvent.getSupplyCode().getCodeSystem(), supplyEvent.getSupplyCode().getCode()));
				if (matchingOpenCdsConceptsSupply != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSupply)
					{
						populateVmrOpenCdsConcept(supplyEvent.getId(), new SupplyConcept(), 
								conceptLists.supplyConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//target body site
			if (supplyEvent.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyEvent.getTargetBodySite().getBodySiteCode().getCodeSystem(), supplyEvent.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(supplyEvent.getId(), new SupplyTargetBodySiteConcept(), 
								conceptLists.supplyTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
			
				//target body site laterality
				if (supplyEvent.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							supplyEvent.getTargetBodySite().getLaterality().getCodeSystem(), supplyEvent.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(supplyEvent.getId(), new SupplyTargetBodySiteLateralityConcept(), 
									conceptLists.supplyTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//supply order 	
		for (SupplyOrder supplyOrder : factLists.internalSupplyOrderList)
		{
			//templateId
			if (supplyOrder.getTemplateId() != null){
				for (String oneTemplateId : supplyOrder.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(supplyOrder.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (supplyOrder.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyOrder.getDataSourceType().getCodeSystem(), supplyOrder.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(supplyOrder.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//supply
			if (supplyOrder.getSupplyCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSupply = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyOrder.getSupplyCode().getCodeSystem(), supplyOrder.getSupplyCode().getCode()));
				if (matchingOpenCdsConceptsSupply != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSupply)
					{
						populateVmrOpenCdsConcept(supplyOrder.getId(), new SupplyConcept(), 
								conceptLists.supplyConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//criticality
			if (supplyOrder.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSupplyCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyOrder.getCriticality().getCodeSystem(), supplyOrder.getCriticality().getCode()));
				if (matchingOpenCdsConceptsSupplyCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSupplyCriticality)
					{
						populateVmrOpenCdsConcept(supplyOrder.getId(), new SupplyCriticalityConcept(), 
								conceptLists.supplyCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//target body site
			if (supplyOrder.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyOrder.getTargetBodySite().getBodySiteCode().getCodeSystem(), supplyOrder.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(supplyOrder.getId(), new SupplyTargetBodySiteConcept(), 
								conceptLists.supplyTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (supplyOrder.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							supplyOrder.getTargetBodySite().getLaterality().getCodeSystem(), supplyOrder.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(supplyOrder.getId(), new SupplyTargetBodySiteLateralityConcept(), 
									conceptLists.supplyTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//supply proposal 	
		for (SupplyProposal supplyProposal : factLists.internalSupplyProposalList)
		{
			//templateId
			if (supplyProposal.getTemplateId() != null){
				for (String oneTemplateId : supplyProposal.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(supplyProposal.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (supplyProposal.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyProposal.getDataSourceType().getCodeSystem(), supplyProposal.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(supplyProposal.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//supply
			if (supplyProposal.getSupplyCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSupply = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyProposal.getSupplyCode().getCodeSystem(), supplyProposal.getSupplyCode().getCode()));
				if (matchingOpenCdsConceptsSupply != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSupply)
					{
						populateVmrOpenCdsConcept(supplyProposal.getId(), new SupplyConcept(), 
								conceptLists.supplyConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//criticality
			if (supplyProposal.getCriticality() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSupplyCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyProposal.getCriticality().getCodeSystem(), supplyProposal.getCriticality().getCode()));
				if (matchingOpenCdsConceptsSupplyCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSupplyCriticality)
					{
						populateVmrOpenCdsConcept(supplyProposal.getId(), new SupplyCriticalityConcept(),
								conceptLists.supplyCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//target body site
			if (supplyProposal.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						supplyProposal.getTargetBodySite().getBodySiteCode().getCodeSystem(), supplyProposal.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(supplyProposal.getId(), new SupplyTargetBodySiteConcept(), 
								conceptLists.supplyTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (supplyProposal.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							supplyProposal.getTargetBodySite().getLaterality().getCodeSystem(), supplyProposal.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(supplyProposal.getId(), new SupplyTargetBodySiteLateralityConcept(), 
									conceptLists.supplyTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		
		//undelivered supply 	
		for (UndeliveredSupply undeliveredSupply : factLists.internalUndeliveredSupplyList)
		{
			//templateId
			if (undeliveredSupply.getTemplateId() != null){
				for (String oneTemplateId : undeliveredSupply.getTemplateId()) {					
					HashSet<ObjectPair> matchingOpenCdsConcepts = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							CodeSystems.CODE_SYSTEM_OID_OPENCDS_TEMPLATES, oneTemplateId ));
					if (matchingOpenCdsConcepts != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConcepts)
						{
							populateVmrOpenCdsConcept(undeliveredSupply.getId(), new ClinicalStatementTemplateConcept(), conceptLists.entityTemplateConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
			
			//data source type
			if (undeliveredSupply.getDataSourceType() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsDataSource = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSupply.getDataSourceType().getCodeSystem(), undeliveredSupply.getDataSourceType().getCode()));
				if (matchingOpenCdsConceptsDataSource != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsDataSource)
					{
						populateVmrOpenCdsConcept(undeliveredSupply.getId(), new DataSourceTypeConcept(), 
								conceptLists.dataSourceTypeConceptList, matchingOpenCdsConcept);						
					}
				}
			}
		
			//supply
			if (undeliveredSupply.getSupplyCode() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSupply = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSupply.getSupplyCode().getCodeSystem(), undeliveredSupply.getSupplyCode().getCode()));
				if (matchingOpenCdsConceptsSupply != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSupply)
					{
						populateVmrOpenCdsConcept(undeliveredSupply.getId(), new SupplyConcept(), 
								conceptLists.supplyConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//reason
			if (undeliveredSupply.getReason() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsSupplyCriticality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSupply.getReason().getCodeSystem(), undeliveredSupply.getReason().getCode()));
				if (matchingOpenCdsConceptsSupplyCriticality != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsSupplyCriticality)
					{
						populateVmrOpenCdsConcept(undeliveredSupply.getId(), new SupplyCriticalityConcept(), 
								conceptLists.supplyCriticalityConceptList, matchingOpenCdsConcept);						
					}
				}
			}
			
			//target body site
			if (undeliveredSupply.getTargetBodySite() != null) {
				HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteCode = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
						undeliveredSupply.getTargetBodySite().getBodySiteCode().getCodeSystem(), undeliveredSupply.getTargetBodySite().getBodySiteCode().getCode()));
				if (matchingOpenCdsConceptsTargetBodySiteCode != null) {
					for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteCode)
					{
						populateVmrOpenCdsConcept(undeliveredSupply.getId(), new SupplyTargetBodySiteConcept(), conceptLists.supplyTargetBodySiteConceptList, matchingOpenCdsConcept);						
					}
				}
				
				//target body site laterality
				if (undeliveredSupply.getTargetBodySite().getLaterality() != null) {
					HashSet<ObjectPair> matchingOpenCdsConceptsTargetBodySiteLaterality = myTerminologyManager.getMatchingOpenCDSConcepts(new org.opencds.common.terminology.CD(
							undeliveredSupply.getTargetBodySite().getLaterality().getCodeSystem(), undeliveredSupply.getTargetBodySite().getLaterality().getCode()));
					if (matchingOpenCdsConceptsTargetBodySiteLaterality != null) {
						for (ObjectPair matchingOpenCdsConcept : matchingOpenCdsConceptsTargetBodySiteLaterality)
						{
							populateVmrOpenCdsConcept(undeliveredSupply.getId(), new SupplyTargetBodySiteLateralityConcept(), conceptLists.supplyTargetBodySiteLateralityConceptList, matchingOpenCdsConcept);						
						}
					}
				}
			}
		}
		/* Concepts */		
		if (conceptLists.cDSInputTemplateConceptList.size() > 0) allFactLists.put("CDSInputTemplateConcept", conceptLists.cDSInputTemplateConceptList);
		if (conceptLists.cDSOutputTemplateConceptList.size() > 0) allFactLists.put("CDSOutputTemplateConcept", conceptLists.cDSOutputTemplateConceptList);
		if (conceptLists.vMRTemplateConceptList.size() > 0) allFactLists.put("VMRTemplateConcept", conceptLists.vMRTemplateConceptList);
		
		if (conceptLists.systemUserTypeConceptList.size() > 0) allFactLists.put("SystemUserTypeConcept", conceptLists.systemUserTypeConceptList);
		if (conceptLists.systemUserPreferredLanguageConceptList.size() > 0) allFactLists.put("SystemUserPreferredLanguageConcept", conceptLists.systemUserPreferredLanguageConceptList);
		if (conceptLists.informationRecipientTypeConceptList.size() > 0) allFactLists.put("InformationRecipientTypeConcept", conceptLists.informationRecipientTypeConceptList);
		if (conceptLists.informationRecipientPreferredLanguageConceptList.size() > 0) allFactLists.put("InformationRecipientPreferredLanguageConcept", conceptLists.informationRecipientPreferredLanguageConceptList);
		if (conceptLists.systemUserTaskContextConceptList.size() > 0) allFactLists.put("SystemUserTaskContextConcept", conceptLists.systemUserTaskContextConceptList);
		if (conceptLists.resourceTypeConceptList.size() > 0) allFactLists.put("ResourceTypeConcept", conceptLists.resourceTypeConceptList);
		
		
		if (conceptLists.entityTemplateConceptList.size() > 0) allFactLists.put("EntityTemplateConcept", conceptLists.entityTemplateConceptList);
		if (conceptLists.entityTypeConceptList.size() > 0) allFactLists.put("EntityTypeConcept", conceptLists.entityTypeConceptList);
		if (conceptLists.preferredLanguageConceptList.size() > 0) allFactLists.put("PreferredLanguageConcept", conceptLists.preferredLanguageConceptList);
		if (conceptLists.ethnicityConceptList.size() > 0) allFactLists.put("EthnicityConcept", conceptLists.ethnicityConceptList);
		if (conceptLists.genderConceptList.size() > 0) allFactLists.put("GenderConcept", conceptLists.genderConceptList);
		if (conceptLists.raceConceptList.size() > 0) allFactLists.put("RaceConcept", conceptLists.raceConceptList);
		
		if (conceptLists.clinicalStatementTemplateConceptList.size() > 0) allFactLists.put("ClinicalStatementTemplateConcept", conceptLists.clinicalStatementTemplateConceptList);
		if (conceptLists.dataSourceTypeConceptList.size() > 0) allFactLists.put("DataSourceTypeConcept", conceptLists.dataSourceTypeConceptList);
	
//		following deprecated des 2012-02-14
//		if (conceptLists.roleConceptList.size() > 0) allFactLists.put("RoleConcept", conceptLists.roleConceptList);
		if (conceptLists.clinicalStatementRelationshipConceptList.size() > 0) allFactLists.put("ClinicalStatementRelationshipConcept", conceptLists.clinicalStatementRelationshipConceptList);
		//ClinicalStatementEntityInRoleRelationshipConcept is actually named simply RoleConcept (see above)
//		if (conceptLists.clinicalStatementEntityInRoleRelationshipConceptList.size() > 0) allFactLists.put("ClinicalStatementEntityInRoleRelationshipConcept", conceptLists.clinicalStatementEntityInRoleRelationshipConceptList);
		if (conceptLists.entityRelationshipConceptList.size() > 0) allFactLists.put("EntityRelationshipConcept", conceptLists.entityRelationshipConceptList);
		if (conceptLists.evaluatedPersonRelationshipConceptList.size() > 0) allFactLists.put("EvaluatedPersonRelationshipConcept", conceptLists.evaluatedPersonRelationshipConceptList);
		
		if (conceptLists.adverseEventAffectedBodySiteConceptList.size() > 0) allFactLists.put("AdverseEventAffectedBodySiteConcept", conceptLists.adverseEventAffectedBodySiteConceptList);
		if (conceptLists.adverseEventAffectedBodySiteLateralityConceptList.size() > 0) allFactLists.put("AdverseEventAffectedBodySiteLateralityConcept", conceptLists.adverseEventAffectedBodySiteLateralityConceptList);
		if (conceptLists.adverseEventAgentConceptList.size() > 0) allFactLists.put("AdverseEventAgentConcept", conceptLists.adverseEventAgentConceptList);
		if (conceptLists.adverseEventCriticalityConceptList.size() > 0) allFactLists.put("AdverseEventCriticalityConcept", conceptLists.adverseEventCriticalityConceptList);
		if (conceptLists.adverseEventConceptList.size() > 0) allFactLists.put("AdverseEventConcept", conceptLists.adverseEventConceptList);
		if (conceptLists.adverseEventSeverityConceptList.size() > 0) allFactLists.put("AdverseEventSeverityConcept", conceptLists.adverseEventSeverityConceptList);
		if (conceptLists.adverseEventStatusConceptList.size() > 0) allFactLists.put("AdverseEventStatusConcept", conceptLists.adverseEventStatusConceptList);
		
		if (conceptLists.encounterTypeConceptList.size() > 0) allFactLists.put("EncounterTypeConcept", conceptLists.encounterTypeConceptList);
		if (conceptLists.encounterCriticalityConceptList.size() > 0) allFactLists.put("EncounterCriticalityConcept", conceptLists.encounterCriticalityConceptList);
		
		if (conceptLists.goalCodedValueConceptList.size() > 0) allFactLists.put("GoalCodedValueConcept", conceptLists.goalCodedValueConceptList);
		if (conceptLists.goalCriticalityConceptList.size() > 0) allFactLists.put("GoalCriticalityConcept", conceptLists.goalCriticalityConceptList);
		if (conceptLists.goalFocusConceptList.size() > 0) allFactLists.put("GoalFocusConcept", conceptLists.goalFocusConceptList);
		if (conceptLists.goalStatusConceptList.size() > 0) allFactLists.put("GoalStatusConcept", conceptLists.goalStatusConceptList);
		if (conceptLists.goalTargetBodySiteConceptList.size() > 0) allFactLists.put("GoalTargetBodySiteConcept", conceptLists.goalTargetBodySiteConceptList);
		if (conceptLists.goalTargetBodySiteLateralityConceptList.size() > 0) allFactLists.put("GoalTargetBodySiteLateralityConcept", conceptLists.goalTargetBodySiteLateralityConceptList);		
		
		if (conceptLists.observationCodedValueConceptList.size() > 0) allFactLists.put("ObservationCodedValueConcept", conceptLists.observationCodedValueConceptList);
		if (conceptLists.observationCriticalityConceptList.size() > 0) allFactLists.put("ObservationCriticalityConcept", conceptLists.observationCriticalityConceptList);
		if (conceptLists.observationFocusConceptList.size() > 0) allFactLists.put("ObservationFocusConcept", conceptLists.observationFocusConceptList);
		if (conceptLists.observationInterpretationConceptList.size() > 0) allFactLists.put("ObservationInterpretationConcept", conceptLists.observationInterpretationConceptList);
		if (conceptLists.observationMethodConceptList.size() > 0) allFactLists.put("ObservationMethodConcept", conceptLists.observationMethodConceptList);
		if (conceptLists.observationTargetBodySiteConceptList.size() > 0) allFactLists.put("ObservationTargetBodySiteConcept", conceptLists.observationTargetBodySiteConceptList);
		if (conceptLists.observationTargetBodySiteLateralityConceptList.size() > 0) allFactLists.put("ObservationTargetBodySiteLateralityConcept", conceptLists.observationTargetBodySiteLateralityConceptList);
		if (conceptLists.observationUnconductedReasonConceptList.size() > 0) allFactLists.put("ObservationUnconductedReasonConcept", conceptLists.observationUnconductedReasonConceptList);
		
		if (conceptLists.problemAffectedBodySiteConceptList.size() > 0) allFactLists.put("ProblemAffectedBodySiteConcept", conceptLists.problemAffectedBodySiteConceptList);
		if (conceptLists.problemAffectedBodySiteLateralityConceptList.size() > 0) allFactLists.put("ProblemAffectedBodySiteLateralityConcept", conceptLists.problemAffectedBodySiteLateralityConceptList);
		if (conceptLists.problemConceptList.size() > 0) allFactLists.put("ProblemConcept", conceptLists.problemConceptList);
		if (conceptLists.problemImportanceConceptList.size() > 0) allFactLists.put("ProblemImportanceConcept", conceptLists.problemImportanceConceptList);
		if (conceptLists.problemSeverityConceptList.size() > 0) allFactLists.put("ProblemSeverityConcept", conceptLists.problemSeverityConceptList);
		if (conceptLists.problemStatusConceptList.size() > 0) allFactLists.put("ProblemStatusConcept", conceptLists.problemStatusConceptList);
		
		if (conceptLists.procedureApproachBodySiteConceptList.size() > 0) allFactLists.put("ProcedureApproachBodySiteConcept", conceptLists.procedureApproachBodySiteConceptList);
		if (conceptLists.procedureApproachBodySiteConceptList.size() > 0) allFactLists.put("ProcedureApproachBodySiteLateralityConcept", conceptLists.procedureApproachBodySiteConceptList);
		if (conceptLists.procedureConceptList.size() > 0) allFactLists.put("ProcedureConcept", conceptLists.procedureConceptList);
		if (conceptLists.procedureCriticalityConceptList.size() > 0) allFactLists.put("ProcedureCriticalityConcept", conceptLists.procedureCriticalityConceptList);
		if (conceptLists.procedureMethodConceptList.size() > 0) allFactLists.put("ProcedureMethodConcept", conceptLists.procedureMethodConceptList);
		if (conceptLists.procedureTargetBodySiteConceptList.size() > 0) allFactLists.put("UndeliveredProcedureReasonConcept", conceptLists.procedureTargetBodySiteConceptList);
		if (conceptLists.procedureTargetBodySiteLateralityConceptList.size() > 0) allFactLists.put("ProcedureTargetBodySiteConcept", conceptLists.procedureTargetBodySiteLateralityConceptList);
		if (conceptLists.undeliveredProcedureReasonConceptList.size() > 0) allFactLists.put("ProcedureTargetBodySiteLateralityConcept", conceptLists.undeliveredProcedureReasonConceptList);		
		
		if (conceptLists.substanceAdministrationApproachBodySiteConceptList.size() > 0) allFactLists.put("SubstanceAdministrationApproachBodySiteConcept", conceptLists.substanceAdministrationApproachBodySiteConceptList);
		if (conceptLists.substanceAdministrationApproachBodySiteLateralityConceptList.size() > 0) allFactLists.put("SubstanceAdministrationApproachBodySiteLateralityConcept", conceptLists.substanceAdministrationApproachBodySiteLateralityConceptList);
		if (conceptLists.substanceAdministrationGeneralPurposeConceptList.size() > 0) allFactLists.put("SubstanceAdministrationGeneralPurposeConcept", conceptLists.substanceAdministrationGeneralPurposeConceptList);
		if (conceptLists.medicationConceptList.size() > 0) allFactLists.put("MedicationConcept", conceptLists.medicationConceptList);
		if (conceptLists.immunizationConceptList.size() > 0) allFactLists.put("ImmunizationConcept", conceptLists.immunizationConceptList);
		if (conceptLists.medicationClassConceptList.size() > 0) allFactLists.put("MedicationClassConcept", conceptLists.medicationClassConceptList);
		if (conceptLists.substanceFormConceptList.size() > 0) allFactLists.put("SubstanceFormConcept", conceptLists.substanceFormConceptList);
		if (conceptLists.manufacturerConceptList.size() > 0) allFactLists.put("ManufacturerConcept", conceptLists.manufacturerConceptList);
		if (conceptLists.brandedMedicationConceptList.size() > 0) allFactLists.put("BrandedMedicationConcept", conceptLists.brandedMedicationConceptList);
		if (conceptLists.genericMedicationConceptList.size() > 0) allFactLists.put("GenericMedicationConcept", conceptLists.genericMedicationConceptList);
		if (conceptLists.substanceDeliveryMethodConceptList.size() > 0) allFactLists.put("SubstanceDeliveryMethodConcept", conceptLists.substanceDeliveryMethodConceptList);
		if (conceptLists.substanceDeliveryRouteConceptList.size() > 0) allFactLists.put("SubstanceDeliveryRouteConcept", conceptLists.substanceDeliveryRouteConceptList);
		if (conceptLists.doseTypeConceptList.size() > 0) allFactLists.put("DoseTypeConcept", conceptLists.doseTypeConceptList);
		if (conceptLists.informationAttestationTypeConceptList.size() > 0) allFactLists.put("InformationAttestationTypeConcept", conceptLists.informationAttestationTypeConceptList);
		if (conceptLists.dosingSigConceptList.size() > 0) allFactLists.put("DosingSigConcept", conceptLists.dosingSigConceptList);
		if (conceptLists.substanceAdministrationCriticalityConceptList.size() > 0) allFactLists.put("SubstanceAdministrationCriticalityConcept", conceptLists.substanceAdministrationCriticalityConceptList);
		if (conceptLists.substanceAdministrationTargetBodySiteConceptList.size() > 0) allFactLists.put("SubstanceAdministrationTargetBodySiteConcept", conceptLists.substanceAdministrationTargetBodySiteConceptList);
		if (conceptLists.substanceAdministrationTargetBodySiteLateralityConceptList.size() > 0) allFactLists.put("SubstanceAdministrationTargetBodySiteLateralityConcept", conceptLists.substanceAdministrationTargetBodySiteLateralityConceptList);
		if (conceptLists.undeliveredSubstanceAdministrationReasonConceptList.size() > 0) allFactLists.put("UndeliveredSubstanceAdministrationReasonConcept", conceptLists.undeliveredSubstanceAdministrationReasonConceptList);
		
		if (conceptLists.supplyConceptList.size() > 0) allFactLists.put("SupplyConcept", conceptLists.supplyConceptList);
		if (conceptLists.supplyCriticalityConceptList.size() > 0) allFactLists.put("SupplyCriticalityConcept", conceptLists.supplyCriticalityConceptList);
		if (conceptLists.supplyTargetBodySiteConceptList.size() > 0) allFactLists.put("SupplyTargetBodySiteConcept", conceptLists.supplyTargetBodySiteConceptList);
		if (conceptLists.supplyTargetBodySiteLateralityConceptList.size() > 0) allFactLists.put("SupplyTargetBodySiteLateralityConcept", conceptLists.supplyTargetBodySiteLateralityConceptList);
    }

    
	
	/**
	 * @param conceptTargetId
	 * @param vmrOpenCdsConceptToAddAfterPopulating
	 * @param vmrOpenCdsConceptList
	 * @param matchingOpenCdsConcept
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static synchronized void populateVmrOpenCdsConcept(String conceptTargetId, VmrOpenCdsConcept vmrOpenCdsConceptToAddAfterPopulating, List vmrOpenCdsConceptList, ObjectPair matchingOpenCdsConcept)
	{
		org.opencds.common.terminology.CD openCdsConcept = (org.opencds.common.terminology.CD) matchingOpenCdsConcept.getObject1();
		org.opencds.common.terminology.CD determinationMethod = (org.opencds.common.terminology.CD) matchingOpenCdsConcept.getObject2();
	
			vmrOpenCdsConceptToAddAfterPopulating.setId(MiscUtility.getUUIDAsString());
			vmrOpenCdsConceptToAddAfterPopulating.setConceptTargetId(conceptTargetId);
			vmrOpenCdsConceptToAddAfterPopulating.setOpenCdsConceptCode(openCdsConcept.getCode());
			vmrOpenCdsConceptToAddAfterPopulating.setDeterminationMethodCode(determinationMethod.getCode());
			vmrOpenCdsConceptToAddAfterPopulating.setDisplayName(openCdsConcept.getDisplayName());
			vmrOpenCdsConceptList.add( vmrOpenCdsConceptToAddAfterPopulating );	
			log.trace("concept: " + vmrOpenCdsConceptToAddAfterPopulating.toString());
		return;

	}
	
	
}
