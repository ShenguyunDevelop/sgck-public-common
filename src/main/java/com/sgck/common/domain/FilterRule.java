package com.sgck.common.domain;

import java.io.Serializable;
import java.util.Date;

public class FilterRule implements Serializable{
	private Integer ruleid;//屏蔽规则id
	private int ruletype;//屏蔽类型：0：机组；1：测点
	private Date createtime;//规则创建时间
	private String orgidlist;//屏蔽对象id列表，以逗号分隔
	private String filtertype;//屏蔽事件类型
	private String rolelist;//屏蔽的角色列表
	private Integer userid;//用户id
	private String phone;//电话号码，为空表示应用于全局的规则
	private int platform;//终端类型，为-1表示应用于全局规则。0：表示适用于短信；1表示适用于app
	private Integer used;//是否启用：0 未启用；1 启用
	private String rulename;//规则名称
	private Integer filterstatus;//屏蔽状态
	private String orgnamelist;//测点机组名列表
	public Integer getRuleid() {
		return ruleid;
	}
	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}
	public int getRuletype() {
		return ruletype;
	}
	public void setRuletype(int ruletype) {
		this.ruletype = ruletype;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getOrgidlist() {
		return orgidlist;
	}
	public void setOrgidlist(String orgidlist) {
		this.orgidlist = orgidlist;
	}
	public String getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(String filtertype) {
		this.filtertype = filtertype;
	}
	public String getRolelist() {
		return rolelist;
	}
	public void setRolelist(String rolelist) {
		this.rolelist = rolelist;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public Integer getUsed() {
		return used;
	}
	public void setUsed(Integer used) {
		this.used = used;
	}
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public Integer getFilterstatus() {
		return filterstatus;
	}
	public void setFilterstatus(Integer filterstatus) {
		this.filterstatus = filterstatus;
	}
	public String getOrgnamelist() {
		return orgnamelist;
	}
	public void setOrgnamelist(String orgnamelist) {
		this.orgnamelist = orgnamelist;
	}
	
}
