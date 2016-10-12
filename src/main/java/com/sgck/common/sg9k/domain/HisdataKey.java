package com.sgck.common.sg9k.domain;

import java.io.Serializable;

/**
 * 转速测点数据
 * 
 * @author 杨浩 2015年10月22日下午5:39:25
 */
public class HisdataKey extends HisBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double speed;// 统计点的速度
	private Double gap;// 统计点的压力

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

}
