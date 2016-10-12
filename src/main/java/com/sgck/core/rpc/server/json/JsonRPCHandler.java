package com.sgck.core.rpc.server.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.sgck.common.log.DSLogger;
import com.sgck.core.exception.DSException;
import com.sgck.core.rpc.server.InvokeError;
import com.sgck.core.rpc.server.InvokeResult;
import com.sgck.core.rpc.server.RecvHandler;
import com.sgck.core.rpc.server.RecvHandlerCallback;
import com.sgck.core.rpc.server.json.model.ClientInfo;
import com.sgck.core.rpc.server.json.model.FuncCallInfo;
import com.sgck.core.rpc.server.json.model.InvokeObject;

public class JsonRPCHandler extends RecvHandler
{
	// 根据返回码�?错误内容，填充返回协�?
	@Override
	protected void writeReturn(OutputStream respWriter, Object ret)
	{
		try
		{
			String jsonStr = JSON.toJSONString(ret);
			byte[] binaryData = jsonStr.getBytes("utf-8");
			respWriter.write(binaryData, 0, (int) binaryData.length);
			respWriter.flush();
			respWriter.close();
		}
		catch (Exception e)
		{
			DSLogger.error("handleError exception:",e);
		}
	}	
	
	// 根据返回码�?错误内容，填充返回协�?
	@Override
	protected void handleError(int errCode, String errString, OutputStream respWriter)
	{
		InvokeError[] retObj = new InvokeError[1];
		retObj[0] = new InvokeError();
		retObj[0].setCode(errCode);
		retObj[0].setWhat(errString);
		//DSLogger.info("handleError:"+errString);
		writeReturn (respWriter, retObj);
	}

	public Object doRPC(String className,String funcName,String paramsEncodedByJson) throws Exception{
		List<Object> paramList = null;
		Class<?> c = Class.forName(handlerPkgName + "." + className);
		if (null == c)
		{
			throw new DSException(1100, "className[" + className + "] is not exist!");
		}
		
		Method func = getMethodByName(c, funcName);
		if (null == func)
		{
			throw new DSException(1100, "funcName[" + funcName + "] is not exist!");
		}
		
		Object object = getClassObj(c);
		Class[] paramTypeList = func.getParameterTypes();
//		if (paramList != null && paramList.size() != paramTypeList.length)
//		{
//			throw new DSException(1100, "func need " + paramTypeList.length + "params, argument count is mismatch");
//		}
		if(paramTypeList.length > 0){
			paramList = JSON.parseArray(paramsEncodedByJson, paramTypeList);
		}
		
		return func.invoke(object, paramList != null ? paramList.toArray() : null);
	}
	
	// 执行客户端的请求
	public ArrayList hanldeClientReq(FuncCallInfo[] clientReqs,RecvHandlerCallback callback)
	{
		ArrayList resps = new ArrayList();
		String className = null;
		String funcName = null;
		String paramsEncodedByJson = null;
		
		for (int i = 0; i < clientReqs.length; i++)
		{// 依次执行多个请求
			try
			{
				FuncCallInfo rpcObj = clientReqs[i];
				if (rpcObj != null)
				{
					className = rpcObj.getDomain();
					funcName = rpcObj.getFoo();
					paramsEncodedByJson = rpcObj.getParams();

					InvokeResult retObj = new InvokeResult();
//					retObj.setProperty("className", className);
//					retObj.setProperty("funcName", funcName);
					retObj.setCode(0);
					retObj.setRet(doRPC(className,funcName,paramsEncodedByJson));
					resps.add(retObj);
					
					if(callback != null){
						callback.onOK(retObj);
					}
				}
			}
			catch (NoSuchMethodException e)
			{
				InvokeError errorObj = new InvokeError();

				errorObj.setCode(1010);
				errorObj.setWhat(className + "." + funcName + " error : " + e.getMessage());
				resps.add(errorObj);
				
				if(callback != null){
					callback.onError(errorObj,clientReqs[i],e);
				}
			}
			catch (IllegalArgumentException e)
			{
				InvokeError errorObj = new InvokeError();

				errorObj.setCode(1011);
				errorObj.setWhat(className + "." + funcName + " error : " + e.getMessage());
				resps.add(errorObj);
				
				if(callback != null){
					callback.onError(errorObj,clientReqs[i],e);
				}
			}
			catch (InvocationTargetException e)
			{
				InvokeError errorObj = new InvokeError();
				
				// 捕获到了数据库连接异常，但上传失败的数据已被写文件，即历史数据虽然没有成功存入数据库，但数据已被以文件的形式保存了下来，故直接返�?code = 0
				// 表示通信成功，则采集器会继续上传新的数据，�?不是重新上传旧数据�?以上都是我猜的，不知道好不好使�?
				errorObj.setWhat(e.getMessage());
				errorObj.setCode(-1);
				resps.add(errorObj);
				
				if(callback != null){
					callback.onError(errorObj,clientReqs[i],e);
				}
			}
			catch (Exception e)
			{   
				InvokeError errorObj = new InvokeError();
				errorObj.setCode(1012);
				errorObj.setWhat(className + "." + funcName + " unknown error : " + e.getMessage());
				resps.add(errorObj);
				if(callback != null){
					callback.onError(errorObj,clientReqs[i],e);
				}
			}	
			finally{
				paramsEncodedByJson = null;
			}
		}

		return resps;
	}
	
	@Override
	protected ArrayList doHandle(byte[] inputRawData,RecvHandlerCallback callback,OutputStream response) throws IOException{
		String jsonStr = new String(inputRawData,"utf-8");
		// 读取请求对象
		InvokeObject invokeObj = JSON.parseObject(jsonStr,InvokeObject.class);
		FuncCallInfo[] callInfos = null;
		if (invokeObj == null || (callInfos = invokeObj.getRpcInfo()) == null || callInfos.length == 0)
		{
			handleError(3, "没有找到函数调用数组", response);
			return null;
		}

		try {
			clientInfo = invokeObj.getClientInfo();
			if(!isServerRequestHandler){
				Object token = _getClientAttribute("token");
				if(null == token){
					throw new DSException(3,"token is empty");//token无效
				}
				checkToken(token);
			}
		} catch (DSException e) {
			handleError(e.getCode(), e.getMessage(), response);
			return null;
		} catch (Exception e){
			handleError(1010, e.getMessage(), response);
			return null;
		}
		
		// 处理远程调用请求
		return hanldeClientReq(callInfos,callback);
	}
}