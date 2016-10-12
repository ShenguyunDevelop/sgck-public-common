package com.sgck.common.sg9k.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 报警状态缓存
 * 
 * @author zhijun_xiao
 * @date 2015年12月1日 下午2:06:54
 * @Description: TODO
 */
public class PosAlarmStatusForCache implements Serializable {
	private Map<Integer, Date> alarmInfo;// key为报警状态，value为报警时间
	private Map<Integer, Map<Integer, Date>> SegStatus;// key分段号，value报警信息

	public Map<Integer, Date> getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(Map<Integer, Date> alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public Map<Integer, Map<Integer, Date>> getSegStatus() {
		return SegStatus;
	}

	public void setSegStatus(Map<Integer, Map<Integer, Date>> segStatus) {
		SegStatus = segStatus;
	}

}
