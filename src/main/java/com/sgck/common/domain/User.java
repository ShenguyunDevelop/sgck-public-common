package com.sgck.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import flex.messaging.io.amf.ASObject;

public class User implements Serializable
{
	private Integer id;//用户id
	private String name;//用户名
	private String password;//密码
	private String username;//姓名
	private Integer roleid;//关联的角色Id
	private String phonenumber;//手机
	private String landlinephone;//固定电话
	private String company;//单位
	private String post;//职位job
	private String email;//email邮箱
	private String wechatid;//微信账号
	private String remark;//备注
	private Date createtime;//用户创建时间
	private Date logintime;//最后登录时间
	private String configs;//扩展参数，以json/XML保存
	private String headimgurl;//微信头像
	private String loginip;//登录ip
	private Integer logincount;//登录次数
	private String extra;//保留扩展
	private String actions; //组织结构权限
	private Integer rolelevel;//角色级别
	private String strphoneinfo;//存储电话号码，及电话号码对应的设置信息，以xml格式存储
	private String userPushConfig;//用户推送配置
	private Date deadline;
	private ASObject phoneinfo;//用户电话及对应的设置信息
	private String department;//部门
	
	private Integer monthLogin;//当月登录次数
	private Integer companytype;//单位类型;0表示导航树单位,否则是手动输入
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public Integer getRoleid()
	{
		return roleid;
	}
	public void setRoleid(Integer roleid)
	{
		this.roleid = roleid;
	}
	public String getPhonenumber()
	{
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}
	public String getLandlinephone()
	{
		return landlinephone;
	}
	public void setLandlinephone(String landlinephone)
	{
		this.landlinephone = landlinephone;
	}
	public String getCompany()
	{
		return company;
	}
	public void setCompany(String company)
	{
		this.company = company;
	}
	public String getPost()
	{
		return post;
	}
	public void setPost(String post)
	{
		this.post = post;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getWechatid()
	{
		return wechatid;
	}
	public void setWechatid(String wechatid)
	{
		this.wechatid = wechatid;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public Date getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
	public Date getLogintime()
	{
		return logintime;
	}
	public void setLogintime(Date logintime)
	{
		this.logintime = logintime;
	}
	public String getConfigs()
	{
		return configs;
	}
	public void setConfigs(String configs)
	{
		this.configs = configs;
	}
	public String getHeadimgurl()
	{
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}
	public String getLoginip()
	{
		return loginip;
	}
	public void setLoginip(String loginip)
	{
		this.loginip = loginip;
	}
	public Integer getLogincount()
	{
		return logincount;
	}
	public void setLogincount(Integer logincount)
	{
		this.logincount = logincount;
	}
	public String getExtra()
	{
		return extra;
	}
	public void setExtra(String extra)
	{
		this.extra = extra;
	}
	public String getActions()
	{
		return actions;
	}
	public void setActions(String actions)
	{
		this.actions = actions;
	}
	public Integer getRolelevel()
	{
		return rolelevel;
	}
	public void setRolelevel(Integer rolelevel)
	{
		this.rolelevel = rolelevel;
	}
	public String getStrphoneinfo()
	{
		return strphoneinfo;
	}
	public void setStrphoneinfo(String strphoneinfo)
	{
		this.strphoneinfo = strphoneinfo;
	}
	public String getUserPushConfig()
	{
		return userPushConfig;
	}
	public void setUserPushConfig(String userPushConfig)
	{
		this.userPushConfig = userPushConfig;
	}
	public Date getDeadline()
	{
		return deadline;
	}
	public void setDeadline(Date deadline)
	{
		this.deadline = deadline;
	}
	public ASObject getPhoneinfo()
	{
		return phoneinfo;
	}
	public void setPhoneinfo(ASObject phoneinfo)
	{
		this.phoneinfo = phoneinfo;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public Integer getMonthLogin()
	{
		return monthLogin;
	}
	public void setMonthLogin(Integer monthLogin)
	{
		this.monthLogin = monthLogin;
	}
	public Integer getCompanytype()
	{
		return companytype;
	}
	public void setCompanytype(Integer companytype)
	{
		this.companytype = companytype;
	}
	
	
}
