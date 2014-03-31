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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.config.OpencdsConfigurator;
import org.opencds.common.terminology.CDWithChildren;
import org.opencds.common.terminology.CodeSystems;
import org.opencds.common.xml.XmlConverter;
import org.opencds.common.xml.XmlEntity;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.opencds.terminology.apelon.ApelonDtsUtility;

import com.apelon.apelonserver.client.ApelonException;
import com.apelon.apelonserver.client.ServerConnectionSecureSocket;
import com.apelon.dts.client.DTSException;
import com.apelon.dts.client.concept.DTSConcept;

/**
 * Creates the XML configuration file of OpenCDS concepts using Apelon. 
 * 
 * This file is created using the public, central OpenCDS Apelon DTS server that contains OpenCDS concepts.
 * 
 * @author kawam001
 *
 */
public class SupportedConceptsFileCreator {
	
	private ApelonDtsUtility myApelonDtsUtility;
	private ServerConnectionSecureSocket myApelonConnection;
//	private SimpleKnowledgeRepository myKR = SimpleKnowledgeRepository.getInstance();

	
	public SupportedConceptsFileCreator(String host, int port, String userName, String password
			) throws ClassNotFoundException, DSSRuntimeExceptionFault, ApelonException
	{
		initialize(host, port, userName, password);
	}
	
	private void initialize(String host, int port, String userName, String password
			) throws ClassNotFoundException, DSSRuntimeExceptionFault, ApelonException
	{
		myApelonDtsUtility = ApelonDtsUtility.getInstance();
		myApelonConnection = myApelonDtsUtility.getSecureSocketConnection(host, port, userName, password);		
	}
	
	public void shutdown() throws Exception
	{
		myApelonDtsUtility.closeConnection(myApelonConnection);
	}

	/**
	 * Creates simply terminology service resources using Apelon
	 * @throws DTSException 
	 * @throws IllegalArgumentException 
	 * @throws DSSRuntimeExceptionFault 
	 */
	public void createResources() throws IllegalArgumentException, DTSException, DSSRuntimeExceptionFault
	{
		createSupportOpenCdsConceptsConfigFile();		
	}
	
	private void createSupportOpenCdsConceptsConfigFile() throws IllegalArgumentException, DTSException, DSSRuntimeExceptionFault
	{
		XmlEntity configFileRootEntity = new XmlEntity("SupportedOpenCdsConceptsConfigFile");
		
		// identify all OpenCDS concept types
//		ArrayList<String> openCdsConceptTypes = OpenCDSConceptTypes.getOpenCdsConceptTypes();
		ArrayList<String> openCdsConceptTypes = SimpleKnowledgeRepository.getOpenCdsConceptTypes();
		
		// process each of the OpenCDS concept types
		for (String openCdsConceptType : openCdsConceptTypes)
		{
			DTSConcept dtsConceptForOpenCdsConceptType = null;
			HashSet<DTSConcept> matchingConcepts = myApelonDtsUtility.getConceptsWithProperty(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS, "OpenCDS Concept Type", openCdsConceptType, myApelonConnection);
			
			for (DTSConcept matchingConcept : matchingConcepts) // should only be 0 or 1
			{
				dtsConceptForOpenCdsConceptType = matchingConcept;
			}
			
			if (dtsConceptForOpenCdsConceptType != null)
			{				
				// at this point, have the DTS concept for the OpenCDS concept type
				
				// now, we need to find all of the concepts of this type, along with all their equivalency associations to codes in other namespaces
				CDWithChildren conceptTypeAndChildrenConcepts = myApelonDtsUtility.getConceptAndDescendants(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS, dtsConceptForOpenCdsConceptType.getCode(), myApelonConnection);
				HashSet<CDWithChildren> childrenConcepts = conceptTypeAndChildrenConcepts.getChildren();
				if (childrenConcepts.size() > 0)
				{
					XmlEntity supportedConceptsForTypeEntity = new XmlEntity("supportedConceptsForType");
					configFileRootEntity.addChild(supportedConceptsForTypeEntity);
					
					supportedConceptsForTypeEntity.addChild(new XmlEntity("openCdsConceptType", openCdsConceptType, false));
					
					for (CDWithChildren child : childrenConcepts)
					{
						if (child == null) {
							System.out.println("OpenCDS DTS: child of " + openCdsConceptType + " is null, ignoring it.  Need to fix contents of OpenCDS DTS.");
						} else {
							XmlEntity supportedOpenCdsConceptEntity = new XmlEntity("supportedOpenCdsConcept");
							supportedConceptsForTypeEntity.addChild(supportedOpenCdsConceptEntity);
							
							XmlEntity openCdsConceptEntity = new XmlEntity("openCdsConcept");
							openCdsConceptEntity.addAttribute("code", child.getCode());
							openCdsConceptEntity.addAttribute("displayName", child.getDisplayName());
							supportedOpenCdsConceptEntity.addChild(openCdsConceptEntity);
							
							// now, look for equivalent concepts
							// TODO: implement support for equivalentCDs
						}
					}					
				}
			}
		}

		String xmlAsString = configFileRootEntity.getAsVerboseXmlString(5, true, true);
		System.out.println(xmlAsString);
		
		File newOrReplacedFile = SimpleKnowledgeRepository.createOrReplaceXMLResource("resourceAttributes", "supportedConceptsConfigFile.xml", xmlAsString);
		System.out.println("created or updated: " + newOrReplacedFile.getAbsolutePath());
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int createResourcesChoice = JOptionPane.showConfirmDialog(null, 
					"Create supportedConceptsConfigFile.xml using Apelon? \n (note that existing configuration file will be deleted)");
			if (createResourcesChoice == JOptionPane.YES_OPTION)
			{
		        OpencdsConfigurator 		oConfig = new OpencdsConfigurator();  										// initializes the configurator.
		        SimpleKnowledgeRepository.setFullPathToKRData(oConfig.getKrFullPath());	// initializes the KnowledgeRepository
				
		        String configFileXmlAsString = SimpleKnowledgeRepository.getResourceAsString("apelon", "CentralOpenCDSApelonDtsConfigFile.xml");

				XmlEntity configFileRootEntity = XmlConverter.getInstance().unmarshalXml(configFileXmlAsString, false, null);
				XmlEntity connectionInfoEntity = configFileRootEntity.getFirstChildWithLabel("connectionInfo");
				
				String host = connectionInfoEntity.getFirstChildWithLabel("host").getValue();
				int port = new Integer(connectionInfoEntity.getFirstChildWithLabel("port").getValue()).intValue();
				String userName  = connectionInfoEntity.getFirstChildWithLabel("userName").getValue();
				String password = connectionInfoEntity.getFirstChildWithLabel("password").getValue();
				
				SupportedConceptsFileCreator supportedConceptsFileCreator = new SupportedConceptsFileCreator(host, port, userName, password);
				
				supportedConceptsFileCreator.createResources();
				supportedConceptsFileCreator.shutdown();
			}		
			
			JOptionPane.showMessageDialog(null, "Process completed successfully.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Process aborted with errors.  Details: " + e.getMessage());			
		}
	}

}
