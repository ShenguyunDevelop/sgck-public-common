package com.sgck.common.sg9k.domain;

import java.io.Serializable;
import java.util.Set;

import net.sf.json.JSONObject;

public class StatHisdataCount extends StatBaseCount implements Serializable {

	private static final long serialVersionUID = -8997166476000013429L;

	private Double speed;
	private Double speedAvg;
	private Double gap;
	private Double gapAvg;
	private Double rms;
	private Double rmsAvg;
	private Double ppValue;
	private Double ppValueAvg;
	private Double pValue;
	private Double pValueAvg;
	private Double offset_voltage;
	private Double offset_voltageAvg;
	private Double subside_value;
	private Double subside_valueAvg;
	private Double static_value;
	private Double static_valueAvg;
	private String seg_values;

	public StatHisdataCount() {
		this.seg_values = "";
	}

	@Override
	public void computerAvg() {
		
		if(getCount() <= 0){
			return;
		}
		
		if(this.speed != null){
			this.speedAvg = this.speed / getCount();
		}
		if(this.gap != null){
			this.gapAvg = this.gap / getCount();
		}
		if(this.rms != null){
			this.rmsAvg = this.rms / getCount();
		}
		if(this.ppValue != null){
			this.ppValueAvg = this.ppValue / getCount();
		}
		if(this.pValue != null){
			this.pValueAvg = this.pValue / getCount();
		}
		if(this.offset_voltage != null){
			this.offset_voltageAvg = this.offset_voltage / getCount();
		}
		if(this.subside_value != null){
			this.subside_valueAvg = this.subside_value / getCount();
		}
		if(this.static_value != null){
			this.static_valueAvg = this.static_value / getCount();
		}

		// 各分段平均值信息统计
		StatSegData[] segDataArr = new StatSegData[36];
		if (null != seg_values && !"".equals(seg_values)) {
			JSONObject segJsonObj = JSONObject.fromObject(seg_values);
			if (segJsonObj != null && !segJsonObj.isEmpty()) {
				Set<String> keys = segJsonObj.keySet();
				for (String key : keys) {
					if (!key.startsWith("seg") && !key.endsWith("_total")) {
						continue;
					}

					String segNoStr = key.substring("seg".length(), key.indexOf("_"));
					Integer segNo = Integer.valueOf(segNoStr);
					if (segDataArr[segNo - 1] == null) {
						segDataArr[segNo - 1] = new StatSegData();
					}
					segDataArr[segNo - 1].setSegNo(segNo);

					if (key.endsWith("_rms_total")) {
						segDataArr[segNo - 1].setRmsTotal(Double.valueOf(segJsonObj.get(key).toString()));
					} else if (key.endsWith("_p_value_total")) {
						segDataArr[segNo - 1].setP_valueTotal(Double.valueOf(segJsonObj.get(key).toString()));
					} else if (key.endsWith("_pp_value_total")) {
						segDataArr[segNo - 1].setPp_valueTotal(Double.valueOf(segJsonObj.get(key).toString()));
					}

				}

				for (int i = 0; i < segDataArr.length; i++) {
					StatSegData statSegData = segDataArr[i];
					if (statSegData == null) {
						continue;
					}

					String prefix = "seg" + (i + 1);
					if(statSegData.getRmsTotal() != null){
						segJsonObj.put(prefix + "_rms_avg", statSegData.getRmsTotal() / getCount());
					}
					if(statSegData.getP_valueTotal()!= null){
						segJsonObj.put(prefix + "_p_value_avg", statSegData.getP_valueTotal() / getCount());
					}
					if(statSegData.getPp_valueTotal() != null){
						segJsonObj.put(prefix + "_pp_value_avg", statSegData.getPp_valueTotal() / getCount());
					}
				}
				
				this.seg_values = segJsonObj.toString();
			}
		}

	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public void addSpeed(Double speed) {
		if(this.speed == null){
			this.speed = speed;
		}else{
			this.speed += speed;
		}
	}

	public Double getSpeedAvg() {
		return speedAvg;
	}

	public void setSpeedAvg(Double speedAvg) {
		this.speedAvg = speedAvg;
	}

	public Double getGap() {
		return gap;
	}

	public void setGap(Double gap) {
		this.gap = gap;
	}

	public void addGap(Double gap) {
		if(this.gap == null){
			this.gap = gap;
		}else{
			this.gap += gap;
		}
	}

	public Double getGapAvg() {
		return gapAvg;
	}

	public void setGapAvg(Double gapAvg) {
		this.gapAvg = gapAvg;
	}

	public Double getRms() {
		return rms;
	}

	public void setRms(Double rms) {
		this.rms = rms;
	}

	public void addRms(Double rms) {
		if(this.rms == null){
			this.rms = rms;
		}else{
			this.rms += rms;
		}
	}

	public Double getRmsAvg() {
		return rmsAvg;
	}

	public void setRmsAvg(Double rmsAvg) {
		this.rmsAvg = rmsAvg;
	}

	public Double getPpValue() {
		return ppValue;
	}

	public void setPpValue(Double ppValue) {
		this.ppValue = ppValue;
	}

	public void addPpValue(Double ppValue) {
		if(this.ppValue == null){
			this.ppValue = ppValue;
		}else{
			this.ppValue += ppValue;
		}
	}

	public Double getPpValueAvg() {
		return ppValueAvg;
	}

	public void setPpValueAvg(Double ppValueAvg) {
		this.ppValueAvg = ppValueAvg;
	}

	public Double getpValue() {
		return pValue;
	}

	public void setpValue(Double pValue) {
		this.pValue = pValue;
	}

	public void addPValue(Double pValue) {
		if(this.pValue == null){
			this.pValue = pValue;
		}else{
			this.pValue += pValue;
		}
	}

	public Double getpValueAvg() {
		return pValueAvg;
	}

	public void setpValueAvg(Double pValueAvg) {
		this.pValueAvg = pValueAvg;
	}

	public Double getOffset_voltage() {
		return offset_voltage;
	}

	public void setOffset_voltage(Double offset_voltage) {
		this.offset_voltage = offset_voltage;
	}

	public void addOffsetVoltage(Double offset_voltage) {
		if(this.offset_voltage == null){
			this.offset_voltage = offset_voltage;
		}else{
			this.offset_voltage += offset_voltage;
		}
	}

	public Double getOffset_voltageAvg() {
		return offset_voltageAvg;
	}

	public void setOffset_voltageAvg(Double offset_voltageAvg) {
		this.offset_voltageAvg = offset_voltageAvg;
	}

	public Double getSubside_value() {
		return subside_value;
	}

	public void setSubside_value(Double subside_value) {
		this.subside_value = subside_value;
	}

	public void addSubsideValue(Double subside_value) {
		if(this.subside_value == null){
			this.subside_value = subside_value;
		}else{
			this.subside_value += subside_value;
		}
	}

	public Double getSubside_valueAvg() {
		return subside_valueAvg;
	}

	public void setSubside_valueAvg(Double subside_valueAvg) {
		this.subside_valueAvg = subside_valueAvg;
	}

	public Double getStatic_value() {
		return static_value;
	}

	public void setStatic_value(Double static_value) {
		this.static_value = static_value;
	}

	public void addStaticValue(Double static_value) {
		if(this.static_value == null){
			this.static_value = static_value;
		}else{
			this.static_value += static_value;
		}
	}

	public Double getStatic_valueAvg() {
		return static_valueAvg;
	}

	public void setStatic_valueAvg(Double static_valueAvg) {
		this.static_valueAvg = static_valueAvg;
	}

	public String getSeg_values() {
		return seg_values;
	}

	public void setSeg_values(String seg_values) {
		this.seg_values = seg_values;
	}

}
