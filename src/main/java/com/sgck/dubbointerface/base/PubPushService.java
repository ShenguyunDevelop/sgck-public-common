package com.sgck.dubbointerface.base;

import com.sgck.dubbointerface.exception.BaseServiceException;

/**
 * 公共推送服务
 * 
 * @author 杨浩 2016年4月13日上午9:50:02
 */
public interface PubPushService {

	public void send(String mqConfigKey, String topic, String tag, Object content) throws BaseServiceException;
}
