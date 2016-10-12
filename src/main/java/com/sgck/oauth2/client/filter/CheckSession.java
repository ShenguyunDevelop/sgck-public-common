package com.sgck.oauth2.client.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.sgck.common.log.DSLogger;
import com.sgck.core.amf.Amf3Output;
import com.sgck.core.rpc.client.Stub;
import com.sgck.core.rpc.server.InvokeError;
import com.sgck.oauth2.client.OAuthConstant;
import com.sgck.oauth2.client.OAuthPath;
import com.sgck.oauth2.client.config.OAuthConfig;
import com.sgck.oauth2.client.service.UserService;
import com.sgck.oauth2.client.utils.CookiesUtil;
import com.sgck.oauth2.client.utils.HttpRequestUtil;
import com.sgck.oauth2.client.utils.SessionUtil;

import flex.messaging.io.amf.ASObject;
import net.sf.json.JSONObject;

/**
 * 拦截每次请求，对请求进行sid验证及其他处理
 * 
 * @author xiaolei
 *
 */
@WebFilter(filterName = "SgckCheckSeesion", urlPatterns = "/*")
public class CheckSession implements Filter {
	public static final int NO_PERMISSIONS_CODE = 520;
	private int HEAD_SIZE = 32;
	static final byte PACKET_CMD_BREAK = 0; // 0 断开连接
	static final byte PACKET_CMD_RPC = 1; // 1 远程调用请求�?
	static final byte PACKET_CMD_RET = 2; // 2 远程调用返回�?
	static final byte PACKET_CMD_EVENT = 3; // 3 事件通知
	static final byte PACKET_CMD_MESSAGE = 4; // 4 实时消息
	static final byte TAG_HAS_CLIENT_INFO = 0X01; // 包含 clientInfo
	// private static final boolean isDebug = true;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//如果不开启oath认证则不过滤
        if(!OAuthConfig.getInstance().getIsOpen()){
        	chain.doFilter(request, response);
			return;
        }
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String serPath = req.getServletPath();
		if (Strings.isNullOrEmpty(serPath)) {
			serPath = "/";
		}
		try {
			// 检测可放行的地址，不予过滤处理
			if (isMappingIgnorePath(serPath)) {
				chain.doFilter(request, response);
				return;
			}

			// 验证标识
			boolean isValid = false;
			Long cDateTime = (new Date()).getTime();
			// 获取上一次验证sid的时间及记录的sid 由于登陆后跳转路径变化sessionID不一致 session无效
			// String cktimeSession =
			// String.valueOf(req.getSession().getAttribute(OAuthConstant.LAST_CHECK_TIME_NAME));
			// String sidSession =
			// String.valueOf(req.getSession().getAttribute(OAuthConstant.SID_NAME));
			Cookie sidck = CookiesUtil.getSidCookies(req);
			String cktimeSession = String.valueOf(req.getSession().getAttribute(OAuthConstant.LAST_CHECK_TIME_NAME));
			if (null != sidck && !sidck.getValue().equals("")) {
				String checkSid = sidck.getValue();
				// 如果上一次验证时间为空、间隔时间超过OAuthConstant.CHECK_SID_INTERVAL或session中的sid与cookie中的sid不匹配，则再次请求验证sid
				if (isNeedCheckSession(cktimeSession, cDateTime)) {
					isValid = SessionUtil.checkSession(checkSid, req);
					if (!isValid) {
						// 如果是记住用户自动登录则用token再次获取用户信息
						if (checkIsReMember(req)) {
							// 通过token再次获取到新的sid及用户信息
							Cookie tokck = CookiesUtil.getCookiesByName(req, OAuthConstant.TOKEN_NAME);
							JSONObject userObj = UserService.getInstance().getUserInfoByToken(tokck.getValue(),
									request);
							if (null != userObj && !userObj.containsKey("error") && userObj.containsKey("sid")) {
								checkSid = userObj.getString("sid");
								CookiesUtil.setCookies(res, OAuthConstant.SID_NAME, userObj.getString("sid"), "/",
										OAuthConstant.COOKIES_EXPIRE);
								isValid = true;
							}
						}
					}

					if (isValid) {
						req.getSession().setAttribute(OAuthConstant.LAST_CHECK_TIME_NAME, String.valueOf(cDateTime));
					}

				} else {
					isValid = true;
				}
			}

			if (!isValid) {
				// 验证无效则跳转到登录页面
				if (isMappingInterface(serPath)) {
					writeReturnAmf(req, res);
				} else {
					sendRedrectByGrantType(req, res);
				}
			} else {
				chain.doFilter(request, response);
			}

		} catch (Exception e) {
			try {
				if (isMappingInterface(serPath)) {
					writeReturnAmf(req, res);
				} else {
					sendRedrectByGrantType(req, res);
				}
			} catch (Exception e1) {

			}
		}

	}

	private boolean checkIsReMember(HttpServletRequest req) throws Exception {
		Cookie rck = CookiesUtil.getCookiesByName(req, OAuthConstant.REMEMBER_USER_NAME);
		Cookie tokck = CookiesUtil.getCookiesByName(req, OAuthConstant.TOKEN_NAME);
		return null != rck && rck.getValue().equals("1") && null != tokck && !tokck.getValue().equals("");
	}

	private boolean isNeedCheckSession(String cktimeSession, long cDateTime) {
		return Strings.isNullOrEmpty(cktimeSession) || "null".equals(cktimeSession)
				|| (cDateTime - Long.parseLong(cktimeSession)) > OAuthConstant.CHECK_SID_INTERVAL;
	}

	private boolean isMappingIgnorePath(String serPath) throws Exception {
		// 检测可放行的地址，不予过滤处理
		ArrayList<String> ifilter = OAuthConfig.getInstance().getIgnorePath();
		if (null != ifilter && !ifilter.isEmpty()) {
			for (String fi : ifilter) {
				// 如果开头匹配则放行
				if (HttpRequestUtil.isPath(serPath, fi)) {
					return true;
				}
			}
		}
		return false;

	}

	private boolean isMappingInterface(String serPath) throws Exception {
		// 检测可放行的地址，不予过滤处理
		ArrayList<String> ifilter = OAuthConfig.getInstance().getInterfacePath();
		if (null != ifilter && !ifilter.isEmpty()) {
			for (String fi : ifilter) {
				// 如果开头匹配则放行
				if (HttpRequestUtil.isPath(serPath, fi)) {
					return true;
				}
			}
		}
		return false;

	}

	private void sendRedrectByGrantType(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("text/html; charset=utf-8");
		String paths = HttpRequestUtil.getUrlWithoutServlet(req);
		res.sendRedirect(paths + OAuthPath.LOGOUT_FROM_AUTHSERVER_URI);
		return;
	}

	private void writeReturnAmf(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ArrayList retObj = new ArrayList();
		InvokeError errorObj = new InvokeError();
		errorObj.setCode(NO_PERMISSIONS_CODE);
		errorObj.setWhat("not login!");
		retObj.add(errorObj);
		writeReturn(res.getOutputStream(), retObj);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("com.sgck.oauth2.client.filter.CheckSession has been initialized!");
	}

	public void writeReturn(OutputStream respWriter, Object ret) {
		byte tags[] = new byte[1];
		tags[0] = TAG_HAS_CLIENT_INFO;
		try {
			// 产生包体 -> respByteStream
			ByteArrayOutputStream respByteStream = new ByteArrayOutputStream();
			Amf3Output amfWriter = new Amf3Output();
			amfWriter.setOutputStream(respByteStream);
			if (tags[0] > 0) {// 如果不需要客户端信息，则不用返回 clientInfo
				amfWriter.writeObject(new ASObject());
				// amfWriter.writeObject(null);
			}
			amfWriter.writeObject(ret);
			amfWriter.flush();

			// 构�?协议包头
			byte[] respHeader = Stub.GetRpcHeader((int) respByteStream.size() + HEAD_SIZE, 1, PACKET_CMD_RET, tags);
			// 输出 -> response
			// ServletOutputStream respWriter = response.getOutputStream();
			respWriter.write(respHeader, 0, HEAD_SIZE);
			respWriter.write(respByteStream.toByteArray(), 0, (int) respByteStream.size());
			respWriter.flush();
			respWriter.close();
		} catch (Exception e) {
			DSLogger.error("handleError exception:", e);
		}
	}

}
