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

package org.opencds.terminology.apelon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.terminology.CD;
import org.opencds.common.terminology.CDWithChildren;
import org.opencds.common.terminology.CodeSystems;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;

import com.apelon.apelonserver.client.ApelonException;
import com.apelon.apelonserver.client.ServerConnectionSecureSocket;
import com.apelon.dts.client.DTSException;
import com.apelon.dts.client.association.AssociationQuery;
import com.apelon.dts.client.association.AssociationType;
import com.apelon.dts.client.attribute.DTSProperty;
import com.apelon.dts.client.attribute.DTSPropertyType;
import com.apelon.dts.client.concept.ConceptAttributeSetDescriptor;
import com.apelon.dts.client.concept.ConceptChild;
import com.apelon.dts.client.concept.DTSConcept;
import com.apelon.dts.client.concept.DTSConceptQuery;
import com.apelon.dts.client.concept.DTSSearchOptions;
import com.apelon.dts.client.concept.NavChildContext;
import com.apelon.dts.client.concept.NavQuery;
import com.apelon.dts.client.concept.OntylogConcept;
import com.apelon.dts.client.concept.OntylogConceptQuery;
import com.apelon.dts.client.concept.SearchQuery;
import com.apelon.dts.client.namespace.Namespace;
import com.apelon.dts.client.namespace.NamespaceQuery;
import com.apelon.dts.client.subset.SubsetQuery;
import com.apelon.dts.common.DataTypeFilter;
import com.apelon.dts.common.subset.Subset;
import com.apelon.dts.common.subset.SubsetDescriptor;

/**
 * <p>ApelonDtsUtility is used to mediate with the Apelon DTS API to facilitate its access and use.  This class was created referencing the ApelonDTSHandler class shared by the OpenInfobutton project.</p>
 * 
 * @author Kensaku Kawamoto
 * @version 1.00
 */
public class ApelonDtsUtility extends Object
{
	// TODO: this is a work in progress.  Also probably should take configuration information currently in initialize() method and externalize to a config file.
	
	private static ApelonDtsUtility instance;  //singleton instance
	private static Log log = LogFactory.getLog(ApelonDtsUtility.class);
	
	private HashMap<String, String> myCodeSystemOIDtoApelonNamespaceName;
	private HashMap<String, String> myApelonNamespaceNameToCodeSystemOID;
	private HashMap<String, Boolean> myCodeSystemOIDtoIsOntylog; // true if Ontylog, false otherwise
	private HashMap<String, String> myCodeSystemOIDtoApelonParentOfRelationshipName; // Apelon's "Parent Of" relationship name for code system.  Only applicable to Thesaurus-type code systems, as Ontylog-type code systems have built-in descendant navigation capabilities.  Assumed to be "Parent Of" if not specified.
	
//	private SimpleKnowledgeRepository myKr;

    private ApelonDtsUtility() throws DSSRuntimeExceptionFault
    {
    	initialize();
    }

    public static synchronized ApelonDtsUtility getInstance() throws DSSRuntimeExceptionFault
    {
        if (instance == null)
        {
            instance = new ApelonDtsUtility();
        }
        return instance;
    }
    
    private void initialize() throws DSSRuntimeExceptionFault
    {
    	// TODO: consider extracting to configuration file.  Add entries as needed for now.
    	myCodeSystemOIDtoApelonNamespaceName = new HashMap<String, String>();
    	myApelonNamespaceNameToCodeSystemOID = new HashMap<String, String>();
    	myCodeSystemOIDtoIsOntylog = new HashMap<String, Boolean>();
    	myCodeSystemOIDtoApelonParentOfRelationshipName = new HashMap<String, String>();
  
//    	myKr = SimpleKnowledgeRepository.getInstance();
    	
    	ArrayList<String> codeSystemOIDs = SimpleKnowledgeRepository.getOpenCdsCodeSystemOIDs();
    	for (String thisCodeSystem : codeSystemOIDs) {
//    		String thisCodeSystemName 	= SimpleKnowledgeRepository.getCodeSystemName(thisCodeSystem);
    		String apelonNamespaceName 	= SimpleKnowledgeRepository.getApelonNamespaceName(thisCodeSystem);
    		Boolean isApelonOntylog 	= SimpleKnowledgeRepository.getIsApelonOntylog(thisCodeSystem);
    		
        	myCodeSystemOIDtoApelonNamespaceName.put(thisCodeSystem, apelonNamespaceName);
        	myApelonNamespaceNameToCodeSystemOID.put(apelonNamespaceName, thisCodeSystem);        	
        	myCodeSystemOIDtoIsOntylog.put(thisCodeSystem, isApelonOntylog); 
    	}

////    	myCodeSystemOIDtoApelonNamespaceName.put(CodeSystems.CODE_SYSTEM_OID_HL7_V3_CODE_SYSTEM, "HL7");
////    	myApelonNamespaceNameToCodeSystemOID.put("HL7", CodeSystems.CODE_SYSTEM_OID_HL7_V3_CODE_SYSTEM);
////    	myCodeSystemOIDtoIsOntylog.put(CodeSystems.CODE_SYSTEM_OID_HL7_V3_CODE_SYSTEM, new Boolean(false)); // TODO: change to true when switch to using proper (i.e., imported) Apelon terminology for HL7
//    	
//    	myCodeSystemOIDtoApelonNamespaceName.put(CodeSystems.CODE_SYSTEM_OID_CPT4, "CPT"); // TODO: revisit whether this should be "CPT extension" instead of "CPT"
//    	myApelonNamespaceNameToCodeSystemOID.put("CPT", CodeSystems.CODE_SYSTEM_OID_CPT4); // TODO: revisit whether this should be "CPT extension" instead of "CPT"
//    	myCodeSystemOIDtoIsOntylog.put(CodeSystems.CODE_SYSTEM_OID_CPT4, new Boolean(false));
//    	
//    	myCodeSystemOIDtoApelonNamespaceName.put(CodeSystems.CODE_SYSTEM_OID_ICD9CM_DIAGNOSES, "ICD-9-CM");
//    	myApelonNamespaceNameToCodeSystemOID.put("ICD-9-CM", CodeSystems.CODE_SYSTEM_OID_ICD9CM_DIAGNOSES);
//    	myCodeSystemOIDtoIsOntylog.put(CodeSystems.CODE_SYSTEM_OID_ICD9CM_DIAGNOSES, new Boolean(true));
//    	
//    	myCodeSystemOIDtoApelonNamespaceName.put(CodeSystems.CODE_SYSTEM_OID_ICD9CM_PROCEDURES, "ICD-9-CM");
//    	myApelonNamespaceNameToCodeSystemOID.put("ICD-9-CM", CodeSystems.CODE_SYSTEM_OID_ICD9CM_PROCEDURES);
//    	myCodeSystemOIDtoIsOntylog.put(CodeSystems.CODE_SYSTEM_OID_ICD9CM_PROCEDURES, new Boolean(true));
//    	
//    	myCodeSystemOIDtoApelonNamespaceName.put(CodeSystems.CODE_SYSTEM_OID_LOINC, "LOINC [Logical Observation Identifiers Names and Codes]");
//    	myApelonNamespaceNameToCodeSystemOID.put("LOINC [Logical Observation Identifiers Names and Codes]", CodeSystems.CODE_SYSTEM_OID_LOINC);
//    	myCodeSystemOIDtoIsOntylog.put(CodeSystems.CODE_SYSTEM_OID_LOINC, new Boolean(false));
//    	
//    	myCodeSystemOIDtoApelonNamespaceName.put(CodeSystems.CODE_SYSTEM_OID_SNOMED_CT, "SNOMED CT");
//    	myApelonNamespaceNameToCodeSystemOID.put("SNOMED CT", CodeSystems.CODE_SYSTEM_OID_SNOMED_CT);
//    	myCodeSystemOIDtoIsOntylog.put(CodeSystems.CODE_SYSTEM_OID_SNOMED_CT, new Boolean(true));
//    	
//    	myCodeSystemOIDtoApelonNamespaceName.put(CodeSystems.CODE_SYSTEM_OID_OPEN_CDS_CONCEPTS, "OpenCDS");
//    	myApelonNamespaceNameToCodeSystemOID.put("OpenCDS", CodeSystems.CODE_SYSTEM_OID_OPEN_CDS_CONCEPTS);
//    	myCodeSystemOIDtoIsOntylog.put(CodeSystems.CODE_SYSTEM_OID_OPEN_CDS_CONCEPTS, new Boolean(false));
    	myCodeSystemOIDtoApelonParentOfRelationshipName.put(CodeSystems.CODE_SYSTEM_OID_OPENCDS_CONCEPTS, "Parent Of");
    }
    
    /**
     * Returns null if match not found.
     * @param codeSystemOID
     * @return
     */
    private String getApelonNamespaceNameForCodeSystem(String codeSystemOID)
    {
    	return myCodeSystemOIDtoApelonNamespaceName.get(codeSystemOID);    	
    }

	public ServerConnectionSecureSocket getSecureSocketConnection(String host, int port, String userName, String password) throws ClassNotFoundException, ApelonException {

		ServerConnectionSecureSocket secureSocketConnection;
		
		secureSocketConnection = new ServerConnectionSecureSocket(host, port, userName, password);
		secureSocketConnection.setQueryServer(Class.forName("com.apelon.dts.server.SubsetServer"),
				       com.apelon.dts.client.common.DTSHeader.SUBSETSERVER_HEADER);
		secureSocketConnection.setQueryServer(Class.forName("com.apelon.dts.server.DTSConceptServer"),
				       com.apelon.dts.client.common.DTSHeader.DTSCONCEPTSERVER_HEADER);
		secureSocketConnection.setQueryServer(Class.forName("com.apelon.dts.server.OntylogConceptServer"),
			       com.apelon.dts.client.common.DTSHeader.ONTYLOGCONCEPTSERVER_HEADER);		
		secureSocketConnection.setQueryServer(Class.forName("com.apelon.dts.server.NamespaceServer"),
				       com.apelon.dts.client.common.DTSHeader.NAMESPACESERVER_HEADER);
		secureSocketConnection.setQueryServer(Class.forName("com.apelon.dts.server.SearchQueryServer"),
			       com.apelon.dts.client.common.DTSHeader.SEARCHSERVER_HEADER);
		secureSocketConnection.setQueryServer(Class.forName("com.apelon.dts.server.AssociationServer"),
			       com.apelon.dts.client.common.DTSHeader.ASSOCIATIONSERVER_HEADER);
		secureSocketConnection.setQueryServer(Class.forName("com.apelon.dts.server.NavQueryServer"),
			       com.apelon.dts.client.common.DTSHeader.NAVSERVER_HEADER);
		return secureSocketConnection;
	}
	
	public void closeConnection(ServerConnectionSecureSocket connection) throws Exception
	{
		connection.close();
	}
	
	/**
	 * Returns DTSConcepts with the specified property within the specified code system.
	 * If none found, will return empty set.
	 * @param codeSystemOID 	Defined as static Strings in org.opencds.terminology.Code.
	 * @param propertyTypeName
	 * @param propertyValue
	 * @param conn
	 * @return
	 * @throws IllegalArgumentException		If codeSystemOID is unrecognized.
	 * @throws DTSException 
	 */
	public HashSet<DTSConcept> getConceptsWithProperty(String codeSystemOID, String propertyTypeName, String propertyValue, ServerConnectionSecureSocket conn) throws IllegalArgumentException, DTSException
	{
		HashSet<DTSConcept> setToReturn = new HashSet<DTSConcept>();
		
		NamespaceQuery namespaceQuery = (NamespaceQuery) NamespaceQuery.createInstance(conn);
		SearchQuery searchQuery = SearchQuery.createInstance(conn);
				
		String namespace = getApelonNamespaceNameForCodeSystem(codeSystemOID);
		
		if (namespace == null)
		{
			String errMsg = "Namespace not found for code system OID of " + codeSystemOID;
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		else
		{			
			Namespace ns = namespaceQuery.findNamespaceByName(namespace);
			int nsId = ns.getId();
			
			ConceptAttributeSetDescriptor casd = new ConceptAttributeSetDescriptor("code only");
			DTSPropertyType codeInSourcePropType = searchQuery.findPropertyTypeByName("Code in Source", nsId);
			DTSPropertyType searchPropType = searchQuery.findPropertyTypeByName(propertyTypeName, nsId);
			casd.addPropertyType(codeInSourcePropType);			
			
			DTSSearchOptions searchOptions = new DTSSearchOptions(DTSSearchOptions.MAXIMUM_LIMIT, nsId, casd);
			
			DTSConcept[] matchingConcepts = (DTSConcept[]) searchQuery.findConceptsWithPropertyMatching(searchPropType, propertyValue, searchOptions);
			
			for (DTSConcept matchingConcept : matchingConcepts)
			{
				setToReturn.add(matchingConcept);
			}								
		}
		return setToReturn;
	}
	
	/**
	 * Returns DTSConcepts with the specified name within the specified code system.
	 * If  none found, will return empty set.
	 * @param codeSystemOID		Defined as static Strings in org.opencds.terminology.Code.
	 * @param name
	 * @param conn
	 * @return
	 * @throws DTSException
	 * @throws IllegalArgumentException		If codeSystemOID is unrecognized.
	 */
	public HashSet<DTSConcept> getConceptsWithName(String codeSystemOID, String name, ServerConnectionSecureSocket conn) throws DTSException, IllegalArgumentException
	{
		HashSet<DTSConcept> setToReturn = new HashSet<DTSConcept>();
		
		NamespaceQuery namespaceQuery = (NamespaceQuery) NamespaceQuery.createInstance(conn);
		DTSConceptQuery dtsConceptQuery = DTSConceptQuery.createInstance(conn);
				
		String namespace = getApelonNamespaceNameForCodeSystem(codeSystemOID);
		
		if (namespace == null)
		{
			String errMsg = "Namespace not found for code system OID of " + codeSystemOID;
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		else
		{			
			Namespace ns = namespaceQuery.findNamespaceByName(namespace);
			int nsId = ns.getId();
			
			ConceptAttributeSetDescriptor casd = new ConceptAttributeSetDescriptor("code only");
			DTSPropertyType codeInSourcePropType = dtsConceptQuery.findPropertyTypeByName("Code in Source", nsId);
			casd.addPropertyType(codeInSourcePropType);			
			
//			DTSSearchOptions searchOptions = new DTSSearchOptions(DTSSearchOptions.MAXIMUM_LIMIT, nsId, casd);
			
			String[] names = new String[1];
			names[0] = name;
			
			int[] nsIds = new int[1];
			nsIds[0] = nsId;
			
			DTSConcept[] matchingConcepts = (DTSConcept[]) dtsConceptQuery.findConceptsByName(names, nsIds, casd);
			
			for (DTSConcept matchingConcept : matchingConcepts)
			{
				setToReturn.add(matchingConcept);
			}								
		}
		return setToReturn;
	}
	
	/**
	 * Returns subsets defined for code system.  May be empty set but is never null.
	 * @param codeSystemOID	Defined as static Strings in org.opencds.terminology.Code.
	 * @param connection
	 * @return
	 * @throws DTSException 
	 * @throws IllegalArgumentException		If codeSystemOID is unrecognized.
	 */
	public HashSet<Subset> getSubsetsForCodeSystem(String codeSystemOID, ServerConnectionSecureSocket conn) throws DTSException, IllegalArgumentException
	{
		HashSet<Subset> setToReturn = new HashSet<Subset>();
		
		NamespaceQuery namespaceQuery = (NamespaceQuery) NamespaceQuery.createInstance(conn);
		SubsetQuery subsetQuery = (SubsetQuery)SubsetQuery.createInstance(conn);
				
		String namespace = getApelonNamespaceNameForCodeSystem(codeSystemOID);
		
		if (namespace == null)
		{
			String errMsg = "Namespace not found for code system OID of " + codeSystemOID;
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		else
		{			
			Namespace ns = namespaceQuery.findNamespaceByName(namespace);
			int nsId = ns.getId();
			
			DataTypeFilter filter = new DataTypeFilter();
			SubsetDescriptor subsetDescriptor = new SubsetDescriptor(false, false);
			
			filter.setNamespaceId(nsId);
			Subset[] subsets =  subsetQuery.find(filter, subsetDescriptor);
			for (Subset subset: subsets)
			{			
				setToReturn.add(subset);
			}
		}
		return setToReturn;
	}
	
	/**
	 * Returns CDs in specified subset.  May be empty set but is never null.
	 * @param subset
	 * @param conn
	 * @return
	 * @throws DTSException
	 * @throws IllegalArgumentException		If codeSystemOID is unrecognized.
	 */
	public HashSet<CD> getCDsForSubset(String codeSystemOID, String codeSystemName, Subset subset, ServerConnectionSecureSocket conn) throws DTSException, IllegalArgumentException
	{
		HashSet<CD> setToReturn = new HashSet<CD>();
		
		SubsetQuery subsetQuery = SubsetQuery.createInstance(conn);
		DTSConceptQuery conceptQuery = DTSConceptQuery.createInstance(conn);
		
		ConceptAttributeSetDescriptor casd = new ConceptAttributeSetDescriptor("default");
		DTSPropertyType searchPropType = conceptQuery.findPropertyTypeByName("Code in Source", subsetQuery.fetchNamespaces(subset.getId())[0]);
		casd.addPropertyType(searchPropType);
		
		DTSConcept[] dtsConcepts = subsetQuery.fetchConcepts(subset.getId(), casd);
				
		for (DTSConcept dtsConcept : dtsConcepts)
		{
			String code = getCodeInSource(dtsConcept);
			String displayName = dtsConcept.getName();	
			
			if ((code != null) && (displayName != null))
			{
				// should always be the case
				CD cd = new CD(codeSystemOID, codeSystemName, code, displayName);		
				setToReturn.add(cd);
			}						
			else
			{
				String errMsg = "Error in ApelonDtsUtility.getCSsForSubset: code and/or displayName is null for dtsConcept: " + dtsConcept.toString();
				log.error(errMsg);
				System.err.println(errMsg);
			}
		}
		
		return setToReturn;
	}
	
	/**
	 * Returns concept of interest with any nested descendants.  Returns null if concept not found.
	 * @param codeSystemOID
	 * @param rootCode
	 * @param conn
	 * @return
	 * @throws IllegalArgumentException		If codeSystemOID is unrecognized.
	 * @throws DTSException 
	 */
	public CDWithChildren getConceptAndDescendants(String codeSystemOID, String rootCode, ServerConnectionSecureSocket conn) throws IllegalArgumentException, DTSException
	{		
		NamespaceQuery namespaceQuery = (NamespaceQuery) NamespaceQuery.createInstance(conn);
		
		String namespace = getApelonNamespaceNameForCodeSystem(codeSystemOID);
		
		if (namespace == null)
		{
			String errMsg = "Namespace not found for code system OID of " + codeSystemOID;
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		else
		{
			Namespace ns = namespaceQuery.findNamespaceByName(namespace);
			int nsId = ns.getId();
			
			Boolean codeSystemIsOntylog = myCodeSystemOIDtoIsOntylog.get(codeSystemOID);
			if ((codeSystemIsOntylog != null) && (codeSystemIsOntylog.booleanValue() == true))
			{
				return getConceptAndDescendants(codeSystemOID, nsId, true, null, rootCode, conn);
			}
			else
			{
				AssociationQuery associationQuery = AssociationQuery.createInstance(conn);
				
				String parentOfAssociationNameForCodeSystem = myCodeSystemOIDtoApelonParentOfRelationshipName.get(codeSystemOID);
				if (parentOfAssociationNameForCodeSystem == null)
				{
					parentOfAssociationNameForCodeSystem = "Parent Of";
				}
				AssociationType associationType = null;
				if (parentOfAssociationNameForCodeSystem != null)
				{
					associationType = associationQuery.findAssociationTypeByName(parentOfAssociationNameForCodeSystem, nsId);
				}
				
				if (associationType == null)
				{
					String errMsg = "Valid \"parent of\" relationship type not found for Thesaurus with code system OID of " + codeSystemOID;
					log.error(errMsg);
					throw new IllegalArgumentException(errMsg);
				}
				else
				{
					return getConceptAndDescendants(codeSystemOID, nsId, false, associationType, rootCode, conn);
				}
			}
		}					
	}
	
	/**
	 * Returns concept of interest with any nested descendants.  Returns null if concept not found.
	 * @param namespaceId
	 * @param namespaceIsOntylog
	 * @param childOfAssociationType		Only applicable for non-Ontylog namespaces 
	 * @param rootCode
	 * @param conn
	 * @return
	 * @throws IllegalArgumentException		If codeSystemOID is unrecognized.
	 * @throws DTSException 
	 */
	private CDWithChildren getConceptAndDescendants(String codeSystemOID, int namespaceId, boolean namespaceIsOntylog, AssociationType childOfAssociationType, String rootCode, ServerConnectionSecureSocket conn) throws IllegalArgumentException, DTSException
	{		
		CDWithChildren cDWithChildrenToReturn = null;
		DTSConceptQuery dtsConceptQuery = (DTSConceptQuery) DTSConceptQuery.createInstance(conn);
		DTSPropertyType searchPropType = dtsConceptQuery.findPropertyTypeByName("Code in Source", namespaceId);
		
		if (namespaceIsOntylog)
		{
			ConceptAttributeSetDescriptor ontylogCasd = new ConceptAttributeSetDescriptor("code with children");
			ontylogCasd.addPropertyType(searchPropType);
			ontylogCasd.setSubconcepts(true);
			
			OntylogConceptQuery ontylogConceptQuery = OntylogConceptQuery.createInstance(conn);
			OntylogConcept rootOntylogConcept = (OntylogConcept) ontylogConceptQuery.findConceptByCode(rootCode, namespaceId, ontylogCasd);
			
			if (rootOntylogConcept != null)
			{
				String code = getCodeInSource(rootOntylogConcept);
				
				cDWithChildrenToReturn = new CDWithChildren(codeSystemOID, SimpleKnowledgeRepository.getCodeSystemName(codeSystemOID), code, rootOntylogConcept.getName());
				
				OntylogConcept[] childOntylogConcepts = rootOntylogConcept.getFetchedSubconcepts();
				
				for (OntylogConcept childOntylogConcept : childOntylogConcepts)
				{
					String childOntylogCode = childOntylogConcept.getCode();
					OntylogConcept childOntylogConceptWithCodeInSource = (OntylogConcept) ontylogConceptQuery.findConceptByCode(childOntylogCode, namespaceId, ontylogCasd);
					
					cDWithChildrenToReturn.addChild(getConceptAndDescendants(codeSystemOID, namespaceId, namespaceIsOntylog, null, getCodeInSource(childOntylogConceptWithCodeInSource), conn));					
				}
			}
		}
		else
		{
//			SearchQuery searchQuery = SearchQuery.createInstance(conn);
			SearchQuery.createInstance(conn);
			NavQuery navQuery = NavQuery.createInstance(conn);
			
			ConceptAttributeSetDescriptor thesaurusCasd = new ConceptAttributeSetDescriptor("code only");
			thesaurusCasd.addPropertyType(searchPropType);			
			
//			DTSSearchOptions searchOptions = new DTSSearchOptions(DTSSearchOptions.MAXIMUM_LIMIT, namespaceId, thesaurusCasd);
			
			DTSConcept rootDtsConcept = dtsConceptQuery.findConceptByCode(rootCode, namespaceId, thesaurusCasd);
			
			if (rootDtsConcept != null)
			{
				String code = getCodeInSource(rootDtsConcept);
				String name = rootDtsConcept.getName();
				cDWithChildrenToReturn = new CDWithChildren(codeSystemOID, SimpleKnowledgeRepository.getCodeSystemName(codeSystemOID), code, name);
				
				NavChildContext navChildContext = navQuery.getNavChildContext(rootDtsConcept, thesaurusCasd, childOfAssociationType);
				ConceptChild[] childThesaurusConcepts = navChildContext.getChildren();
				for (ConceptChild childThesaurusConcept : childThesaurusConcepts)
				{					
					String childThesaurusConceptCode = childThesaurusConcept.getCode();
					DTSConcept childThesaurusConceptWithCodeInSource = dtsConceptQuery.findConceptByCode(childThesaurusConceptCode, namespaceId, thesaurusCasd);
					cDWithChildrenToReturn.addChild(getConceptAndDescendants(codeSystemOID, namespaceId, namespaceIsOntylog, childOfAssociationType, getCodeInSource(childThesaurusConceptWithCodeInSource), conn));
				}							
			}						
		}
		return cDWithChildrenToReturn;
	}
	
	private String getCodeInSource(DTSConcept concept)
	{
		String code = null;
		
		if (concept != null)
		{
			DTSProperty props[] = concept.getFetchedProperties();
			
			for (DTSProperty property : props){
				if (property.getName().equals("Code in Source"))
				{
					code = property.getValue();
				}
			}			
		}
		return code;
	}
	
	public static void main(String args[]) throws DSSRuntimeExceptionFault
    {
//		ApelonDtsUtility utility = ApelonDtsUtility.getInstance();
//		try {
//			String pw = "REPLACE_ME";
//			
//			ServerConnectionJDBC conn = utility.getSecureSocketConnection("dev-db.further.utah.edu", "1521", "further", "dts", pw);
//			
//			// get subset contents
//			/*
//			HashSet<Subset> icd9CMsubsets = utility.getSubsetsForCodeSystem(CodeSystems.CODE_SYSTEM_OID_ICD9CM_DIAGNOSES, conn);
//			for (Subset icd9CMsubset : icd9CMsubsets)
//			{
//				System.out.println("Contents of ICD9CM subset " + icd9CMsubset.getName() + ":");
//				HashSet<CS> subsetCSs = utility.getCSsForSubset(icd9CMsubset, conn);
//				for (CS subsetCS : subsetCSs)
//				{
//					System.out.println("    " + subsetCS.getCode() + " (" + subsetCS.getDisplayName() + ")");
//				}				
//			}
//			System.out.println();
//			*/
//			
//			// get Ontylog hierarchy for ICD9CM 250
//			/*
//			CSWithChildren icd9dmAndChildren = utility.getConceptAndDescendants(CodeSystems.CODE_SYSTEM_OID_ICD9CM_DIAGNOSES, "250", conn);
//			System.out.println(icd9dmAndChildren.getNestedStringRepresentation("\t", true));
//			*/
//
//			// get Thesaurus hierarchy for OpenCDS C3
//			
//			/*
//			CSWithChildren openCdsConceptRootAndChildren = utility.getConceptAndDescendants(CodeSystems.CODE_SYSTEM_OID_OPEN_CDS, "C3", conn);
//			System.out.println(openCdsConceptRootAndChildren.getNestedStringRepresentation("\t", true));
//			*/
//			
//			// get OpenCDS concept types
//			/*
//			HashSet<DTSConcept> matchingConcepts = utility.getConceptsWithProperty(CodeSystems.CODE_SYSTEM_OID_OPEN_CDS, "OpenCDS Concept Type", "problem", conn);
//			for (DTSConcept concept : matchingConcepts)
//			{
//				System.out.println(concept.getCode() + " (" + concept.getName() + ")");
//			}
//			*/
//			
//			// get OpenCDS concept by name
//			HashSet<DTSConcept> matchingConcepts = utility.getConceptsWithName(CodeSystems.CODE_SYSTEM_OID_OPEN_CDS_CONCEPTS, "Best available concept determination method", conn);
//			for (DTSConcept concept : matchingConcepts)
//			{
//				System.out.println(concept.getCode() + " (" + concept.getName() + ")");
//			}
//			
//			utility.closeConnection(conn);
//			System.out.println("finished");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }
}