package com.sgck.common.extdatasource.domain;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class ReadConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dataSourceId;
	private String interfaceId;
	private JSONObject dataSourceConfig;
	private JSONObject interfaceConfig;
	private Integer interfaceType;

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public JSONObject getDataSourceConfig() {
		return dataSourceConfig;
	}

	public void setDataSourceConfig(JSONObject dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}

	public JSONObject getInterfaceConfig() {
		return interfaceConfig;
	}

	public void setInterfaceConfig(JSONObject interfaceConfig) {
		this.interfaceConfig = interfaceConfig;
	}

	public Integer getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(Integer interfaceType) {
		this.interfaceType = interfaceType;
	}

}
