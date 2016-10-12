package com.sgck.common.consts;

//上级服务器下发的任务类型
public class RemoteTaskType {
	public final static int IMPORT_DATA = 101;	//数据导入 
	
	//由下级uums处理
	public final static int SYSMON_UPLOADORG = 1001;//需要获取组织结构树
	
	//由下级cms处理   ---  由cms定时取该任务
	public final static int CMS_ADDORG = 2001;//上传cms中的算法配置信息/测点基本信息
	public final static int CMS_UPDATE_CACHE_AFTER_DRAG_ORGANIZATION = 2002;
}
