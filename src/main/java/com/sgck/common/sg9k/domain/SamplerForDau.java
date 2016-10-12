package com.sgck.common.sg9k.domain;

import java.io.Serializable;

import flex.messaging.io.amf.ASObject;

public class SamplerForDau implements Serializable {

	protected boolean isExist = false;
	protected boolean isValid = false;

	protected long lastRequestTime;
	protected int configVersion;
	protected String host;
	protected int hops;

	protected ASObject sampleConfig;
	protected ASObject basicConfig;
	protected String id;
	protected String name;
	protected String type;

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public long getLastRequestTime() {
		return lastRequestTime;
	}

	public void setLastRequestTime(long lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}

	public int getConfigVersion() {
		return configVersion;
	}

	public void setConfigVersion(int configVersion) {
		this.configVersion = configVersion;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getHops() {
		return hops;
	}

	public void setHops(int hops) {
		this.hops = hops;
	}

	public ASObject getSampleConfig() {
		return sampleConfig;
	}

	public void setSampleConfig(ASObject sampleConfig) {
		this.sampleConfig = sampleConfig;
	}

	public ASObject getBasicConfig() {
		return basicConfig;
	}

	public void setBasicConfig(ASObject basicConfig) {
		this.basicConfig = basicConfig;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
