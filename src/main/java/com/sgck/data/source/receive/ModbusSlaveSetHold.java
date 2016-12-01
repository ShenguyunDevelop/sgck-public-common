package com.sgck.data.source.receive;

import com.serotonin.modbus4j.ModbusSlaveSet;

/**
 * modbus从 关联关系管理类
 * 
 * @author 杨浩 2016年7月6日下午1:38:01
 */
public class ModbusSlaveSetHold implements Hold{

	private ModbusSlaveSet modbusSlaveSet;

	private boolean started;

	private int retryTimes;
	
	private boolean isClosed;

	public ModbusSlaveSet getModbusSlaveSet() {
		return modbusSlaveSet;
	}

	public void setModbusSlaveSet(ModbusSlaveSet modbusSlaveSet) {
		this.modbusSlaveSet = modbusSlaveSet;
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
		return modbusSlaveSet;
	}

	@Override
	public void stop() {
		isClosed = true;
		modbusSlaveSet.stop();
	}

	@Override
	public boolean isStop() {
		return isClosed;
	}

}
