package com.sgck.oauth2.client.config;

import java.io.Serializable;
import java.util.ArrayList;

import com.sgck.oauth2.client.GrantType;
import com.sgck.oauth2.client.OAuthPath;

public class OAuthConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private static OAuthConfig instance = null;

	/************************************************
	 ******************** 必须设置的参数*****************
	 ************************************************/

	// 应用自身的clientid（必须初始化）
	private String clientId = "sg8k";
	// 验证模式，password或authorization_code（必须初始化）
	private String grantType = GrantType.PASSWORD;
	// OAuth认证服务器服务名称（必须初始化）
	private String oauthServiceName = "authserver";
	// OAuth认证服务器端口（必须初始化）
	private int oauthSrverPort = 80;
	// 自身应用服务名称
	private String seftServiceName = null;

	/************************************************
	 ***************** 某场景下必须设置参数************
	 ************************************************/

	// 应用自身的登录页面地址（password模式下必须初始化）
	private String loginPath = "login.jsp";
	// 授权码验证模式，返回地址（授权码模式下必须初始化）
	private String redirectUri = "test.jsp";

	/************************************************
	 ************************************************
	 ************************************************/

	// 应用自身的secret
	private String clientSecret;
	// sid的远程验证接口
	private String sidCheck;
	// OAuth认证服务器统一登录界面地址
	private String oauthLoginUrl;
	// OAuth认证服务器登录接口(password验证模式专用)
	private String oauthPasswordLogin;
	// OAuth认证服务器统一注销接口
	private String oauthLogout;
	// OAuth认证服务器token处理接口
	private String oauthToken;
	// OAuth认证服务器auth_code处理接口
	private String oauthAuthorize;
	// OAuth认证服务器用户信息获取接口
	private String oauthMe;
	// OAuth认证服务器删除缓存接口
	private String oauthDelCache;
	// OAuth认证服务器新增用户登录记录接口
	private String oauthLoginRecord;
	// OAuth认证服务器用户信息获取接口（通过SID获取）
	private String oauthUser;
	// OAuth认证服务器域名或IP（必须初始化）
	private String oauthDomain;
	// 可以放行通过的页面地址
	private ArrayList<String> ignorePath;

	private ArrayList<String> defaultIgnrePath;

	private ArrayList<String> interfacePath;
	//是否开机oath认证
	private boolean isOpen;

	private OAuthConfig(){
		defaultIgnrePath = new ArrayList<String>();
		defaultIgnrePath.add("/" + OAuthPath.IMPORT_FILE);
		defaultIgnrePath.add("/" + OAuthPath.LOGOUT_FROM_AUTHSERVER_URI);
		defaultIgnrePath.add("/" + OAuthPath.REDIRECT_SERVLET_URI);
		defaultIgnrePath.add("/" + OAuthPath.NOT_FILTER_PATH);
		defaultIgnrePath.add("/" + OAuthPath.NOT_FILTER_PATH_9K_FOR_TEST);
		defaultIgnrePath.add("/js/*");
		ignorePath = new ArrayList<String>();
		ignorePath.addAll(defaultIgnrePath);
		interfacePath = new ArrayList<String>();
		interfacePath.add("/" + OAuthPath.INTERFACE_SYSMON);
		interfacePath.add("/" + OAuthPath.INTERFACE_SG9K);

	}

	public static synchronized OAuthConfig getInstance() {
		if (null == instance) {
			instance = new OAuthConfig();
		}
		return instance;
	}
	

	public boolean getIsOpen()
	{
		return isOpen;
	}

	public void setIsOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 获取应用ID 默认为一个随机UUID
	 * 
	 * @return
	 */
	public String getClientId() {
		if (null == this.clientId)
			this.clientId = "sgck";
		return this.clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientSecret() {
		if (null == this.clientSecret)
			this.clientSecret = "";
		return this.clientSecret;
	}

	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	/**
	 * 获取应用自身的登录界面地址<br>
	 * 默认登录地址为/login.html
	 * 
	 * @return
	 */
	public String getLoginPath() {
		if (null == this.loginPath)
			this.loginPath = "login.jsp";
		return this.loginPath;
	}

	public void setOauthDomain(String oauthDomain) {
		this.oauthDomain = oauthDomain;
	}

	/**
	 * 获取OAuth认证服务器的域名地址
	 * 
	 * @return
	 */
	public String getOauthDomain() {
		// if (null == this.oauthDomain)
		// this.oauthDomain = "localhost";
		return this.oauthDomain;
	}

	public void setOauthSrverPort(int oauthSrverPort) {
		this.oauthSrverPort = oauthSrverPort;
	}

	/**
	 * 获取认证服务器端口，默认80端口
	 * 
	 * @return
	 */
	public int getOauthSrverPort() {
		if (this.oauthSrverPort <= 0)
			this.oauthSrverPort = 80;
		return this.oauthSrverPort;
	}

	public void setServiceName(String oauthServiceName) {
		this.oauthServiceName = oauthServiceName;
	}

	/**
	 * 获取应用名称
	 * 
	 * @return
	 */
	public String getServiceName() {
		if (null == this.oauthServiceName)
			this.oauthServiceName = "authserver";
		return this.oauthServiceName;
	}

	public void setSidCheck(String sidCheck) {
		this.sidCheck = sidCheck;
	}

	/**
	 * 获取sid验证接口地址
	 * 
	 * @return
	 */
	public String getSidCheck() {
		if (null == this.sidCheck) {
			this.sidCheck = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/check";
		}
		// if (!this.sidCheck.startsWith(OAuthConstant.OAUTH_HTTP))
		// this.sidCheck = OAuthConstant.OAUTH_HTTP + this.sidCheck;
		return this.sidCheck;

	}

	public void setOauthLoginUrl(String oauthLoginUrl) {
		this.oauthLoginUrl = oauthLoginUrl;
	}

	/**
	 * 获取认证服务器的统一登录地址
	 * 
	 * @return
	 */
	public String getOauthLoginUrl() {
		if (null == this.oauthLoginUrl) {
			this.oauthLoginUrl = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/login.jsp?title=123456";
		}
		// if (!this.oauthLoginUrl.startsWith(OAuthConstant.OAUTH_HTTP))
		// this.oauthLoginUrl = OAuthConstant.OAUTH_HTTP + this.oauthLoginUrl;
		return this.oauthLoginUrl;
	}

	public void setOauthPasswordLogin(String oauthPasswordLogin) {
		this.oauthPasswordLogin = oauthPasswordLogin;
	}

	/**
	 * 获取认证服务器的登录接口(PASSWORD验证模式)
	 * 
	 * @return
	 */
	public String getOauthPasswordLogin() {
		if (null == this.oauthPasswordLogin) {
			this.oauthPasswordLogin = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/oauth/token";
		}

		// if (!this.oauthPasswordLogin.startsWith(OAuthConstant.OAUTH_HTTP))
		// this.oauthPasswordLogin = OAuthConstant.OAUTH_HTTP +
		// this.oauthPasswordLogin;

		return this.oauthPasswordLogin;
	}

	public void setOauthLogout(String oauthLogout) {
		this.oauthLogout = oauthLogout;
	}

	/**
	 * 获取认证服务器的注销接口
	 * 
	 * @return
	 */
	public String getOauthLogout() {
		if (null == this.oauthLogout) {
			this.oauthLogout = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/logout.do";
		}
		// if (!this.oauthLogout.startsWith(OAuthConstant.OAUTH_HTTP))
		// this.oauthLogout = OAuthConstant.OAUTH_HTTP + this.oauthLogout;
		return this.oauthLogout;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	/**
	 * 获取认证服务器的token处理接口
	 * 
	 * @return
	 */
	public String getOauthToken() {
		/*
		 * if (null == this.oauthToken) { if (null != this.oauthDomain &&
		 * this.oauthSrverPort > 0) this.oauthToken = this.oauthDomain + ":" +
		 * String.valueOf(this.oauthSrverPort) + ((null !=
		 * this.oauthServiceName) ? ("/" + oauthServiceName) : "") +
		 * "/oauth/token"; } if
		 * (!this.oauthToken.startsWith(OAuthConstant.OAUTH_HTTP))
		 * this.oauthToken = OAuthConstant.OAUTH_HTTP + this.oauthToken;
		 */
		return this.oauthToken;
	}

	public void setOauthAuthorize(String oauthAuthorize) {
		this.oauthAuthorize = oauthAuthorize;
	}

	/**
	 * 获取认证服务器的验证入口
	 * 
	 * @return
	 */
	public String getOauthAuthorize() {
		if (null == this.oauthAuthorize) {
			this.oauthAuthorize = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/oauth/authorize";
		}
		// if (!this.oauthAuthorize.startsWith(OAuthConstant.OAUTH_HTTP))
		// this.oauthAuthorize = OAuthConstant.OAUTH_HTTP + this.oauthAuthorize;
		return this.oauthAuthorize;
	}

	public void setOauthMe(String oauthMe) {
		this.oauthMe = oauthMe;
	}

	/**
	 * 获取认证服务器的用户信息获取接口
	 * 
	 * @return
	 */
	public String getOauthMe() {
		if (null == this.oauthMe) {
			this.oauthMe = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/userinfo";
		}

		return this.oauthMe;
	}

	/**
	 * 获取认证服务器新增用户登录记录
	 */
	public String getOauthLoginRecord() {
		if (null == this.oauthLoginRecord) {
			this.oauthLoginRecord = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/addUserLoginRecord";
		}

		return this.oauthLoginRecord;
	}

	/**
	 * 获取认证服务器的删除redis缓存接口
	 * 
	 * @return
	 */
	public String getOauthDelCache() {
		if (null == this.oauthDelCache) {
			this.oauthDelCache = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/delCache";
		}
		return this.oauthDelCache;
	}

	public void setOauthUser(String oauthUser) {
		this.oauthUser = oauthUser;
	}

	/**
	 * 获取认证服务器的用户信息获取接口（通过SID获取用户信息）
	 * 
	 * @return
	 */
	public String getOauthUser() {
		if (null == this.oauthUser) {
			this.oauthUser = ((this.oauthSrverPort > 0) ? (":" + String.valueOf(this.oauthSrverPort)) : "")
					+ ((null != this.oauthServiceName) ? ("/" + oauthServiceName) : "") + "/userinfo";
		}
		// if (!this.oauthUser.startsWith(OAuthConstant.OAUTH_HTTP))
		// this.oauthUser = OAuthConstant.OAUTH_HTTP + this.oauthUser;
		return this.oauthUser;
	}

	public void setIgnorePath(ArrayList<String> ignorePath) {
		this.ignorePath = ignorePath;
	}

	/**
	 * ] 获取允许通过的页面地址数组，注意每一个地址是以'/'开头的相对路径
	 * 如：'/test'表示http://domain/app/test下的所有页面均不用通过滤即直接显示
	 * 
	 * @return
	 */
	public ArrayList<String> getIgnorePath() {

		if (null == this.ignorePath)
			this.ignorePath = new ArrayList<String>();
		if (!this.ignorePath.containsAll(defaultIgnrePath)) {
			this.ignorePath.addAll(defaultIgnrePath);
		}
		// this.addIgnorePath("/login.*").addIgnorePath("/webjars/*").addIgnorePath("/login").addIgnorePath("/"
		// + OAuthPath.REDIRECT_SERVLET_URI
		// ).addIgnorePath("/sgck_import_file*").addIgnorePath("/sgck_logout_from_authserver_handle")
		// ;
		return this.ignorePath;
	}

	public ArrayList<String> getInterfacePath() {
		return interfacePath;
	}

	public void setInterfacePath(ArrayList<String> interfacePath) {
		this.interfacePath = interfacePath;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	/**
	 * 获取对应应用的验证模式
	 * 
	 * @return
	 */
	public String getGrantType() {
		if (null == this.grantType)
			this.grantType = GrantType.PASSWORD;
		return this.grantType;
	}

	public void setSeftServiceName(String seftServiceName) {
		this.seftServiceName = seftServiceName;
	}

	public String getSeftServiceName() {
		return this.seftServiceName;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	/**
	 * 获取授权码模式下的回调地址
	 * 
	 * @return
	 */
	public String getRedirectUri() {
		return this.redirectUri;
	}

	public void addIgnorePath(String path) {
		if (null == this.ignorePath)
			this.ignorePath = new ArrayList<String>();
		this.ignorePath.add(path);
	}

	/**
	 * 清除放行地址
	 * 
	 * @return
	 */
	public void clearIgnorePath() {
		if (null != this.ignorePath && this.ignorePath.size() > 0) {
			this.ignorePath.clear();
			this.ignorePath.addAll(defaultIgnrePath);
		}
	}

}
