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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventAffectedBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventAffectedBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventAgentConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventSeverityConcept;
import org.opencds.vmr.v1_0.internal.concepts.AdverseEventStatusConcept;
import org.opencds.vmr.v1_0.internal.concepts.BrandedMedicationConcept;
import org.opencds.vmr.v1_0.internal.concepts.CDSInputTemplateConcept;
import org.opencds.vmr.v1_0.internal.concepts.CDSOutputTemplateConcept;
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
import org.opencds.vmr.v1_0.internal.concepts.GoalCriticalityConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalFocusConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalStatusConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalTargetBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalTargetBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.ImmunizationConcept;
import org.opencds.vmr.v1_0.internal.concepts.InformationAttestationTypeConcept;
import org.opencds.vmr.v1_0.internal.concepts.InformationRecipientPreferredLanguageConcept;
import org.opencds.vmr.v1_0.internal.concepts.InformationRecipientTypeConcept;
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
import org.opencds.vmr.v1_0.internal.concepts.ProcedureMethodConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureTargetBodySiteConcept;
import org.opencds.vmr.v1_0.internal.concepts.ProcedureTargetBodySiteLateralityConcept;
import org.opencds.vmr.v1_0.internal.concepts.RaceConcept;
import org.opencds.vmr.v1_0.internal.concepts.ResourceTypeConcept;
//import org.opencds.vmr.v1_0.internal.concepts.RoleConcept;
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
import org.opencds.vmr.v1_0.internal.concepts.SystemUserPreferredLanguageConcept;
import org.opencds.vmr.v1_0.internal.concepts.SystemUserTaskContextConcept;
import org.opencds.vmr.v1_0.internal.concepts.SystemUserTypeConcept;
import org.opencds.vmr.v1_0.internal.concepts.GoalCodedValueConcept;
import org.opencds.vmr.v1_0.internal.concepts.UndeliveredProcedureReasonConcept;
import org.opencds.vmr.v1_0.internal.concepts.UndeliveredSubstanceAdministrationReasonConcept;
import org.opencds.vmr.v1_0.internal.concepts.VMRTemplateConcept;

/**
 * Collection of ConceptLists for input to Drools, moved to this class from BuildCDSInputFactLists.java
 * 
 * @author David Shields
 *
 */
public class ConceptLists 
{

    
	// ================= ConceptLists (in more or less alpha order by ClinicalStatement category) =======================//
/* Root templates */    
    public List<CDSInputTemplateConcept> 				cDSInputTemplateConceptList					= Collections.synchronizedList(new ArrayList<CDSInputTemplateConcept>());
    public List<CDSOutputTemplateConcept> 				cDSOutputTemplateConceptList				= Collections.synchronizedList(new ArrayList<CDSOutputTemplateConcept>());
    public List<VMRTemplateConcept> 					vMRTemplateConceptList						= Collections.synchronizedList(new ArrayList<VMRTemplateConcept>());
    
/* CDSInput */
    public List<SystemUserTypeConcept> 					systemUserTypeConceptList					= Collections.synchronizedList(new ArrayList<SystemUserTypeConcept>());
    public List<SystemUserPreferredLanguageConcept> 	systemUserPreferredLanguageConceptList		= Collections.synchronizedList(new ArrayList<SystemUserPreferredLanguageConcept>());
    public List<InformationRecipientTypeConcept> 		informationRecipientTypeConceptList			= Collections.synchronizedList(new ArrayList<InformationRecipientTypeConcept>());
    public List<InformationRecipientPreferredLanguageConcept> informationRecipientPreferredLanguageConceptList = Collections.synchronizedList(new ArrayList<InformationRecipientPreferredLanguageConcept>());
    public List<SystemUserTaskContextConcept> 			systemUserTaskContextConceptList			= Collections.synchronizedList(new ArrayList<SystemUserTaskContextConcept>());
    public List<ResourceTypeConcept> 					resourceTypeConceptList						= Collections.synchronizedList(new ArrayList<ResourceTypeConcept>());
    
/* Entities */
    public List<EntityTemplateConcept> 					entityTemplateConceptList					= Collections.synchronizedList(new ArrayList<EntityTemplateConcept>());
    public List<EntityTypeConcept> 						entityTypeConceptList						= Collections.synchronizedList(new ArrayList<EntityTypeConcept>());
	public List<PreferredLanguageConcept> 				preferredLanguageConceptList				= Collections.synchronizedList(new ArrayList<PreferredLanguageConcept>());
	public List<RaceConcept> 							raceConceptList								= Collections.synchronizedList(new ArrayList<RaceConcept>());
	public List<EthnicityConcept> 						ethnicityConceptList						= Collections.synchronizedList(new ArrayList<EthnicityConcept>());
	public List<GenderConcept> 							genderConceptList							= Collections.synchronizedList(new ArrayList<GenderConcept>());
	
/* ClinicalStatements */
    public List<ClinicalStatementTemplateConcept> 		clinicalStatementTemplateConceptList		= Collections.synchronizedList(new ArrayList<ClinicalStatementTemplateConcept>());
	public List<DataSourceTypeConcept> 					dataSourceTypeConceptList					= Collections.synchronizedList(new ArrayList<DataSourceTypeConcept>());
	
/* Relationships or Roles */
//	RoleConcept deprecated des 2012-02-11
//	public List<RoleConcept> 							roleConceptList								= Collections.synchronizedList(new ArrayList<RoleConcept>());
	public List<ClinicalStatementRelationshipConcept> 	clinicalStatementRelationshipConceptList	= Collections.synchronizedList(new ArrayList<ClinicalStatementRelationshipConcept>());
//	ClinicalStatementEntityInRoleRelationshipConcept is actually named simply RoleConcept (see above)
//	public List<ClinicalStatementEntityInRoleRelationshipConcept> 	clinicalStatementEntityInRoleRelationshipConceptList	= new Collections.synchronizedList(ArrayList<ClinicalStatementEntityInRoleRelationshipConcept>());
	public List<EvaluatedPersonRelationshipConcept> 	evaluatedPersonRelationshipConceptList		= Collections.synchronizedList(new ArrayList<EvaluatedPersonRelationshipConcept>());
	public List<EntityRelationshipConcept> 				entityRelationshipConceptList				= Collections.synchronizedList(new ArrayList<EntityRelationshipConcept>());
	
/* AdverseEvent */
	public List<AdverseEventAffectedBodySiteConcept>	adverseEventAffectedBodySiteConceptList		= Collections.synchronizedList(new ArrayList<AdverseEventAffectedBodySiteConcept>());
	public List<AdverseEventAffectedBodySiteLateralityConcept> adverseEventAffectedBodySiteLateralityConceptList = Collections.synchronizedList(new ArrayList<AdverseEventAffectedBodySiteLateralityConcept>());
	public List<AdverseEventAgentConcept>				adverseEventAgentConceptList 				= Collections.synchronizedList(new ArrayList<AdverseEventAgentConcept>());
	public List<AdverseEventCriticalityConcept> 		adverseEventCriticalityConceptList			= Collections.synchronizedList(new ArrayList<AdverseEventCriticalityConcept>());
	public List<AdverseEventConcept> 					adverseEventConceptList						= Collections.synchronizedList(new ArrayList<AdverseEventConcept>());
	public List<AdverseEventSeverityConcept> 			adverseEventSeverityConceptList				= Collections.synchronizedList(new ArrayList<AdverseEventSeverityConcept>());
	public List<AdverseEventStatusConcept> 				adverseEventStatusConceptList				= Collections.synchronizedList(new ArrayList<AdverseEventStatusConcept>());

/* Encounter */
	public List<EncounterTypeConcept> 					encounterTypeConceptList					= Collections.synchronizedList(new ArrayList<EncounterTypeConcept>());
	public List<EncounterCriticalityConcept> 			encounterCriticalityConceptList				= Collections.synchronizedList(new ArrayList<EncounterCriticalityConcept>());
	
/* Goal */
	public List<GoalFocusConcept> 						goalFocusConceptList						= Collections.synchronizedList(new ArrayList<GoalFocusConcept>());
	public List<GoalStatusConcept> 						goalStatusConceptList						= Collections.synchronizedList(new ArrayList<GoalStatusConcept>());
	public List<GoalCodedValueConcept> 					goalCodedValueConceptList					= Collections.synchronizedList(new ArrayList<GoalCodedValueConcept>());
	public List<GoalCriticalityConcept> 				goalCriticalityConceptList					= Collections.synchronizedList(new ArrayList<GoalCriticalityConcept>());
	public List<GoalTargetBodySiteConcept> 				goalTargetBodySiteConceptList				= Collections.synchronizedList(new ArrayList<GoalTargetBodySiteConcept>());
	public List<GoalTargetBodySiteLateralityConcept> 	goalTargetBodySiteLateralityConceptList		= Collections.synchronizedList(new ArrayList<GoalTargetBodySiteLateralityConcept>());
	
/* Observation */
	public List<ObservationCodedValueConcept> 			observationCodedValueConceptList			= Collections.synchronizedList(new ArrayList<ObservationCodedValueConcept>());
	public List<ObservationCriticalityConcept> 			observationCriticalityConceptList			= Collections.synchronizedList(new ArrayList<ObservationCriticalityConcept>());
	public List<ObservationFocusConcept> 				observationFocusConceptList					= Collections.synchronizedList(new ArrayList<ObservationFocusConcept>());
	public List<ObservationInterpretationConcept> 		observationInterpretationConceptList		= Collections.synchronizedList(new ArrayList<ObservationInterpretationConcept>());
	public List<ObservationMethodConcept> 				observationMethodConceptList				= Collections.synchronizedList(new ArrayList<ObservationMethodConcept>());
	public List<ObservationUnconductedReasonConcept> 	observationUnconductedReasonConceptList		= Collections.synchronizedList(new ArrayList<ObservationUnconductedReasonConcept>());
	public List<ObservationTargetBodySiteConcept> 		observationTargetBodySiteConceptList		= Collections.synchronizedList(new ArrayList<ObservationTargetBodySiteConcept>());
	public List<ObservationTargetBodySiteLateralityConcept> observationTargetBodySiteLateralityConceptList	= Collections.synchronizedList(new ArrayList<ObservationTargetBodySiteLateralityConcept>());
	
/* Problem */
	public List<ProblemAffectedBodySiteConcept> 		problemAffectedBodySiteConceptList			= Collections.synchronizedList(new ArrayList<ProblemAffectedBodySiteConcept>());
	public List<ProblemAffectedBodySiteLateralityConcept> problemAffectedBodySiteLateralityConceptList	= Collections.synchronizedList(new ArrayList<ProblemAffectedBodySiteLateralityConcept>());
	public List<ProblemConcept> 						problemConceptList							= Collections.synchronizedList(new ArrayList<ProblemConcept>());
	public List<ProblemImportanceConcept> 				problemImportanceConceptList				= Collections.synchronizedList(new ArrayList<ProblemImportanceConcept>());
	public List<ProblemSeverityConcept> 				problemSeverityConceptList					= Collections.synchronizedList(new ArrayList<ProblemSeverityConcept>());
	public List<ProblemStatusConcept> 					problemStatusConceptList					= Collections.synchronizedList(new ArrayList<ProblemStatusConcept>());
	
/* Procedure */
	public List<ProcedureApproachBodySiteConcept> 		procedureApproachBodySiteConceptList		= Collections.synchronizedList(new ArrayList<ProcedureApproachBodySiteConcept>());	
	public List<ProcedureApproachBodySiteLateralityConcept> procedureApproachBodySiteLateralityConceptList = Collections.synchronizedList(new ArrayList<ProcedureApproachBodySiteLateralityConcept>());	
	public List<ProcedureConcept> 						procedureConceptList						= Collections.synchronizedList(new ArrayList<ProcedureConcept>());
	public List<ProcedureCriticalityConcept> 			procedureCriticalityConceptList				= Collections.synchronizedList(new ArrayList<ProcedureCriticalityConcept>());
	public List<ProcedureMethodConcept> 				procedureMethodConceptList					= Collections.synchronizedList(new ArrayList<ProcedureMethodConcept>());
	public List<UndeliveredProcedureReasonConcept> 		undeliveredProcedureReasonConceptList		= Collections.synchronizedList(new ArrayList<UndeliveredProcedureReasonConcept>());	
	public List<ProcedureTargetBodySiteConcept> 		procedureTargetBodySiteConceptList			= Collections.synchronizedList(new ArrayList<ProcedureTargetBodySiteConcept>());	
	public List<ProcedureTargetBodySiteLateralityConcept> procedureTargetBodySiteLateralityConceptList	= Collections.synchronizedList(new ArrayList<ProcedureTargetBodySiteLateralityConcept>());	

/* SubstanceAdministration */
	public List<SubstanceAdministrationApproachBodySiteConcept> substanceAdministrationApproachBodySiteConceptList	= Collections.synchronizedList(new ArrayList<SubstanceAdministrationApproachBodySiteConcept>());
	public List<SubstanceAdministrationApproachBodySiteLateralityConcept> substanceAdministrationApproachBodySiteLateralityConceptList	= Collections.synchronizedList(new ArrayList<SubstanceAdministrationApproachBodySiteLateralityConcept>());
	public List<SubstanceAdministrationTargetBodySiteConcept> substanceAdministrationTargetBodySiteConceptList	= Collections.synchronizedList(new ArrayList<SubstanceAdministrationTargetBodySiteConcept>());
	public List<SubstanceAdministrationTargetBodySiteLateralityConcept> substanceAdministrationTargetBodySiteLateralityConceptList	= Collections.synchronizedList(new ArrayList<SubstanceAdministrationTargetBodySiteLateralityConcept>());
	public List<SubstanceAdministrationGeneralPurposeConcept> 	substanceAdministrationGeneralPurposeConceptList	= Collections.synchronizedList(new ArrayList<SubstanceAdministrationGeneralPurposeConcept>());
	public List<MedicationConcept> 						medicationConceptList						= Collections.synchronizedList(new ArrayList<MedicationConcept>());
	public List<ImmunizationConcept> 					immunizationConceptList						= Collections.synchronizedList(new ArrayList<ImmunizationConcept>());
	public List<MedicationClassConcept> 				medicationClassConceptList					= Collections.synchronizedList(new ArrayList<MedicationClassConcept>());
	public List<SubstanceFormConcept> 					substanceFormConceptList					= Collections.synchronizedList(new ArrayList<SubstanceFormConcept>());
	public List<ManufacturerConcept> 					manufacturerConceptList						= Collections.synchronizedList(new ArrayList<ManufacturerConcept>());
	public List<BrandedMedicationConcept> 				brandedMedicationConceptList				= Collections.synchronizedList(new ArrayList<BrandedMedicationConcept>());
	public List<GenericMedicationConcept> 				genericMedicationConceptList				= Collections.synchronizedList(new ArrayList<GenericMedicationConcept>());
	public List<SubstanceDeliveryMethodConcept> 		substanceDeliveryMethodConceptList			= Collections.synchronizedList(new ArrayList<SubstanceDeliveryMethodConcept>());
	public List<SubstanceDeliveryRouteConcept> 			substanceDeliveryRouteConceptList			= Collections.synchronizedList(new ArrayList<SubstanceDeliveryRouteConcept>());
	public List<DoseTypeConcept> 						doseTypeConceptList							= Collections.synchronizedList(new ArrayList<DoseTypeConcept>());
	public List<InformationAttestationTypeConcept> 		informationAttestationTypeConceptList		= Collections.synchronizedList(new ArrayList<InformationAttestationTypeConcept>());
	public List<DosingSigConcept> 						dosingSigConceptList						= Collections.synchronizedList(new ArrayList<DosingSigConcept>());
	public List<SubstanceAdministrationCriticalityConcept> substanceAdministrationCriticalityConceptList	= Collections.synchronizedList(new ArrayList<SubstanceAdministrationCriticalityConcept>());
	public List<UndeliveredSubstanceAdministrationReasonConcept> undeliveredSubstanceAdministrationReasonConceptList	= Collections.synchronizedList(new ArrayList<UndeliveredSubstanceAdministrationReasonConcept>());

/* Supply */
	public List<SupplyConcept> 							supplyConceptList							= Collections.synchronizedList(new ArrayList<SupplyConcept>());
	public List<SupplyCriticalityConcept> 				supplyCriticalityConceptList				= Collections.synchronizedList(new ArrayList<SupplyCriticalityConcept>());
	public List<SupplyTargetBodySiteConcept> 			supplyTargetBodySiteConceptList				= Collections.synchronizedList(new ArrayList<SupplyTargetBodySiteConcept>());
	public List<SupplyTargetBodySiteLateralityConcept> 	supplyTargetBodySiteLateralityConceptList	= Collections.synchronizedList(new ArrayList<SupplyTargetBodySiteLateralityConcept>());
    
    public synchronized void clearAllConceptLists() throws DSSRuntimeExceptionFault	
    {
    	// ================= ConceptLists (in more or less alpha order by ClinicalStatement category) =======================//
    	/* Root templates */    
    	    cDSInputTemplateConceptList.clear();
    	    cDSOutputTemplateConceptList.clear();
    	    vMRTemplateConceptList.clear();
    	    
    	/* CDSInput */
    	    systemUserTypeConceptList.clear();
    	    systemUserPreferredLanguageConceptList.clear();
    	    informationRecipientTypeConceptList.clear();
    	    informationRecipientPreferredLanguageConceptList.clear();
    	    systemUserTaskContextConceptList.clear();
    	    resourceTypeConceptList.clear();
    	    
    	/* Entities */
    	    entityTemplateConceptList.clear();
    	    entityTypeConceptList.clear();
    		preferredLanguageConceptList.clear();
    		raceConceptList.clear();
    		ethnicityConceptList.clear();
    		genderConceptList.clear();
    		
    	/* ClinicalStatements */
    	    clinicalStatementTemplateConceptList.clear();
    		dataSourceTypeConceptList.clear();
    		
    	/* Relationships or Roles */
//    		RoleConcept deprecated des 2012-02-11
//    		roleConceptList.clear();
    		clinicalStatementRelationshipConceptList.clear();
//    		ClinicalStatementEntityInRoleRelationshipConcept is actually named simply RoleConcept (see above)
//    		clinicalStatementEntityInRoleRelationshipConceptList.clear();
    		evaluatedPersonRelationshipConceptList.clear();
    		entityRelationshipConceptList.clear();
    		
    	/* AdverseEvent */
    		adverseEventAffectedBodySiteConceptList.clear();
    		adverseEventAffectedBodySiteLateralityConceptList.clear();
    		adverseEventAgentConceptList.clear();
    		adverseEventCriticalityConceptList.clear();
    		adverseEventConceptList.clear();
    		adverseEventSeverityConceptList.clear();
    		adverseEventStatusConceptList.clear();

    	/* Encounter */
    		encounterTypeConceptList.clear();
    		encounterCriticalityConceptList.clear();
    		
    	/* Goal */
    		goalFocusConceptList.clear();
    		goalStatusConceptList.clear();
    		goalCodedValueConceptList.clear();
    		goalCriticalityConceptList.clear();
    		goalTargetBodySiteConceptList.clear();
    		goalTargetBodySiteLateralityConceptList.clear();
    		
    	/* Observation */
    		observationCodedValueConceptList.clear();
    		observationCriticalityConceptList.clear();
    		observationFocusConceptList.clear();
    		observationInterpretationConceptList.clear();
    		observationMethodConceptList.clear();
    		observationUnconductedReasonConceptList.clear();
    		observationTargetBodySiteConceptList.clear();
    		observationTargetBodySiteLateralityConceptList.clear();
    		
    	/* Problem */
    		problemAffectedBodySiteConceptList.clear();
    		problemAffectedBodySiteLateralityConceptList.clear();
    		problemConceptList.clear();
    		problemImportanceConceptList.clear();
    		problemSeverityConceptList.clear();
    		problemStatusConceptList.clear();
    		
    	/* Procedure */
    		procedureApproachBodySiteConceptList.clear();	
    		procedureApproachBodySiteLateralityConceptList.clear();	
    		procedureConceptList.clear();
    		procedureCriticalityConceptList.clear();
    		procedureMethodConceptList.clear();
    		undeliveredProcedureReasonConceptList.clear();	
    		procedureTargetBodySiteConceptList.clear();	
    		procedureTargetBodySiteLateralityConceptList.clear();	

    	/* SubstanceAdministration */
    		substanceAdministrationApproachBodySiteConceptList.clear();
    		substanceAdministrationApproachBodySiteLateralityConceptList.clear();
    		substanceAdministrationTargetBodySiteConceptList.clear();
    		substanceAdministrationTargetBodySiteLateralityConceptList.clear();
    		substanceAdministrationGeneralPurposeConceptList.clear();
    		medicationConceptList.clear();
    		immunizationConceptList.clear();
    		medicationClassConceptList.clear();
    		substanceFormConceptList.clear();
    		manufacturerConceptList.clear();
    		brandedMedicationConceptList.clear();
    		genericMedicationConceptList.clear();
    		substanceDeliveryMethodConceptList.clear();
    		substanceDeliveryRouteConceptList.clear();
    		doseTypeConceptList.clear();
    		informationAttestationTypeConceptList.clear();
    		dosingSigConceptList.clear();
    		substanceAdministrationCriticalityConceptList.clear();
    		undeliveredSubstanceAdministrationReasonConceptList.clear();

    	/* Supply */
    		supplyConceptList.clear();
    		supplyCriticalityConceptList.clear();
    		supplyTargetBodySiteConceptList.clear();
    		supplyTargetBodySiteLateralityConceptList.clear();
    	
    }
}
