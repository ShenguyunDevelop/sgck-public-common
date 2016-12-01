package com.sgck.data.source.task;

import com.sgck.core.task.SGTask;

public class ExtDsTestTask extends SGTask{
	private boolean isOnTest;	//是否正在测试
	
	public ExtDsTestTask(String id, int type, Object contentObj, int timeoutInSesc) {
		super(id, type, contentObj, timeoutInSesc, null, null);
		// TODO Auto-generated constructor stub
	}

	public boolean isOnTest() {
		return isOnTest;
	}

	public void setOnTest(boolean isOnTest) {
		this.isOnTest = isOnTest;
	}
}
