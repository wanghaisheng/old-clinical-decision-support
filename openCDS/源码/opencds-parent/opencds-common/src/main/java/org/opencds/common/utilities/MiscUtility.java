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

package org.opencds.common.utilities;


/**
 * @author David Shields
 * 
 * @date 12-9-2011
 *
 */
public class MiscUtility {

	/**
	 * Generate a UUID using default Java UUID generator.  
	 * 
	 * Will consider generating UUID using Johann Burkard's algorithm http://johannburkard.de/software/uuid/
	 * in the future as this algorithm is an order of magnitude faster than java.util.UUID.
	 * 
	 * However, this is not done now due to some Maven/POM issues with using Burkard's algorithm.
	 * 
	 * It is converted to String to be useable in our standard String ID classes
	 * 
	 * @return
	 */
	public static String getUUIDAsString() {
		//return new com.eaio.uuid.UUID().toString(); // using default Java UUID generator for now
		// TODO: consider switching to above UUID generator for performance
		return (java.util.UUID.randomUUID()).toString();
	}

}
