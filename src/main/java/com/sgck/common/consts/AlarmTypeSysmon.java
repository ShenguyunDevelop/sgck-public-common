package com.sgck.common.consts;

public class AlarmTypeSysmon {
	public static final int AT_DATAALARM = 0;	//数据报警
	public static final int AT_LIFETIMEALARM = 1; 	//寿命报警
	public static final int AT_REALSTATUS = 2; 	//探针实时状态

	//sysmon报警状态
	public static final int SYSMON_NORMAL = 0;//正常
	public static final int SYSMON_L_ALARM = 1;//低报
	public static final int SYSMON_H_ALARM = 2;//高报
	public static final int SYSMON_LL_ALARM = 3;//低低报
	public static final int SYSMON_HH_ALARM = 4;//高高报
	public static final int SYSMON_LIFE_ALARM = 5;//寿命报
	public static final int SYSMON_DISABLE = 6;//停用
	public static final int SYSMON_NOT_LINE = 7;//不在线
	public static final int SYSMON_ERROR = 8;//故障
	public static final int SYSMON_UNKNOW = 9;//未知状态
	public static final int SYSMON_STOP = 10;//停机
	public static final int SYSMON_STARTING = 11;//正在开机
	public static final int SYSMON_STOPING = 12;//正在停机
	public static final int SYSMON_SSING = 13;//启停机
	//dau报警状态
	
	//机组状态
	public static final int DAU_MACHINE_UNKONW = 0;//未知状态
	public static final int DAU_MACHINE_STOP = 1;//停机状态
	public static final int DAU_MACHINE_STARTING = 2;//正在启动
	public static final int DAU_MACHINE_NORMAL = 3;//正常
	public static final int DAU_MACHINE_OFFLINE = 6;//不在线
	public static final int DAU_MACHINE_STOPING = 4;//正在停机
	public static final int DAU_MACHINE_SSING = 5;//启停机
	
	//测点通道状态
	public static final int DAU_CHANNEL_NORMAL = 0;//正常
	public static final int DAU_CHANNEL_ALARM = 1;//报警
	public static final int DAU_CHANNEL_NOT_LINE = 2;//不在线
	public static final int DAU_CHANNEL_UNABLE = 3;//禁用
	public static final int DAU_CHANNEL_ERROR = 4;//错误
	
	
}
