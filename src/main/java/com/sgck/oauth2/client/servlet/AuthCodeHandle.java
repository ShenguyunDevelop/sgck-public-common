package com.sgck.oauth2.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;
import com.sgck.oauth2.client.OAuthConstant;
import com.sgck.oauth2.client.OAuthPath;
import com.sgck.oauth2.client.config.OAuthConfig;
import com.sgck.oauth2.client.service.UserService;
import com.sgck.oauth2.client.utils.CookiesUtil;
import com.sgck.oauth2.client.utils.HttpRequestUtil;

import net.sf.json.JSONObject;

@WebServlet("/" + OAuthPath.REDIRECT_SERVLET_URI)
public class AuthCodeHandle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String code = request.getParameter("code");
		String state = request.getParameter("state");

		if (null == code || code.equals("")) {
			//response.getWriter().print("ERROR:Without auth code!");
			//response.getWriter().close();
			Logger.getRootLogger().info("ERROR:Without auth code!");
			response404(request,response);
			return;
		}
		JSONObject json = null;
		try {

			Cookie isrem = CookiesUtil.getCookiesByName(request, OAuthConstant.REMEMBER_USER_NAME);
			String userInfoResult = UserService.getInstance().login(code, request, response,
					(null != isrem && isrem.getValue().equals("1")));
			json  = JSONObject.fromObject(userInfoResult);
			if (null == userInfoResult || !json.containsKey("sid")) {
				//response.getWriter().print("ERROR:Authorization error");
				//response.getWriter().close();
				Logger.getRootLogger().info("ERROR:Authorization error!");
				response404(request,response);
				return;
			} else {
				String sid = json.getString("sid");
				int openId = json.getInt("openid");
				String userName = json.getString("name");
				int isSuperParam = 0;
				//判斷用戶名是否存在
				if(Strings.isNullOrEmpty(userName)){
					//不存在从cookie里面获取
					Cookie  isSuperCookie = CookiesUtil.getCookiesByName(request, OAuthConstant.IS_SUPER);
					if(null != isSuperCookie){
						try{
							isSuperParam = Integer.parseInt(isSuperCookie.getValue());
						}catch(Exception e){
						}
					}
				}
				if(null!=userName && userName.equals("superadmin")){
					isSuperParam = 1;
				}
				addLoginRecord(request,sid,openId);
				
				//存入Cookie
				CookiesUtil.setCookies(response, OAuthConstant.IS_SUPER, isSuperParam+"", "/",
						OAuthConstant.COOKIES_EXPIRE);
				
				String paths = HttpRequestUtil.getRootUrlWithoutServlet(request);
				String redirectUrl = paths + OAuthConfig.getInstance().getRedirectUri() + "?code=" + code + "&sid="
						+ sid + "&isSuper="+isSuperParam +  ((null == state) ? "" : "&state=" + state );
				System.out.println("登录成功，重定向url:" + redirectUrl);
				response.sendRedirect(redirectUrl);
				
			}
		} catch (Exception e) {
			Logger.getRootLogger().error(e.getMessage(),e);
			response404(request,response);
		}finally {
			if(null!=json){
				json = null;
			}
		}

	}

	private void response404(HttpServletRequest request, HttpServletResponse response){
		try {
			//服务器内部错误
			String paths = HttpRequestUtil.getRootUrlWithoutServlet(request);
			response.sendRedirect(paths + OAuthConfig.getInstance().getRedirectUri() + "?error=error");
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				response.getWriter().print(e1.getMessage());
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void addLoginRecord(HttpServletRequest req,String sid,int openId) throws Exception {
		String port = (req.getServerPort() > 0) ? (":" + String.valueOf(req.getServerPort())) : "";
		String loginRecordUrl = req.getScheme() + "://" + req.getServerName() + port
				+ OAuthConfig.getInstance().getOauthLoginRecord() + "?openId=" + openId + "&sid="+sid;
		System.out.println("登录记录url:" + loginRecordUrl);
		HttpRequestUtil.doGet(loginRecordUrl);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
