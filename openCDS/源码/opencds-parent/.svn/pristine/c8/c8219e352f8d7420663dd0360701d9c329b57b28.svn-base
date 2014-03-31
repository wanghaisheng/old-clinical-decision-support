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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.config.OpencdsConfigurator;
import org.opencds.common.terminology.CD;
import org.opencds.common.terminology.CodeSystems;
import org.opencds.common.utilities.StringToArrayUtility;
import org.opencds.common.xml.XmlConverter;
import org.opencds.common.xml.XmlEntity;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.opencds.terminology.apelon.ApelonDtsUtility;

import com.apelon.apelonserver.client.ApelonException;
import com.apelon.apelonserver.client.ServerConnectionSecureSocket;
import com.apelon.dts.client.DTSException;
import com.apelon.dts.client.concept.DTSConcept;
import com.apelon.dts.common.subset.Subset;

/**
 * Optional class that creates the XML concept mapping specification files generated from a local Apelon instance. 
 * 
 * Note that the following conventions must be used in the Apelon instance in order for this class to be used:
 * - Apelon DTS is used.
 * - Mapping files are based on namespace subsets that take the form:  
 * OPENCDS_CodeSystemName^ConceptName^MethodologyName__ConceptCode^MethodologyCode  
 * E.g., subsets names will take the form of OPENCDS_CPT4^Bilateral^NQF__C51^C45  
 * 
 * The exception is for ICD9CM diagnoes and procedures.  Here, the subset name should start with "OPENCDS_ICD9CM", 
 * rather than "OPENCDS_ICD9CM diagnoses" and "OPENCDS_ICD9CM procedures", as Apelon considers both to be 
 * part of the same namespace.
 *  
 * Only the codes (not the names) will have meaning.  The file will be named according to the subset name, 
 * with "OPENCDS_" removed.
 * 
 * Names are meant to be able to be changed with no semantic consequences.
 * 
 * If names include characters that are not allowed in a file name (e.g., / ? : * " > < |), any substitute can be used.  
 * As a convention, these characters should be replaced by a blank space (" ").  
 * Note that file names are for convenience of human consumption and are not used semantically by the system.
 * 
 * The generated concept mapping file names will be as follows:
 * CodeSystemName^ConceptName^MethodologyName__CodeSystemOID^ConceptCode^MethodologyCode
 * 
 * CodeSystemName should be the one specified in openCDSCodeSystems.xml.  
 * Other names should be the ones specified in supportedConceptsConfigurationFile.xml.
 * 
 * E.g.,
 * CPT4^Bilateral^NQF__2.16.840.1.113883.6.12^C51^C45
 * 
 * @author kawam001
 *
 */
public class ApelonConceptMappingSpecificationFilesCreator {
	
	private ApelonDtsUtility myApelonDtsUtility;
	private ServerConnectionSecureSocket myLocalApelonDtsConnection;
	private ServerConnectionSecureSocket myCentralOpenCDSApelonDtsConnection;
	private ArrayList<String> mySupportedCodeSystemOIDs;
//	private SimpleKnowledgeRepository myKr = SimpleKnowledgeRepository.getInstance();
	
	public ApelonConceptMappingSpecificationFilesCreator(
			String localDtsHost, 
			int localDtsPort, 
			String localDtsUserName, 
			String localDtsPassword, 
			String centralDtsHost, 
			int centralDtsPort, 
			String centralDtsUserName, 
			String centralDtsPassword, 
			ArrayList<String> supportedCodeSystemOIDs
			) throws ClassNotFoundException, DSSRuntimeExceptionFault, ApelonException
	{
		initialize(
				localDtsHost, 
				localDtsPort, 
				localDtsUserName, 
				localDtsPassword, 
				centralDtsHost, 
				centralDtsPort, 
				centralDtsUserName, 
				centralDtsPassword, 
				supportedCodeSystemOIDs);
	}
	
	private void initialize(
			String localDtsHost, 
			int localDtsPort, 
			String localDtsUserName, 
			String localDtsPassword, 
			String centralDtsHost, 
			int centralDtsPort, 
			String centralDtsUserName, 
			String centralDtsPassword, 
			ArrayList<String> supportedCodeSystemOIDs
			) throws ClassNotFoundException, DSSRuntimeExceptionFault, ApelonException
	{
		mySupportedCodeSystemOIDs = supportedCodeSystemOIDs;
		myApelonDtsUtility = ApelonDtsUtility.getInstance();
		myLocalApelonDtsConnection = myApelonDtsUtility.getSecureSocketConnection(
				localDtsHost, localDtsPort, localDtsUserName, localDtsPassword);		
		myCentralOpenCDSApelonDtsConnection = myApelonDtsUtility.getSecureSocketConnection(
				centralDtsHost, centralDtsPort, centralDtsUserName, centralDtsPassword);
	}
	
	public void shutdown() throws Exception
	{
		myApelonDtsUtility.closeConnection(myLocalApelonDtsConnection);
		myApelonDtsUtility.closeConnection(myCentralOpenCDSApelonDtsConnection);
	}

	/**
	 * Creates simply terminology service resources using Apelon
	 * @throws DTSException 
	 * @throws IllegalArgumentException 
	 * @throws DSSRuntimeExceptionFault 
	 */
	public void createResources() throws IllegalArgumentException, DTSException, DSSRuntimeExceptionFault
	{
		createConceptMappingSpecifications();
	}
	
	/**
	 * Creates concept mapping specification files.
	 * File name is [code system name according to org.opencds.terminology.CodeSystems]_[concept name], 
	 * 		with _[concept derivation method] if not using the "best available" method.
	 * 
	 * @throws IllegalArgumentException 
	 * @throws DTSException 
	 * @throws DSSRuntimeExceptionFault 
	 */
	private void createConceptMappingSpecifications() throws DTSException, IllegalArgumentException, DSSRuntimeExceptionFault
	{
		//File conceptMappingSpecificationsDirectory = new File(".\\src\\main\\resources\\conceptMappingSpecifications");
		//FileUtility.deleteDir(conceptMappingSpecificationsDirectory, false);
		// above approach deleted .svn hidden folder, which caused SVN problems
		//ArrayList<File> conceptMappingFilesToDelete = FileUtility.getInstance().getFileListInDirectory(".\\src\\main\\resources\\conceptMappingSpecifications", null, ".xml");
		//for (File conceptMappingFileToDelete : conceptMappingFilesToDelete)
		//{
		//	conceptMappingFileToDelete.delete();
		//}
		//FileUtility.deleteDir(conceptMappingSpecificationsDirectory, false);
		
		for (String supportedCodeSystemOID : mySupportedCodeSystemOIDs)
		{
			System.out.println("Processing subsets for code system: " + supportedCodeSystemOID);
			HashSet<Subset> subsetsForCodeSystem = myApelonDtsUtility.getSubsetsForCodeSystem(supportedCodeSystemOID, myLocalApelonDtsConnection);
			
			for (Subset subsetForCodeSystem : subsetsForCodeSystem)
			{
//				processSubset(supportedCodeSystemOID, CodeSystems.getCodeSystemName(supportedCodeSystemOID), subsetForCodeSystem);
				String codeSystemName = SimpleKnowledgeRepository.getCodeSystemName(supportedCodeSystemOID);
				processSubset(supportedCodeSystemOID, codeSystemName, subsetForCodeSystem);				
			}
		}		
	}
	

	private void processSubset(String codeSystemOID, String codeSystemName, Subset subset) 
			throws IllegalArgumentException, DTSException, DSSRuntimeExceptionFault
	{
		// Note: currently, Apelon subsets for ICD-9-CM create subset files for two OIDs, 
		//		due to separate OIDs being available for ICD-9-CM based on procedures and diagnoses.
		// TODO: revisit above, although probably okay since clients may confuse the use of the two ICD-9-CM OIDs.

		String subsetName = subset.getName();
		
		String conceptDeterminationMethodDisplayName = null;
		String conceptDeterminationMethodCode = null;
		String conceptDisplayName = null;
		String conceptCode = null;
		
		String openCdsSubsetFileName = null;
		
		if (subsetName.startsWith("OPENCDS_"))
		{
			openCdsSubsetFileName = subsetName.substring(8);
			ArrayList<String> subsetNameParts = (ArrayList<String>) StringToArrayUtility.getInstance().getRegularArrayFromString(openCdsSubsetFileName, "__");
	
			boolean subsetNameValid = true;
			
			if (subsetNameParts.size() != 2)
			{
				System.err.println("The OpenCDS subset " + openCdsSubsetFileName + " could not be processed as it does not have two parts separated by \"__\".");
				subsetNameValid = false;
			}
			
			if (subsetNameValid)
			{
				ArrayList<String> subsetNameComponents = (ArrayList<String>) StringToArrayUtility.getInstance().getRegularArrayFromString(subsetNameParts.get(1), "^");
				
				if (subsetNameComponents.size() == 2)
				{
					conceptCode = subsetNameComponents.get(0);
					conceptDeterminationMethodCode = subsetNameComponents.get(1);					
				}
				else
				{
					System.err.println("The OpenCDS subset " + openCdsSubsetFileName + " could not be processed as it did not contain 2 coded elements after \"__\".");
					subsetNameValid = false;
				}
			}
			
			if (subsetNameValid)
			{
				// get the OpenCDS codes associated with these concepts
				HashSet<DTSConcept> conceptDeterminationMethodDTSConcepts = myApelonDtsUtility.getConceptsWithProperty(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS, "Code in Source", conceptDeterminationMethodCode, myCentralOpenCDSApelonDtsConnection);
				HashSet<DTSConcept> conceptDTSConcepts = myApelonDtsUtility.getConceptsWithProperty(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS, "Code in Source", conceptCode, myCentralOpenCDSApelonDtsConnection);
				
				for (DTSConcept conceptDeterminationMethodDTSConcept : conceptDeterminationMethodDTSConcepts)
				{
					// should be 1 member in array
					conceptDeterminationMethodDisplayName = conceptDeterminationMethodDTSConcept.getName();
					
					// FIXME: use configuration file here or Apelon property.  Resolution to temporary problem due to use of file system and long names exceeding Windows file path max size.
					if (conceptDeterminationMethodCode.equals("C45"))
					{
						conceptDeterminationMethodDisplayName = "NQF";
					}
					else if (conceptDeterminationMethodCode.equals("C36"))
					{
						conceptDeterminationMethodDisplayName = "OpenCDS";
					} 
				}
				if (conceptDeterminationMethodDisplayName == null) 
				{
					throw new IllegalArgumentException("OpenCDS subset " + subsetName + " specifies a concept determination method of [" + conceptDeterminationMethodCode + "] that is not recognized by the Apelon server.");			
				}
				
				for (DTSConcept conceptDTSConcept : conceptDTSConcepts)
				{
					// should be 1 member in array
					conceptDisplayName = conceptDTSConcept.getName();
				}
				if (conceptDisplayName == null) 
				{
					throw new IllegalArgumentException("OpenCDS subset " + subsetName + " specifies a concept of [" + conceptCode + "] that is not recognized by the Apelon server.");			
				}
				
				// Now have all the required values for the focal concept of the subset.  Now get subset members.
				HashSet<CD> subsetMembers = myApelonDtsUtility.getCDsForSubset(codeSystemOID, codeSystemName, subset, myLocalApelonDtsConnection);
				
				boolean allInformationRequiredForMappingSpecificationAvailable = false;
				if (subsetMembers.size() > 0)
				{
					allInformationRequiredForMappingSpecificationAvailable = true;
				}
				
				if (allInformationRequiredForMappingSpecificationAvailable)
				{
					XmlEntity openCdsConceptMappingSpecificationFileEntity = new XmlEntity("OpenCdsConceptMappingSpecificationFile");
					XmlEntity specficationNotesEntity = new XmlEntity("specificationNotes", "Created using ApelonConceptMappingSpecificationFileCreator.", false);  // removed timestamp so file contents don't get flagged as change in version control system based on timestamp difference.  // at " + (new java.util.Date()).toString(), false);
					openCdsConceptMappingSpecificationFileEntity.addChild(specficationNotesEntity);
					
					XmlEntity openCdsConceptEntity = new XmlEntity("openCdsConcept");
					openCdsConceptEntity.addAttribute("code", conceptCode);
					openCdsConceptEntity.addAttribute("displayName", conceptDisplayName);
					openCdsConceptMappingSpecificationFileEntity.addChild(openCdsConceptEntity);
					
					XmlEntity openCdsConceptDeterminationMethodEntity = new XmlEntity("conceptDeterminationMethod");
					openCdsConceptDeterminationMethodEntity.addAttribute("code", conceptDeterminationMethodCode);
					openCdsConceptDeterminationMethodEntity.addAttribute("displayName", conceptDeterminationMethodDisplayName);
					openCdsConceptMappingSpecificationFileEntity.addChild(openCdsConceptDeterminationMethodEntity);
					
					XmlEntity membersForCodeSystemEntity = new XmlEntity("membersForCodeSystem");
					membersForCodeSystemEntity.addAttribute("codeSystem", codeSystemOID);
					membersForCodeSystemEntity.addAttribute("codeSystemName", codeSystemName);
					openCdsConceptMappingSpecificationFileEntity.addChild(membersForCodeSystemEntity);
					
					ArrayList<String> subsetMembersAsString = new ArrayList<String>();
					HashMap<String, String> subsetMemberCodeToDisplayName = new HashMap<String, String>();
					
					for (CD subsetMember : subsetMembers)
					{
						String code = subsetMember.getCode();
						String displayName = subsetMember.getDisplayName();
						subsetMemberCodeToDisplayName.put(code, displayName);
						subsetMembersAsString.add(code);						
					}
					
					Collections.sort(subsetMembersAsString);
					
					for (String subsetMemberAsString : subsetMembersAsString)
					{
						XmlEntity cdEntity = new XmlEntity("CD");
						cdEntity.addAttribute("code", subsetMemberAsString);
						cdEntity.addAttribute("displayName", subsetMemberCodeToDisplayName.get(subsetMemberAsString));
						membersForCodeSystemEntity.addChild(cdEntity);
					}				

//	following code replaced by code to use the KR interface
//					// save the file, after deleting existing file if it exists
//					// String configFileName = ".\\src\\main\\resources\\conceptMappingSpecifications\\autoGeneratedMappings\\" + openCdsSubsetFileName + ".xml";
//					String configFileName = ".\\src\\main\\resources\\conceptMappingSpecifications\\autoGeneratedMappings\\" + codeSystemName + "^" + conceptDisplayName + "^" + conceptDeterminationMethodDisplayName + "__" + codeSystemOID + "^" + conceptCode + "^" + conceptDeterminationMethodCode + ".xml";
//					configFileName = replaceInvalidFilenameCharactersWithSpace(configFileName);
//					
//					File oldConfigFile = new File(configFileName);
//					if (oldConfigFile != null)
//					{
//						oldConfigFile.delete();
//					}
//					
//					String xmlAsString = openCdsConceptMappingSpecificationFileEntity.getAsVerboseXmlString(5, true, true);
//					ArrayList<String> fileLines = new ArrayList<String>();
//					fileLines.add(xmlAsString);
//					WriteUtility.getInstance().saveText(fileLines, new File(configFileName), "UTF-8");				

					// save the resource, which will add the resource if it does not exist, or replace it if it does
					String configResourceId = codeSystemName + "^" + conceptDisplayName + "^" + conceptDeterminationMethodDisplayName + "__" + codeSystemOID + "^" + conceptCode + "^" + conceptDeterminationMethodCode + ".xml";
					configResourceId = replaceInvalidFilenameCharactersWithSpace(configResourceId);
					
					String xmlAsString = openCdsConceptMappingSpecificationFileEntity.getAsVerboseXmlString(5, true, true);

					File newOrUpdatedFile = SimpleKnowledgeRepository.createOrReplaceXMLResource("conceptMappingSpecifications/autoGeneratedMappings", configResourceId, xmlAsString);
					System.out.println("created/replaced: " + newOrUpdatedFile.getAbsolutePath());
				}
				else
				{
					System.out.println("Concept mapping file not created for OpenCDS subset " + subsetName + " as subset members not identified.");
				}
			}
			System.out.println("Processed subset: " + subsetName);
		}
	}
	
	/**
	 * Replaces / ? : * " > < | with " ".
	 * @param originalName
	 * @return
	 */
	private String replaceInvalidFilenameCharactersWithSpace(String originalName)
	{
		if (originalName == null)
		{
			return null;
		}
		else
		{
			originalName = originalName.replaceAll("/", " ");
			originalName = originalName.replaceAll("\\?", " ");
			originalName = originalName.replaceAll(":", " ");
			originalName = originalName.replaceAll("\\*", " ");
			originalName = originalName.replaceAll("\"", " ");
			originalName = originalName.replaceAll(">", " ");
			originalName = originalName.replaceAll("<", " ");
			originalName = originalName.replaceAll("\\|", " ");
			return originalName;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int createResourcesChoice = JOptionPane.showConfirmDialog(null, "Create concept mapping specifications using Apelon? \n (note that existing autoGeneratedMappings configuration files the same names as newly created files will be deleted)");
			if (createResourcesChoice == JOptionPane.YES_OPTION)
			{
//				File apelonConfigFile = new File(".\\src\\main\\resources\\apelon\\LocalApelonDtsConfigFile.xml");
//				String configFileXmlAsString = FileUtility.getInstance().getFileContentsAsString(apelonConfigFile);
				// above replaced by 3 lines below to use automatic configuration based on config file des 2012-01-02
//				new OpencdsConfigurator();
//				SimpleKnowledgeRepository myKR = SimpleKnowledgeRepository.getInstance();
				// revised to configure SimpleKnowledgeRepository correctly des 2012-07-05
		        OpencdsConfigurator 		oConfig = new OpencdsConfigurator();  										// initializes the OpencdsConfigurator.
		        SimpleKnowledgeRepository.setFullPathToKRData(oConfig.getKrFullPath());	// initializes the KnowledgeRepository				
				String configFileXmlAsString 		= SimpleKnowledgeRepository.getResourceAsString("apelon", "LocalApelonDtsConfigFile.xml");
				
				XmlEntity configFileRootEntity = XmlConverter.getInstance().unmarshalXml(configFileXmlAsString, false, null);
				XmlEntity connectionInfoEntity = configFileRootEntity.getFirstChildWithLabel("connectionInfo");
				
				String localDtsHost = connectionInfoEntity.getFirstChildWithLabel("host").getValue();
				String localDtsPort = connectionInfoEntity.getFirstChildWithLabel("port").getValue();
				//String user = connectionInfoEntity.getFirstChildWithLabel("user").getValue();
				//String password = connectionInfoEntity.getFirstChildWithLabel("password").getValue();

				String localDtsUserName = "";
				String localDtsPassword = "";
				JTextField textField = new JTextField();
				JPasswordField passwordField = new JPasswordField();
				passwordField.setEchoChar('*');
				Object[] obj = {"Please enter the Apelon DTS user name:\n\n", textField};
				Object stringArray[] = {"OK","Cancel"};
				if (JOptionPane.showOptionDialog(null, obj, "Need user name", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION)
				{
					localDtsUserName = textField.getText();
				}
				
				Object[] obj2 = {"Please enter the Apelon DTS password:\n\n", passwordField};
				if (JOptionPane.showOptionDialog(null, obj2, "Need password", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj2) == JOptionPane.YES_OPTION)
				{
					localDtsPassword = new String(passwordField.getPassword());
				}
				
				if ((localDtsUserName.equals("")) ||  (localDtsPassword.equals("")))
				{				
					JOptionPane.showMessageDialog(null, "Aborting process.");
				}
				else
				{				
					// Get Central OpenCDS Apelon connection info
//					File centralOpenCDSApelonConfigFile = new File(".\\src\\main\\resources\\apelon\\CentralOpenCDSApelonDtsConfigFile.xml");
//					String centralOpenCDSConfigFileXmlAsString = FileUtility.getInstance().getFileContentsAsString(centralOpenCDSApelonConfigFile);
					String centralOpenCDSConfigFileXmlAsString = SimpleKnowledgeRepository.getResourceAsString("apelon", "CentralOpenCDSApelonDtsConfigFile.xml");
					
					XmlEntity centralOpenCDSConfigFileRootEntity = XmlConverter.getInstance().unmarshalXml(centralOpenCDSConfigFileXmlAsString, false, null);
					XmlEntity centralOpenCDSConnectionInfoEntity = centralOpenCDSConfigFileRootEntity .getFirstChildWithLabel("connectionInfo");
					
					String centralDtsHost = centralOpenCDSConnectionInfoEntity.getFirstChildWithLabel("host").getValue();
					int centralDtsPort = new Integer(centralOpenCDSConnectionInfoEntity.getFirstChildWithLabel("port").getValue()).intValue();
					String centralDtsUserName  = centralOpenCDSConnectionInfoEntity.getFirstChildWithLabel("userName").getValue();
					String centralDtsPassword = centralOpenCDSConnectionInfoEntity.getFirstChildWithLabel("password").getValue();
					
					// get supported code systems for local Apelon DTS					
					XmlEntity supportedCodeSystemsEntity = configFileRootEntity.getFirstChildWithLabel("supportedCodeSystems");
					ArrayList<String> supportedCodeSystemOIDs = new ArrayList<String>();
					ArrayList<XmlEntity> supportedCodeSystemEntities = (ArrayList<XmlEntity>) supportedCodeSystemsEntity.getChildren();
					for (XmlEntity supportedCodeSystemEntity : supportedCodeSystemEntities)
					{
						supportedCodeSystemOIDs.add(supportedCodeSystemEntity.getValue());
					}
					
					System.out.println("Initiating connection to Apelon servers...");
					
					ApelonConceptMappingSpecificationFilesCreator apelonResourceCreator = new ApelonConceptMappingSpecificationFilesCreator(localDtsHost, new Integer(localDtsPort).intValue(), localDtsUserName, localDtsPassword, centralDtsHost, new Integer(centralDtsPort).intValue(), centralDtsUserName, centralDtsPassword, supportedCodeSystemOIDs);
					
					System.out.println("Creating concept mapping resources...");
					
					apelonResourceCreator.createResources();
					apelonResourceCreator.shutdown();
					
					JOptionPane.showMessageDialog(null, "Process completed successfully.");
				}
			}					
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Process aborted with errors.  Details: " + e.getMessage());			
		}
	}	
}
