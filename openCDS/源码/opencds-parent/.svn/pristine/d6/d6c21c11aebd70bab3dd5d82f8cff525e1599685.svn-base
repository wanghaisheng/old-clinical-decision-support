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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.structures.ObjectPair;
import org.opencds.common.terminology.CD;
import org.opencds.common.terminology.CodeSystems;
import org.opencds.common.terminology.OpenCDSConceptTypes;
import org.opencds.common.xml.XmlConverter;
import org.opencds.common.xml.XmlEntity;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.xml.sax.SAXParseException;

/**
 * Simple OpenCDS terminology manager.
 * 
 * Will be converted into a proper Web service in the future.
 * 
 * Intended to be superseded by proper terminology services (e.g., Apelon DTS) in the future.
 * 
 * @author kawam001
 *
 */
public class SimpleTerminologyManager extends TerminologyManager
{
	private static SimpleTerminologyManager instance;  //singleton instance
	private static Log log = LogFactory.getLog(SimpleTerminologyManager.class);
	
	private HashMap<CD, HashSet<ObjectPair>> myCdToOpenCdsConceptObjectPairSetMap = new HashMap<CD, HashSet<ObjectPair>>(); 
	// key = CD (note that CD's hashCode() method only considers code and code system, not the display names.
	// target = HashSet of ObjectPairs, in which object1 is an CD representing a matched OpenCDS concept and object2 is a CS containing the determinationMethod 
	// used to determine that the code matches the identified concept.
	
	private HashMap<ObjectPair, HashSet<ObjectPair>> myObjectPairToOpenCdsConceptObjectPairSetMap = new HashMap<ObjectPair, HashSet<ObjectPair>>(); 
	// key = ObjectPair of two CD elements (note that CD's hashCode() method only considers code and code system, not the display names.  This HashMap
	//				is designed to support concepts of BodySite with a separate Laterality CD
	// target = HashSet of ObjectPairs, in which object1 is an CD representing a matched OpenCDS concept and object2 is a CS containing the determinationMethod 
	// used to determine that the code matches the identified concept.
	
	private HashMap<String, String> myConceptTypesNametoConceptTypeMap = new HashMap<String, String>(); 
	// key = ConceptType name; target = ConceptType display name. 
	private HashMap<String, String> myCodeSystemOIDtoCodeSystemDisplayNameMap = new HashMap<String, String>(); 
	// key = code system OID; target = code system display name. 
	private HashMap<String, String> myCodeSystemDisplayNametoCodeSystemOIDMap = new HashMap<String, String>(); 
	// key = code system display name; target = code system OID. 

	//following code to force SimpleKnowledgeRepository to initialize itself...
	@SuppressWarnings("unused")
	private void SimpleKnowledgeRepository()  throws DSSRuntimeExceptionFault
	{
		initialize();
	}

	private SimpleTerminologyManager() throws DSSRuntimeExceptionFault
	{
		initialize();
	}
	
	public static synchronized SimpleTerminologyManager getInstance() throws DSSRuntimeExceptionFault
	{
		if (instance == null)
		{
			instance = new SimpleTerminologyManager();
		}
		return instance;
	}
	
	private void initialize() throws DSSRuntimeExceptionFault
	{
		// load code system OID to display name map and displayName to OID map		
		String openCDSCodeSystems = SimpleKnowledgeRepository.getResourceAsString("resourceAttributes","openCDSCodeSystems.xml");
		XmlEntity codeSystemsRootEntity = null;
		try 
		{
			codeSystemsRootEntity = XmlConverter.getInstance().unmarshalXml(openCDSCodeSystems, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleTerminologyService.initialize() failed to unmarshall '" + openCDSCodeSystems + "' " + e.getMessage());
		}

		// get target OpenCDS resource element
		ArrayList<XmlEntity> codeSystemEntitites = codeSystemsRootEntity.getChildrenWithLabel("codeSystem"); 
		for (XmlEntity codeSystemEntity : codeSystemEntitites)
		{
			String	codeSystemOid			= codeSystemEntity.getAttributeValue("codeSystemOID");
			String 	codeSystemDisplayName	= codeSystemEntity.getAttributeValue("codeSystemDisplayName");
			myCodeSystemOIDtoCodeSystemDisplayNameMap.put(codeSystemOid, codeSystemDisplayName);
			myCodeSystemDisplayNametoCodeSystemOIDMap.put(codeSystemDisplayName, codeSystemOid);
			log.trace("codeSystem: " + codeSystemOid + ", name: " + codeSystemDisplayName);
		}	
/*		
		// load ConceptType name to value map
		String openCDSConceptTypes = SimpleKnowledgeRepository.getInstance().getResourceAsString("resourceAttributes","openCDSConceptTypes.xml");
		XmlEntity conceptTypesfileRootEntity = null;
		try 
		{
			conceptTypesfileRootEntity = XmlConverter.getInstance().unmarshalXml(openCDSConceptTypes, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleTerminologyService.initialize() failed to unmarshall '" + openCDSConceptTypes + "' " + e.getMessage());
		}

		// get target OpenCDS resource element
		ArrayList<XmlEntity> conceptTypeEntities = conceptTypesfileRootEntity.getChildrenWithLabel("ConceptType");
		for (XmlEntity conceptTypeEntity : conceptTypeEntities)
		{
			myConceptTypesNametoConceptTypeMap.put(conceptTypeEntity.getAttributeValue("name"), conceptTypeEntity.getAttributeValue("value"));
		}			
*/		
		/*
		 * Above code replaced by single line below, since openCDSConceptTypes.xml file is deprecated in the KR, des 2012-03-14
		 */
		// load ConceptType name to value map from OpenCDSConceptTypes.java
		OpenCDSConceptTypes.getOpenCdsConceptTypes(myConceptTypesNametoConceptTypeMap);
		
		// load concept specification files
		String extendedPath = "conceptMappingSpecifications//autoGeneratedMappings";
		List<String> fileNames = SimpleKnowledgeRepository.listResourceNamesByType(extendedPath);
		loadEachFile(fileNames, extendedPath);
		
		String extendedPath2 = "conceptMappingSpecifications//manualMappings";
		List<String> fileNames2 = SimpleKnowledgeRepository.listResourceNamesByType(extendedPath2);
		loadEachFile(fileNames2, extendedPath2);
	}
	
	
	private void loadEachFile(List<String> fileNames, String extendedPath) throws DSSRuntimeExceptionFault
	{
	
		for (String fileName : fileNames)
		{
			String fileNameLowerCase = fileName.toLowerCase();	//so we catch .xml, .XML, .XmL, etc.  des 20121112
			if (fileNameLowerCase.endsWith(".xml")) // without this check, when packaged into jars, seems to find a filename of "" that gets processed
			{
				String fileXmlAsString = SimpleKnowledgeRepository.getResourceAsXML(extendedPath, fileName);

				XmlEntity fileNamesRootEntity = null;
				try {
					fileNamesRootEntity = XmlConverter.getInstance().unmarshalXml(fileXmlAsString, false, null);
				} catch (SAXParseException e) {
					e.printStackTrace();
					throw new DSSRuntimeExceptionFault("SimpleTerminologyService.initialize() failed to unmarshall '" + fileXmlAsString + "' " + e.getMessage());
				}

				// get target OpenCDS concept
				XmlEntity openCdsConceptEntity = fileNamesRootEntity.getFirstChildWithLabel("openCdsConcept");
				CD openCdsConcept = new CD(	CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS,
						getCodeSystemFromOID(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS),
											openCdsConceptEntity.getAttributeValue("code"), 
											openCdsConceptEntity.getAttributeValue("displayName"));

				// get concept determination method
				XmlEntity conceptDeterminationMethodEntity = fileNamesRootEntity.getFirstChildWithLabel("conceptDeterminationMethod");
				CD conceptDeterminationMethod  = new CD(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS,
														getCodeSystemFromOID(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS),
														conceptDeterminationMethodEntity.getAttributeValue("code"), 
														conceptDeterminationMethodEntity.getAttributeValue("displayName"));

				ObjectPair targetObjectPair = new ObjectPair();
				targetObjectPair.setObject1(openCdsConcept);
				targetObjectPair.setObject2(conceptDeterminationMethod);
				log.trace("OpenCdsConcept: " + openCdsConcept.toString() 
						+ ", ConceptDeterminationMethod: " + conceptDeterminationMethod.toString() 
						+ ", mappingFile: " + fileName );

				// get members for code system
				XmlEntity membersForCodeSystemEntity = fileNamesRootEntity.getFirstChildWithLabel("membersForCodeSystem");
				String codeSystem = membersForCodeSystemEntity.getAttributeValue("codeSystem");

				for (XmlEntity csEntity : (ArrayList<XmlEntity>) membersForCodeSystemEntity.getChildren())
				{
					CD cd = new CD(codeSystem, csEntity.getAttributeValue("code"));
					log.trace(" - OpenCdsConcept code: " + csEntity.getAttributeValue("code") 
							+ ", codeSystem: " + codeSystem.toString());

					if ((myCdToOpenCdsConceptObjectPairSetMap != null) && (myCdToOpenCdsConceptObjectPairSetMap.containsKey(cd)))
					{
						HashSet<ObjectPair> targetSet = myCdToOpenCdsConceptObjectPairSetMap.get(cd);
						targetSet.add(targetObjectPair);
					}
					else
					{
						HashSet<ObjectPair> objectPairSet = new HashSet<ObjectPair>();
						objectPairSet.add(targetObjectPair);
						myCdToOpenCdsConceptObjectPairSetMap.put(cd, objectPairSet);	
					}
				}
			}
		}
		
	}

	@Override
	/**
	 * Returns a HashSet containing an ObjectPair in which object1 is an CD representing a matched 
	 * OpenCDS concept and object2 is a CS containing the determinationMethod used to 
	 * determine that the code matches the identified concept.
	 * 
	 * Returns null if no matches found.
	 * @param cd 	codeSystem and code must be non-null if a match attempt is to be made
	 * @return
	 */
	public HashSet<ObjectPair> getMatchingOpenCDSConcepts(CD cd) {
		if ((cd.getCode() == null) || (cd.getCodeSystem() == null))
		{
			return null;
		}
		else
		{
			return myCdToOpenCdsConceptObjectPairSetMap.get(cd);
		}
	}

	/**
	 * This method is to return a single OpenCDS Concept for a coordinated pair of concepts, such as BodySite and Laterality
	 * 
	 * Returns a HashSet containing an ObjectPair in which object1 is an CD representing a matched 
	 * OpenCDS concept and object2 is a CS containing the determinationMethod used to 
	 * determine that the code matches the identified concept.
	 * 
	 * Returns null if no matches found.
	 * @param ObjectPair 	two sets of codeSystem and code must be non-null if a match attempt is to be made
	 * @return
	 */
	public HashSet<ObjectPair> getMatchingOpenCDSConcepts(ObjectPair bodySiteAndLaterality) {
		CD bodySite = (CD)bodySiteAndLaterality.getObject1();
		CD laterality = (CD)bodySiteAndLaterality.getObject2();
		if ((bodySite.getCode() == null) || (bodySite.getCodeSystem() == null))
		{
			return null;
		}
		else if ((laterality.getCode() == null) || (laterality.getCodeSystem() == null))
		{
			return null;
		}
		else
		{
			return myObjectPairToOpenCdsConceptObjectPairSetMap.get(bodySiteAndLaterality);
		}
	}

	@Override
	/**
	 * Returns a HashMap containing the OpenCDSConcepts supported by the terminology service.
	 * 
	 * All OpenCDSConcept types are represented.  If there are n matching OpenCDSConcepts for the type, an empty 
	 * HashSet is associated with the key.
	 * 
	 * Key = supported OpenCDSConcept type enumerated in org.opencds.terminology.OpenCDSConceptTypes.
	 * Target = HashSet of supported OpenCDSConcepts for type.
	 *  
	 * @return
	 */
	public HashMap<String, HashSet<CD>> getSupportedOpenCDSConcepts() throws DSSRuntimeExceptionFault {
		
		HashMap<String, HashSet<CD>> mapToReturn = new HashMap<String, HashSet<CD>>();
		
		// load config file with contents
		//InputStream configFileStream = this.getClass().getClassLoader().getResourceAsStream("SupportedConceptsConfigFile.xml");
		//String configFileXmlAsString = StreamUtility.getInstance().getStringFromInputStream_depleteStream(configFileStream);
		String configFileXmlAsString = SimpleKnowledgeRepository.getResourceAsString("resourceAttributes", "supportedConceptsConfigFile.xml");
		XmlEntity configFileRootEntity = null;
		try {
			configFileRootEntity = XmlConverter.getInstance().unmarshalXml(configFileXmlAsString, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("SAXParseException in SimpleTerminologyManager.getSupportedOpenCDSConcepts: " + e.getMessage());
		}
		for (XmlEntity supportedConceptsForTypeEntity : (ArrayList<XmlEntity>) configFileRootEntity.getChildren())
		{
			String openCdsConceptType = supportedConceptsForTypeEntity.getFirstChildWithLabel("openCdsConceptType").getValue();
			
			ArrayList<XmlEntity> supportedOpenCdsConceptEntities = (ArrayList<XmlEntity>) supportedConceptsForTypeEntity.getChildrenWithLabel("supportedOpenCdsConcept");
			
			HashSet<CD> openCdsConceptsForType = new HashSet<CD>();
			
			for (XmlEntity supportedOpenCdsConceptEntity : supportedOpenCdsConceptEntities)
			{
				XmlEntity openCdsConceptEntity = supportedOpenCdsConceptEntity.getFirstChildWithLabel("openCdsConcept");
//				CD openCdsConcept = new CD("2.16.840.1.113883.3.795.12.1.1", "OpenCDS concepts", openCdsConceptEntity.getAttributeValue("code"), openCdsConceptEntity.getAttributeValue("displayName"));
				CD openCdsConcept = new CD(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS, 
											"OpenCDS concepts", 
											openCdsConceptEntity.getAttributeValue("code"), 
											openCdsConceptEntity.getAttributeValue("displayName"));
				openCdsConceptsForType.add(openCdsConcept);
			}
			mapToReturn.put(openCdsConceptType, openCdsConceptsForType);
		}
		
		ArrayList<String> openCdsConceptTypes = OpenCDSConceptTypes.getOpenCdsConceptTypes();
		for (String openCdsConceptType : openCdsConceptTypes)
		{
			if (! mapToReturn.containsKey(openCdsConceptType))
			{
				mapToReturn.put(openCdsConceptType, new HashSet<CD>());
			}
		}
		
		return mapToReturn;
	}
	
	@Override
	/**
	 * Returns CDs that are equivalent matches to the specified OpenCDSConcept. 
	 * 
	 * @param openCDSConcept
	 * @return
	 */
	public HashSet<CD> getMatchingCodes(CD openCDSConcept) {
		// TODO: this method is not yet implemented
		return null;
	}
	
	/**
	 * Returns a String containing the OpenCDSConceptType
	 * key = ConceptType name; 
	 * target = ConceptType value.
	 * 
	 * @param conceptTypeName
	 * @return
	 */
	public String getConceptTypeValue(String conceptTypeName) {
		if ((conceptTypeName == null) || ( "".equals(conceptTypeName) ))
		{
			return null;
		}
		else
		{
			return myConceptTypesNametoConceptTypeMap.get(conceptTypeName);
		}
	}

	/**
	 * Returns a String containing the OpenCDS CodeSystem corresponding to the submitted OID
	 * key = CodeSystem OID; 
	 * target = CodeSystem Display name.
	 * 
	 * @param codeSystemOID
	 * @return
	 */
	public String getCodeSystemFromOID(String codeSystemOID) {
		if ((codeSystemOID == null) || ( "".equals(codeSystemOID) ))
		{
			return null;
		}
		else
		{
			return myCodeSystemOIDtoCodeSystemDisplayNameMap.get(codeSystemOID);
		}
	}
	
	/**
	 * Returns a String containing the OpenCDS CodeSystem corresponding to the submitted OID
	 * key = CodeSystem OID; 
	 * target = CodeSystem Display name.
	 * 
	 * @param codeSystemOID
	 * @return
	 */
	public String getOIDFromCodeSystem(String codeSystemName) {
		if ((codeSystemName == null) || ( "".equals(codeSystemName) ))
		{
			return null;
		}
		else
		{
			return myCodeSystemDisplayNametoCodeSystemOIDMap.get(codeSystemName);
		}
	}
}