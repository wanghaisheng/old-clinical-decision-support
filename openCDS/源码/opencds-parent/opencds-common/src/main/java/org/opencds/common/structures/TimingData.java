/**
 * 
 */
package org.opencds.common.structures;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author David Shields
 * 
 * @date
 *
 */
public class TimingData {
	protected AtomicLong		startMessageRequestTime;
	protected AtomicLong		startUnmarshalTime;
	protected AtomicLong		startBuildFactListsTime;
	protected AtomicLong		startBuildConceptListsTime;
	protected AtomicLong		startInferenceEngineLoopTime;
	protected CopyOnWriteArrayList<TimingDataKM> timingDataKMList;
	protected AtomicLong		finishInferenceEngineLoopTime;
	protected AtomicLong		finishMessageRequestTime;

	/**
	 * @return the startMessageRequestTime
	 */
	public AtomicLong getStartMessageRequestTime() {
		return startMessageRequestTime;
	}

	/**
	 * @param startMessageRequestTime the startMessageRequestTime to set
	 */
	public void setStartMessageRequestTime(AtomicLong startMessageRequestTime) {
		this.startMessageRequestTime = (startMessageRequestTime);
	}

	/**
	 * @return the startUnmarshalTime
	 */
	public AtomicLong getStartUnmarshalTime() {
		return startUnmarshalTime;
	}

	/**
	 * @param startUnmarshalTime the startUnmarshalTime to set
	 */
	public void setStartUnmarshalTime(AtomicLong startUnmarshalTime) {
		this.startUnmarshalTime = (startUnmarshalTime);
	}

	/**
	 * @return the finishBuildFactListsTime
	 */
	public AtomicLong getStartBuildFactListsTime() {
		return startBuildFactListsTime;
	}

	/**
	 * @param finishBuildFactListsTime the finishBuildFactListsTime to set
	 */
	public void setStartBuildFactListsTime(AtomicLong startBuildFactListsTime) {
		this.startBuildFactListsTime = (startBuildFactListsTime);
	}

	/**
	 * @return the finishBuildConceptListsTime
	 */
	public AtomicLong getStartBuildConceptListsTime() {
		return startBuildConceptListsTime;
	}

	/**
	 * @param finishBuildConceptListsTime the finishBuildConceptListsTime to set
	 */
	public void setStartBuildConceptListsTime(AtomicLong startBuildConceptListsTime) {
		this.startBuildConceptListsTime = (startBuildConceptListsTime);
	}

	/**
	 * @return the startInferenceEngineLoopTime
	 */
	public AtomicLong getStartInferenceEngineLoopTime() {
		return startInferenceEngineLoopTime;
	}

	/**
	 * @param startInferenceEngineAdapterTime the startInferenceEngineAdapterTime to set
	 */
	public void setStartInferenceEngineLoopTime(AtomicLong startInferenceEngineLoopTime) {
		this.startInferenceEngineLoopTime = (startInferenceEngineLoopTime);
	}

	/**
	 * @return the timingDataKMList
	 */
	public CopyOnWriteArrayList<TimingDataKM> getTimingDataKMList() {
		return timingDataKMList;
	}
	


	/**
	 * @param timingDataKMList the timingDataKMList to set
	 */
	public void addTimingDataKMList(TimingDataKM timingDataKM) {
		this.timingDataKMList.add(timingDataKM);
	}
	
	/**
	 * @return the finishInferenceEngineLoopTime
	 */
	public AtomicLong getFinishInferenceEngineLoopTime() {
		return finishInferenceEngineLoopTime;
	}
	

	/**
	 * @param finishInferenceEngineLoopTime the finishInferenceEngineLoopTime to set
	 */
	public void setFinishInferenceEngineLoopTime(AtomicLong finishInferenceEngineLoopTime) {
		this.finishInferenceEngineLoopTime = (finishInferenceEngineLoopTime);
	}
	

	/**
	 * @return the finishMessageRequestTime
	 */
	public AtomicLong getFinishMessageRequestTime() {
		return finishMessageRequestTime;
	}

	/**
	 * @param finishMessageRequestTime the finishMessageRequestTime to set
	 */
	public void setFinishMessageRequestTime(AtomicLong finishMessageRequestTime) {
		this.finishMessageRequestTime = (finishMessageRequestTime);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimingData [startMessageRequestTime=" + startMessageRequestTime
				+ ", startUnmarshalTime=" + startUnmarshalTime
				+ ", finishBuildFactListsTime=" + startBuildFactListsTime
				+ ", finishBuildConceptListsTime=" + startBuildConceptListsTime
				+ ", startInferenceEngineLoopTime=" + startInferenceEngineLoopTime 
				+ ", timingDataKMList=" + timingDataKMList 
				+ ", finishInferenceEngineLoopTime=" + finishInferenceEngineLoopTime 
				+ ", finishMessageRequestTime=" + finishMessageRequestTime 
				+ "]";
	}

}
