package com.sgck.common.sg9k.domain;

import java.io.Serializable;


/**
 * 
 * @author 杨浩 2015年11月6日下午4:18:19
 */
public class StatdataVibCount extends StatBaseCount implements Serializable {

	private static final long serialVersionUID = -2563637828096247384L;
	private double pValueAvg;
	private double pValue;

	private Double ppValueAvg;
	private Double ppValue;

	private Double speedAvg;
	private Double speed;

	private Double gapAvg;
	private Double gap;

	private Double rmsAvg;
	private Double rms;

	public double getpValueAvg() {
		return pValueAvg;
	}

	public void setpValueAvg(double pValueAvg) {
		this.pValueAvg = pValueAvg;
	}

	public double getpValue() {
		return pValue;
	}

	public void setpValue(double pValue) {
		this.pValue = pValue;
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

	public void computerAvg() {
		if (getCount() != 0) {
			this.pValueAvg = this.pValue / getCount();
			this.ppValueAvg = this.ppValue / getCount();
			this.speedAvg = this.speed / getCount();
			this.gapAvg = this.gap / getCount();
			this.rmsAvg = this.rms / getCount();
		}
	}

	public void addPValue(double add) {
		this.pValueAvg = this.pValueAvg + add;
	}

	public void addPpValue(double add) {
		this.ppValueAvg = this.ppValueAvg + add;
	}

	public void addSpeed(double add) {
		this.speed = this.speed + add;
	}

	public void addGap(double add) {
		this.gap = this.gap + add;
	}

	public void addRms(double add) {
		this.rms = this.rms + add;
	}

}
