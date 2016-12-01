package com.sgck.oauth2.client.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgck.oauth2.client.OAuthConstant;

public class CookiesUtil {

	public static boolean checkSid(String sid) {

		return false;
	}

	/**
	 * 获取根域下的sid
	 * @param req request请求
	 * @return
	 * @throws Exception
	 */
	public static Cookie getSidCookies(HttpServletRequest req) throws Exception {
 
		return getCookiesByName( req  , OAuthConstant.SID_NAME);
	}
	
	public static String getSid(HttpServletRequest req) throws Exception{
		Cookie ck = getSidCookies(req);
		if(null != ck)
			return ck.getValue();
		return null;
	}
	
	public static Cookie getCookiesByName(HttpServletRequest req , String cookieName) throws Exception{
		Cookie[] cks = req.getCookies();
		if (null != cks) {
			for (Cookie ck : cks) {
				if (ck.getName().equalsIgnoreCase(cookieName))
					return ck;
			}
		}

		return null;
	}
	
 	
	/**
	 * 保存一个cookies数据
	 * @param res http输出对象
	 * @param name cookies名称
	 * @param value cookies值
	 * @param path 保存路径
	 * @param expire 过期时间，秒
	 */
	public static void setCookies(HttpServletResponse res , String name , String value , String path , int expire){
		Cookie ck = new Cookie(name , value);
		ck.setMaxAge(expire);
		ck.setPath(path);
		res.addCookie(ck);
	}
	
	/**
	 * 删除一个cookies数据
	 * @param res http输出对象
	 * @param name cookies名称
	 * @param path 保存路径
	 */
	public static void removeCookies(HttpServletResponse res , String name , String path){
		Cookie ck = new Cookie(name , "");
		//设置有效期为立即失效
		ck.setMaxAge(0);
		ck.setPath((null == path || path.equals("")?  "/" : path ));
		res.addCookie(ck);
	}

}
