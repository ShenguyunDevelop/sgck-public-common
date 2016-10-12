package com.sgck.common.sg9k.domain;

import java.io.Serializable;

import flex.messaging.io.amf.ASObject;

public class MachineForDau implements Serializable{

	final public static int MACHINE_STATUS_OK = 0;
	final public static int MACHINE_STATUS_OFFLINE = 1;
	final public static int MACHINE_STATUS_DEL = 2;
	final public static int MACHINE_STATUS_FREEZE = 3;
	
	protected boolean isExist;
	protected boolean isValid;
	
	protected byte currentStatus;
	protected byte type;
	protected String subType;
	protected String id;

	protected ASObject rtStatus;
	protected ASObject alarmChannelMap;
	protected ASObject structCfg;
	protected String collectorId;
	private int source;
	private ASObject status;

	private long lastUploadedDataTime = 0;
	private long lastLastUploadedDataTime = 0;

	public MachineForDau() {
		isExist = true;
		isValid = true;
		
		status = new ASObject();
		status.put("status", MACHINE_STATUS_OFFLINE);
		status.put("alarmstatus", -1);
		status.put("num", 0);
	}

	/**
	 * 机组是否存在
	 * 
	 * @return true/false
	 */
	public boolean isExist() {
		return isExist;
	}

	/**
	 * 机组是否被禁用
	 * 
	 * @return true/false
	 */
	public boolean isValid() {
		return isValid;
	}

	public byte getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(byte currentStatus) {
		this.currentStatus = currentStatus;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ASObject getRtStatus() {
		return rtStatus;
	}

	public void setRtStatus(ASObject rtStatus) {
		this.rtStatus = rtStatus;
	}

	public ASObject getAlarmChannelMap() {
		return alarmChannelMap;
	}

	public void setAlarmChannelMap(ASObject alarmChannelMap) {
		this.alarmChannelMap = alarmChannelMap;
	}

	public ASObject getStructCfg() {
		return structCfg;
	}

	public void setStructCfg(ASObject structCfg) {
		this.structCfg = structCfg;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public ASObject getStatus() {
		return status;
	}

	public void setStatus(ASObject status) {
		this.status = status;
	}

	public long getLastUploadedDataTime() {
		return lastUploadedDataTime;
	}

	public void setLastUploadedDataTime(long lastUploadedDataTime) {
		this.lastUploadedDataTime = lastUploadedDataTime;
	}

	public long getLastLastUploadedDataTime() {
		return lastLastUploadedDataTime;
	}

	public void setLastLastUploadedDataTime(long lastLastUploadedDataTime) {
		this.lastLastUploadedDataTime = lastLastUploadedDataTime;
	}

	public static int getMachineStatusOk() {
		return MACHINE_STATUS_OK;
	}

	public static int getMachineStatusOffline() {
		return MACHINE_STATUS_OFFLINE;
	}

	public static int getMachineStatusDel() {
		return MACHINE_STATUS_DEL;
	}

	public static int getMachineStatusFreeze() {
		return MACHINE_STATUS_FREEZE;
	}
	
}
