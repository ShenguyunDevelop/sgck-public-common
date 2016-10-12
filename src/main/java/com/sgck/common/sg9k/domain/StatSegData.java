package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class StatSegData extends StatBase implements Serializable {

	private static final long serialVersionUID = 855180827015791617L;

	private Integer segNo;

	private Double rms;
	private Double rmsTotal;
	private Double rmsMax;
	private Double rmsMin;
	private Double rmsAvg;

	private Double p_value;
	private Double p_valueTotal;
	private Double p_valueMax;
	private Double p_valueMin;
	private Double p_valueAvg;

	private Double pp_value;
	private Double pp_valueTotal;
	private Double pp_valueMax;
	private Double pp_valueMin;
	private Double pp_valueAvg;
	
	public StatSegData(){
	}

	public Integer getSegNo() {
		return segNo;
	}

	public void setSegNo(Integer segNo) {
		this.segNo = segNo;
	}

	public Double getRms() {
		return rms;
	}

	public void setRms(Double rms) {
		this.rms = rms;
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

	public Double getP_value() {
		return p_value;
	}

	public void setP_value(Double p_value) {
		this.p_value = p_value;
	}

	public Double getP_valueMax() {
		return p_valueMax;
	}

	public void setP_valueMax(Double p_valueMax) {
		this.p_valueMax = p_valueMax;
	}

	public Double getP_valueMin() {
		return p_valueMin;
	}

	public void setP_valueMin(Double p_valueMin) {
		this.p_valueMin = p_valueMin;
	}

	public Double getP_valueAvg() {
		return p_valueAvg;
	}

	public void setP_valueAvg(Double p_valueAvg) {
		this.p_valueAvg = p_valueAvg;
	}

	public Double getPp_value() {
		return pp_value;
	}

	public void setPp_value(Double pp_value) {
		this.pp_value = pp_value;
	}

	public Double getPp_valueMax() {
		return pp_valueMax;
	}

	public void setPp_valueMax(Double pp_valueMax) {
		this.pp_valueMax = pp_valueMax;
	}

	public Double getPp_valueMin() {
		return pp_valueMin;
	}

	public void setPp_valueMin(Double pp_valueMin) {
		this.pp_valueMin = pp_valueMin;
	}

	public Double getPp_valueAvg() {
		return pp_valueAvg;
	}

	public void setPp_valueAvg(Double pp_valueAvg) {
		this.pp_valueAvg = pp_valueAvg;
	}

	public Double getRmsTotal() {
		return rmsTotal;
	}

	public void setRmsTotal(Double rmsTotal) {
		this.rmsTotal = rmsTotal;
	}

	public Double getP_valueTotal() {
		return p_valueTotal;
	}

	public void setP_valueTotal(Double p_valueTotal) {
		this.p_valueTotal = p_valueTotal;
	}

	public Double getPp_valueTotal() {
		return pp_valueTotal;
	}

	public void setPp_valueTotal(Double pp_valueTotal) {
		this.pp_valueTotal = pp_valueTotal;
	}
	
	

}
