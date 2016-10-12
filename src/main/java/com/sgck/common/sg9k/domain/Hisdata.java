package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class Hisdata extends HisBase implements Serializable {

	private static final long serialVersionUID = -6856661922411128192L;

	private Double speed;
	private Double gap;
	private Double rms;
	private Double pp_value;
	private Double p_value;
	private Double offset_voltage;
	private Double subside_value;
	private Double static_value;
	private String seg_values;

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

	public Double getPp_value() {
		return pp_value;
	}

	public void setPp_value(Double pp_value) {
		this.pp_value = pp_value;
	}

	public Double getP_value() {
		return p_value;
	}

	public void setP_value(Double p_value) {
		this.p_value = p_value;
	}

	public Double getOffset_voltage() {
		return offset_voltage;
	}

	public void setOffset_voltage(Double offset_voltage) {
		this.offset_voltage = offset_voltage;
	}

	public Double getSubside_value() {
		return subside_value;
	}

	public void setSubside_value(Double subside_value) {
		this.subside_value = subside_value;
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

}
