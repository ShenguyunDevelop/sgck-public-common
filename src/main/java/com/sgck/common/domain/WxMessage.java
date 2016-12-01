package com.sgck.common.domain;

import java.io.Serializable;
import java.util.Date;

//消息类
public class WxMessage implements Serializable{
	private String id;//对应数据的消息ID
	private Date sendTime;	//消息发送时间
	private String sender;	//发送人
	private String weixinId;	//接收人绑定的微信ID
	private String url;//消息详情链接可为空
	private WxMessageContent wxMessageContent;//针对于消息模板的内容
	
	
	public WxMessageContent getWxMessageContent()
	{
		return wxMessageContent;
	}
	public void setWxMessageContent(WxMessageContent wxMessageContent)
	{
		this.wxMessageContent = wxMessageContent;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	public Date getSendTime()
	{
		return sendTime;
	}
	public void setSendTime(Date sendTime)
	{
		this.sendTime = sendTime;
	}
	public String getSender()
	{
		return sender;
	}
	public void setSender(String sender)
	{
		this.sender = sender;
	}
	public String getWeixinId()
	{
		return weixinId;
	}
	public void setWeixinId(String weixinId)
	{
		this.weixinId = weixinId;
	}
	
}
