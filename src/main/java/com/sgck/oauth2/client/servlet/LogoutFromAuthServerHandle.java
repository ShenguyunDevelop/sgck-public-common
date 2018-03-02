package com.sgck.oauth2.client.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.sgck.oauth2.client.GrantType;
import com.sgck.oauth2.client.OAuthPath;
import com.sgck.oauth2.client.config.OAuthConfig;
import com.sgck.oauth2.client.service.UserService;
import com.sgck.oauth2.client.utils.CookiesUtil;
import com.sgck.oauth2.client.utils.HttpRequestUtil;
import com.sgck.oauth2.client.utils.SessionUtil;

@WebServlet("/" + OAuthPath.LOGOUT_FROM_AUTHSERVER_URI)
public class LogoutFromAuthServerHandle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String isForceClearCache = request.getParameter("isForceClearCache");
			if (Strings.isNullOrEmpty(isForceClearCache) || !isForceClearCache.equals("true")) {
				Cookie sck = CookiesUtil.getSidCookies(request);
				if (null != sck) {
					String sid = sck.getValue();
					if (!Strings.isNullOrEmpty(sid) && SessionUtil.checkSession(sid, request)) {
						// 执行跳转到首页
						String paths = HttpRequestUtil.getRootUrlWithoutServlet(request);
						response.sendRedirect(paths + OAuthConfig.getInstance().getRedirectUri());
						return;
					}
					// 执行下面的代码（登录跳转）
				}
			}

			String jumpPath = request.getParameter("redirect_uri");
			PrintWriter pw = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			String port = (request.getServerPort() > 0) ? (":" + String.valueOf(request.getServerPort())) : "";
			
			if (null == jumpPath || jumpPath.equals("")) {
				if (OAuthConfig.getInstance().getGrantType().equals(GrantType.PASSWORD)) {
					jumpPath = OAuthConfig.getInstance().getLoginPath();
				} else if (OAuthConfig.getInstance().getGrantType().equals(GrantType.AUTH_CODE)) {
					String paths = HttpRequestUtil.getUrlWithoutServlet(request);
					jumpPath = request.getScheme() + "://" + request.getServerName() + port
							+ OAuthConfig.getInstance().getOauthAuthorize() + "?response_type=code&client_id="
							+ OAuthConfig.getInstance().getClientId() + "&redirect_uri=" + paths
							+ OAuthPath.REDIRECT_SERVLET_URI;
				} else {
					jumpPath = request.getScheme() + "://" + request.getServerName() + port + OAuthConfig.getInstance().getOauthLoginUrl();
				}
			}
			if (jumpPath.startsWith("/"))
				jumpPath = jumpPath.substring(1, jumpPath.length());
			System.out.println("退出跳转url：" + jumpPath);
			// 清除session及cookies
			UserService.getInstance().logout(request, response);

			// pw.println("请稍后...");
			pw.println("<script type=\"text/javascript\" src=\"" + OAuthPath.IMPORT_FILE
					+ "?filename=jquery.js\"></script>");
			pw.println("<script> if(window != top) {top.location.href = location.href;}");
			pw.println("var GrantType=\"" + OAuthConfig.getInstance().getGrantType() + "\";");

			pw.println("$.ajax({");
			pw.println("	type : \"get\",");
			pw.println("	async : false,");
			// 从authserver端退出登录
			pw.println("	url : \"" + request.getScheme() + "://" + request.getServerName() + port
					+ OAuthConfig.getInstance().getOauthLogout() + "\",");
			pw.println("	cache : false,");
			pw.println("	dataType : \"jsonp\",");
			pw.println("	success : function(json){");
			pw.println("		window.location.href = \"" + jumpPath + "\";");
			pw.println("	},");
			pw.println("	error : function(){");
			pw.println("		window.location.href = \"" + jumpPath + "\";");
			pw.println("	}");
			pw.println("});");
			pw.println("</script>");
			pw.flush();
			pw.close();

			// pw.println("<script>document.getElementById('authform').submit();</script><iframe
			// id=\"authiframe\" name=\"authiframe\"></iframe><form
			// id=\"authform\" action=\"" +
			// OAuthConfig.getInstance().getOauthLogout() + "\"
			// target=\"authiframe\"></form>");

		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
		}

	}

	private void checkIsLogined(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cookie sck = CookiesUtil.getSidCookies(req);
		if (null != sck) {
			String sid = sck.getValue();
			if (!Strings.isNullOrEmpty(sid) && SessionUtil.checkSession(sid, req)) {
				// 执行跳转到首页
				String paths = HttpRequestUtil.getRootUrlWithoutServlet(req);
				resp.sendRedirect(paths + OAuthConfig.getInstance().getRedirectUri());
				return;
			}
			// 执行下面的代码（登录跳转）
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
