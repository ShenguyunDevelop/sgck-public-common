package com.sgck.common.sg9k.domain;

import java.io.Serializable;


/**
 * 转速测点统计数据
 * 
 * @author 杨浩 2015年10月22日下午5:38:13
 */
public class StatdataKey extends StatBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Double speedMin;// 转速(最小)
	private Double speedMax;// 转速(最大)
	private Double speedAvg;// 转速(平均)
	private Double gapMin;// GAP电压(最小)
	private Double gapMax;// GAP电压(最大)
	private Double gapAvg;// GAP电压(平均)
	private Double speed;// 统计点的速度
	private Double gap;// 统计点的压力
	

	public Double getSpeedMin() {
		return speedMin;
	}

	public void setSpeedMin(Double speedMin) {
		this.speedMin = speedMin;
	}

	public Double getSpeedMax() {
		return speedMax;
	}

	public void setSpeedMax(Double speedMax) {
		this.speedMax = speedMax;
	}

	public Double getSpeedAvg() {
		return speedAvg;
	}

	public void setSpeedAvg(Double speedAvg) {
		this.speedAvg = speedAvg;
	}

	public Double getGapMin() {
		return gapMin;
	}

	public void setGapMin(Double gapMin) {
		this.gapMin = gapMin;
	}

	public Double getGapMax() {
		return gapMax;
	}

	public void setGapMax(Double gapMax) {
		this.gapMax = gapMax;
	}

	public Double getGapAvg() {
		return gapAvg;
	}

	public void setGapAvg(Double gapAvg) {
		this.gapAvg = gapAvg;
	}

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
