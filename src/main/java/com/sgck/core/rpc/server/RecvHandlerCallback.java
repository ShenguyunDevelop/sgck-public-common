package com.sgck.core.rpc.server;

import java.util.Properties;


public abstract class RecvHandlerCallback {
	 public abstract void onOK(InvokeResult reqObj);
	 public abstract void onError(InvokeError errorInfo,Object invokedObject,Exception e);
}
