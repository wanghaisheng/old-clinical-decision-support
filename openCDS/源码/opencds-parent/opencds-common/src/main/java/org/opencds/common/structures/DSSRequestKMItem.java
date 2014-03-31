/**
 * 
 */
package org.opencds.common.structures;




/**
 * @author David Shields
 * 
 * @date
 *
 */
public class DSSRequestKMItem {
	protected String 				requestedKmId;
	protected String				requestedKmPrimaryProcessName;
	protected String 				focalPersonId;
	protected DSSRequestDataItem	dssRequestDataItem;
	protected TimingDataKM 			kmTimingData;
//	protected JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> 	cdsInput;
	protected Object			 	cdsInput;



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
	 * @return the focalPersonId
	 */
	public String getFocalPersonId() {
		return focalPersonId;
	}

	/**
	 * @param focalPersonId the focalPersonId to set
	 */
	public void setFocalPersonId(String focalPersonId) {
		this.focalPersonId = focalPersonId;
	}

	/**
	 * @return the dssRequestDataItem
	 */
	public DSSRequestDataItem getDssRequestDataItem() {
		return dssRequestDataItem;
	}

	/**
	 * @param dssRequestDataItem the dssRequestDataItem to set
	 */
	public void setDssRequestDataItem(DSSRequestDataItem dssRequestDataItem) {
		this.dssRequestDataItem = dssRequestDataItem;
	}

	/**
	 * @return the kmTimingData
	 */
	public TimingDataKM getKmTimingData() {
		return kmTimingData;
	}

	/**
	 * @param kmTimingData the kmTimingData to set
	 */
	public void setKmTimingData(TimingDataKM kmTimingData) {
		this.kmTimingData = kmTimingData;
	}

	/**
	 * @return the cdsInput
	 * NOTE: the object returned must be cast to JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput>
	 */
//	public JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> getCdsInput() {
	public Object getCdsInput() {
		return cdsInput;
	}

	/**
	 * @param cdsInput the cdsInput to set
	 * NOTE: the cdsInput object is expected to be a JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput>
	 */
//	public void setCdsInput(JAXBElement<org.opencds.vmr.v1_0.schema.CDSInput> cdsInput) {
	public void setCdsInput(Object cdsInput) {
		this.cdsInput = cdsInput;
	}


}
