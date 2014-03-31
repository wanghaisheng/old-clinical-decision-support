/**
 * 
 */
package org.opencds.common.structures;

import java.util.Date;

/**
 * @author David Shields
 * 
 * @date
 *
 */
public class DecodedDSSRequest {
	protected Date 		evalTime; 
	protected String 	clientLanguage;
	protected String 	clientTimeZoneOffset;
	protected String 	requestedKmId;
	protected String	requestedKmPrimaryProcessName;
	protected String 	externalFactModelSSId;
	protected String	inputItemName;
	protected String	inputContainingEntityId;
	protected String 	inputPayloadString;
	protected String	interactionId;
	protected TimingData timingData;
	protected TimingDataKM timingDataKM;

	/**
	 * @return the evalTime
	 */
	public Date getEvalTime() {
		return evalTime;
	}

	/**
	 * @param evalTime the evalTime to set
	 */
	public void setEvalTime(Date evalTime) {
		this.evalTime = evalTime;
	}

	/**
	 * @return the clientLanguage
	 */
	public String getClientLanguage() {
		return clientLanguage;
	}

	/**
	 * @param clientLanguage the clientLanguage to set
	 */
	public void setClientLanguage(String clientLanguage) {
		this.clientLanguage = clientLanguage;
	}

	/**
	 * @return the clientTimeZoneOffset
	 */
	public String getClientTimeZoneOffset() {
		return clientTimeZoneOffset;
	}

	/**
	 * @param clientTimeZoneOffset the clientTimeZoneOffset to set
	 */
	public void setClientTimeZoneOffset(String clientTimeZoneOffset) {
		this.clientTimeZoneOffset = clientTimeZoneOffset;
	}

	/**
	 * @return the requestedKmId
	 */
	public String getRequestedKmId() {
		return requestedKmId;
	}

	/**
	 * @param requestedKmId the requestedKmId to set
	 */
	public void setRequestedKmId(String requestedKmId) {
		this.requestedKmId = requestedKmId;
	}

	/**
	 * @return the requestedKmPrimaryProcessName
	 */
	public String getRequestedKmPrimaryProcessName() {
		return requestedKmPrimaryProcessName;
	}

	/**
	 * @param requestedKmPrimaryProcessName the requestedKmPrimaryProcessName to set
	 */
	public void setRequestedKmPrimaryProcessName(String requestedKmPrimaryProcessName) {
		this.requestedKmPrimaryProcessName = requestedKmPrimaryProcessName;
	}

	/**

	 * @return the externalFactModelSSId
	 */
	public String getExternalFactModelSSId() {
		return externalFactModelSSId;
	}

	/**
	 * @param externalFactModelSSId the externalFactModelSSId to set
	 */
	public void setExternalFactModelSSId(String externalFactModelSSId) {
		this.externalFactModelSSId = externalFactModelSSId;
	}

	/**
	 * @return the inputItemName
	 */
	public String getInputItemName() {
		return inputItemName;
	}

	/**
	 * @param inputItemName the inputItemName to set
	 */
	public void setInputItemName(String inputItemName) {
		this.inputItemName = inputItemName;
	}

	/**
	 * @return the inputContainingEntityId
	 */
	public String getInputContainingEntityId() {
		return inputContainingEntityId;
	}

	/**
	 * @param inputContainingEntityId the inputContainingEntityId to set
	 */
	public void setInputContainingEntityId(String inputContainingEntityId) {
		this.inputContainingEntityId = inputContainingEntityId;
	}

	/**
	 * @return the inputPayloadString
	 */
	public String getInputPayloadString() {
		return inputPayloadString;
	}

	/**
	 * @param inputPayloadString the inputPayloadString to set
	 */
	public void setInputPayloadString(String inputPayloadString) {
		this.inputPayloadString = inputPayloadString;
	}

	/**
	 * @return the interactionId
	 */
	public String getInteractionId() {
		return interactionId;
	}

	/**
	 * @param interactionId the interactionId to set
	 */
	public void setInteractionId(String interactionId) {
		this.interactionId = interactionId;
	}

	/**
	 * @return the timingData
	 */
	public TimingData getTimingData() {
		return timingData;
	}

	/**
	 * @param timingData the timingData to set
	 */
	public void setTimingData(TimingData timingData) {
		this.timingData = timingData;
	}

	/**
	 * @return the timingDataKM
	 */
	public TimingDataKM getTimingDataKM() {
		return timingDataKM;
	}

	/**
	 * @param timingDataKM the timingDataKM to set
	 */
	public void setTimingDataKM(TimingDataKM timingDataKM) {
		this.timingDataKM = timingDataKM;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DecodedDSSRequest [evalTime=" + evalTime + ", clientLanguage="
				+ clientLanguage + ", clientTimeZoneOffset="
				+ clientTimeZoneOffset + ", requestedKmId=" + requestedKmId
				+ ", requestedKmPrimaryProcessName=" + requestedKmPrimaryProcessName
				+ ", externalFactModelSSId=" + externalFactModelSSId
				+ ", inputItemName=" + inputItemName
				+ ", inputContainingEntityId=" + inputContainingEntityId
				+ ", inputPayloadString=" + inputPayloadString
				+ ", interactionId=" + interactionId 
				+ ", timingData=" + timingData 
				+ ", timingDataKM=" + timingDataKM 
				+ "]";
	}
}
