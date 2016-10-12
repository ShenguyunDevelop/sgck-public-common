package com.sgck.core.rpc.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import flex.messaging.io.amf.ASObject;

public class InvokeObjectWrapper {
	static public ASObject  packInvokeObject(String domain,String func,Object... params){
		ASObject ret = new ASObject();
		ret.put("domain", domain);
		ret.put("foo", func);
		
		ArrayList paramList = new ArrayList();
		paramList.addAll(Arrays.asList(params));
		ret.put("params",paramList );
		return ret;
	}
	
	/*static public ASObject  packInvokeObject(String domain,String func,List params){
		ASObject ret = new ASObject();
		ret.put("domain", domain);
		ret.put("foo", func);
		ret.put("params", params);
		return ret;
	}*/
}
