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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.comparators.CDDisplayNameComparator;
import org.opencds.common.config.OpencdsConfigurator;
import org.opencds.common.terminology.CD;
import org.opencds.common.terminology.OpenCDSConceptTypes;
import org.opencds.common.utilities.StringUtility;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;

/**
 * EnumerationUtility enables population of enumerations within the VMR.  
 * 
 * The approach used for accessing enumerations follows the approach used in the 
 * Drools Guvnor documentation at http://downloads.jboss.com/drools/docs/5.1.1.34858.FINAL/drools-guvnor/html_single/index.html.
 * This is done to facilitate use of the enumerations within Guvnor.
 * 
 * @author Kensaku Kawamoto
 *
 */
public class EnumerationUtility {
	
	public Map<String, List<String>> loadEnumerations() throws DSSRuntimeExceptionFault {
	    System.out.println("Loading enumerations.");
		
		SimpleTerminologyManager terminologyManager = SimpleTerminologyManager.getInstance();
		StringUtility stringUtility = StringUtility.getInstance();
	    
		CDDisplayNameComparator conceptComparator = new CDDisplayNameComparator();

		Map<String, List<String>> data = new HashMap<String, List<String>>();

		List<String> d = new ArrayList<String>();		
	    d.add(Calendar.YEAR + "=year(s)");
	    d.add(Calendar.MONTH + "=month(s)");
	    d.add(Calendar.WEEK_OF_YEAR + "=week(s)");
	    d.add(Calendar.DATE + "=day(s)");
	    d.add(Calendar.HOUR_OF_DAY + "=hour(s)");
	    d.add(Calendar.MINUTE + "=minute(s)");
	    d.add(Calendar.SECOND + "=second(s)");
	    data.put("EnumerationTarget.javaCalendarUnit", d);
	    
	    //use the same list for ages...
	    data.put("EvaluatedPersonAgeAtEvalTime.ageUnit", d);
	    
	    HashMap<String, HashSet<CD>> supportedConcepts = terminologyManager.getSupportedOpenCDSConcepts();
	    ArrayList<CD> conceptDeterminationMethods = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.CONCEPT_DETERMINATION_METHOD));
	    Collections.sort(conceptDeterminationMethods, conceptComparator);
	    
/* Root templates */   
	    ArrayList<CD> cDSInputTemplates = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.CDSINPUT_TEMPLATE));
	    ArrayList<CD> cDSOutputTemplates = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.CDSOUTPUT_TEMPLATE));
	    ArrayList<CD> vMRTemplates = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.VMR_TEMPLATE));
	    Collections.sort(cDSInputTemplates, conceptComparator);
	    Collections.sort(cDSOutputTemplates, conceptComparator);
	    Collections.sort(vMRTemplates, conceptComparator);
	    
/* CDSInput */
	    ArrayList<CD> informationRecipientPreferredLanguages = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.INFORMATION_RECIPIENT_PREFERRED_LANGUAGE));
	    ArrayList<CD> informationRecipientTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.INFORMATION_RECIPIENT_TYPE));
	    ArrayList<CD> resourceTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.RESOURCE_TYPE));
	    ArrayList<CD> systemUserPreferredLanguages = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SYSTEM_USER_PREFERRED_LANGUAGE));
	    ArrayList<CD> systemUserTaskContexts = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SYSTEM_USER_TASK_CONTEXT));
	    ArrayList<CD> systemUserTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SYSTEM_USER_TYPE));
	    Collections.sort(informationRecipientPreferredLanguages, conceptComparator);
	    Collections.sort(informationRecipientTypes, conceptComparator);
	    Collections.sort(resourceTypes, conceptComparator);
	    Collections.sort(systemUserPreferredLanguages, conceptComparator);
	    Collections.sort(systemUserTaskContexts, conceptComparator);
	    Collections.sort(systemUserTypes, conceptComparator);
	    
/* Entity */
	    ArrayList<CD> entityTemplates = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ENTITY_TEMPLATE));
	    ArrayList<CD> entityTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ENTITY_TYPE));
	    ArrayList<CD> ethnicities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ETHNICITY));
	    ArrayList<CD> genders = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GENDER));
	    ArrayList<CD> languages = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.LANGUAGE));
	    ArrayList<CD> races = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.RACE));
	    Collections.sort(entityTemplates, conceptComparator);
	    Collections.sort(entityTypes, conceptComparator);
	    Collections.sort(ethnicities, conceptComparator);
	    Collections.sort(genders, conceptComparator);
	    Collections.sort(languages, conceptComparator);
	    Collections.sort(races, conceptComparator);
		
/* ClinicalStatements */
	    ArrayList<CD> clinicalStatementTemplates = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.CLINICAL_STATEMENT_TEMPLATE));
	    ArrayList<CD> dataSourceTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.DATA_SOURCE_TYPE));
	    Collections.sort(clinicalStatementTemplates, conceptComparator);
	    Collections.sort(dataSourceTypes, conceptComparator);
		
/* Relationships or Roles */
	    ArrayList<CD> clinicalStatementRelationships = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.CLINICAL_STATEMENT_RELATIONSHIP));
	    ArrayList<CD> entityRelationships = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ENTITY_RELATIONSHIP));
	    ArrayList<CD> evaluatedPersonRelationships = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.EVALUATED_PERSON_RELATIONSHIP));
//	    ArrayList<CD> roles = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ROLE));
	    Collections.sort(clinicalStatementRelationships, conceptComparator);
	    Collections.sort(entityRelationships, conceptComparator);
	    Collections.sort(evaluatedPersonRelationships, conceptComparator);
//	    Collections.sort(roles, conceptComparator);
		
/* AdverseEvent */
	    ArrayList<CD> adverseEventAffectedBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ADVERSE_EVENT_AFFECTED_BODY_SITE));
		ArrayList<CD> adverseEventAffectedBodySitesLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ADVERSE_EVENT_AFFECTED_BODY_SITE_LATERALITY));
	    ArrayList<CD> adverseEventAgents = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ADVERSE_EVENT_AGENT));
	    ArrayList<CD> adverseEventCriticalities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ADVERSE_EVENT_CRITICALITY));
	    ArrayList<CD> adverseEvents = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ADVERSE_EVENT));
	    ArrayList<CD> adverseEventSeverities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ADVERSE_EVENT_SEVERITY));
	    ArrayList<CD> adverseEventStatuses = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ADVERSE_EVENT_STATUS));
	    Collections.sort(adverseEventAffectedBodySites, conceptComparator);
	    Collections.sort(adverseEventAffectedBodySitesLateralities, conceptComparator);
	    Collections.sort(adverseEventAgents, conceptComparator);
	    Collections.sort(adverseEventCriticalities, conceptComparator);
	    Collections.sort(adverseEvents, conceptComparator);
	    Collections.sort(adverseEventSeverities, conceptComparator);
	    Collections.sort(adverseEventStatuses, conceptComparator);

/* Encounter */
	    ArrayList<CD> encounterTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ENCOUNTER_TYPE));
	    ArrayList<CD> encounterCriticalities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.ENCOUNTER_CRITICALITY));
	    Collections.sort(encounterTypes, conceptComparator);
	    Collections.sort(encounterCriticalities, conceptComparator);
		
/* Goal */
	    ArrayList<CD> goalCodedValues = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GOAL_CODED_VALUE));
	    ArrayList<CD> goalCriticalities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GOAL_CRITICALITY));
	    ArrayList<CD> goalFoci = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GOAL_FOCUS));
	    ArrayList<CD> goalStatuses = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GOAL_STATUS));
	    ArrayList<CD> goalTargetBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GOAL_TARGET_BODY_SITE));
	    ArrayList<CD> goalTargetBodySiteLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GOAL_TARGET_BODY_SITE_LATERALTIY));
	    Collections.sort(goalCodedValues, conceptComparator);
	    Collections.sort(goalCriticalities, conceptComparator);
	    Collections.sort(goalFoci, conceptComparator);
	    Collections.sort(goalStatuses, conceptComparator);
	    Collections.sort(goalTargetBodySites, conceptComparator);
	    Collections.sort(goalTargetBodySiteLateralities, conceptComparator);
		
/* Observation */
	    ArrayList<CD> observationCodedValues = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_CODED_VALUE));
	    ArrayList<CD> observationCriticalities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_CRITICALITY));
	    ArrayList<CD> observationFoci = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_FOCUS));
	    ArrayList<CD> observationInterpretations = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_INTERPRETATION));
	    ArrayList<CD> observationMethods = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_METHOD));
	    ArrayList<CD> observationTargetBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_TARGET_BODY_SITE));
	    ArrayList<CD> observationTargetBodySiteLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_TARGET_BODY_SITE_LATERALITY));
	    ArrayList<CD> observationUnconductedReasons = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.OBSERVATION_UNCONDUCTED_REASON));
	    Collections.sort(observationCodedValues, conceptComparator);
	    Collections.sort(observationCriticalities, conceptComparator);
	    Collections.sort(observationFoci, conceptComparator);
	    Collections.sort(observationInterpretations, conceptComparator);
	    Collections.sort(observationMethods, conceptComparator);
	    Collections.sort(observationTargetBodySites, conceptComparator);
	    Collections.sort(observationTargetBodySiteLateralities, conceptComparator);
	    Collections.sort(observationUnconductedReasons, conceptComparator);
		
/* Problem */
	    ArrayList<CD> problemAffectedBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROBLEM_AFFECTED_BODY_SITE));
	    ArrayList<CD> problemAffectedBodySiteLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROBLEM_AFFECTED_BODY_SITE_LATERALITY));
	    ArrayList<CD> problemImportances = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROBLEM_IMPORTANCE));
	    ArrayList<CD> problems = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROBLEM));
	    ArrayList<CD> problemSeverities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROBLEM_SEVERITY));
	    ArrayList<CD> problemStatuses = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROBLEM_STATUS));
	    Collections.sort(problemAffectedBodySites, conceptComparator);
	    Collections.sort(problemAffectedBodySiteLateralities, conceptComparator);
	    Collections.sort(problemImportances, conceptComparator);
	    Collections.sort(problems, conceptComparator);
	    Collections.sort(problemSeverities, conceptComparator);
	    Collections.sort(problemStatuses, conceptComparator);

/* Procedure */
	    ArrayList<CD> procedureApproachBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE_APPROACH_BODY_SITE));
	    ArrayList<CD> procedureApproachBodySiteLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE_APPROACH_BODY_SITE_LATERALITY));
	    ArrayList<CD> procedureCriticalities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE_CRITICALITY));
	    ArrayList<CD> procedureMethods = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE_METHOD));
	    ArrayList<CD> procedures = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE));
	    ArrayList<CD> procedureTargetBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE_TARGET_BODY_SITE));
	    ArrayList<CD> procedureTargetBodySiteLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE_TARGET_BODY_SITE_LATERALITY));
	    ArrayList<CD> procedureUndeliveredReasons = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.PROCEDURE_UNDELIVERED_REASON));
	    Collections.sort(procedureApproachBodySites, conceptComparator);
	    Collections.sort(procedureApproachBodySiteLateralities, conceptComparator);
	    Collections.sort(procedureCriticalities, conceptComparator);
	    Collections.sort(procedureMethods, conceptComparator);
	    Collections.sort(procedures, conceptComparator);
	    Collections.sort(procedureTargetBodySites, conceptComparator);
	    Collections.sort(procedureTargetBodySiteLateralities, conceptComparator);
	    Collections.sort(procedureUndeliveredReasons, conceptComparator);

/* SubstanceAdministration */
	    ArrayList<CD> brandedMedications = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.BRANDED_MEDICATION));
	    ArrayList<CD> doseTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.DOSE_TYPE));
	    ArrayList<CD> dosingSigs = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.DOSING_SIG));
	    ArrayList<CD> genericMedications = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.GENERIC_MEDICATION));
	    ArrayList<CD> immunizations = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.IMMUNIZATION));
	    ArrayList<CD> informationAttestationTypes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.INFORMATION_ATTESTATION_TYPE));
	    ArrayList<CD> manufacturers = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.MANUFACTURER));
	    ArrayList<CD> medicationClasses = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.MEDICATION_CLASS));
	    ArrayList<CD> medications = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.MEDICATION));
	    ArrayList<CD> substanceAdministrationApproachBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_ADMINISTRATION_APPROACH_BODY_SITE));
	    ArrayList<CD> substanceAdministrationApproachBodySiteLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_ADMINISTRATION_APPROACH_BODY_SITE_LATERALITY));
	    ArrayList<CD> substanceAdministrationCriticalities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_ADMINISTRATION_CRITICALITY));
	    ArrayList<CD> substanceAdministrationGeneralPurposes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_ADMINISTRATION_GENERAL_PURPOSE));
	    ArrayList<CD> substanceAdministrationTargetBodySites = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_ADMINISTRATION_TARGET_BODY_SITE));
	    ArrayList<CD> substanceAdministrationTargetBodySiteLateralities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_ADMINISTRATION_TARGET_BODY_SITE_LATERALITY));
	    ArrayList<CD> substanceAdministrationUndeliveredReasons = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_ADMINISTRATION_UNDELIVERED_REASON));
	    ArrayList<CD> substanceDeliveryMethods = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_DELIVERY_METHOD));
	    ArrayList<CD> substanceDeliveryRoutes = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_DELIVERY_ROUTE));
	    ArrayList<CD> substanceForms = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUBSTANCE_FORM));
	    Collections.sort(brandedMedications, conceptComparator);
	    Collections.sort(doseTypes, conceptComparator);
	    Collections.sort(dosingSigs, conceptComparator);
	    Collections.sort(genericMedications, conceptComparator);
	    Collections.sort(immunizations, conceptComparator);
	    Collections.sort(informationAttestationTypes, conceptComparator);
	    Collections.sort(manufacturers, conceptComparator);
	    Collections.sort(medicationClasses, conceptComparator);
	    Collections.sort(medications, conceptComparator);
	    Collections.sort(substanceAdministrationApproachBodySites, conceptComparator);
	    Collections.sort(substanceAdministrationApproachBodySiteLateralities, conceptComparator);
	    Collections.sort(substanceAdministrationCriticalities, conceptComparator);
	    Collections.sort(substanceAdministrationGeneralPurposes, conceptComparator);
	    Collections.sort(substanceAdministrationTargetBodySites, conceptComparator);
	    Collections.sort(substanceAdministrationTargetBodySiteLateralities, conceptComparator);
	    Collections.sort(substanceAdministrationUndeliveredReasons, conceptComparator);
	    Collections.sort(substanceDeliveryMethods, conceptComparator);
	    Collections.sort(substanceDeliveryRoutes, conceptComparator);
	    Collections.sort(substanceForms, conceptComparator);
	    
/* Supply */
	    ArrayList<CD> supplies = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUPPLY));
	    ArrayList<CD> supplyCriticalities = new ArrayList<CD>(supportedConcepts.get(OpenCDSConceptTypes.SUPPLY_CRITICALITY));
	    Collections.sort(supplies, conceptComparator);
	    Collections.sort(supplyCriticalities, conceptComparator);
	    
	    // process concept determination method for each Concept
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : conceptDeterminationMethods){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    
	    HashSet<String> openCdsConceptClassNames = new HashSet<String> (OpenCDSConceptTypes.getOpenCdsConceptTypes());
//	    Set<String> openCdsConceptClassNames = SimpleTerminologyManager.getInstance().getSupportedOpenCDSConcepts().keySet();
	    openCdsConceptClassNames.remove(OpenCDSConceptTypes.CONCEPT_DETERMINATION_METHOD);
	    
	    for (String openCdsConceptClassName : openCdsConceptClassNames)
	    {
	    	openCdsConceptClassName = stringUtility.getStringWithFirstLetterCapitalized(openCdsConceptClassName) + "Concept";
	    	data.put(openCdsConceptClassName + ".determinationMethodCode", d);
	    }

/*
 *  process Concepts individually from here on
 */
	    
/*
 *  Root templates   
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : cDSInputTemplates){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("CDSInputTemplateConcept.openCdsConceptCode", d);

	    d = new ArrayList<String>();
	    for (CD openCDSConcept : cDSOutputTemplates){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("CDSOutputTemplateConcept.openCdsConceptCode", d);

	    d = new ArrayList<String>();
	    for (CD openCDSConcept : vMRTemplates){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("VMRTemplateConcept.openCdsConceptCode", d);

	    
/*
 *  CDSInput 
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : systemUserTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SystemUserTypeConcept.openCdsConceptCode", d);

	    d = new ArrayList<String>();
	    for (CD openCDSConcept : systemUserPreferredLanguages){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SystemUserPreferredLanguageConcept.openCdsConceptCode", d);

	    d = new ArrayList<String>();
	    for (CD openCDSConcept : informationRecipientTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("InformationRecipientTypeConcept.openCdsConceptCode", d);

	    d = new ArrayList<String>();
	    for (CD openCDSConcept : informationRecipientPreferredLanguages){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("InformationRecipientPreferredLanguageConcept.openCdsConceptCode", d);

	    d = new ArrayList<String>();
	    for (CD openCDSConcept : systemUserTaskContexts){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SystemUserTaskContextConcept.openCdsConceptCode", d);

	    d = new ArrayList<String>();
	    for (CD openCDSConcept : resourceTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ResourceTypeConcept.openCdsConceptCode", d);

	    
/*
 *  Entity
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : entityTemplates){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("EntityTemplateConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : entityTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("EntityTypeConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : genders){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GenderConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : ethnicities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("EthnicityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : races){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("RaceConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : languages){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("LanguageConcept.openCdsConceptCode", d);
	    
/* 
 * ClinicalStatements 
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : clinicalStatementTemplates){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ClinicalStatementTemplateConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : dataSourceTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("DataSourceTypeConcept.openCdsConceptCode", d);
	    
	    
/* 
 * Relationships or Roles	    
 */
//	    d = new ArrayList<String>();
//	    for (CD openCDSConcept : roles){
//	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
//	    }
//	    data.put("RoleConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : entityRelationships){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("EntityRelationshipConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : evaluatedPersonRelationships){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("EvaluatedPersonRelationshipConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : clinicalStatementRelationships){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ClinicalStatementRelationshipConcept.openCdsConceptCode", d);
	    

/* 
 * AdverseEvent	    
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : adverseEventAffectedBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("AdverseEventAffectedBodySitesConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : adverseEventAffectedBodySitesLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("AdverseEventAffectedBodySitesLateralityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : adverseEventAgents){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("AdverseEventAgentConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : adverseEventCriticalities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("AdverseEventCriticalityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : adverseEvents){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("AdverseEventConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : adverseEventStatuses){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("AdverseEventStatusConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : adverseEventSeverities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("AdverseEventSeverityConcept.openCdsConceptCode", d);
	    

/* 
 * Encounter	    
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : encounterTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("EncounterTypeConcept.openCdsConceptCode", d);
	    	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : encounterCriticalities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("EncounterCriticalityConcept.openCdsConceptCode", d);
	    	    

/* 
 * Goal	    
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : goalTargetBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GoalTargetBodySiteConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : goalTargetBodySiteLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GoalTargetBodySiteLateralityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : goalCodedValues){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GoalCodedValueConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : goalCriticalities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GoalCriticalityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : goalFoci){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GoalFocusConcept.openCdsConceptCode", d);
	    
	    for (CD openCDSConcept : goalStatuses){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GoalStatusConcept.openCdsConceptCode", d);
	    
	    	    	    
/* 
 * Observation	    
 */	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationCodedValues){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationCodedValueConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationCriticalities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationCriticalityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationFoci){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationFocusConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationInterpretations){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationInterpretationConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationMethods){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationMethodConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationTargetBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationTargetBodySiteConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationTargetBodySiteLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationTargetBodySiteLateralityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : observationUnconductedReasons){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ObservationUnconductedReasonConcept.openCdsConceptCode", d);
	    
	    
/* 
 * Problem	    
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : problemAffectedBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProblemAffectedBodySiteConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : problemAffectedBodySiteLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProblemAffectedBodySiteLateralityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : problemImportances){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProblemImportanceConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : problems){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProblemConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : problemSeverities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProblemSeverityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : problemStatuses){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProblemStatusConcept.openCdsConceptCode", d);
	    
	    
/* 
 * Procedure	    
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedureApproachBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureApproachBodySiteConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedureApproachBodySiteLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureApproachBodySiteLateralityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedureCriticalities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureCriticalityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedureMethods){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureMethodConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedures){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedureTargetBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureTargetBodySiteConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedureTargetBodySiteLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureTargetBodySiteLateralityConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : procedureUndeliveredReasons){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ProcedureUndeliveredReasonConcept.openCdsConceptCode", d);
	    
	    
/* 
 * Substance Administration	    
 */	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : brandedMedications){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("BrandedMedicationConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : doseTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("DoseTypeConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : dosingSigs){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("DosingSigElementConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : genericMedications){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("GenericMedicationConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : immunizations){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ImmunizationConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : informationAttestationTypes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("InformationAttestationTypeConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : manufacturers){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("ManufacturerConcept.openCdsConceptCode", d);	    	    
	    	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : medicationClasses){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("MedicationClassConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : medications){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("MedicationConcept.openCdsConceptCode", d);
	    
		d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceAdministrationApproachBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceAdministrationApproachBodySiteConcept.openCdsConceptCode", d);
	    
		d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceAdministrationApproachBodySiteLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceAdministrationApproachBodySiteLateralityConcept.openCdsConceptCode", d);
	    
		d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceAdministrationCriticalities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceAdministrationCriticalityConcept.openCdsConceptCode", d);
	    
		d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceAdministrationGeneralPurposes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceAdministrationGeneralPurposeConcept.openCdsConceptCode", d);
	    
		d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceAdministrationTargetBodySites){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceAdministrationTargetBodySiteConcept.openCdsConceptCode", d);
	    
		d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceAdministrationTargetBodySiteLateralities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceAdministrationTargetBodySiteLateralityConcept.openCdsConceptCode", d);
	    
		d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceAdministrationUndeliveredReasons){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceAdministrationUndeliveredReasonConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceDeliveryMethods){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceDeliveryMethodConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceDeliveryRoutes){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceDeliveryRouteConcept.openCdsConceptCode", d);
	
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : substanceForms){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SubstanceFormConcept.openCdsConceptCode", d);
	    
	    
/* 
 * Supply
 */
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : supplies){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SupplyConcept.openCdsConceptCode", d);
	    
	    d = new ArrayList<String>();
	    for (CD openCDSConcept : supplyCriticalities){
	    	d.add(openCDSConcept.getCode() + "=" + openCDSConcept.getDisplayName());		    		    	
	    }
	    data.put("SupplyCriticalityConcept.openCdsConceptCode", d);
	    	    
	    return data;
	}		
	
	public static void main(String[] args) throws DSSRuntimeExceptionFault 
	{
		// revised to configure SimpleKnowledgeRepository correctly des 2012-07-05
        OpencdsConfigurator 		oConfig = new OpencdsConfigurator();// initializes the OpencdsConfigurator.
        SimpleKnowledgeRepository.setFullPathToKRData(oConfig.getKrFullPath());	// initializes the KnowledgeRepository

        EnumerationUtility utility = new EnumerationUtility();
	      Map<String, List<String>> enums = utility.loadEnumerations();
	      
	      System.out.println("Copy the content below into the Drools Guvnor Enumerations entry.");
	      System.out.println();
	      System.out.println();
	      System.out.println();
	      System.out.println("// Enumerations generated from org.opencds.vmr.v1_0.internal.utilities.EnumerationUtility.  Taking this approach to enable enumeration manipulation without server restart.");
	      System.out.println();
	      
	      ArrayList<String> keys = new ArrayList<String>(enums.keySet());
	      Collections.sort(keys);
	      for (String key : keys)
	      {
	    	  List<String> enumContents = enums.get(key);
	    	  if (enumContents.size() > 0)
	    	  {
	    		  StringBuffer line = new StringBuffer();
	    		  line.append("'");
	    		  line.append(key);
	    		  line.append("' : [");
	    		  for (int k = 0; k < enumContents.size(); k++)
	    		  {
	    			  line.append("'");
	    			  line.append(enumContents.get(k));
	    			  line.append("'");
	    			  if (k < enumContents.size() - 1)
	    			  {
	    				  line.append(", ");
	    			  }
	    		  }
	    		  line.append("]");
	    		  System.out.println(line);
	    	  }	    	  
	      }	      
	}
}
