package com.sgck.oauth2.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			response.getWriter().print("ERROR:Without auth code!");
			response.getWriter().close();
			return;
		}
		JSONObject json = null;
		try {

			Cookie isrem = CookiesUtil.getCookiesByName(request, OAuthConstant.REMEMBER_USER_NAME);
			String userInfoResult = UserService.getInstance().login(code, request, response,
					(null != isrem && isrem.getValue().equals("1")));
			json  = JSONObject.fromObject(userInfoResult);
			if (null == userInfoResult || !json.containsKey("sid")) {
				response.getWriter().print("ERROR:Authorization error");
				response.getWriter().close();
				return;
			} else {
				String sid = json.getString("sid");
				int openId = json.getInt("openid");
				addLoginRecord(request,sid,openId);
				String paths = HttpRequestUtil.getRootUrlWithoutServlet(request);
				response.sendRedirect(paths + OAuthConfig.getInstance().getRedirectUri() + "?code=" + code + "&sid="
						+ sid + ((null == state) ? "" : "&state=" + state));
			}
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
			response.getWriter().close();
			return;
		}finally {
			if(null!=json){
				json = null;
			}
		}

	}

	private void addLoginRecord(HttpServletRequest req,String sid,int openId) throws Exception {
		HttpRequestUtil.doGet(req.getScheme() + "://" + req.getServerName()
		+ OAuthConfig.getInstance().getOauthLoginRecord() + "?openId=" + openId + "&sid="+sid);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
