package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class DauMachineRelations implements Serializable {
	private static final long serialVersionUID = 8608046829166374444L;
	private String dauId;// 采集器编码
	private String machineId;// 机组编码

	public String getDauId() {
		return dauId;
	}

	public void setDauId(String dauId) {
		this.dauId = dauId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
}
