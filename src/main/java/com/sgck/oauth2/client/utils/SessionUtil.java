package com.sgck.oauth2.client.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sgck.oauth2.client.OAuthConstant;
import com.sgck.oauth2.client.config.OAuthConfig;
import com.sgck.oauth2.client.service.UserService;

import net.sf.json.JSONObject;

public class SessionUtil {
	
 
	/**
	 * 检测共享sid是否有效
	 * 
	 * @param sid
	 * @return
	 */
	public static boolean checkSession(String sid , ServletRequest req) {

		if (null == sid)
			return false;

 		String pars = "sid=" + sid;
  
		try {
			
			JSONObject res = null;
			String reqPath =  req.getScheme() + "://" + req.getServerName() + OAuthConfig.getInstance().getSidCheck();
			
			String postResult = HttpRequestUtil.doPost( reqPath , pars, null, null);
			if(null != postResult)
				res = JSONObject.fromObject(postResult);
			
 			if (null != res) {
				int isvalid = res.getInt("success");
				return (isvalid == 1);
			} else
				return false;
		} catch (Exception e) {
			return false;
		}

	}
	
	public static boolean checkSession(HttpServletRequest req , HttpServletResponse res){
		
		try{
			Cookie sck = CookiesUtil.getSidCookies(req);
			if(null == sck)
				return false;
 			String sid = sck.getValue();
			
			if(checkSession( sid , req )){
				
  				JSONObject ujson = UserService.getInstance().getUserInfoBySid( sid , req );
				if(null != ujson && ujson.containsKey("sid")){
   					//CookiesUtil.setCookies(res, OAuthConstant.COOKIES_USERNAME, ujson.getString("name") , "/", OAuthConstant.COOKIES_EXPIRE);
  					return true;
				}
			}
 		}catch(Exception e){
 		}
		
		return false;
 	}
 

}
