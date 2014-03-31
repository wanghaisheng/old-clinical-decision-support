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
import java.util.List;
import java.util.Map;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.opencds.common.config.OpencdsConfigurator;
import org.opencds.common.utilities.DateUtility;
import org.opencds.knowledgeRepository.SimpleKnowledgeRepository;

/**
 * GuvnorEnumerationCreator creates enumerations in the form expected in Guvnor.  
 * 
 * The main method results can be copied directly into Guvnor as enumerations.  
 * 
 * @author Kensaku Kawamoto
 *
 */
public class GuvnorEnumerationCreator {
	
	public static void main(String[] args) throws DSSRuntimeExceptionFault 
	{
        OpencdsConfigurator oConfig = new OpencdsConfigurator();  		// initializes the OpencdsConfigurator.
        SimpleKnowledgeRepository.setFullPathToKRData(oConfig.getKrFullPath());	// initializes the KnowledgeRepository
		
	      EnumerationUtility utility = new EnumerationUtility();
	      Map<String, List<String>> enums = utility.loadEnumerations();
	      
	      System.out.println("Copy the content below into the Drools Guvnor Enumerations entry.  Taking this approach to enable enumeration manipulation without server restart.");
	      System.out.println();
	      String startTime = DateUtility.getInstance().getDateAsString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	      System.out.println("// " + startTime + " Enumerations generated from org.opencds.terminology.GuvnorEnumerationCreator.");
	      System.out.println();
	      
	      ArrayList<String> keys = new ArrayList<String>(enums.keySet());
	      Collections.sort(keys);
	      for (String key : keys)
	      {
	    	  List<String> enumContents = enums.get(key);
	    	  if (enumContents.size() > 0)
	    	  {
	    		  StringBuffer line = new StringBuffer();
	    		  line.append("'");
	    		  line.append(key);
	    		  line.append("' : [");
	    		  for (int k = 0; k < enumContents.size(); k++)
	    		  {
	    			  line.append("'");
	    			  line.append(enumContents.get(k));
	    			  line.append("'");
	    			  if (k < enumContents.size() - 1)
	    			  {
	    				  line.append(", ");
	    			  }
	    		  }
	    		  line.append("]");
	    		  System.out.println(line);
	    	  }	    	  
	      }	      
	}
}
