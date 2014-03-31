/**
 * Copyright 2012 OpenCDS.org
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

package org.opencds.common.structures;

/**
 * <p>KnowledgeBaseObject</p>
 * 
 *  @author David Shields
 * @version 1.00
 */
public class KnowledgeBaseObject extends Object
{
//    protected KnowledgeBase		knowledgeBaseObject;
    protected Object			knowledgeBaseObject;
    protected long 				useCount;
    protected java.util.Date 	timeStamp;
    
//    public KnowledgeBaseObject(KnowledgeBase knowledgeBaseObject)
    public KnowledgeBaseObject(Object knowledgeBaseObject)
    {
    	this.knowledgeBaseObject 	= knowledgeBaseObject;
    	this.useCount				= 0;
    	this.timeStamp				= new java.util.Date();
    }

	/**
	 * @return the knowledgeBaseObject
	 */
//	public KnowledgeBase getKnowledgeBaseObject() {
	public Object getKnowledgeBaseObject() {
		//auto update the useCount and timeStamp
		this.useCount++;
		this.timeStamp				= new java.util.Date();
		return knowledgeBaseObject;
	}

//	/**
//	 * @param knowledgeBaseObject the knowledgeBaseObject to set
//	 */
//	public void setKnowledgeBaseObject(Object knowledgeBaseObject) {
//		this.knowledgeBaseObject = knowledgeBaseObject;
//	}

	/**
	 * @return the useCount
	 */
	public long getUseCount() {
		return useCount;
	}

	/**
	 * @param useCount the useCount to set
	 * 
	 * not normally used, since this is incremented automatically
	 */
	public void setUseCount() {
		this.useCount = useCount++;
		setTimeStamp();
	}

	/**
	 * @return the timeStamp
	 */
	public java.util.Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 * 
	 * not normally used, since this is set automatically
	 */
	public void setTimeStamp(java.util.Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp() {
		this.timeStamp = new java.util.Date();
	}

}