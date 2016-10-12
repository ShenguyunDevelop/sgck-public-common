package com.sgck.common.domain;

import java.io.Serializable;

public class Organization implements Serializable{
	private Integer id;//组织ID
	private String text; //组织名称
	private Integer parentId; // 上级组织ID，顶级上级组织为null
	private Integer type;//组织类型
	private String extraInfo; //组织对应的配置，json格式
	private String configInfo;//扩展属性
	private Integer displayOrder;//排序
	private String name;//组织名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	public String getConfigInfo() {
		return configInfo;
	}
	public void setConfigInfo(String configInfo) {
		this.configInfo = configInfo;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
