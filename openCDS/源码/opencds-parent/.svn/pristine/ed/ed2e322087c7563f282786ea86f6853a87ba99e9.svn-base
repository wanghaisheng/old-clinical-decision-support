/**
 * Copyright 2012 OpenCDS.org
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

package org.opencds.service;

import java.io.File;
import java.util.List;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.EvaluationExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.InvalidTimeZoneOffsetExceptionFault;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.omg.dss.UnrecognizedLanguageExceptionFault;
import org.omg.dss.UnrecognizedScopedEntityExceptionFault;
import org.omg.dss.UnsupportedLanguageExceptionFault;
import org.omg.dss.common.EntityIdentifier;
import org.omg.dss.common.InteractionIdentifier;
import org.omg.dss.common.ItemIdentifier;
import org.omg.dss.common.SemanticPayload;
import org.omg.dss.evaluation.Evaluate;
import org.omg.dss.evaluation.EvaluateResponse;
import org.omg.dss.evaluation.requestresponse.DataRequirementItemData;
import org.omg.dss.evaluation.requestresponse.EvaluationRequest;
import org.omg.dss.evaluation.requestresponse.FinalKMEvaluationResponse;
import org.omg.dss.evaluation.requestresponse.KMEvaluationRequest;
import org.opencds.common.utilities.FileUtility;
import org.opencds.dss.evaluate.EvaluationImpl;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;

/**
 * TestEvaluationImpl is for debugging the core code of OpenCDS, and/or debugging individual KnowledgeModules.
 * 
 * It bypasses the web interface, and allows you to reference a single file containing the VMR for the data,
 * and to specify what KnowledgeModule[s] to want to apply to that data.
 * 
 * It displays the resulting XML on the console.
 * 
 * 
 * @author David Shields
 * 
 * @date 2012-12-28
 *
 */
public class TestEvaluationImpl {

	@SuppressWarnings("unused")
	public static void main(String [ ] args) 
	{
		try {
			SimpleKnowledgeRepository.setFullPathToKRData("C:/OpenCDS/opencds-knowledge-repository-data/resources_v1.1");	// initializes the KnowledgeRepository

/**
 * to test for memory leaks anywhere in OpenCDS repeat entire evaluation at least 50,000 times
 * 
 */
			for (int j = 0; j < 1000; j++) {
				System.out.println("starting outer loop=" + j);
				String 				payloadString 	= null;
				String				filePath		= null;
				
				Evaluate parameters = new Evaluate();
				InteractionIdentifier ii = new InteractionIdentifier();
				ii.setInteractionId("testFromMain");
				ii.setScopingEntityId("scopingEntity");
				ii.setSubmissionTime(null);
				parameters.setInteractionId(ii);
				
	//			XMLGregorianCalendar et = XMLDateUtility.date2XMLGregorian(new Date()); 
				
				EvaluationRequest er = new EvaluationRequest();
				er.setClientLanguage("");
				er.setClientTimeZoneOffset("");
				
//				er.getKmEvaluationRequest().add(new KMEvaluationRequest());
//				er.getKmEvaluationRequest().get(0).setKmId(new EntityIdentifier());
//				er.getKmEvaluationRequest().get(0).getKmId().setBusinessId("bounce");
//				er.getKmEvaluationRequest().get(0).getKmId().setScopingEntityId("org.opencds");
//				er.getKmEvaluationRequest().get(0).getKmId().setVersion("1.5.5");
//				
//				er.getKmEvaluationRequest().add(new KMEvaluationRequest());
//				er.getKmEvaluationRequest().get(1).setKmId(new EntityIdentifier());
//				er.getKmEvaluationRequest().get(1).getKmId().setBusinessId("AHRQ_PSI_12");
//				er.getKmEvaluationRequest().get(1).getKmId().setScopingEntityId("edu.utah");
//				er.getKmEvaluationRequest().get(1).getKmId().setVersion("4.3.1");
				
				
				er.getKmEvaluationRequest().add(new KMEvaluationRequest());
				er.getKmEvaluationRequest().get(0).setKmId(new EntityIdentifier());
				er.getKmEvaluationRequest().get(0).getKmId().setBusinessId("AHRQ_PSI_02");
				er.getKmEvaluationRequest().get(0).getKmId().setScopingEntityId("edu.utah");
				er.getKmEvaluationRequest().get(0).getKmId().setVersion("4.3.1");
				
				er.getKmEvaluationRequest().add(new KMEvaluationRequest());
				er.getKmEvaluationRequest().get(1).setKmId(new EntityIdentifier());
				er.getKmEvaluationRequest().get(1).getKmId().setBusinessId("AHRQ_PSI_11");
				er.getKmEvaluationRequest().get(1).getKmId().setScopingEntityId("edu.utah");
				er.getKmEvaluationRequest().get(1).getKmId().setVersion("4.3.1");
				
				er.getKmEvaluationRequest().add(new KMEvaluationRequest());
				er.getKmEvaluationRequest().get(2).setKmId(new EntityIdentifier());
				er.getKmEvaluationRequest().get(2).getKmId().setBusinessId("AHRQ_PSI_12");
				er.getKmEvaluationRequest().get(2).getKmId().setScopingEntityId("edu.utah");
				er.getKmEvaluationRequest().get(2).getKmId().setVersion("4.3.1");
				
//				er.getKmEvaluationRequest().add(new KMEvaluationRequest());
//				er.getKmEvaluationRequest().get(3).setKmId(new EntityIdentifier());
//				er.getKmEvaluationRequest().get(3).getKmId().setBusinessId("bounce");
//				er.getKmEvaluationRequest().get(3).getKmId().setScopingEntityId("org.opencds");
//				er.getKmEvaluationRequest().get(3).getKmId().setVersion("1.5.5");
				
				er.getDataRequirementItemData().add(new DataRequirementItemData());
				er.getDataRequirementItemData().get(0).setDriId(new ItemIdentifier());
				er.getDataRequirementItemData().get(0).getDriId().setItemId("testData");
				er.getDataRequirementItemData().get(0).getDriId().setContainingEntityId(new EntityIdentifier());
				er.getDataRequirementItemData().get(0).getDriId().getContainingEntityId().setBusinessId("testDataStructure");
				er.getDataRequirementItemData().get(0).getDriId().getContainingEntityId().setScopingEntityId("edu.utah");
				er.getDataRequirementItemData().get(0).getDriId().getContainingEntityId().setVersion("1.0");
				er.getDataRequirementItemData().get(0).setData(new SemanticPayload());
				er.getDataRequirementItemData().get(0).getData().setInformationModelSSId(new EntityIdentifier());
				er.getDataRequirementItemData().get(0).getData().getInformationModelSSId().setBusinessId("VMR");
				er.getDataRequirementItemData().get(0).getData().getInformationModelSSId().setScopingEntityId("org.opencds.vmr");
				er.getDataRequirementItemData().get(0).getData().getInformationModelSSId().setVersion("1.0");	
				parameters.setEvaluationRequest(er);
				
				filePath = "T:/Mirth/in/read/184651367.txt";  //must be a valid VMR xml in a single file
				
				try {
					payloadString = FileUtility.getInstance().getFileContentsAsString(new File (filePath));
					filePath = null;
					er.getDataRequirementItemData().get(0).getData().getBase64EncodedPayload().add(payloadString.getBytes());
					payloadString = null;
				} catch (DSSRuntimeExceptionFault e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				
/**
 * to test for memory leaks in just Drools Adapter repeat evaluation at least 50000 times 
 * 
 */
				for (int i = 0; i < 1; i++) {
					System.out.println("starting inner loop=" + i);
					try {
						
						String adapterClassPathName = "org.opencds.dss.evaluate.EvaluationImpl";
						Class<?> c = Class.forName(adapterClassPathName);
						adapterClassPathName = null;
						EvaluationImpl myEvaluationImpl = (EvaluationImpl)c.newInstance();
						
						EvaluateResponse evalResp = myEvaluationImpl.evaluate(parameters);
						System.out.println("");
					
/**
 * print the input.  Uncomment the prtLine below to print the input payload as text
 * 
 */
//						System.out.println("input=" + payloadString);
						
						
													
						for (FinalKMEvaluationResponse kmer : evalResp.getEvaluationResponse().getFinalKMEvaluationResponse()) {
/**
 * print the kmid of the output.  Uncomment the println() below to print just the kmid of each output
 * 
 */
							String kmid = kmer.getKmId().getBusinessId().toString();									
//							System.out.println("kmid=" + kmid);
							kmid = null;
						

/**
 * print the full XML output.  Uncomment the println() below to print each output payload as XML text
 * 
 */
							List<byte[]> 		output 							= 
								kmer.getKmEvaluationResultData().get(0).getData().getBase64EncodedPayload();
							StringBuffer 		outputPayloadStringBuffer		= new StringBuffer();
							for ( byte[] byteList : output ) {
								// assembles from chunks of encoded data separated by newline, which is fairly common					
								outputPayloadStringBuffer.append(new String(byteList, "UTF-8"));
							}
							String 				outputPayloadString 			= outputPayloadStringBuffer.toString();
							
//							System.out.println("output=" + outputPayloadString.toString());
							
							outputPayloadString = null;
							outputPayloadStringBuffer = null;
							output = null;
						}
						
//						System.out.println("finished inner loop: " + i);
						
						//clear things we created
						evalResp = null;
						myEvaluationImpl = null;
						c = null;
						
/**
 *  this slows things down a LOT, but it will make it easier to see a memory leak
 */
//						long usedMemory = Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory();
//						System.gc(); 
//						System.out.println("Loop #" + i + " - heap used memory: " + usedMemory / 1000  + "KB");
//						System.out.println("");
						
					} catch (InvalidDriDataFormatExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (UnrecognizedLanguageExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (RequiredDataNotProvidedExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (UnsupportedLanguageExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (UnrecognizedScopedEntityExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (EvaluationExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (InvalidTimeZoneOffsetExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (DSSRuntimeExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}	
					
				} // end inner loop repeating DroolsAdapter
				
				er = null;
				ii = null;
				parameters = null;
				
//				System.out.println("finished outer loop: " + j);
				
			} // end outer loop repeating entire thing
		
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
}
