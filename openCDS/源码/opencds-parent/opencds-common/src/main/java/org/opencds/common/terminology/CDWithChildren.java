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

package org.opencds.common.terminology;

import java.util.Collections;
import java.util.HashSet;

import java.util.ArrayList;

import org.opencds.common.comparators.CDCodeComparator;
import org.opencds.common.comparators.CDDisplayNameComparator;

/**
 * <p>CDWithChildren represents a CS and its children.</p>
 * 
 * @author Kensaku Kawamoto
 * @version 1.00
 */
public class CDWithChildren extends CD
{
	protected HashSet<CDWithChildren> children;

	public CDWithChildren(String codeSystem, String code) {
		super(codeSystem, code);
		this.children = new HashSet<CDWithChildren>();
	}
	
	public CDWithChildren(String codeSystem, String codeSystemName, String code, String displayName) {
		super(codeSystem, codeSystemName, code, displayName);
		this.children = new HashSet<CDWithChildren>();
	}
	
	
	/**
	 * Returns an empty HashSet if no children.
	 * @return
	 */
	public HashSet<CDWithChildren> getChildren() {
		return children;
	}

	public void setChildren(HashSet<CDWithChildren> children) {
		this.children = children;
	}
	
	public void addChild(CDWithChildren child){
		this.children.add(child);
	}
	
	
	/**
	 * Returns String with each child level indented according to the specified indentation.  E.g.,
	 * A (Top level entry)
	 * 		B (Level 2 entry) 
	 * 			C (Level 3 entry)
	 * 		D (Level 2 entry)
	 * 			E (Level 3 entry)
	 * 
	 * @param indentation
	 * @param sortByCode	Sorts by code.  Otherwise sorts by display name.
	 * @return
	 */
	public String getNestedStringRepresentation(String indentation, boolean sortByCode)
	{
		return getNestedStringRepresentation(this, indentation, 0, sortByCode);
	}
	
	private String getNestedStringRepresentation(CDWithChildren csWithChildren, String indentation, int nestingLevel, boolean sortByCode){
		StringBuffer buffer = new StringBuffer();
		
		for (int k = 0; k < nestingLevel; k++)
		{
			buffer.append(indentation);
		}
		nestingLevel++;
		
		buffer.append(csWithChildren.getCode());
		buffer.append(" (");
		buffer.append(csWithChildren.getDisplayName());
		buffer.append(")\n");
		
		ArrayList<CDWithChildren> childrenAsList = new ArrayList<CDWithChildren>(csWithChildren.getChildren());
		
		if (sortByCode)
		{
			Collections.sort( childrenAsList, new CDCodeComparator());
		}
		else
		{
			Collections.sort(childrenAsList, new CDDisplayNameComparator());
		}
		
		for (CDWithChildren child: childrenAsList)
		{
			buffer.append(getNestedStringRepresentation(child, indentation, nestingLevel, sortByCode));			
		}
		return buffer.toString();
	}
}