package com.sgck.core.task;

public interface TaskCallback {
	public void onError(Object result);

	public void onOK(Object result);
}
