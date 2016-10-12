package com.sgck.core.rpc.client;

import flex.messaging.io.amf.ASObject;


public class RemoteCallBack
{
	private ASObject cbInfo;

	public RemoteCallBack()
	{

	}

	public RemoteCallBack(ASObject cbInfo)
	{
		this.cbInfo = cbInfo;
	}

	void onOK(Object result)
	{
		System.out.println(result);
	}

	void onError(int errCode, String what)
	{
		System.out.println(what + ",error code is " + errCode);
	}
}
