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

package org.opencds.common.comparators;

import java.util.Comparator;

import org.opencds.common.terminology.CD;

/**
 * Comparator for sorting CDs by displayName, in ascending order.
 * @author kawam001
 *
 */
public class CDDisplayNameComparator implements Comparator<Object> {

	public int compare(Object conceptObject1, Object conceptObject2)
    {
		CD cd1 = (CD) conceptObject1;
		CD cd2 = (CD) conceptObject2;
        int returnValue = (cd1.getDisplayName()).compareTo(cd2.getDisplayName());
        return returnValue;
    }	
}
