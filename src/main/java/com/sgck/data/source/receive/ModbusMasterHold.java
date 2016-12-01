package com.sgck.data.source.receive;

import com.serotonin.modbus4j.ModbusMaster;

/**
 * modbus从 关联关系管理类
 * 
 * @author 杨浩 2016年7月6日下午1:38:01
 */
public class ModbusMasterHold implements Hold{

	private ModbusMaster modbusMaster;

	private boolean started;

	private boolean isClosed;
	
	private int retryTimes;

	public ModbusMaster getModbusMaster() {
		return modbusMaster;
	}

	public void setModbusMaster(ModbusMaster modbusMaster) {
		this.modbusMaster = modbusMaster;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public void addRetryTimes() {
		this.retryTimes++;
	}

	@Override
	public Object getHold() {
		return modbusMaster;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		isClosed = true;
		modbusMaster.destroy();
	}

	@Override
	public boolean isStop() {
		// TODO Auto-generated method stub
		return isClosed;
	}

}
