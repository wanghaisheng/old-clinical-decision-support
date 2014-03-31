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

package org.opencds.knowledgeRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.common.EntityIdentifier;
import org.opencds.common.structures.KnowledgeBaseObject;
import org.opencds.common.terminology.OpenCDSConceptTypes;
import org.opencds.common.utilities.DSSUtility;
import org.opencds.common.utilities.DateUtility;
import org.opencds.common.utilities.FileUtility;
import org.opencds.common.utilities.WriteUtility;
import org.opencds.common.xml.XmlConverter;
import org.opencds.common.xml.XmlEntity;
import org.xml.sax.SAXParseException;

public class SimpleKnowledgeRepository {
	
	private static 		Log 						log 		= LogFactory.getLog(SimpleKnowledgeRepository.class);    
	private static 		FileUtility					myFu		= FileUtility.getInstance();
	private static  	boolean 					isSimpleKrInitialized = false;	
	
	private	static		ConcurrentMap<String, Integer>	myCacheCountsMap = new ConcurrentHashMap<String, Integer>();
    
	private static		ConcurrentMap<String, EntityIdentifier>	myKMIdToSSIdMap = new ConcurrentHashMap<String, EntityIdentifier>();
	// key = String containing requested knowledge module id, as [scopingEntityId]^[businessId]^[version], 
	// target = SSId required
	
	private static		ConcurrentMap<String, String>	mySSIdToUnmarshallerClassNameMap = new ConcurrentHashMap<String, String>();
	// key = String containing requested semantic signifier id, as [scopingEntityId]^[businessId]^[version], 
	// target = className to process unmarshalling of input
	
	private static		ConcurrentMap<String, JAXBContext>	myUnmarshallerToJaxBContextCache = new ConcurrentHashMap<String, JAXBContext>();
	// key = String containing required unmarshaller fully qualified className as String for required semantic signifier, 
	// target = JaxBContext for that unmarshaller
	
	private static		ConcurrentMap<String, JAXBContext>	myPayloadCreatorNameToJaxBContextCache = new ConcurrentHashMap<String, JAXBContext>();
	// key = String containing OpenCDS class name to process building output payload and marshalling the output,
	// target = JaxBContext for that payloadCreator
	
	private static		ConcurrentMap<String, String>	mySSIdToPayloadCreatorMap = new ConcurrentHashMap<String, String>();
	// key = String containing requested semantic signifier id, as [scopingEntityId]^[businessId]^[version], 
	// target = OpenCDS class name to process marshalling of output
	
	private static		ConcurrentMap<String, String>	myKMIdToInferenceEngineAdapterMap = new ConcurrentHashMap<String, String>();
	// key = String containing requested knowledge module id, as [scopingEntityId]^[businessId]^[version], 
	// target = className to process request
	
	private static		ConcurrentMap<String, String>	myKMIdToPrimaryProcessNameMap = new ConcurrentHashMap<String, String>();
	// key = String containing requested knowledge module id, as [scopingEntityId]^[businessId]^[version], 
	// target = primary process name if the KM uses BPMN.  If empty and Drools uses BPMN, then will default to "PrimaryProcess"
	
	private static		ConcurrentMap<String, List<String>>	myKMIdToSupportedOperationsMap = new ConcurrentHashMap<String, List<String>>();
	// key = String containing requested knowledge module id, as [scopingEntityId]^[businessId]^[version], 
	// target = String[] of supported operations
	
	private static		ConcurrentMap<String, List<String>>	myExecutionEngineToSupportedOperationsMap = new ConcurrentHashMap<String, List<String>>();
	// key = String containing fully qualified java package name of executionEngine, 
	// target = String[] of supported operations
	
/**
 *  following maps are part of SimpleTerminologyManager, since they are part of terminology services
 */
	private	static		ConcurrentMap<String, String> myCodeSystemOIDtoCodeSystemDisplayNameMap = new ConcurrentHashMap<String, String>();
	// key = String containing OID, target = displayName for OID
	
	private	static		ArrayList<String> myOpenCdsCodeSystemOIDs = new ArrayList<String>();
	private	static		ArrayList<String> myOpenCdsConceptTypes = new ArrayList<String>();

	private static ConcurrentMap<String, String> myCodeSystemOIDtoApelonNamespaceName = new ConcurrentHashMap<String, String>();
	
	private static ConcurrentMap<String, String> myApelonNamespaceNameToCodeSystemOID = new ConcurrentHashMap<String, String>();
	
	private static ConcurrentMap<String, Boolean> myCodeSystemOIDtoIsOntylog = new ConcurrentHashMap<String, Boolean>(); 
	// true if Ontylog, false otherwise

/**
 * 	Following maps are used in caching objects that are expensive to recreate	
 */
	private	static		ConcurrentMap<String, KnowledgeBaseObject> myKnowledgeBaseCache = new ConcurrentHashMap<String, KnowledgeBaseObject>();
	// key = String containing KMID, // target = KnowledgeBaseObject containing: <KnowledgeBase>Object, useCount, timeStamp
	// constructor receives only KnowledgeBase, and sets useCount to 0, and timeStamp to now.
	// setUseCount method on KBO takes no parameters, increments useCount, and sets timeStamp to now.

	private static		ConcurrentMap<String, Object>	myInboundPayloadProcessorClassNameToInstanceCache = new ConcurrentHashMap<String, Object>();
	// key = String containing fully qualified className of required inbound payload processing class instance, 
	// target = Instance of class to process inbound payload of type IPayloadInbound2Internal
	
	private static		ConcurrentMap<String, Unmarshaller>	myUnmarshallerClassNameToUnmarshallerInstanceCache = new ConcurrentHashMap<String, Unmarshaller>();
	// key = String containing required XML unmarshaller fully qualified className as String, 
	// target = Instance of class to do XML unmarshalling of input of type Unmarshaller
	
	private static		ConcurrentMap<String, Object>	myPayloadCreatorNameToInstanceCache = new ConcurrentHashMap<String, Object>();
	// key = String containing OpenCDS class name to process building output payload and marshalling the output, 
	// target = OpenCDS object to process marshalling of output
	
	private static		ConcurrentMap<String, Marshaller>	myPayloadCreatorClassNameToMarshallerInstanceCache = new ConcurrentHashMap<String, Marshaller>();
	// key = String containing OpenCDS class name to process building output payload and marshalling the output,
	// target = Instance of class to process marshalling of output
	
	public static synchronized boolean isKnowledgeRepositoryInitialized() {
		return SimpleKnowledgeRepository.isSimpleKrInitialized;
	}
	
	protected static synchronized void setKnowledgeRepositoryInitialized(boolean value) {
		SimpleKnowledgeRepository.isSimpleKrInitialized = value;
	}
	
	protected SimpleKnowledgeRepository(String fullPathToKRData) throws DSSRuntimeExceptionFault
    {
        initialize(fullPathToKRData);
    }
	
	public static void setFullPathToKRData (String path) throws DSSRuntimeExceptionFault {
		initialize(path);
	}
	
	protected static String fullPathToKRData = "notYetInitialized";

	protected static void initialize(String submittedFullPathToKRData) throws DSSRuntimeExceptionFault 
    {
		String startTime = DateUtility.getInstance().getDateAsString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println();
        System.out.println(startTime + " <<< Initializing OpenCDS SimpleKnowledgeRepository at " + fullPathToKRData + " >>>");
        
		// save the fullPathToKRData where we can use it.
		fullPathToKRData = submittedFullPathToKRData.trim();
		// add a trailing / if it is missing...
		if ( !"/".equals(fullPathToKRData.substring(fullPathToKRData.length() ) ) ) {
			fullPathToKRData = fullPathToKRData + "/";
		}
		log.info("Path to SimpleKnowledgeRepository data: " + fullPathToKRData);
		
		// read executionEngines resource
        String executionEngines = "";
		try {
			executionEngines = getResourceAsString("resourceAttributes","openCdsExecutionEngines.xml");
		} catch (DSSRuntimeExceptionFault e1) {
			e1.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to getResourceAsString('resourceAttributes','openCdsExecutionEngines.xml': " + e1.getCause());
		}
		XmlEntity executionEnginesRootEntity = null;
		try 
		{
			executionEnginesRootEntity = XmlConverter.getInstance().unmarshalXml(executionEngines, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to unmarshall openCdsExecutionEngines.xml " + executionEngines + " " + e.getCause());
		}
		
		// load executionEngine array
		List<XmlEntity>  executionEngineList = executionEnginesRootEntity.getChildrenWithLabel("executionEngine");
		for (XmlEntity executionEngine : executionEngineList) {
			// load kmId supported operations 
			String engineName = executionEngine.getAttributeValue("name");
			String logEngine = engineName;
			List<String> supportedDSSOperationStringList = new ArrayList<String>(); 
			Set<String> supportedOps = executionEngine.getAttributeLabels();
			Iterator<String> it = supportedOps.iterator();
			while ( it.hasNext()) {
				String thisSupportedOperationName = it.next();
				String thisSupportedOperationValue = executionEngine.getAttributeValue(thisSupportedOperationName);
				if ("true".equals(thisSupportedOperationValue)) {
					supportedDSSOperationStringList.add( thisSupportedOperationName );
					logEngine = logEngine + ", " + thisSupportedOperationName;
				}
			}
			log.debug("Execution Engine: " + logEngine + ", name: " + engineName );
			myExecutionEngineToSupportedOperationsMap.put( engineName, supportedDSSOperationStringList );
			
		}

 		// read semanticSignifiers resource
        String semanticSignifiers = "";
		try {
			semanticSignifiers = getResourceAsString("resourceAttributes","semanticSignifiers.xml");
		} catch (DSSRuntimeExceptionFault e1) {
			e1.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to getResourceAsString('resourceAttributes','semanticSignifiers.xml': " + e1.getCause());
		}
		XmlEntity semanticSignifiersRootEntity = null;
		try 
		{
			semanticSignifiersRootEntity = XmlConverter.getInstance().unmarshalXml(semanticSignifiers, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to unmarshall semanticSignifiers.xml " + executionEngines + " " + e.getCause());
		}
		
		// load SSID arrays
		List<XmlEntity>  semanticSignifierList = semanticSignifiersRootEntity.getChildrenWithLabel("semanticSignifier");
		for (XmlEntity semanticSignifier : semanticSignifierList) {
			// load ssid supported operations 
			XmlEntity dataModelElement = semanticSignifier.getFirstChildWithLabel("dataModel");
			String dataModel = dataModelElement.getValue();
			XmlEntity  unmarshalClassName = semanticSignifier.getFirstChildWithLabel("unmarshalClass");
			
			mySSIdToUnmarshallerClassNameMap.put( dataModel, unmarshalClassName.getValue() );
			XmlEntity  marshalClassName = semanticSignifier.getFirstChildWithLabel("marshalClass");
			mySSIdToPayloadCreatorMap.put( dataModel, marshalClassName.getValue() );
			log.debug("SSID: " + dataModel + ", marshalClassName: " + marshalClassName);
		}

		// read knowledgeModules
 		String knowledgeModules = "";
		try {
			knowledgeModules = getResourceAsString("resourceAttributes","knowledgeModules.xml");
		} catch (DSSRuntimeExceptionFault e1) {
			e1.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to getResourceAsString('resourceAttributes','knowledgeModules.xml': " + e1.getCause());
		}
		XmlEntity kmMetadataRootEntity = null;
		try 
		{
			kmMetadataRootEntity = XmlConverter.getInstance().unmarshalXml(knowledgeModules, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to unmarshall knowledgeModules.xml " + knowledgeModules + " " + e.getCause());
		}
		
		// load km arrays
		List<XmlEntity>  kmMetadataList = kmMetadataRootEntity.getChildrenWithLabel("kmMetadata");
		for (XmlEntity kmMetadata : kmMetadataList) {
			// load kmId to dataModel map
			XmlEntity identifier = kmMetadata.getFirstChildWithLabel("identifier"); 
			String kmId = identifier.getAttributeValue("scopingEntityId") + "^" 
				+ identifier.getAttributeValue("businessId") + "^" + identifier.getAttributeValue("version");
			XmlEntity dataModel = kmMetadata.getFirstChildWithLabel("dataModel");
			EntityIdentifier dataModelEI = DSSUtility.makeEI(dataModel.getValue());
			myKMIdToSSIdMap.put( kmId, dataModelEI );
			
			// load kmId to inference engine adapter map
			XmlEntity executionEngine = kmMetadata.getFirstChildWithLabel("executionEngine");
			myKMIdToInferenceEngineAdapterMap.put( kmId, executionEngine.getValue() );
			
			// load kmId to primary process name map
			XmlEntity knowledgeModulePrimaryProcessName = kmMetadata.getFirstChildWithLabel("knowledgeModulePrimaryProcessName");
			if ((knowledgeModulePrimaryProcessName != null) && (knowledgeModulePrimaryProcessName.getValue() != null) 
					&& !("".equals(knowledgeModulePrimaryProcessName.getValue()) )) {
				myKMIdToPrimaryProcessNameMap.put( kmId, knowledgeModulePrimaryProcessName.getValue() );
			} else {
				myKMIdToPrimaryProcessNameMap.put( kmId, "" );
			}
			
			// load kmId to supported operations map
			if (myExecutionEngineToSupportedOperationsMap.get(executionEngine) != null) {
				myKMIdToSupportedOperationsMap.put( kmId, myExecutionEngineToSupportedOperationsMap.get(executionEngine) );
				log.debug("KMiD: " + kmId);
			}
			
		}

//		private				HashMap<String, String> myCodeSystemOIDtoCodeSystemDisplayNameMap = new HashMap<String, String>();
//		// key = String containing OID, target = displayName for OID
    
		// read codeSystem
 		String codeSystems = "";
		try {
			codeSystems = getResourceAsString("resourceAttributes","openCDSCodeSystems.xml");
		} catch (DSSRuntimeExceptionFault e1) {
			e1.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to getResourceAsString('resourceAttributes','openCDSCodeSystems.xml': " + e1.getCause());
		}
		XmlEntity codeSystemsRootEntity = null;
		try 
		{
			codeSystemsRootEntity = XmlConverter.getInstance().unmarshalXml(codeSystems, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository failed to unmarshall openCDSCodeSystems.xml " + codeSystems + " " + e.getCause());
		}
		
		// load codeSystem arrays
		List<XmlEntity>  codeSystemsList = codeSystemsRootEntity.getChildrenWithLabel("codeSystem");
		for (XmlEntity codeSystem : codeSystemsList) {
			// load oid to codeSystemName map
			String oid = codeSystem.getAttributeValue("codeSystemOID");
			String name = codeSystem.getAttributeValue("codeSystemDisplayName");
			String apelonNamespace = codeSystem.getAttributeValue("apelonNamespaceName");
			boolean isOntylog = "true".equals(codeSystem.getAttributeValue("isApelonOntylog"));
			myCodeSystemOIDtoCodeSystemDisplayNameMap.put( oid, name );
			if (apelonNamespace != null) {
				myCodeSystemOIDtoApelonNamespaceName.put( oid, apelonNamespace );
				myApelonNamespaceNameToCodeSystemOID.put( apelonNamespace, oid );
			}
			myCodeSystemOIDtoIsOntylog.put( oid, isOntylog );
			
			//load OIDs
			myOpenCdsCodeSystemOIDs.add(oid);
			log.debug("OpenCDSCodeSystems: " + oid + ", name: " + name);
		}
			
		// load codeSystem arrays
		List<String>  conceptTypesList = OpenCDSConceptTypes.getOpenCdsConceptTypes();//conceptTypesRootEntity.getChildrenWithLabel("ConceptType");
		for (String conceptType : conceptTypesList) {
			// load concepts
			myOpenCdsConceptTypes.add(conceptType);
			log.trace("OpenCDSConceptTypes: " + conceptType);
		}
		
		String initTime = DateUtility.getInstance().getDateAsString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(initTime + " <<< OpenCDS SimpleKnowledgeRepository Initialized >>>");
        System.out.println();
        setKnowledgeRepositoryInitialized(true);
    }
	
	
    public static ConcurrentMap<String, Integer> getCacheCounts() throws DSSRuntimeExceptionFault 
    {
    	//HashMap<String, Integer> cacheCounts = new 
    	int cnt;
    	cnt = myInboundPayloadProcessorClassNameToInstanceCache.size();
    	myCacheCountsMap.put("myInboundPayloadProcessorClassNameToInstanceCache", new Integer(cnt));
    	cnt = myUnmarshallerClassNameToUnmarshallerInstanceCache.size();
    	myCacheCountsMap.put("myUnmarshallerClassNameToUnmarshallerInstanceCache", new Integer(cnt));
    	cnt = myPayloadCreatorNameToInstanceCache.size();
    	myCacheCountsMap.put("myPayloadCreatorNameToInstanceCache", new Integer(cnt));
    	cnt = myPayloadCreatorClassNameToMarshallerInstanceCache.size();
    	myCacheCountsMap.put("myPayloadCreatorClassNameToMarshallerInstanceCache", new Integer(cnt));
    	cnt = myKnowledgeBaseCache.size();
    	myCacheCountsMap.put("myKnowledgeBaseCache", new Integer(cnt));
//    	cnt = myKMIdToSSIdMap.size();
//    	myCacheCountsMap.put("myKMIdToSSIdMap", new Integer(cnt));
//    	cnt = mySSIdToUnmarshallerClassNameMap.size();
//    	myCacheCountsMap.put("mySSIdToUnmarshallerClassNameMap", new Integer(cnt));
//    	cnt = myUnmarshallerToJaxBContextCache.size();
//    	myCacheCountsMap.put("myUnmarshallerToJaxBContextCache", new Integer(cnt));
//    	cnt = mySSIdToPayloadCreatorMap.size();
//    	myCacheCountsMap.put("mySSIdToPayloadCreatorMap", new Integer(cnt));
//    	cnt = myPayloadCreatorNameToJaxBContextCache.size();
//    	myCacheCountsMap.put("myPayloadCreatorNameToJaxBContextCache", new Integer(cnt));
//    	cnt = myKMIdToInferenceEngineAdapterMap.size();
//    	myCacheCountsMap.put("myKMIdToInferenceEngineAdapterMap", new Integer(cnt));
//    	cnt = myKMIdToPrimaryProcessNameMap.size();
//    	myCacheCountsMap.put("myKMIdToPrimaryProcessNameMap", new Integer(cnt));
//    	cnt = myKMIdToSupportedOperationsMap.size();
//    	myCacheCountsMap.put("myKMIdToSupportedOperationsMap", new Integer(cnt));
//    	cnt = myExecutionEngineToSupportedOperationsMap.size();
//    	myCacheCountsMap.put("myExecutionEngineToSupportedOperationsMap", new Integer(cnt));
//    	cnt = myCodeSystemOIDtoCodeSystemDisplayNameMap.size();
//    	myCacheCountsMap.put("myCodeSystemOIDtoCodeSystemDisplayNameMap", new Integer(cnt));
//    	cnt = myOpenCdsCodeSystemOIDs.size();
//    	myCacheCountsMap.put("myOpenCdsCodeSystemOIDs", new Integer(cnt));
//    	cnt = myOpenCdsConceptTypes.size();
//    	myCacheCountsMap.put("myOpenCdsConceptTypes", new Integer(cnt));
//    	cnt = myCodeSystemOIDtoApelonNamespaceName.size();
//    	myCacheCountsMap.put("myCodeSystemOIDtoApelonNamespaceName", new Integer(cnt));
//    	cnt = myApelonNamespaceNameToCodeSystemOID.size();
//    	myCacheCountsMap.put("myApelonNamespaceNameToCodeSystemOID", new Integer(cnt));
//    	cnt = myCodeSystemOIDtoIsOntylog.size();
//    	myCacheCountsMap.put("myCodeSystemOIDtoIsOntylog", new Integer(cnt));
		return SimpleKnowledgeRepository.myCacheCountsMap;
    }
	
    
	public static void putKnowledgeBaseCache( String kmid, Object knowledgeBaseObject ){
		KnowledgeBaseObject kbo = new KnowledgeBaseObject(knowledgeBaseObject);
		myKnowledgeBaseCache.putIfAbsent(kmid, kbo);
	}

	
	public static Object getKnowledgeBaseCache( String kmid){
		if (myKnowledgeBaseCache.get( kmid ) == null) {
			return null;
		} else {
			return myKnowledgeBaseCache.get( kmid ).getKnowledgeBaseObject();
		}
	} 
	
	
	public static synchronized Map<String, Long> getKnowledgeBaseCacheUseCounts(){
		HashMap<String, Long> useCountMap = new HashMap<String, Long>();
		for (String oneKmid : myKnowledgeBaseCache.keySet()) {
			useCountMap.put(oneKmid, Long.valueOf(myKnowledgeBaseCache.get(oneKmid).getUseCount()));
		}
		
		return useCountMap;
	}

	
	public static synchronized Map<String, java.util.Date> getKnowledgeBaseCacheTimeStamps(){
		HashMap<String, java.util.Date> timeStamps = new HashMap<String, java.util.Date>();
		for (String oneKmid : myKnowledgeBaseCache.keySet()) {
			timeStamps.put(oneKmid, myKnowledgeBaseCache.get(oneKmid).getTimeStamp());
		}
		return timeStamps;
	}
	
	
	public static synchronized void removeOldKnowledgeBaseCaches(java.util.Date destroyDate) {
		for (String oneKmid : myKnowledgeBaseCache.keySet()) {
			if (destroyDate.after(myKnowledgeBaseCache.get(oneKmid).getTimeStamp()) ) {
				myKnowledgeBaseCache.remove(oneKmid);
			}
		}		
	}

	
	public static synchronized void keepBusiestKnowledgeBaseCachesAndDestroyRemainder(int topNumberToKeep) {
		int cacheSize = myKnowledgeBaseCache.keySet().size();
		if (cacheSize <= topNumberToKeep) {
			return;
		}
		long[] 	useCounts 	= new long[cacheSize];
		int		i			= 0;
		for (String oneKmid : myKnowledgeBaseCache.keySet()) {
			long oneUseCount = myKnowledgeBaseCache.get(oneKmid).getUseCount();
			i++;
			useCounts[i] = oneUseCount;
		}
		
		Arrays.sort(useCounts, 0, useCounts.length -1);
		long topNumberCount = useCounts[useCounts.length - topNumberToKeep -1];
		
		//following code may leave more than the topNumberToKeep if there are 
		//	multiple KBs in the cache with the same number of hits...
		for (String oneKmid : myKnowledgeBaseCache.keySet()) {
			long oneUseCount = myKnowledgeBaseCache.get(oneKmid).getUseCount();
			if (oneUseCount < topNumberCount) {
				myKnowledgeBaseCache.remove(oneKmid);
			}
		}
	}

	
	public static synchronized void flushAllKnowledgeBaseCaches() {
		myKnowledgeBaseCache.clear();
	}

	
	public static synchronized void flushThisKnowledgeBaseCache(String kmid) {
		myKnowledgeBaseCache.remove(kmid);
	}
	
	
	public static synchronized Object getRequiredInboundPayloadProcessorInstanceCache( String ssid ) 
		throws DSSRuntimeExceptionFault, InvalidDriDataFormatExceptionFault
	//Object for unmarshallerInstance should be cast when it is used to IPayloadUnmarshaller
	{
		Object inboundPayloadProcessorInstance = myInboundPayloadProcessorClassNameToInstanceCache.get( ssid );
		if (inboundPayloadProcessorInstance == null) {
			log.debug("SSID: " + ssid + " inboundPayloadProcessor creation");
			String requiredPayloadInboundProcessorName	= getRequiredOpenCDSUnmarshallerClassNameForSSID(ssid);
			if (( requiredPayloadInboundProcessorName == null ) || ( "".equals(requiredPayloadInboundProcessorName) )) {
				throw new InvalidDriDataFormatExceptionFault( "OpenCDS does not recognize unmarshaller class '" 
						+ requiredPayloadInboundProcessorName + "' for SSId='" 
						+ ssid + "', and cannot continue" );
			}
			
			Class<?> 		c						= null;
			try {
				c = Class.forName(requiredPayloadInboundProcessorName);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				throw new InvalidDriDataFormatExceptionFault( "OpenCDS unable to find payload processor class '" 
						+ requiredPayloadInboundProcessorName + "' for SSId='" 
						+ ssid + "', and cannot continue." );
			}	
			
			try {
				inboundPayloadProcessorInstance = c.newInstance();
				//putRequiredInboundPayloadProcessorInstanceCache(ssid, payloadInboundProcessor);
				myInboundPayloadProcessorClassNameToInstanceCache.putIfAbsent(ssid, inboundPayloadProcessorInstance);
			} catch (InstantiationException e1) {
				e1.printStackTrace();
				throw new InvalidDriDataFormatExceptionFault( "OpenCDS unable to instantiate payload processor '" 
						+ requiredPayloadInboundProcessorName + "' for SSId='" 
						+ ssid + "', and cannot continue." );
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
				throw new InvalidDriDataFormatExceptionFault( "OpenCDS received IllegalAccessException for payload processor '" 
						+ requiredPayloadInboundProcessorName + "' for SSId='" 
						+ ssid + "', and cannot continue." );
			}
			log.debug("SSID" + ssid + " inboundPayloadProcessor created and returned");
			return inboundPayloadProcessorInstance;			
		} else {		
			log.debug("SSID" + ssid + " inboundPayloadProcessor returned from cache");
			return inboundPayloadProcessorInstance;
		}
	}
	
//deprecated	
//	public void putRequiredInboundPayloadProcessorInstanceCache( String ssid, Object inboundPayloadProcessorInstance ) throws DSSRuntimeExceptionFault
//	//Object for unmarshallerInstance should be cast when it is used to IPayloadUnmarshaller
//	{
//		myInboundPayloadProcessorClassNameToInstanceCache.putIfAbsent(ssid, inboundPayloadProcessorInstance);
//	}
	
	
	public static synchronized Unmarshaller getRequiredUnmarshallerInstanceForUnmarshallerClassCache( String ssid ) throws DSSRuntimeExceptionFault
	//Object for unmarshallerInstance should be cast when it is used to IPayloadUnmarshaller
	{
		Unmarshaller unmarshallerInstance = myUnmarshallerClassNameToUnmarshallerInstanceCache.get( ssid );
		if (unmarshallerInstance == null) {
			
			log.debug(ssid + ": creating unmarshaller instance in cache");
			try {
				
				unmarshallerInstance 	= getRequiredJAXBContextForUnmarshallerClassCache( ssid ).createUnmarshaller();
				
			} catch (JAXBException e) {
				throw new DSSRuntimeExceptionFault("requested Unmarshaller for SSID: " + ssid 
						+ " created JAXBException: " + e.getMessage());
			}
			myUnmarshallerClassNameToUnmarshallerInstanceCache.putIfAbsent( ssid, unmarshallerInstance);
			
			return unmarshallerInstance;	
			
		} else {
			log.debug(ssid + ": using unmarshaller instance from cache");
			
			return unmarshallerInstance;
		}
	}
	
//deprecated	
//	public static void putRequiredUnmarshallerInstanceForUnmarshallerClassCache( String ssid, Unmarshaller unmarshallerInstance ) throws DSSRuntimeExceptionFault
//	//Object for unmarshallerInstance should be cast when it is used to IPayloadUnmarshaller
//	{
//		log.debug(ssid + ": storing unmarshaller instance in cache");
//		myUnmarshallerClassNameToUnmarshallerInstanceCache.putIfAbsent(ssid, unmarshallerInstance);
//	}
	
	
	public static synchronized Object getPayloadCreatorInstanceForClassNameCache( String className ) throws DSSRuntimeExceptionFault
	{
		Object payloadCreator = myPayloadCreatorNameToInstanceCache.get( className );
		if (payloadCreator == null) {
			log.debug(className + ": creating payloadCreator instance");
    		@SuppressWarnings("rawtypes")
			Class c;
			try {
				
				c = Class.forName(className);
				
			} catch (ClassNotFoundException e1) {
				throw new DSSRuntimeExceptionFault("requested Payload Creator name does not exist: " + className);
			}
			
    		try {
    			
				payloadCreator = c.newInstance();
				
			} catch (InstantiationException e) {
				throw new DSSRuntimeExceptionFault("requested Payload Creator InstantiationException: " + className + ", " + e.getMessage());
			} catch (IllegalAccessException e) {
				throw new DSSRuntimeExceptionFault("requested Payload Creator IllegalAccessException: " + className + ", " + e.getMessage());
			}
    		myPayloadCreatorNameToInstanceCache.putIfAbsent( className, payloadCreator);
    		return payloadCreator;		
		} else {
			log.debug(className + ": using payloadCreator instance from cache");
			return payloadCreator;					
		}
	}
	
//deprecated	
//	public void putPayloadCreatorInstanceForClassNameCache( String className, Object payloadCreator ) throws DSSRuntimeExceptionFault
//	{
//		myPayloadCreatorNameToInstanceCache.putIfAbsent( className, payloadCreator);
//	}
	
	
	public static synchronized Marshaller getRequiredMarshallerInstanceForMarshallerClassCache( String className, JAXBContext jaxbContext ) throws DSSRuntimeExceptionFault
	{
		Marshaller marshallerInstance = myPayloadCreatorClassNameToMarshallerInstanceCache.get( className );
		if (marshallerInstance == null) {
			log.debug(className + ": creating marshaller instance");
			try {
				
				marshallerInstance 	= jaxbContext.createMarshaller();
				marshallerInstance.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
				marshallerInstance.setProperty( Marshaller.JAXB_ENCODING, "UTF-8" );
				
			} catch (JAXBException e) {
				throw new DSSRuntimeExceptionFault("requested Marshaller for className: " + className 
						+ " created JAXBException: " + e.getMessage());
			}
			myPayloadCreatorClassNameToMarshallerInstanceCache.putIfAbsent( className, marshallerInstance);
			return marshallerInstance;
		} else {					
			log.debug(className + ": using marshaller instance from cache");
			return marshallerInstance;
		}
	}
	
//deprecated	
//	public void putRequiredMarshallerInstanceForMarshallerClassCache( String className, Marshaller marshallerInstance ) throws DSSRuntimeExceptionFault
//	{
//		myPayloadCreatorClassNameToMarshallerInstanceCache.putIfAbsent( className, marshallerInstance);
//	}
	
	
	public static synchronized JAXBContext getRequiredJAXBContextForUnmarshallerClassCache( String ssid ) throws JAXBException
	{
		JAXBContext jaxbContextUnmarshallerInstance = myUnmarshallerToJaxBContextCache.get( ssid );
		if (jaxbContextUnmarshallerInstance == null) {
			log.debug(ssid + ": creating jaxbContextUnmarshallerInstance in cache");
			
			jaxbContextUnmarshallerInstance 	= JAXBContext.newInstance( "org.opencds.vmr.v1_0.schema" );
			myUnmarshallerToJaxBContextCache.putIfAbsent( ssid, jaxbContextUnmarshallerInstance );
			
			return jaxbContextUnmarshallerInstance;				
		} else {
			log.debug(ssid + ": using jaxbContextUnmarshallerInstance from cache");
			
			return jaxbContextUnmarshallerInstance;
		}
	}
	
//deprecated	
//	public static void putRequiredJAXBContextForUnmarshallerClassCache( String className, JAXBContext jaxbContext ) throws DSSRuntimeExceptionFault
//	{
//		log.debug(className + ": putting jaxbContextUnmarshallerInstance into cache");
//		myUnmarshallerToJaxBContextCache.putIfAbsent( className, jaxbContext );
//	}
	
	
	public static synchronized JAXBContext getRequiredJAXBContextForMarshallerClassCache( String className ) throws JAXBException
	{
//		if (myPayloadCreatorNameToJaxBContextCache.get( className ) == null) {
//			return null;
//		} else {		
//			JAXBContext jaxbInstance = myPayloadCreatorNameToJaxBContextCache.get( className );
//			return jaxbInstance;
//		}
		JAXBContext jaxbContextMarshallerInstance = myPayloadCreatorNameToJaxBContextCache.get( className );
		if (jaxbContextMarshallerInstance == null) {
			log.debug(className + ": creating jaxbContextMarshallerInstance in cache");
			
			jaxbContextMarshallerInstance 	= JAXBContext.newInstance( "org.opencds.vmr.v1_0.schema" );
			myPayloadCreatorNameToJaxBContextCache.putIfAbsent( className, jaxbContextMarshallerInstance );

			return jaxbContextMarshallerInstance;				
		} else {
			log.debug(className + ": using jaxbContextMarshallerInstance from cache");
			return jaxbContextMarshallerInstance;
		}
	}
	
//deprecated	
//	public static void putRequiredJAXBContextForMarshallerClassCache( String className, JAXBContext jaxbInstance ) throws DSSRuntimeExceptionFault
//	{
//		log.debug(className + ": putting jaxbContextMarshallerInstance into cache");
//		myPayloadCreatorNameToJaxBContextCache.putIfAbsent( className, jaxbInstance);
//	}
	
	
	public static synchronized String getRequiredPayloadCreatorForSSID( String ssid ) throws DSSRuntimeExceptionFault
	{
		if (mySSIdToPayloadCreatorMap.get( ssid ) == null) {
			throw new DSSRuntimeExceptionFault("requested SSID does not exist: " + ssid);
		}
		String className = mySSIdToPayloadCreatorMap.get( ssid );
		log.debug(ssid + ": using payload creator name instance from cache");
		return className;		
	}
	
	
	public static void putRequiredPayloadCreatorForSSID( String ssid, String className ) throws DSSRuntimeExceptionFault
	{
		log.debug(ssid + ": creating instance of payload creator name: " + className);
		mySSIdToPayloadCreatorMap.putIfAbsent( ssid, className);
	}

	
	/**
	 * @param ssid
	 * @return
	 * @throws DSSRuntimeExceptionFault 
	 */
	public static synchronized String getRequiredOpenCDSUnmarshallerClassNameForSSID( String ssid ) throws DSSRuntimeExceptionFault
	{
		if (mySSIdToUnmarshallerClassNameMap.get( ssid ) == null) {
			throw new DSSRuntimeExceptionFault("requested SSID does not exist: " + ssid);
		}
		String unmarshaller = mySSIdToUnmarshallerClassNameMap.get( ssid );
		return unmarshaller;		
	}
	
	
	/**
	 * code to lookup required SSId for KMId from hard-coded map in this module
	 * 		- is loaded by knowledge repository getResourceAttribute(KMId, "SSID")
	 * 
	 * @param kmid = name of the knowledge module requested in the DSS request
	 * @return String = name of the Semantic Signifier for the data structure required by the knowledge module
	 */
	public static final String getRequiredSSIdForKMId( String kmid )
	{
		EntityIdentifier ssid = myKMIdToSSIdMap.get(kmid);
		log.debug(kmid + ": getRequiredSSIdForKMId from cache");
		return DSSUtility.makeEIString(ssid);	
	}

	/**
	 * code to lookup required inference engine for KMId from hard-coded map in this module
	 * 		- is loaded by knowledge repository getResourceAttribute(KMId, "InferenceEngine") 
	 * 
	 * @param kmid = name of the knowledge module requested in the DSS request
	 * @return String = name of class which functions as an adapter to a specific inference engine which is required by the knowledge module
	 * @throws DSSRuntimeExceptionFault 
	 */
	public static final String getRequiredInferenceEngineAdapterClasspathNameForKMId( String kmid ) throws DSSRuntimeExceptionFault
	{
		if ((myKMIdToInferenceEngineAdapterMap == null) || (myKMIdToInferenceEngineAdapterMap.size() == 0)) {
			throw new DSSRuntimeExceptionFault("OpenCDS error - probably called 'getRequiredInferenceEngineAdapterForKMId(" 
					+ kmid 
					+ ")' before SimpleKnowledgeRepository was initialized with the fullPath to the run-time KRData.");
		}
		return myKMIdToInferenceEngineAdapterMap.get( kmid );		
	}

	/**
	 * code to lookup required inference engine for KMId from hard-coded map in this module
	 * 		- is loaded by knowledge repository getResourceAttribute(KMId, "InferenceEngine") 
	 * 
	 * @param kmid = name of the knowledge module requested in the DSS request
	 * @return String = name of class which functions as an adapter to a specific inference engine which is required by the knowledge module
	 * @throws DSSRuntimeExceptionFault 
	 */
	public static final String getRequiredKMPrimaryProcessNameForKMId( String kmid ) throws DSSRuntimeExceptionFault
	{
		if ((myKMIdToPrimaryProcessNameMap == null) || (myKMIdToPrimaryProcessNameMap.size() == 0)) {
//			throw new DSSRuntimeExceptionFault("OpenCDS error - called 'getRequiredInferenceEngineAdapterForKMId(" + kmid 
//					+ ")' before SimpleKnowledgeRepository was initialized with the fullPath to the run-time KRData.");
			return "";
		} else {
			return myKMIdToPrimaryProcessNameMap.get( kmid );
		}
	}

	/**
	 * Interim code to get knowledge module given the KMId from a file...
	 * 		- will be replaced by knowledge repository getKM(KMId)
	 * 
	 * @param kmid = name of the knowledge module requested in the DSS request 
	 * @return Object = knowledgeModule containing the actual executable rules which can be run on the inference engine
	 * @throws DSSRuntimeExceptionFault 
	 */
//	public static synchronized Object getRequiredKMByKMId( String kmid ) throws DSSRuntimeExceptionFault
//	{
//		throw new DSSRuntimeExceptionFault("Called 'getRequiredKMByKMId' with kmid='" + kmid 
//				+ "', and this routine is not yet implemented.");
//		//TODO complete this routine...
////		Object knowledgeModule = new Object();
////		return knowledgeModule;		
//	}


	/**
	 * @param kmid
	 * @return
	 * @throws DSSRuntimeExceptionFault
	 */
//	public static synchronized Object getSupportedOperationsByKMId( String kmid ) throws DSSRuntimeExceptionFault
//	{
//		throw new DSSRuntimeExceptionFault("Called 'getSupportedOperationsByKMId' with kmid='" + kmid 
//				+ "', and this routine is not yet implemented.");
//		//TODO complete this routine...
////		Object knowledgeModule = new Object();
////		return knowledgeModule;		
//	}


	/**
	 * @return
	 */
	public static final ArrayList<String> getOpenCdsConceptTypes()
	{
		return myOpenCdsConceptTypes;
	}

	
	/**
	 * @return
	 */
	public static final ArrayList<String> getOpenCdsCodeSystemOIDs()
	{
		return myOpenCdsCodeSystemOIDs;
	}

	
	/**
	 * @param oid
	 * @return
	 */
	public static final String getCodeSystemName( String oid ) 
	{
		return myCodeSystemOIDtoCodeSystemDisplayNameMap.get(oid);
	}

	
	/**
	 * @param oid
	 * @return
	 */
	public static final String getApelonNamespaceName( String oid ) 
	{
		return myCodeSystemOIDtoApelonNamespaceName.get(oid);
	}

	
	/**
	 * @param name
	 * @return
	 */
	public static final String getOIDFromApelonNamespaceName( String name ) 
	{
		return myApelonNamespaceNameToCodeSystemOID.get(name);
	}

	
	/**
	 * @param oid
	 * @return
	 */
	public static final Boolean getIsApelonOntylog( String oid ) 
	{
		return myCodeSystemOIDtoIsOntylog.get(oid);
	}

	
	/**
	 * Given the resourceType and the resourceId, returns the File matching that resourceId.  Returns null if not found.
	 * 
	 * @param resourceType
	 * @param resourceId
	 * @return resourceXML
	 * @throws DSSRuntimeExceptionFault
	 */
	public static synchronized String getResourceAsXML(String resourceType, String resourceId) throws DSSRuntimeExceptionFault 
	//		the interim notion is that each type of resource will be in a separate directory, whose directoryName == the resourceType name
	//		the interim notion will be replaced by the actual knowledgeRepository once it is completed
	{
		File 				file 		= myFu.getFile(fullPathToKRData + resourceType + "/" + resourceId);
		String 				xML			= myFu.getFileContentsAsString( file );
		return 				xML;
	} 
	
	
	public static synchronized String getResourceAsString(String resourceType, String resourceId) throws DSSRuntimeExceptionFault 
	//		the interim notion is that each type of resource will be in a separate directory, whose directoryName == the resourceType name
	//		the interim notion will be replaced by the actual knowledgeRepository once it is completed
	{
		File 				file 		= myFu.getFile(fullPathToKRData + resourceType + "/" + resourceId);
		String 				s 			= myFu.getFileContentsAsString( file );
		return 				s;
	} 
	
	
	/**
	 * @param resourceType
	 * @param resourceId
	 * @return
	 * @throws DSSRuntimeExceptionFault
	 */
	public static synchronized InputStream getResourceAsStream(String resourceType, String resourceId) throws DSSRuntimeExceptionFault 
	//		the interim notion is that each type of resource will be in a separate directory, whose directoryName == the resourceType name
	//		the interim notion will be replaced by the actual knowledgeRepository once it is completed
	{
		File 				file 		= myFu.getFile(fullPathToKRData + resourceType + "/" + resourceId);
		String 				s 			= myFu.getFileContentsAsString( file );
		InputStream 		inputStream = myFu.getInputStreamFromString( s );
		return 				inputStream;
	} 
	
	
	/**
	 * @param resourceType
	 * @param resourceId
	 * @return
	 * @throws DSSRuntimeExceptionFault
	 */
	public static synchronized File getResourceAsFile(String resourceType, String resourceId) throws DSSRuntimeExceptionFault 
	//		the interim notion is that each type of resource will be in a separate directory, whose directoryName == the resourceType name
	//		the interim notion will be replaced by the actual knowledgeRepository once it is completed
	{
		File 				file 		= myFu.getFile(fullPathToKRData + resourceType + "/" + resourceId);
		return 				file;
	} 
	
	
	/**
	 * @param resourceType
	 * @param resourceId
	 * @return
	 * @throws DSSRuntimeExceptionFault
	 */
	public static synchronized File getResourceAsFileWithoutException(String resourceType, String resourceId)  
	//		the interim notion is that each type of resource will be in a separate directory, whose directoryName == the resourceType name
	//		the interim notion will be replaced by the actual knowledgeRepository once it is completed
	{
		File 				file 		= myFu.getFileWithoutException(fullPathToKRData + resourceType + "/" + resourceId);
		return 				file;
	} 
	
	
	/**
	 * Get a list of all the resource ID values available for a given resource type.  
	 * 		Returns null if the resourceType is not recognized.
	 * 		Returns an empty list if no resources found for a valid type.  
	 * 
	 * @param resourceType
	 * @return resourceList
	 * @throws DSSRuntimeExceptionFault
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static synchronized List<File> listResourcesByType(String resourceType) throws DSSRuntimeExceptionFault
	{
		List<File> 	resourceList 		= new ArrayList<File>();
		String[] 	resourceNameList 	= getKRResourceTypeListing(resourceType);
		for (String resourceName : resourceNameList) {
			String 	filePath 			= resourceType + "/" + resourceName;
			resourceList.add(new File(filePath));
		}
		return resourceList;
	} 
	
	
	/**
	 * Get a list of all the resource ID values available for a given resource type.  
	 * 		Returns null if the resourceType is not recognized.
	 * 		Returns an empty list if no resources found for a valid type.  
	 * 
	 * @param resourceType
	 * @return resourceList
	 * @throws DSSRuntimeExceptionFault
	 * @throws IOException 
	 */
	public static synchronized List<String> listResourceNamesByType(String resourceType) throws DSSRuntimeExceptionFault 
	{
		List<String>	resourceList	= new ArrayList<String>();
		String[] 		resourceNameList = getKRResourceTypeListing(resourceType);
		for (String resourceName : resourceNameList) {
			resourceList.add(resourceName);
		}
		return resourceList;
	} 
	
	
    /**
     * Lists directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * 
     * @param clazz 	Any java class that lives in the same place as the desired resources.
     * @param path 		Should end with "/", but not start with one.
     * @return 			Name of each member item, not the full paths.
     * @throws URISyntaxException 
     * @throws IOException 
     * @throws DSSRuntimeExceptionFault 
     */
    public static synchronized String[] getKRResourceTypeListing(String resourceType) throws DSSRuntimeExceptionFault 
    {
    	String 			directoryPath 	= fullPathToKRData + resourceType;
    	List<String> 	result 			= myFu.getFileNameListInDirectory(directoryPath, "", "");
    	return 			result.toArray(new String[result.size()]);
    }
	
    
    /**
     * removeResource deletes a resource of a resourceType and resourceId from the KR, returns false if it fails
     * 
     * @param resourceType
     * @param resourceId
     * @return
     * @throws DSSRuntimeExceptionFault
     */
    public static synchronized boolean removeResource(String resourceType, String resourceId) throws DSSRuntimeExceptionFault 
    {
    	String 			directoryPath 	= fullPathToKRData + resourceType;
		File 			file 			= myFu.getFileWithoutException(directoryPath + "/" + resourceId);
		return file.delete();
    }
	
    
    /**
     * removeAllResourcesByType deletes all resources of a given resourceType, returns false if it fails
     * 
     * @param resourceType
     * @return
     * @throws DSSRuntimeExceptionFault
     */
    public static synchronized boolean removeAllResourcesByType(String resourceType) throws DSSRuntimeExceptionFault 
    {
    	String 			directoryPath 	= fullPathToKRData + resourceType;
    	return FileUtility.deleteDir(new File(directoryPath), false);
    }
	
    
    /**
     * createOrReplaceXMLResource will either replace the existing resource with the submitted ID
     * using the submitted XML, or it will create a new resource with the submitted ID and XML content.
     * 
     * @param resourceType
     * @param resourceId
     * @param xmlAsString
     * @return
     * @throws DSSRuntimeExceptionFault
     */
    public static synchronized File createOrReplaceXMLResource(String resourceType, String resourceId, String xmlAsString) 
    	throws DSSRuntimeExceptionFault 
    {
    	String 				directoryPath 	= fullPathToKRData + resourceType;
		ArrayList<String> 	fileLines 		= new ArrayList<String>();
		fileLines.add(xmlAsString);
		File newOrReplacedFile = new File(directoryPath + "/" + resourceId);
		WriteUtility.getInstance().saveText(fileLines, newOrReplacedFile, "UTF-8");	
		return newOrReplacedFile;
    }
	
    
	/**
	 * Get the value of a particular attribute for a particular resource by ID
	 * 
	 * @param attributeName the name of the attribute as defined in metadata.xsd
	 * @param resourceId
	 * @return attributeValue
	 * @throws DSSRuntimeExceptionFault
	 */
//	public synchronized String getResourceAttributeValue(String attributeName, String resourceId) throws DSSRuntimeExceptionFault 
//	{
//		String attributeValue = "";
//		//TODO implement this
////		File resource = null;
////		String resourceKey = "/" + resourceType + "/" + resourceId;
////		try {
////			resource = FileUtility.getResourceFile(this.getClass(), resourceKey);
////		} catch (IOException e) {
////			e.printStackTrace();
////			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository had IOException in getResource (" 
////					+ resourceKey + ") " + e.getMessage());
////		} catch (URISyntaxException e) {
////			e.printStackTrace();
////			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository had URISyntaxException in getResource (" 
////					+ resourceKey + ") " + e.getMessage());
////		}
//		return attributeValue;
//	} 
	
	
	/**
	 * find the type of a particular resource given only its resourceId.  
	 * Returns an empty string if not found
	 * 
	 * @param resourceId
	 * @return resourceType
	 * @throws DSSRuntimeExceptionFault
	 */
//	//TODO implement this
//	public synchronized String findResourceTypeById(String resourceId) throws DSSRuntimeExceptionFault 
//	{
//		//	findResourceById(String id) ??? requires recursion into subdirectory tree to look for it, do we want to do this?
//		String resourceType = "";
////		File resource = null;
////		String resourceKey = "/" + resourceType + "/" + resourceId;
////		try {
////			resource = FileUtility.getResourceFile(this.getClass(), resourceKey);
////		} catch (IOException e) {
////			e.printStackTrace();
////			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository had IOException in getResource (" 
////					+ resourceKey + ") " + e.getMessage());
////		} catch (URISyntaxException e) {
////			e.printStackTrace();
////			throw new DSSRuntimeExceptionFault("SimpleKnowledgeRepository had URISyntaxException in getResource (" 
////					+ resourceKey + ") " + e.getMessage());
////		}
//		return resourceType;
//	} 

	
	public static void main(String args[]) throws DSSRuntimeExceptionFault
	{
//		SimpleKnowledgeRepository kr = SimpleKnowledgeRepository.getInstance("C:/OpenCDS/opencds-parent/opencds-knowledge-repository-data/src/main/resources");
		
		System.out.println("name of OID 2.16.840.1.113883.6.103 = " + getCodeSystemName("2.16.840.1.113883.6.103"));
		System.out.println();
		
		List<String> resourceNameList = listResourceNamesByType("schema");
		for (String eachResource : resourceNameList) {
			System.out.println(eachResource);
		}		
		System.out.println();
		
		List<File> resourceList = listResourcesByType("apelon");
		for (File eachResource : resourceList) {
			System.out.println(eachResource.getName());
		}
		System.out.println();
	
		String resourceString = getResourceAsString("knowledgeModules", "org.opencds^NQF_0031_v1^1.0.0.drl");
		if (resourceString == null) {
			System.out.println("resourceString is null.");
		} else {
			System.out.println(resourceString);
		}
		System.out.println();
		
		String xmlString = getResourceAsXML("resourceAttributes", "knowledgeModules.xml");
		System.out.println(xmlString);
		
	}
}
