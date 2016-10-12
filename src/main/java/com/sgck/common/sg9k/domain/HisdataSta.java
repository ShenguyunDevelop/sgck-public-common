package com.sgck.common.sg9k.domain;

import java.io.Serializable;

/**
 * 过程量历史数据表
 * 
 * @author 杨浩 2015年11月6日下午2:53:44
 */
public class HisdataSta extends HisBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8783196682408537973L;

	private Double value;
	private Integer posStatus;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getPosStatus() {
		return posStatus;
	}

	public void setPosStatus(Integer posStatus) {
		this.posStatus = posStatus;
	}

}
