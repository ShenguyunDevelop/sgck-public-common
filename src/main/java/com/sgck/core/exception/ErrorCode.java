package com.sgck.core.exception;

public class ErrorCode
{
	// 201  第一�? �?server .
	public static final int Level_WARN = 2;
	public static final int Level_ERROR = 3;

	public static final int DAU_Exist = 201001;
	public static final int DAU_NOT_EXIST = 201002;
	public static final int DAU_NO_BASICINFO = 201003;
	public static final int DAU_NO_STRUCTINFO = 201004;
//	public static final int DAU_NO_CHANNEL = 201005;

	public static final int MAC_NOT_EXIST = 202001;
	public static final int MAC_no_GP = 202002;

	public static final int MACtype = 203001;
	public static final int GP_type = 203002;
	public static final int EIGENVALUE = 203003;
	public static final int PARAM = 203005;

	//	

	public static final int DB_TOO_MUCH = 205000;
	public static final int DB_MAC_ADD = 205001;
	public static final int DB_MAC_UP = 205002;

	public static final int DB_DAU_UP = 205003;
	public static final int DB_DAU_GET = 205004;

	public static final int DB_MACEvent = 205005;
	public static final int DB_SYSEvent = 205006;

	public static final int DB_USER_ADD = 205007;
	public static final int DB_USER_DEL = 205008;

	public static final int DB_Report = 205009;
	public static final int DB_Del_DATA = 205010;

	public static final int SERVER_MESSAGE = 206001;
	
	public static final int FAILED = 206002; //~yaofang
	public static final int REPEAT = 206003; //~yaofang
	public static final int DB_CONNECTION_FAILED = 206004; //~yaofang	
	public static final int PG_POOL_EXCEPTION = 206005; //~yaofang
	
	public static final int DIAGNOSIS_EXCEPTION = 206006; //智能诊断获取结果错误
	
	public static final int REMOTE_READ_TIMEOUT = 206007;//读超�?
	
	public static final int DATA_PARASE_EXCEPTION = 206008;
	
	public static final int TEL_REPEAT = 207001;//app登录电话号码重复
}
