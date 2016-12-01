package com.sgck.data.source.cache;

public interface ModbusHold {

	public Integer getCom();
	
	public void open();
	
	public void close();
	
	public boolean isStarted();
	
}
