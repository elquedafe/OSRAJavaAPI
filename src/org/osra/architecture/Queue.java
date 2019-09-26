package org.osra.architecture;

import java.util.Vector;

/**
 *
 * @author alvaroluismartinez
 */
public class Queue {
    private long queueId;
	private String switchId;
	private String minRate;
	private String maxRate;
	private String burst;
	private long qosId;
	private String portNumber;
	private String portName;
	/**
	 * @param queueId
	 * @param minRate
	 * @param maxRate
	 * @param burst
	 * @param qosId
	 * @param portNumber
	 * @param portName
	 */
	public Queue(long queueId, String switchId, String minRate, String maxRate, String burst, long qosId, String portNumber,
			String portName) {
		super();
		this.queueId = queueId;
		this.switchId = switchId;
		this.minRate = minRate;
		this.maxRate = maxRate;
		this.burst = burst;
		this.qosId = qosId;
		this.portNumber = portNumber;
		this.portName = portName;
	}
	/**
	 * @return the queueId
	 */
	public long getQueueId() {
		return queueId;
	}
	/**
	 * @param queueId the queueId to set
	 */
	public void setQueueId(long queueId) {
		this.queueId = queueId;
	}
	
	/**
	 * @return the switchId
	 */
	public String getSwitchId() {
		return switchId;
	}
	/**
	 * @param switchId the switchId to set
	 */
	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}
	/**
	 * @return the minRate
	 */
	public String getMinRate() {
		return minRate;
	}
	/**
	 * @param minRate the minRate to set
	 */
	public void setMinRate(String minRate) {
		this.minRate = minRate;
	}
	/**
	 * @return the maxRate
	 */
	public String getMaxRate() {
		return maxRate;
	}
	/**
	 * @param maxRate the maxRate to set
	 */
	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}
	/**
	 * @return the burst
	 */
	public String getBurst() {
		return burst;
	}
	/**
	 * @param burst the burst to set
	 */
	public void setBurst(String burst) {
		this.burst = burst;
	}
	/**
	 * @return the qosId
	 */
	public long getQosId() {
		return qosId;
	}
	/**
	 * @param qosId the qosId to set
	 */
	public void setQosId(long qosId) {
		this.qosId = qosId;
	}
	/**
	 * @return the portNumber
	 */
	public String getPortNumber() {
		return portNumber;
	}
	/**
	 * @param portNumber the portNumber to set
	 */
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	/**
	 * @return the portName
	 */
	public String getPortName() {
		return portName;
	}
	/**
	 * @param portName the portName to set
	 */
	public void setPortName(String portName) {
		this.portName = portName;
	}
    
    public Object[] toTableArray(){
        Vector<Object> v = new Vector<Object>();
        v.add(this.queueId); v.add(this.switchId); v.add(this.portName);
        v.add(this.maxRate); v.add(this.minRate); v.add(this.burst);
        Object[] array = v.toArray();
        return array;
    }
    
}
