package com.sgck.common.sg9k.domain;

import java.io.Serializable;

/**
 * 过程量数据统计表
 * 
 * @author 杨浩 2015年11月6日下午3:11:06
 */
public class StatdataSta extends StatBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2784323696529021209L;

	private Double valueMin;
	private Double valueMax;
	private Double valueAvg;
	private Integer posStatus;
	private Double value;

	public Double getValueMin() {
		return valueMin;
	}

	public void setValueMin(Double valueMin) {
		this.valueMin = valueMin;
	}

	public Double getValueMax() {
		return valueMax;
	}

	public void setValueMax(Double valueMax) {
		this.valueMax = valueMax;
	}

	public Double getValueAvg() {
		return valueAvg;
	}

	public void setValueAvg(Double valueAvg) {
		this.valueAvg = valueAvg;
	}

	public Integer getPosStatus() {
		return posStatus;
	}

	public void setPosStatus(Integer posStatus) {
		this.posStatus = posStatus;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
