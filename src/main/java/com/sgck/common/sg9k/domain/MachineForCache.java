package com.sgck.common.sg9k.domain;

import java.io.Serializable;
import java.util.Map;

public class MachineForCache implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8691159539642222579L;

	/**
	 * 
	 */
//	private String machineName;//机组名
	
	private String keyPosId;//键相测点id
	
	private String craftItemNum;//工艺位号

	//测点缓存列表
	private Map<String,PositionForCache> positionForCaches;


	public String getKeyPosId() {
		return keyPosId;
	}

	public void setKeyPosId(String keyPosId) {
		this.keyPosId = keyPosId;
	}

	public Map<String, PositionForCache> getPositionForCaches() {
		return positionForCaches;
	}

	public void setPositionForCaches(Map<String, PositionForCache> positionForCaches) {
		this.positionForCaches = positionForCaches;
	}

	public String getCraftItemNum() {
		return craftItemNum;
	}

	public void setCraftItemNum(String craftItemNum) {
		this.craftItemNum = craftItemNum;
	}

//	public String getMachineName() {
//		return machineName;
//	}
//
//	public void setMachineName(String machineName) {
//		this.machineName = machineName;
//	}
}
