package com.sgck.common.domain;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class WxMessageContent implements Serializable
{

	private String templateId;//模板ID
	private String first;//标题
	private String remark;//支持换行
	private JSONObject content;//{keyword1:xx,keyword2:xx} 
	
	public String getTemplateId()
	{
		return templateId;
	}
	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}
	public String getFirst()
	{
		return first;
	}
	public void setFirst(String first)
	{
		this.first = first;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public JSONObject getContent()
	{
		return content;
	}
	public void setContent(JSONObject content)
	{
		this.content = content;
	}
	
	
}
