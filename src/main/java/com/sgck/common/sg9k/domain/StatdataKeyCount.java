package com.sgck.common.sg9k.domain;

import java.io.Serializable;

/**
 * 用户数据统计缓存
 * 
 * @author 杨浩 2015年10月22日下午6:08:37
 */
public class StatdataKeyCount extends StatBaseCount implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double speed;
	private Double gap;
	private Double speedAvg;
	private Double gapAvg;

	public StatdataKeyCount() {
	}
	
	public Double getSpeed() {
		return speed;
	}


	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	
	public void addSpeed(Double speed){
		if(this.speed == null){
			this.speed = speed;
		}else{
			this.speed += speed;
		}
	}

	public Double getGap() {
		return gap;
	}

	public void setGap(Double gap) {
		this.gap = gap;
	}
	
	public void addGap(Double gap){
		if(this.gap == null){
			this.gap = gap;
		}else{
			this.gap += gap;
		}
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

	@Override
	public void computerAvg() {
		if (getCount() <= 0) {
			return;
		}
		
		if(this.gapAvg != null){
			this.gapAvg = this.gap / getCount();
		}
		
		if(this.speedAvg != null){
			this.speedAvg = this.speed / getCount();
		}
	}

}
