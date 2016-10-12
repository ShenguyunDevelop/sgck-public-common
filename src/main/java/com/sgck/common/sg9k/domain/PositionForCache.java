package com.sgck.common.sg9k.domain;

import java.io.Serializable;

import flex.messaging.io.amf.ASObject;

public class PositionForCache implements Serializable {

	private String gpid;
	
	private Integer mainDataType;//主类型
	
	private Integer gptype;//测点类型
	
	private String posName;//测点名
	
	private ASObject dauChannel;//采集器管道信息{duaid:channel};
	
	private ASObject segments;//分段信息key为分段号，value为分段名
	
	private String unit;//单位，键相测点无单位
	
	private String craftItemNum;//工艺位号
	
	public PositionForCache(String gpid) {
		this.gpid = gpid;
	}

	public PositionForCache(String gpid, String posName) {
		this.gpid = gpid;
		this.posName = posName;
	}

	public String getGpid() {
		return gpid;
	}

	public void setGpid(String gpid) {
		this.gpid = gpid;
	}

	public Integer getMainDataType() {
		return mainDataType;
	}

	public void setMainDataType(Integer mainDataType) {
		this.mainDataType = mainDataType;
	}

	public Integer getGptype() {
		return gptype;
	}

	public void setGptype(Integer gptype) {
		this.gptype = gptype;
	}

	public ASObject getDauChannel() {
		return dauChannel;
	}

	public void setDauChannel(ASObject dauChannel) {
		this.dauChannel = dauChannel;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public ASObject getSegments() {
		return segments;
	}

	public void setSegments(ASObject segments) {
		this.segments = segments;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCraftItemNum() {
		return craftItemNum;
	}

	public void setCraftItemNum(String craftItemNum) {
		this.craftItemNum = craftItemNum;
	}
	
	
}
