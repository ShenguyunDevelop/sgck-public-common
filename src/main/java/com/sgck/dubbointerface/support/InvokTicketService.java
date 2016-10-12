package com.sgck.dubbointerface.support;

/**
 * 获取调用凭据
 * 
 * @author 杨浩 2016年4月5日上午9:59:32
 */
public interface InvokTicketService {

	public String getDBTicketId();

	public String getRedisTicketId();

	public void init();
}
