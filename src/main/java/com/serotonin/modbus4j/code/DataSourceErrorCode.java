package com.serotonin.modbus4j.code;

import java.util.HashSet;

import com.serotonin.modbus4j.exception.ModbusConnectionExeption;

public class DataSourceErrorCode {

	public final static int READ_TIMEOUT = 404;

	public final static int READ_COMMON_ERROR = 500;

	public final static int UNILLEGAL_FUNCTION = 0X01;//非法功能
	
	public final static int UNILLEGAL_ADDRESS = 0x02;//非法数据地址
	
	public final static int UNILLEGAL_VALUE = 0x03;//非法数据值
	
	public final static int SLAVE_PRO = 0x04;//从设备故障
	
	public final static int SURE = 0x05;//确认
	
	public final static int SLAVE_BUSSINESS = 0x06;//从设备故障
	
	public final static int ODDEVEN_ERROR = 0x08;//存储奇偶差错
	
	public final static int UNGATEWAY_PATH = 0x0A;//不可用网关路径
	
	public final static int  GATEWAY_RESPONSE_ERRPR = 0x0B;//网关目标设置响应失败
	
	public final static int NORMAL = 0x00;//正常
	
	public final static int TIMEOUT = 0x0C;//超时
	
	public final static int ERROR = 0x0D;//错误
	
	public final static int OTHER = 0X0E;//其他
	
	//判断返回的是不是异常码
	public static boolean isBelongConExc(int code){
		switch(code){
		case DataSourceErrorCode.UNILLEGAL_FUNCTION:
		case DataSourceErrorCode.UNILLEGAL_ADDRESS:
		case DataSourceErrorCode.UNILLEGAL_VALUE:
		case DataSourceErrorCode.SLAVE_PRO:
		case DataSourceErrorCode.SURE:
		case DataSourceErrorCode.SLAVE_BUSSINESS:
		case DataSourceErrorCode.ODDEVEN_ERROR:
		case DataSourceErrorCode.UNGATEWAY_PATH:
			return true;
		}
		return false;
	}

}
