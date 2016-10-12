package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class SegsAlarmConfig implements Serializable{
	
	private String gpid;
	
	private String segsConfig ;



	public String getGpid() {
		return gpid;
	}

	public void setGpid(String gpid) {
		this.gpid = gpid;
	}

	public String getSegsConfig() {
		return segsConfig;
	}

	public void setSegsConfig(String segsConfig) {
		this.segsConfig = segsConfig;
	}

	

	
	
}
