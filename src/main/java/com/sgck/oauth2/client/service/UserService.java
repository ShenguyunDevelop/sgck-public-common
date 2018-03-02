package com.sgck.oauth2.client.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.google.common.base.Strings;
import com.sgck.oauth2.client.GrantType;
import com.sgck.oauth2.client.OAuthConstant;
import com.sgck.oauth2.client.OAuthPath;
import com.sgck.oauth2.client.config.OAuthConfig;
import com.sgck.oauth2.client.utils.CookiesUtil;
import com.sgck.oauth2.client.utils.HttpRequestUtil;

import net.sf.json.JSONObject;

public class UserService {

	private static UserService instance = null;

	public UserService() {
	}

	public static UserService getInstance() {
		if (null == instance)
			instance = new UserService();
		return instance;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUserInfo(HttpServletRequest request) throws Exception {

		String sid = String.valueOf(request.getSession().getAttribute(OAuthConstant.SID_NAME));
		if (null == sid || sid.equals("") || sid.equals("null")) {
			sid = CookiesUtil.getSid(request);
		}
		if (null != sid && !sid.equals("") || sid.equals("null")) {
			return getUserInfoBySid(sid, request);
		} else {
			String tk = CookiesUtil.getCookiesByName(request, OAuthConstant.TOKEN_NAME).getValue();
			if (null != tk && !tk.equals("")) {
				return getUserInfoByToken(tk, request);
			}
		}

		return null;

	}

	/**
	 * 通过sid远程获取用户信息
	 * 
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUserInfoBySid(String sid, HttpServletRequest req) throws Exception {

		String meresult = HttpRequestUtil.doGet(getReuqestServerName(req)
				+ OAuthConfig.getInstance().getOauthUser() + "?sid=" + sid);
		if (null != meresult) {
			return JSONObject.fromObject(meresult);
		}
		return null;

		/*
		 * Map<String, String> par = new HashMap<String, String>();
		 * par.put("sid", sid); return
		 * HttpRequestUtil.getHttpResponse(OAuthConfig.getInstance().
		 * getOauthUser(), par, null, "POST");
		 */
	}

	/**
	 * 根据token获取用户信息
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUserInfoByToken(String token, ServletRequest req) throws Exception {
		String toresult = HttpRequestUtil.doGet(getReuqestServerName((HttpServletRequest)req)
				+ OAuthConfig.getInstance().getOauthUser() + "?access_token=" + token);
		if (null != toresult) {
			return JSONObject.fromObject(toresult);
		}
		return null;
	}

	/**
	 * 用户登录(password验证模式)
	 * 
	 * @param req
	 *            请求对象
	 * @param res
	 *            输出对象
	 * @param isRemember
	 *            是否记住用户信息自动登录
	 * @return
	 * @throws Exception
	 */
	public String Login(HttpServletRequest req, HttpServletResponse res, boolean isRemember) throws Exception {

		String clientId = OAuthConfig.getInstance().getClientId();
		String clientSecret = OAuthConfig.getInstance().getClientSecret();

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		String paramStr = "grant_type=" + GrantType.PASSWORD + "&client_id=" + clientId + "&username=" + username
				+ "&password=" + password;

		JSONObject tokenResult = null;
		String loginUrl = getReuqestServerName(req) + OAuthConfig.getInstance().getOauthPasswordLogin(req);
		System.out.println("调用授权接口rul：" + loginUrl);
		String postResult = HttpRequestUtil.doPost(loginUrl,paramStr, clientId, clientSecret);
		System.out.println("授权结果：" + postResult);
		if (null != postResult)
			tokenResult = JSONObject.fromObject(postResult);

		String token = null;
		if (null != tokenResult && tokenResult.containsKey("access_token")
				&& null != tokenResult.getString("access_token")) {

			token = tokenResult.getString("access_token");

			String userInfoUrl = getReuqestServerName(req) + OAuthConfig.getInstance().getOauthMe() + "?access_token=" + token;
			System.out.println("调用获取用户信息url：" + userInfoUrl);
			String meresult = HttpRequestUtil.doGet(userInfoUrl);
			System.out.println("获取用户信息结果：" + meresult);
			JSONObject result = null;
			if (null != meresult)
				result = JSONObject.fromObject(meresult);

			if (null != result && !result.containsKey("error") && result.containsKey("sid")) {

				Date cd = new Date();

				// 将sid记录到session
				req.getSession().setAttribute(OAuthConstant.SID_NAME, result.getString("sid"));
				// 将检测sid的时间记录到session
				req.getSession().setAttribute(OAuthConstant.LAST_CHECK_TIME_NAME, String.valueOf(cd.getTime()));

				// 存储sid、isRemember到cookies
				CookiesUtil.setCookies(res, OAuthConstant.SID_NAME, result.getString("sid"), "/",
						OAuthConstant.COOKIES_EXPIRE);
				CookiesUtil.setCookies(res, OAuthConstant.REMEMBER_USER_NAME, (isRemember ? "1" : "0"), "/",
						OAuthConstant.COOKIES_EXPIRE);
				// 如果是记住密码则将token亦存如cookies
				if (isRemember)
					CookiesUtil.setCookies(res, OAuthConstant.TOKEN_NAME, token, "/", OAuthConstant.COOKIES_EXPIRE);

				return result.toString();
			}
		}

		return null;

	}
	
	public String getReuqestServerName(HttpServletRequest req){

		if(OAuthConstant.IS_ONLY_LOCAL){
			return req.getScheme() + "://127.0.0.1" + ":" + OAuthConfig.getInstance().getOauthSrverPort();
		}
		return req.getScheme() + "://" + req.getServerName();
	}
	
	/**
	 * 用户登录（授权码模式）
	 * 
	 * @param authCode
	 *            获取到的授权码
	 * @return
	 * @throws Exception
	 */
	public String login(String authCode, HttpServletRequest req, HttpServletResponse res, boolean isRemember)
			throws Exception {

		String clientId = OAuthConfig.getInstance().getClientId();
		String clientSecret = OAuthConfig.getInstance().getClientSecret();
		String rootPath = HttpRequestUtil.getUrlWithoutServlet(req);
		String ruri = rootPath + OAuthPath.REDIRECT_SERVLET_URI;

		if (null == clientId || null == authCode){
			Logger.getRootLogger().error("请求参数clientId为空或者授权码为空！");
			return null;
		}
			


		String requestUrl = getReuqestServerName(req) + OAuthConfig.getInstance().getOauthPasswordLogin(req);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("grant_type", GrantType.AUTH_CODE);
		params.put("client_id", clientId);
		params.put("code", authCode);
		params.put("redirect_uri", ruri);
		
		Map<String,String> headers = new HashMap<String,String>();
		
		if (null != clientId) {
			String scr = clientSecret = ((null == clientSecret) ? "" : clientSecret);
			headers.put("Authorization", "Basic " + new String(Base64.encodeBase64((clientId + ":" + scr).getBytes())));
			
		}
		
		JSONObject tokenResult = null;
		
		System.out.println("向authserver请求accesstoken:" + requestUrl+",params:" + params.toString());
		
		String postResult = HttpRequestUtil.doGet(requestUrl,params, headers);
		
		System.out.println("请求accesstoken结果:" + postResult);
		
		
		if (null != postResult){
			tokenResult = JSONObject.fromObject(postResult);
		}
			

		String token = null;
		if (null != tokenResult && tokenResult.containsKey("access_token")
				&& null != tokenResult.getString("access_token")) {

			token = tokenResult.getString("access_token");
			
			requestUrl = getReuqestServerName(req) + OAuthConfig.getInstance().getOauthMe() + "?access_token=" + token;
			
			Logger.getRootLogger().info("请求userinfo:" + requestUrl);

			String meresult = HttpRequestUtil.doGet(requestUrl);
			JSONObject result = null;
			if (null != meresult)
				result = JSONObject.fromObject(meresult);
			if (null != result && !result.containsKey("error") && result.containsKey("sid")) {
				// result.put("access_token", token);
				// Date cd = new Date();
				// 因为跳转到sysmonui和sgck_web项目路径不一致导致sessionId不一样，此处session设置没卵用
				// 将sid记录到session
				// req.getSession().setAttribute(OAuthConstant.SID_NAME,
				// result.getString("sid"));
				// 将检测sid的时间记录到session
				// req.getSession().setAttribute(OAuthConstant.LAST_CHECK_TIME_NAME,
				// String.valueOf(cd.getTime()));
				// req.getSession().setAttribute(OAuthConstant.TOKEN_NAME,
				// token);

				CookiesUtil.setCookies(res, OAuthConstant.REMEMBER_USER_NAME, (isRemember ? "1" : "0"), "/",
						OAuthConstant.COOKIES_EXPIRE);
				CookiesUtil.setCookies(res, OAuthConstant.SID_NAME, result.getString("sid"), "/",
						OAuthConstant.COOKIES_EXPIRE);

				if (isRemember) {
					CookiesUtil.setCookies(res, OAuthConstant.TOKEN_NAME, token, "/", OAuthConstant.COOKIES_EXPIRE);
				}

				return result.toString();
			}

		}

		return null;
	}

	/**
	 * 注销用户，清除所有相关Cookeis及session
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public String logout(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 将sid失效

		Cookie sck = CookiesUtil.getSidCookies(req);
		Cookie tck = CookiesUtil.getCookiesByName(req, OAuthConstant.TOKEN_NAME);
		// Cookie remck = CookiesUtil.getCookiesByName(req,
		// OAuthConstant.REMEMBER_USER_NAME);

		if (null != tck)
			CookiesUtil.removeCookies(res, tck.getName(), tck.getPath());
		if (null != sck) {
			// 去除缓存
			String sid = sck.getValue();
			if (!Strings.isNullOrEmpty(sid)) {
				String delCacheUrl = getReuqestServerName(req)
						+ OAuthConfig.getInstance().getOauthDelCache() + "?sid=" + sck.getValue();
				System.out.println("清除缓存url：" + delCacheUrl);
				HttpRequestUtil.doGet(delCacheUrl);
			}
			CookiesUtil.removeCookies(res, sck.getName(), sck.getPath());
		}

		// if (null != remck)
		// CookiesUtil.removeCookies(res, remck.getName(), remck.getPath());

		req.getSession().setAttribute(OAuthConstant.LAST_CHECK_TIME_NAME, null);
		req.getSession().setAttribute(OAuthConstant.SID_NAME, null);

		System.out.println("清除缓存完成");
		return "{\"success\":1}";

	}

}
