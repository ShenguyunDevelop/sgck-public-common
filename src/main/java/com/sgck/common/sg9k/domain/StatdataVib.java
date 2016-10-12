package com.sgck.common.sg9k.domain;

import java.io.Serializable;

/**
 * 振动数据统计表
 * 
 * @author 杨浩 2015年11月6日下午4:59:56
 */
public class StatdataVib extends StatBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 180698914761163470L;

	
	private Integer vibrationType;
	private Integer posStatus;

	private Double pValueMax;
	private Double pValueMin;
	private Double pValueAvg;
	private Double pValue;

	private Double ppValueMax;
	private Double ppValueMin;
	private Double ppValueAvg;
	private Double ppValue;

	private Double speedMax;
	private Double speedMin;
	private Double speedAvg;
	private Double speed;

	private Double gapMax;
	private Double gapMin;
	private Double gapAvg;
	private Double gap;

	private Double rmsMax;
	private Double rmsMin;
	private Double rmsAvg;
	private Double rms;

	public Integer getVibrationType() {
		return vibrationType;
	}

	public void setVibrationType(Integer vibrationType) {
		this.vibrationType = vibrationType;
	}

	public Integer getPosStatus() {
		return posStatus;
	}

	public void setPosStatus(Integer posStatus) {
		this.posStatus = posStatus;
	}

	public Double getpValueMax() {
		return pValueMax;
	}

	public void setpValueMax(Double pValueMax) {
		this.pValueMax = pValueMax;
	}

	public Double getpValueMin() {
		return pValueMin;
	}

	public void setpValueMin(Double pValueMin) {
		this.pValueMin = pValueMin;
	}

	public Double getpValueAvg() {
		return pValueAvg;
	}

	public void setpValueAvg(Double pValueAvg) {
		this.pValueAvg = pValueAvg;
	}

	public Double getpValue() {
		return pValue;
	}

	public void setpValue(Double pValue) {
		this.pValue = pValue;
	}

	public Double getPpValueMax() {
		return ppValueMax;
	}

	public void setPpValueMax(Double ppValueMax) {
		this.ppValueMax = ppValueMax;
	}

	public Double getPpValueMin() {
		return ppValueMin;
	}

	public void setPpValueMin(Double ppValueMin) {
		this.ppValueMin = ppValueMin;
	}

	public Double getPpValueAvg() {
		return ppValueAvg;
	}

	public void setPpValueAvg(Double ppValueAvg) {
		this.ppValueAvg = ppValueAvg;
	}

	public Double getPpValue() {
		return ppValue;
	}

	public void setPpValue(Double ppValue) {
		this.ppValue = ppValue;
	}

	public Double getSpeedMax() {
		return speedMax;
	}

	public void setSpeedMax(Double speedMax) {
		this.speedMax = speedMax;
	}

	public Double getSpeedMin() {
		return speedMin;
	}

	public void setSpeedMin(Double speedMin) {
		this.speedMin = speedMin;
	}

	public Double getSpeedAvg() {
		return speedAvg;
	}

	public void setSpeedAvg(Double speedAvg) {
		this.speedAvg = speedAvg;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getGapMax() {
		return gapMax;
	}

	public void setGapMax(Double gapMax) {
		this.gapMax = gapMax;
	}

	public Double getGapMin() {
		return gapMin;
	}

	public void setGapMin(Double gapMin) {
		this.gapMin = gapMin;
	}

	public Double getGapAvg() {
		return gapAvg;
	}

	public void setGapAvg(Double gapAvg) {
		this.gapAvg = gapAvg;
	}

	public Double getGap() {
		return gap;
	}

	public void setGap(Double gap) {
		this.gap = gap;
	}

	public Double getRmsMax() {
		return rmsMax;
	}

	public void setRmsMax(Double rmsMax) {
		this.rmsMax = rmsMax;
	}

	public Double getRmsMin() {
		return rmsMin;
	}

	public void setRmsMin(Double rmsMin) {
		this.rmsMin = rmsMin;
	}

	public Double getRmsAvg() {
		return rmsAvg;
	}

	public void setRmsAvg(Double rmsAvg) {
		this.rmsAvg = rmsAvg;
	}

	public Double getRms() {
		return rms;
	}

	public void setRms(Double rms) {
		this.rms = rms;
	}

}
