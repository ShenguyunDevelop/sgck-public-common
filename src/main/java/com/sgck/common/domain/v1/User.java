package com.sgck.common.domain.v1;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private Integer id;// 鐢ㄦ埛id
	private String name;// 鐢ㄦ埛鍚�
	private String password;// 瀵嗙爜
	private String fullname;// 濮撳悕
	private Integer roleid;// 鍏宠仈鐨勮鑹睮d
	private String mobilephone;// 鎵嬫満
	private String telephone;// 鍥哄畾鐢佃瘽
	private String unit;// 鍗曚綅
	private String department;// 閮ㄩ棬
	private String job;// 鑱屼綅job
	private String email;// email閭
	private String wechatid;// 寰俊璐﹀彿
	private String remark;// 澶囨敞
	private Date deadtime;// 璐﹀彿鐨勬湁鏁堟椂闂�
	private Date createtime;// 鐢ㄦ埛鍒涘缓鏃堕棿
	private Date loginTime;// 鏈�悗鐧诲綍鏃堕棿
	private String configs;// 鎵╁睍鍙傛暟锛屼互json/XML淇濆瓨
	private String headimgurl;// 寰俊澶村儚
	private String loginip;// 鐧诲綍ip
	private Integer logincount;// 鐧诲綍娆℃暟
	private String extra;// 淇濈暀鎵╁睍
	private String actions; // 缁勭粐缁撴瀯鏉冮檺
	private Integer rolelevel;// 瑙掕壊绾у埆

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDeadtime() {
		return deadtime;
	}

	public void setDeadtime(Date deadtime) {
		this.deadtime = deadtime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getConfigs() {
		return configs;
	}

	public void setConfigs(String configs) {
		this.configs = configs;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public Integer getLogincount() {
		return logincount;
	}

	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public Integer getRolelevel() {
		return rolelevel;
	}

	public void setRolelevel(Integer rolelevel) {
		this.rolelevel = rolelevel;
	}

}
