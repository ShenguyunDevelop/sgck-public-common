package com.sgck.oauth2.client;

public class OAuthConstant {

	/**
	 * sid名称，注意这里并不是指SessionId
	 */
	public final static String SID_NAME = "sgSid";
	/**
	 * token名称
	 */
	public final static String TOKEN_NAME = "sgToken";
	/**
	 * 记录最近一次检测sid的时间毫秒数存储名称
	 */
	public final static String LAST_CHECK_TIME_NAME = "SG_LAST_SESSION_CHECK_TIME";
	/**
	 * 用户名称
	 */
	public final static String USERNAME_NAME = "SG_USERNAME";
	
	public final static String USERID_NAME = "SG_USERID";
	/**
	 * 是否是超级用户
	 */
	public final static String IS_SUPER = "IS_SUPER";
	/**
	 * 是否记住用户信息自动登录
	 */
	public final static String REMEMBER_USER_NAME = "SG_REMEMBER_USER";

	/**
	 * sid的验证请求间隔时间，毫秒；默认1分钟
	 */
	public final static long CHECK_SID_INTERVAL = 60 * 1000;
	/**
	 * Cookies过期时间，默认三个月
	 */
	public final static int COOKIES_EXPIRE = 3 * 30 * 24 * 60 * 60;

	public final static String OAUTH_HTTP = "http://";

}
