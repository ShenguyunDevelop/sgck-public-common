package com.sgck.data.source.receive;

public interface Hold {

	public boolean isStarted();
	
	public Object getHold();
	
	public void setStarted(boolean started) ;
	
	public void stop();
	
	public boolean isStop();
	
	
}
