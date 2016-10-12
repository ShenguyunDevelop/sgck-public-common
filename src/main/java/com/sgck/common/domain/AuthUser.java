package com.sgck.common.domain;

import java.util.Date;

public class AuthUser 
{
	private int id;//用户id
	private int roleid;//角色id
	private String name;//用户名称
	private String username;//用户姓名
	private String actions;//用户能查看的组织id
	private String phonenumber;//电话号码
	private String phoneInfo;//用户电话号码及配置信息
	private Date createTime;
	private Date deadLine;
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public int getRoleid() 
	{
		return roleid;
	}
	public void setRoleid(int roleid) 
	{
		this.roleid = roleid;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
	public String getActions() 
	{
		return actions;
	}
	public void setActions(String actions) 
	{
		this.actions = actions;
	}
	public String getPhonenumber() 
	{
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) 
	{
		this.phonenumber = phonenumber;
	}
	public String getPhoneInfo() {
		return phoneInfo;
	}
	public void setPhoneInfo(String phoneInfo) {
		this.phoneInfo = phoneInfo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}
}
