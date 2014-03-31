/**
 * Copyright 2011 - 2013 OpenCDS.org
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
import java.util.Map;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.vmr.v1_0.internal.AdministrableSubstance;
import org.opencds.vmr.v1_0.internal.AdverseEvent;
import org.opencds.vmr.v1_0.internal.AppointmentProposal;
import org.opencds.vmr.v1_0.internal.AppointmentRequest;
import org.opencds.vmr.v1_0.internal.Assertion;
import org.opencds.vmr.v1_0.internal.CDSInput;
import org.opencds.vmr.v1_0.internal.CDSOutput;
import org.opencds.vmr.v1_0.internal.ClinicalStatementRelationship;
import org.opencds.vmr.v1_0.internal.DeniedAdverseEvent;
import org.opencds.vmr.v1_0.internal.DeniedProblem;
import org.opencds.vmr.v1_0.internal.EncounterEvent;
import org.opencds.vmr.v1_0.internal.Entity;
import org.opencds.vmr.v1_0.internal.EntityRelationship;
import org.opencds.vmr.v1_0.internal.EvalTime;
import org.opencds.vmr.v1_0.internal.EvaluatedPerson;
import org.opencds.vmr.v1_0.internal.EvaluatedPersonAgeAtEvalTime;
import org.opencds.vmr.v1_0.internal.EvaluatedPersonRelationship;
import org.opencds.vmr.v1_0.internal.Facility;
import org.opencds.vmr.v1_0.internal.FocalPersonId;
import org.opencds.vmr.v1_0.internal.Goal;
import org.opencds.vmr.v1_0.internal.GoalProposal;
import org.opencds.vmr.v1_0.internal.MissedAppointment;
import org.opencds.vmr.v1_0.internal.NamedObject;
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
import org.opencds.vmr.v1_0.internal.VMR;

/**
 * Collection of FactLists for input to Drools, moved to this class from BuildCDSInputFactLists.java
 * 
 * @author David Shields
 *
 */
public class FactLists  
{
   
    // ================= Send these as both globals and as facts =======================//
    public List<FocalPersonId>							focalPersonIdList								= Collections.synchronizedList(new ArrayList<FocalPersonId>());
    public List<EvalTime>								evalTimeList									= Collections.synchronizedList(new ArrayList<EvalTime>());
	
    // ================= Helper lists for temporary data elements within Drools =======================//
    public List<Assertion>								assertionList									= Collections.synchronizedList(new ArrayList<Assertion>());
    public List<NamedObject>							namedObjectList									= Collections.synchronizedList(new ArrayList<NamedObject>());
	
    // ================= VMR (in same order as schema, alpha within category) =======================//
//    following deprecated since the ids are easily found in internalEvaluatedPersonList des 2012-02-20
//    public List<String>									evaluatedPersonIdList							= Collections.synchronizedList(new ArrayList<String>());
		/* Extended VMR Datatypes */	
		/* Entities */
    public List<AdministrableSubstance> 				internalAdministrableSubstanceList 				= Collections.synchronizedList(new ArrayList<AdministrableSubstance>());
    public List<Entity> 								internalEntityList 								= Collections.synchronizedList(new ArrayList<Entity>());
    public List<EvaluatedPerson> 						internalEvaluatedPersonList 					= Collections.synchronizedList(new ArrayList<EvaluatedPerson>());
    public List<EvaluatedPersonAgeAtEvalTime>			internalPersonAgeAtEvalTimeList 				= Collections.synchronizedList(new ArrayList<EvaluatedPersonAgeAtEvalTime>());
    public List<Facility> 								internalFacilityList 							= Collections.synchronizedList(new ArrayList<Facility>());
    public List<Organization> 							internalOrganizationList 						= Collections.synchronizedList(new ArrayList<Organization>());
    public List<Person> 								internalPersonList 								= Collections.synchronizedList(new ArrayList<Person>());
    public List<Specimen> 								internalSpecimenList 							= Collections.synchronizedList(new ArrayList<Specimen>());
		/* Relationships */
//    following list deprecated des 201202-11
//    public List<ClinicalStatementEntityInRoleRelationship> internalClinicalStatementEntityInRoleRelationshipList = new ArrayList<ClinicalStatementEntityInRoleRelationship>());			
	public List<ClinicalStatementRelationship> 			internalClinicalStatementRelationshipList		= Collections.synchronizedList(new ArrayList<ClinicalStatementRelationship>());
    public List<EntityRelationship> 					internalEntityRelationshipList 					= Collections.synchronizedList(new ArrayList<EntityRelationship>());
    public List<EvaluatedPersonRelationship> 			internalEvaluatedPersonRelationshipList 		= Collections.synchronizedList(new ArrayList<EvaluatedPersonRelationship>());
		/* The Whole Thing */
    public List<CDSInput>								internalCDSInputList							= Collections.synchronizedList(new ArrayList<CDSInput>());
    public List<CDSOutput>								internalCDSOutputList							= Collections.synchronizedList(new ArrayList<CDSOutput>());
    public List<VMR>									internalVMRList									= Collections.synchronizedList(new ArrayList<VMR>());
		/* BaseClinicalStatement */
		/* Adverse Event Clinical Statements */
    public List<AdverseEvent> 							internalAdverseEventList 						= Collections.synchronizedList(new ArrayList<AdverseEvent>());
    public List<DeniedAdverseEvent> 					internalDeniedAdverseEventList 					= Collections.synchronizedList(new ArrayList<DeniedAdverseEvent>());
		/* Encounter Clinical Statements */
    public List<AppointmentProposal> 					internalAppointmentProposalList 				= Collections.synchronizedList(new ArrayList<AppointmentProposal>());
    public List<AppointmentRequest> 					internalAppointmentRequestList 					= Collections.synchronizedList(new ArrayList<AppointmentRequest>());
    public List<EncounterEvent> 						internalEncounterEventList 						= Collections.synchronizedList(new ArrayList<EncounterEvent>());
    public List<MissedAppointment> 						internalMissedAppointmentList 					= Collections.synchronizedList(new ArrayList<MissedAppointment>());
    public List<ScheduledAppointment> 					internalScheduledAppointmentList 				= Collections.synchronizedList(new ArrayList<ScheduledAppointment>());
		/* Goal Clinical Statements */
    public List<Goal> 									internalGoalList 								= Collections.synchronizedList(new ArrayList<Goal>());
    public List<GoalProposal> 							internalGoalProposalList 						= Collections.synchronizedList(new ArrayList<GoalProposal>());
		/* Observation Clinical Statements */
    public List<ObservationOrder> 						internalObservationOrderList 					= Collections.synchronizedList(new ArrayList<ObservationOrder>());
    public List<ObservationProposal> 					internalObservationProposalList 				= Collections.synchronizedList(new ArrayList<ObservationProposal>());
    public List<ObservationResult> 						internalObservationResultList 					= Collections.synchronizedList(new ArrayList<ObservationResult>());
    public List<UnconductedObservation> 				internalUnconductedObservationList 				= Collections.synchronizedList(new ArrayList<UnconductedObservation>());
		/* Problem Clinical Statements */
    public List<DeniedProblem> 							internalDeniedProblemList 						= Collections.synchronizedList(new ArrayList<DeniedProblem>());
    public List<Problem> 								internalProblemList 							= Collections.synchronizedList(new ArrayList<Problem>());
		/* Procedure Clinical Statements */
    public List<ProcedureEvent> 						internalProcedureEventList 						= Collections.synchronizedList(new ArrayList<ProcedureEvent>());
    public List<ProcedureOrder> 						internalProcedureOrderList 						= Collections.synchronizedList(new ArrayList<ProcedureOrder>());
    public List<ProcedureProposal> 						internalProcedureProposalList 					= Collections.synchronizedList(new ArrayList<ProcedureProposal>());
    public List<ScheduledProcedure> 					internalScheduledProcedureList 					= Collections.synchronizedList(new ArrayList<ScheduledProcedure>());
    public List<UndeliveredProcedure> 					internalUndeliveredProcedureList 				= Collections.synchronizedList(new ArrayList<UndeliveredProcedure>());
		/* Substance Administration Clinical Statements */
    public List<SubstanceAdministrationEvent> 			internalSubstanceAdministrationEventList 		= Collections.synchronizedList(new ArrayList<SubstanceAdministrationEvent>());
    public List<SubstanceAdministrationOrder>			internalSubstanceAdministrationOrderList 		= Collections.synchronizedList(new ArrayList<SubstanceAdministrationOrder>());
    public List<SubstanceAdministrationProposal>		internalSubstanceAdministrationProposalList 	= Collections.synchronizedList(new ArrayList<SubstanceAdministrationProposal>());
    public List<SubstanceDispensationEvent> 			internalSubstanceDispensationEventList 			= Collections.synchronizedList(new ArrayList<SubstanceDispensationEvent>());
    public List<UndeliveredSubstanceAdministration>		internalUndeliveredSubstanceAdministrationList 	= Collections.synchronizedList(new ArrayList<UndeliveredSubstanceAdministration>());
		/* Supply Clinical Statements */
    public List<SupplyEvent> 							internalSupplyEventList 						= Collections.synchronizedList(new ArrayList<SupplyEvent>());
    public List<SupplyOrder> 							internalSupplyOrderList 						= Collections.synchronizedList(new ArrayList<SupplyOrder>());
    public List<SupplyProposal> 						internalSupplyProposalList 						= Collections.synchronizedList(new ArrayList<SupplyProposal>());
    public List<UndeliveredSupply> 						internalUndeliveredSupplyList 					= Collections.synchronizedList(new ArrayList<UndeliveredSupply>());
	

    public synchronized void clearAllFactLists() throws DSSRuntimeExceptionFault
    {
    	focalPersonIdList.clear();
    	evalTimeList.clear();
    	assertionList.clear();
    	namedObjectList.clear();
    	internalAdministrableSubstanceList.clear();
    	internalEntityList.clear();
    	internalEvaluatedPersonList.clear();
    	internalPersonAgeAtEvalTimeList.clear();
    	internalFacilityList.clear();
    	internalOrganizationList.clear();
    	internalPersonList.clear();
    	internalSpecimenList.clear();
    	internalClinicalStatementRelationshipList.clear();
    	internalEntityRelationshipList.clear();
    	internalEvaluatedPersonRelationshipList.clear();
    	internalCDSInputList.clear();
    	internalCDSOutputList.clear();
    	internalVMRList.clear();
    	internalAdverseEventList.clear();
    	internalDeniedAdverseEventList.clear();
    	internalAppointmentProposalList.clear();
    	internalAppointmentRequestList.clear();
    	internalEncounterEventList.clear();
    	internalMissedAppointmentList.clear();
    	internalScheduledAppointmentList.clear();
    	internalGoalList.clear();
    	internalGoalProposalList.clear();
    	internalObservationOrderList.clear();
    	internalObservationProposalList.clear();
    	internalObservationResultList.clear();
    	internalUnconductedObservationList.clear();
    	internalDeniedProblemList.clear();
    	internalProblemList.clear();
    	internalProcedureEventList.clear();
    	internalProcedureOrderList.clear();
    	internalProcedureOrderList.clear();
    	internalScheduledProcedureList.clear();
    	internalUndeliveredProcedureList.clear();
    	internalSubstanceAdministrationEventList.clear();
    	internalSubstanceAdministrationOrderList.clear();
    	internalSubstanceAdministrationProposalList.clear();
    	internalSubstanceDispensationEventList.clear();
    	internalUndeliveredSubstanceAdministrationList.clear();
    	internalSupplyEventList.clear();
    	internalSupplyOrderList.clear();
    	internalSupplyProposalList.clear();
    	internalUndeliveredSupplyList.clear();    	
    }
    

    public synchronized void populateAllFactLists(Map<String,List<?>> allFactLists)
    {
		 
//moved to globals		addToAllFactLists(allFactLists, focalPersonIdList);
//changed back to both exist as global and as factList, des 20111212
    	if (focalPersonIdList.size() > 0) allFactLists.put("FocalPersonId", focalPersonIdList);
		if (evalTimeList.size() > 0) allFactLists.put("EvalTime", evalTimeList);
    	
/* Extended VMR Datatypes */			
/* Entities */
		if (internalAdministrableSubstanceList.size() > 0) allFactLists.put("AdministrableSubstance", internalAdministrableSubstanceList);
		if (internalEntityList.size() > 0) allFactLists.put("Entity", internalEntityList);
		if (internalEvaluatedPersonList.size() > 0) allFactLists.put("EvaluatedPerson", internalEvaluatedPersonList);
		if (internalPersonAgeAtEvalTimeList.size() > 0) allFactLists.put("PersonAgeAtEvalTime", internalPersonAgeAtEvalTimeList);
		if (internalFacilityList.size() > 0) allFactLists.put("Facility", internalFacilityList);
		if (internalOrganizationList.size() > 0) allFactLists.put("Organization", internalOrganizationList);
		if (internalPersonList.size() > 0) allFactLists.put("Person", internalPersonList);
		if (internalSpecimenList.size() > 0) allFactLists.put("Specimen", internalSpecimenList);
/* Relationships */
//		following deprecated des 2012-02-14
//		if (internalClinicalStatementEntityInRoleRelationshipList() > 0) allFactLists.put("FocalPersonId", internalClinicalStatementEntityInRoleRelationshipList);
		if (internalClinicalStatementRelationshipList.size() > 0) allFactLists.put("ClinicalStatementRelationship", internalClinicalStatementRelationshipList);
		if (internalEntityRelationshipList.size() > 0) allFactLists.put("EntityRelationship", internalEntityRelationshipList);
		if (internalEvaluatedPersonRelationshipList.size() > 0) allFactLists.put("EvaluatedPersonRelationship", internalEvaluatedPersonRelationshipList);
/* The Whole Thing */
		if (internalCDSInputList.size() > 0) allFactLists.put("CDSInput", internalCDSInputList);			
		if (internalCDSOutputList.size() > 0) allFactLists.put("CDSOutput", internalCDSOutputList);
		if (internalVMRList.size() > 0) allFactLists.put("VMR", internalVMRList);				
/* BaseClinicalStatement */
/* Adverse Event Clinical Statements */
		if (internalAdverseEventList.size() > 0) allFactLists.put("AdverseEvent", internalAdverseEventList);
		if (internalDeniedAdverseEventList.size() > 0) allFactLists.put("DeniedAdverseEvent", internalDeniedAdverseEventList);
/* Encounter Clinical Statements */
		if (internalAppointmentProposalList.size() > 0) allFactLists.put("AppointmentProposal", internalAppointmentProposalList);
		if (internalAppointmentRequestList.size() > 0) allFactLists.put("AppointmentRequest", internalAppointmentRequestList);
		if (internalEncounterEventList.size() > 0) allFactLists.put("EncounterEvent", internalEncounterEventList);
		if (internalMissedAppointmentList.size() > 0) allFactLists.put("MissedAppointment", internalMissedAppointmentList);
		if (internalScheduledAppointmentList.size() > 0) allFactLists.put("ScheduledAppointment", internalScheduledAppointmentList);
/* Goal Clinical Statements */
		if (internalGoalList.size() > 0) allFactLists.put("Goal", internalGoalList);
		if (internalGoalProposalList.size() > 0) allFactLists.put("GoalProposal", internalGoalProposalList);
/* Observation Clinical Statements */
		if (internalObservationOrderList.size() > 0) allFactLists.put("ObservationOrder", internalObservationOrderList);
		if (internalObservationProposalList.size() > 0) allFactLists.put("ObservationProposal", internalObservationProposalList);
		if (internalObservationResultList.size() > 0) allFactLists.put("ObservationResult", internalObservationResultList);
		if (internalUnconductedObservationList.size() > 0) allFactLists.put("UnconductedObservation", internalUnconductedObservationList);
/* Problem Clinical Statements */
		if (internalDeniedProblemList.size() > 0) allFactLists.put("DeniedProblem", internalDeniedProblemList);
		if (internalProblemList.size() > 0) allFactLists.put("Problem", internalProblemList);
/* Procedure Clinical Statements */
		if (internalProcedureEventList.size() > 0) allFactLists.put("ProcedureEvent", internalProcedureEventList);
		if (internalProcedureOrderList.size() > 0) allFactLists.put("ProcedureOrder", internalProcedureOrderList);
		if (internalProcedureProposalList.size() > 0) allFactLists.put("ProcedureProposal", internalProcedureProposalList);
		if (internalScheduledProcedureList.size() > 0) allFactLists.put("ScheduledProcedure", internalScheduledProcedureList);
		if (internalUndeliveredProcedureList.size() > 0) allFactLists.put("UndeliveredProcedure", internalUndeliveredProcedureList);
/* Substance Administration Clinical Statements */
		if (internalSubstanceAdministrationEventList.size() > 0) allFactLists.put("SubstanceAdministrationEvent", internalSubstanceAdministrationEventList);
		if (internalSubstanceAdministrationOrderList.size() > 0) allFactLists.put("SubstanceAdministrationOrder", internalSubstanceAdministrationOrderList);
		if (internalSubstanceAdministrationProposalList.size() > 0) allFactLists.put("SubstanceAdministrationProposal", internalSubstanceAdministrationProposalList);
		if (internalSubstanceDispensationEventList.size() > 0) allFactLists.put("SubstanceDispensationEvent", internalSubstanceDispensationEventList);
		if (internalUndeliveredSubstanceAdministrationList.size() > 0) allFactLists.put("UndeliveredSubstanceAdministration", internalUndeliveredSubstanceAdministrationList);
/* Supply Clinical Statements */
		if (internalSupplyEventList.size() > 0) allFactLists.put("SupplyEvent", internalSupplyEventList);
		if (internalSupplyOrderList.size() > 0) allFactLists.put("SupplyOrder", internalSupplyOrderList);
		if (internalSupplyProposalList.size() > 0) allFactLists.put("SupplyProposal", internalSupplyProposalList);
		if (internalUndeliveredSupplyList.size() > 0) allFactLists.put("UndeliveredSupply", internalUndeliveredSupplyList);
		    	
    }
    
}
