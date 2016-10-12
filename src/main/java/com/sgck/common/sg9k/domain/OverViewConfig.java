package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class OverViewConfig implements Serializable{
	private Integer id;
	
	private Integer type;
	
	private String name;
	
	private String ownerid;
	
	private String overViewConfig;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getOverViewConfig() {
		return overViewConfig;
	}

	public void setOverViewConfig(String overViewConfig) {
		this.overViewConfig = overViewConfig;
	}
	
	
}
