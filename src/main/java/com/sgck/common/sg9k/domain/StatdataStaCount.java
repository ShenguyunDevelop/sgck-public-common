package com.sgck.common.sg9k.domain;

import java.io.Serializable;


/**
 * 
 * @author 杨浩 2015年11月6日下午4:18:19
 */
public class StatdataStaCount extends StatBaseCount implements Serializable {

	private static final long serialVersionUID = -2563637828096247384L;
	private double valueAvg;
	private double value;

	public double getValueAvg() {
		return valueAvg;
	}

	public void setValueAvg(double valueAvg) {
		this.valueAvg = valueAvg;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public void computerAvg() {
		if (getCount() != 0) {
			this.valueAvg = this.value / getCount();
		}
	}

	public void addValue(double add) {
		this.value = this.value + add;
	}

}
