package com.sgck.oauth2.client.vo;

import java.io.Serializable;

/**
 * 应用SDK配置，需在应用启动时被初始化
 * @author xiaolei
 *
 */
public class ClientOAuthConfig implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private String clientId;
	private String loginPath;
	
	
	public void setClientId(String clientId){
		this.clientId = clientId;
	}
	
	public String getClientId(){
		return this.clientId;
	}
	
	public void setLoginPath(String loginPath){
		this.loginPath = loginPath;
	}
	
	public String getLoginPath(){
		return this.loginPath;
	}

}
