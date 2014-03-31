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

package org.opencds.dss.evaluate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.omg.dss.evaluation.requestresponse.IterativeEvaluationRequest;
import org.opencds.common.structures.DSSRequestKMItem;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;


/**
 * 
 * @author Andrew Iskander, mod by David Shields
 * 
 * @version 2.0
 */
public class EvaluationFactory 
{
	private static 	Log 						log 					= LogFactory.getLog(EvaluationFactory.class);
	
	/**
	 * 
	 * @param oneRequest
	 * @return evaluater
	 * @throws DSSRuntimeExceptionFault 
	 * @throws RequiredDataNotProvidedExceptionFault 
	 */
	public static Evaluater createEvaluater (
			DSSRequestKMItem dssRequestKMItem
		) throws 
			DSSRuntimeExceptionFault, 
			RequiredDataNotProvidedExceptionFault 
    {	
		Evaluater evaluater;
		String adapterClassPathName = "";
		try {
			log.debug("starting EvaluationFactory");
			adapterClassPathName = SimpleKnowledgeRepository.getRequiredInferenceEngineAdapterClasspathNameForKMId( dssRequestKMItem.getRequestedKmId() );
			log.debug("EvaluationFactory adapterClassPathName: " + adapterClassPathName);
			//adapterClassPathName = "org.opencds.service.drools.v54.DroolsAdapter";
			
			if ((adapterClassPathName == null) || ("".equals(adapterClassPathName))) {
				throw new RequiredDataNotProvidedExceptionFault("ClassPathNameInvalidException trying to locate RequiredInferenceEngineAdapter: '" 
						+ adapterClassPathName + "'.");
			}
			
			Class<?> c = Class.forName(adapterClassPathName);
			evaluater = (Evaluater)c.newInstance();
			return  evaluater;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RequiredDataNotProvidedExceptionFault("ClassNotFoundException: " + adapterClassPathName + " " + e.getMessage());
		}
		  catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("IllegalAccessException: " + adapterClassPathName + " " + e.getMessage());
		}
		  catch (InstantiationException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("InstantiationException: " + adapterClassPathName + " " + e.getMessage());
		}
		  catch (Exception e) {
				e.printStackTrace();
				throw new DSSRuntimeExceptionFault("Unrecognized Exception in EvaluationFactory: " + adapterClassPathName + " " + e.getMessage());
		}
	}


	/**
	 * 
	 * @param request
	 * @return iterativeEvaluater
	 */
	public static IterativeEvaluater createIterativeEvaluater (IterativeEvaluationRequest iterativeRequest) throws DSSRuntimeExceptionFault, RequiredDataNotProvidedExceptionFault 
	//TODO this code is inoperative, and needs to be modified to implement the IterativeEvaluation Request
    {	
		IterativeEvaluater evaluater;
//		String adapterName = "GenericIterativeEvaluation";
//		String adapterName = SimpleKnowledgeRepository.getInstance().getRequiredInferenceEngineAdapterForKMId( iterativeRequest );
//		String adapterClassPathName = getAdapterClassPathName( iterativeRequest );  //may need to be adjusted once iterative adapter is created...
		String adapterClassPathName = "notImplementedYet";
		try {
//			String requestID = "GenericIterativeEvaluation";
//			Class<?> c = Class.forName("org.opencds.dss.evaluate." + adapterName);
			Class<?> c = Class.forName(adapterClassPathName);
			evaluater = (IterativeEvaluater)c.newInstance();
			return  evaluater;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RequiredDataNotProvidedExceptionFault("ClassNotFoundException: " + adapterClassPathName + " " + e.getMessage());
		}
		  catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("IllegalAccessException: " + adapterClassPathName + " " + e.getMessage());
		}
		  catch (InstantiationException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("InstantiationException: " + adapterClassPathName + " " + e.getMessage());
		}
	}


}	
