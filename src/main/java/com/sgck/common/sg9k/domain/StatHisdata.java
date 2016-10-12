package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class StatHisdata extends StatBase implements Serializable {

	private static final long serialVersionUID = -1865451972174342078L;

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

	private Double ppValueMax;
	private Double ppValueMin;
	private Double ppValueAvg;
	private Double ppValue;

	private Double pValueMax;
	private Double pValueMin;
	private Double pValueAvg;
	private Double pValue;

	private Double offset_voltageMax;
	private Double offset_voltageMin;
	private Double offset_voltageAvg;
	private Double offset_voltage;

	private Double subside_valueMax;
	private Double subside_valueMin;
	private Double subside_valueAvg;
	private Double subside_value;

	private Double static_valueMax;
	private Double static_valueMin;
	private Double static_valueAvg;
	private Double static_value;

	private String seg_values;

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

	public Double getRms() {
		return rms;
	}

	public void setRms(Double rms) {
		this.rms = rms;
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

	public Double getPpValue() {
		return ppValue;
	}

	public void setPpValue(Double ppValue) {
		this.ppValue = ppValue;
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

	public Double getpValue() {
		return pValue;
	}

	public void setpValue(Double pValue) {
		this.pValue = pValue;
	}

	public Double getOffset_voltageMax() {
		return offset_voltageMax;
	}

	public void setOffset_voltageMax(Double offset_voltageMax) {
		this.offset_voltageMax = offset_voltageMax;
	}

	public Double getOffset_voltageMin() {
		return offset_voltageMin;
	}

	public void setOffset_voltageMin(Double offset_voltageMin) {
		this.offset_voltageMin = offset_voltageMin;
	}

	public Double getOffset_voltage() {
		return offset_voltage;
	}

	public void setOffset_voltage(Double offset_voltage) {
		this.offset_voltage = offset_voltage;
	}

	public Double getSubside_valueMax() {
		return subside_valueMax;
	}

	public void setSubside_valueMax(Double subside_valueMax) {
		this.subside_valueMax = subside_valueMax;
	}

	public Double getSubside_valueMin() {
		return subside_valueMin;
	}

	public void setSubside_valueMin(Double subside_valueMin) {
		this.subside_valueMin = subside_valueMin;
	}

	public Double getSubside_value() {
		return subside_value;
	}

	public void setSubside_value(Double subside_value) {
		this.subside_value = subside_value;
	}

	public Double getStatic_valueMax() {
		return static_valueMax;
	}

	public void setStatic_valueMax(Double static_valueMax) {
		this.static_valueMax = static_valueMax;
	}

	public Double getStatic_valueMin() {
		return static_valueMin;
	}

	public void setStatic_valueMin(Double static_valueMin) {
		this.static_valueMin = static_valueMin;
	}

	public Double getStatic_value() {
		return static_value;
	}

	public void setStatic_value(Double static_value) {
		this.static_value = static_value;
	}

	public String getSeg_values() {
		return seg_values;
	}

	public void setSeg_values(String seg_values) {
		this.seg_values = seg_values;
	}

	public Double getSpeedAvg() {
		return speedAvg;
	}

	public void setSpeedAvg(Double speedAvg) {
		this.speedAvg = speedAvg;
	}

	public Double getGapAvg() {
		return gapAvg;
	}

	public void setGapAvg(Double gapAvg) {
		this.gapAvg = gapAvg;
	}

	public Double getRmsAvg() {
		return rmsAvg;
	}

	public void setRmsAvg(Double rmsAvg) {
		this.rmsAvg = rmsAvg;
	}

	public Double getPpValueAvg() {
		return ppValueAvg;
	}

	public void setPpValueAvg(Double ppValueAvg) {
		this.ppValueAvg = ppValueAvg;
	}

	public Double getpValueAvg() {
		return pValueAvg;
	}

	public void setpValueAvg(Double pValueAvg) {
		this.pValueAvg = pValueAvg;
	}

	public Double getOffset_voltageAvg() {
		return offset_voltageAvg;
	}

	public void setOffset_voltageAvg(Double offset_voltageAvg) {
		this.offset_voltageAvg = offset_voltageAvg;
	}

	public Double getSubside_valueAvg() {
		return subside_valueAvg;
	}

	public void setSubside_valueAvg(Double subside_valueAvg) {
		this.subside_valueAvg = subside_valueAvg;
	}

	public Double getStatic_valueAvg() {
		return static_valueAvg;
	}

	public void setStatic_valueAvg(Double static_valueAvg) {
		this.static_valueAvg = static_valueAvg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
