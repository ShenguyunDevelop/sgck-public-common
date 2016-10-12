package com.sgck.common.sg9k.domain;

import java.io.Serializable;

//暂时解决用sync组件传输时Long字段不正确问题  ---  将Long改为String来传输
public class PosEnvelopAlarmForTrans implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5436678425275534879L;
	
	private String gpid;
	private String halarmConfig;
	private byte[] halarmEnvelop;
	private String hhalarmConfig;
	private byte[] hhalarmEnvelop;
	private String configInUse;
	private Integer configVersion;

	public Integer getConfigVersion() {
		return configVersion;
	}

	public void setConfigVersion(Integer configVersion) {
		this.configVersion = configVersion;
	}

	public String getGpid() {
		return gpid;
	}

	public void setGpid(String gpid) {
		this.gpid = gpid;
	}

	public String getHalarmConfig() {
		return halarmConfig;
	}

	public void setHalarmConfig(String halarmConfig) {
		this.halarmConfig = halarmConfig;
	}

	public byte[] getHalarmEnvelop() {
		return halarmEnvelop;
	}

	public void setHalarmEnvelop(byte[] halarmEnvelop) {
		this.halarmEnvelop = halarmEnvelop;
	}

	public String getHhalarmConfig() {
		return hhalarmConfig;
	}

	public void setHhalarmConfig(String hhalarmConfig) {
		this.hhalarmConfig = hhalarmConfig;
	}

	public byte[] getHhalarmEnvelop() {
		return hhalarmEnvelop;
	}

	public void setHhalarmEnvelop(byte[] hhalarmEnvelop) {
		this.hhalarmEnvelop = hhalarmEnvelop;
	}

	public String getConfigInUse() {
		return configInUse;
	}

	public void setConfigInUse(String configInUse) {
		this.configInUse = configInUse;
	}

}
