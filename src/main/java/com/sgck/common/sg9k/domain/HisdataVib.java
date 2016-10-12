package com.sgck.common.sg9k.domain;

import java.io.Serializable;

/**
 * 振动历史数据表
 * 
 * @author 杨浩 2015年11月6日下午2:51:46
 */
public class HisdataVib extends HisBase implements Serializable {

	private static final long serialVersionUID = 962798547395584761L;
	private Double speed;
	private Double gap;
	private Double rms;
	private Double ppValue;
	private Double pValue;
	private Integer posStatus;
	private Integer subDatatype;
	private Integer vibrationType;

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getGap() {
		return gap;
	}

	public void setGap(Double gap) {
		this.gap = gap;
	}

	public Double getRms() {
		return rms;
	}

	public void setRms(Double rms) {
		this.rms = rms;
	}

	public Double getPpValue() {
		return ppValue;
	}

	public void setPpValue(Double ppValue) {
		this.ppValue = ppValue;
	}

	public Double getpValue() {
		return pValue;
	}

	public void setpValue(Double pValue) {
		this.pValue = pValue;
	}

	public Integer getPosStatus() {
		return posStatus;
	}

	public void setPosStatus(Integer posStatus) {
		this.posStatus = posStatus;
	}

	public Integer getSubDatatype() {
		return subDatatype;
	}

	public void setSubDatatype(Integer subDatatype) {
		this.subDatatype = subDatatype;
	}

	public Integer getVibrationType() {
		return vibrationType;
	}

	public void setVibrationType(Integer vibrationType) {
		this.vibrationType = vibrationType;
	}

}
