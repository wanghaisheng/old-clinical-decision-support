/**
 * 
 */
package org.opencds.common.config;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.utilities.FileUtility;
import org.opencds.common.xml.XmlConverter;
import org.opencds.common.xml.XmlEntity;
//import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.xml.sax.SAXParseException;

/**
 * OpencdsConfigurator parses the config file, and returns values within it via getters, and protected setters...
 * 
 * @author David Shields
 * 
 * @date 2012-01-02
 *
 */
public class OpencdsConfigurator {
	
    private static 	OpencdsConfigurator 		instance;  //singleton instance
	private static 	Log 						log 					= LogFactory.getLog(OpencdsConfigurator.class);
	private			FileUtility					fileUtility				= FileUtility.getInstance();
	
    public OpencdsConfigurator() throws DSSRuntimeExceptionFault
    
    {
        initialize();
    }

    // public functions
    public static synchronized OpencdsConfigurator getInstance() throws DSSRuntimeExceptionFault
    {
        if (instance == null)
        {
            instance = new OpencdsConfigurator();
        }
        return instance;
    }
    

	public void initialize() throws DSSRuntimeExceptionFault 
	{
	    File configFile = fileUtility.getResourceFile(this.getClass(), "opencds-decision-support-service-config.xml");
	    
		// read config resource
	    String configXml = fileUtility.getFileContentsAsString( configFile );
		XmlEntity configRootEntity = null;
		try 
		{
			configRootEntity = XmlConverter.getInstance().unmarshalXml(configXml, false, null);
		} catch (SAXParseException e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("OpencdsConfigurator failed to unmarshall opencds-decision-support-service-config.xml " + configXml + " " + e.getCause());
		}
		
		// load config type 
		XmlEntity krRootElement = configRootEntity.getFirstChildWithLabel("knowledge-repository-config");
		if (krRootElement == null) {
			throw new DSSRuntimeExceptionFault("OpencdsConfigurator failed to find 'knowledge-repository-config' tag: " + configXml);				
		}
		XmlEntity krTypeElement = krRootElement.getFirstChildWithLabel("knowledge-repository-type");
		String krType = krTypeElement.getAttributeValue("type");
		if ( !"SIMPLE_FILE".equals(krType) ) {
			throw new DSSRuntimeExceptionFault("OpencdsConfigurator found knowledge-repository-type.type='" + krType + "' in opencds-decision-support-service-config.xml. " 
					+ "Only 'SIMPLE_FILE' is supported at this time: " + configXml);				
		}
		
		// load fullPath
		XmlEntity  fullPathElement = krRootElement.getFirstChildWithLabel("knowledge-repository-full-path");
		String krFullPath = fullPathElement.getAttributeValue("fullPath");
		// now make it externally accessible...
		setKrFullPath(krFullPath);  
		
		// initialize SimpleKnowledgeRepository with fullPath
		log.debug("opencds-decision-support-service-config.krFullPath=" + krFullPath);
//		SimpleKnowledgeRepository.getInstance(krFullPath);
	}
	
	protected String krFullPath;

	/**
	 * @return the krFullPath
	 */
	public String getKrFullPath() {
		return krFullPath;
	}

	/**
	 * @param krFullPath the krFullPath to set
	 */
	protected void setKrFullPath(String krFullPath) {
		this.krFullPath = krFullPath;
	}
	
	
}
