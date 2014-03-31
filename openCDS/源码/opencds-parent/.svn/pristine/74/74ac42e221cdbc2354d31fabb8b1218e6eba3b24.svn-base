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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.config.OpencdsConfigurator;
import org.opencds.common.utilities.DateUtility;
import org.opencds.common.utilities.StringToArrayUtility;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;
import org.opencds.terminology.EnumerationUtility;

/**
 * GuvnorConceptNameFunctionCreator creates a function for looking up OpenCDS concept names within Guvnor.  
 * 
 * The main method results can be copied directly into Guvnor as a function.  
 * 
 * @author Kensaku Kawamoto
 *
 */
public class GuvnorConceptNameLookupFunctionCreator {
	
	public static void main(String[] args) throws DSSRuntimeExceptionFault 
	{
		// added line below to use automatic configuration based on config file des 2012-01-10
//		new OpencdsConfigurator();
		// revised to configure SimpleKnowledgeRepository correctly des 2012-07-05
        OpencdsConfigurator oConfig = new OpencdsConfigurator();  		// initializes the configurator.
        SimpleKnowledgeRepository.setFullPathToKRData(oConfig.getKrFullPath());	// initializes the KnowledgeRepository
		
	      EnumerationUtility utility = new EnumerationUtility();
	      Map<String, List<String>> enums = utility.loadEnumerations();
	      
	      System.out.println("Copy the content below into the Drools Guvnor Functions entry.");
	      System.out.println();
	      System.out.println();
	      System.out.println();
	      String startTime = DateUtility.getInstance().getDateAsString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	      System.out.println("// " + startTime + " Lookups generated from org.opencds.terminology.GuvnorConceptNameLookupFunctionCreator.  Taking this approach to enable enumeration manipulation without server restart.");
	      System.out.println();
	      
	      ArrayList<String> keys = new ArrayList<String>(enums.keySet());
	      HashSet<String> conceptToDisplayNameSet = new HashSet<String>();
	      for (String key : keys)
	      {
	    	  List<String> enumContents = enums.get(key);
	    	  if (enumContents.size() > 0)
	    	  {
	    		  for (int k = 0; k < enumContents.size(); k++)
	    		  {
	    			  conceptToDisplayNameSet.add(enumContents.get(k));	    			
	    		  }	    		 
	    	  }		    	    	 
	      }	      
	      ArrayList<String> conceptToDisplayNameList = new ArrayList<String>(conceptToDisplayNameSet);
	      Collections.sort(conceptToDisplayNameList);
	      
	      System.out.println("function String GetOpenCDSConceptName(String conceptCode)");
	      System.out.println("{");
	      System.out.println("    HashMap<String, String> openCdsConceptCodeToName = new HashMap<String, String>();");
	      for (String conceptToDisplayName : conceptToDisplayNameList)
	      {
	    	  if (conceptToDisplayName.startsWith("C"))
	    	  {
		    	  ArrayList<String> components = StringToArrayUtility.getInstance().getRegularArrayFromString(conceptToDisplayName, "=");
		    	  String conceptCode = components.get(0);
		    	  String displayName = components.get(1);
		    	  System.out.println("    openCdsConceptCodeToName.put(\"" + conceptCode + "\", \"" + displayName + "\");");
	    	  }
	      }
	      System.out.println("    String name = openCdsConceptCodeToName.get(conceptCode);");
	      System.out.println("    if (name == null)");
	      System.out.println("    {  name = \"\"; } ");
	      System.out.println("    return name;");
	      System.out.println("}");
	}
}
