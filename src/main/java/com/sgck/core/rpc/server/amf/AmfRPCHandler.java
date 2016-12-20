package com.sgck.core.rpc.server.amf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


import com.sgck.common.log.DSLogger;
import com.sgck.core.amf.Amf3Input;
import com.sgck.core.amf.Amf3Output;
import com.sgck.core.exception.DSException;
import com.sgck.core.rpc.client.Stub;
import com.sgck.core.rpc.server.InvokeError;
import com.sgck.core.rpc.server.InvokeResult;
import com.sgck.core.rpc.server.RecvHandler;
import com.sgck.core.rpc.server.RecvHandlerCallback;

import flex.messaging.io.amf.ASObject;

public class AmfRPCHandler extends RecvHandler
{
	
	private int HEAD_SIZE = 32;
	private byte[] crypt = {'A', 'M', 'F', '_', 'R', 'E', 'M', 'O', 'T', 'I', 'N', 'G', '_', 'H', 'E', 'A', 'D'};

	static final byte PACKET_CMD_BREAK = 0; // 0 断开连接 
	static final byte PACKET_CMD_RPC = 1; // 1 远程调用请求�?
	static final byte PACKET_CMD_RET = 2; // 2 远程调用返回�?
	static final byte PACKET_CMD_EVENT = 3; // 3 事件通知
	static final byte PACKET_CMD_MESSAGE = 4; // 4 实时消息
	static final byte TAG_HAS_CLIENT_INFO = 0X01; // 包含 clientInfo

	static private BeanConventor bc = new BeanConventor();
	
	// 根据返回码�?错误内容，填充返回协�?
	@Override
	protected void writeReturn(OutputStream respWriter, Object ret)
	{
		byte tags[] = new byte[1];
		tags[0] = TAG_HAS_CLIENT_INFO;
		try
		{
			// 产生包体 -> respByteStream
			ByteArrayOutputStream respByteStream = new ByteArrayOutputStream();
			Amf3Output amfWriter = new Amf3Output();
			amfWriter.setOutputStream(respByteStream);
			if (tags[0] > 0 )
			{// 如果不需要客户端信息，则不用返回 clientInfo
				if (null == clientInfo)
					clientInfo = new ASObject();
				amfWriter.writeObject(clientInfo);
				//amfWriter.writeObject(null);
			}
			amfWriter.writeObject(ret);
			amfWriter.flush();
			
			// 构�?协议包头
			byte[] respHeader = Stub.GetRpcHeader((int) respByteStream.size() + HEAD_SIZE, 1, PACKET_CMD_RET, tags);
			// 输出 -> response
			//ServletOutputStream respWriter = response.getOutputStream();
			respWriter.write(respHeader, 0, HEAD_SIZE);
			respWriter.write(respByteStream.toByteArray(), 0, (int) respByteStream.size());
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
		ArrayList retObj = new ArrayList();
		InvokeError errorObj = new InvokeError();
		errorObj.setCode(errCode);
		errorObj.setWhat(errString);
		retObj.add(errorObj);
		writeReturn (respWriter, retObj);
	}

	// 判断给定的类是否包装�?
	public static boolean isWrappedClass(Class clz)
	{
		try
		{
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/*protected <T> T[] arrayConvert(Class<T> wantedArrayItemClass,List origArray) throws Exception{
		int len = origArray.size();
		Object origObj = null;
		T[] ret = (T[])Array.newInstance(wantedArrayItemClass,len);	
		
		for(int i = 0; i < len; i++){
			origObj = origArray.get(i);
			if(origObj.getClass() == wantedArrayItemClass){
				ret[i] = (T)origObj;
			}
			else if (origObj instanceof ASObject){
				ret[i] = (T)bc.toBean((ASObject) origObj, wantedArrayItemClass);
			}else{
				ret[i] = null;
			}
		}
		return ret;
	}*/
	// 将对象类型转换为Amf可接受的类型
	protected Object typeConvert(Object paramObjInvoked, Class<?> paramClass) throws Exception
	{
		if(paramObjInvoked == null){
			return paramObjInvoked;
		}
		
		Object ret = null;
		Class paramClassNameInvoked = paramObjInvoked.getClass();
		String paramClassName = paramClass.getName().intern();
		Number paramObjInvokedNum = null;
		
		if(paramClass.isArray() && paramObjInvoked instanceof java.util.Collection){
			return bc.arrayConvert(paramClass.getComponentType(),(List)paramObjInvoked);
		}
		
		if (paramClass.isAssignableFrom(paramClassNameInvoked)){
			return paramObjInvoked;
		}

		if (isWrappedClass(paramClass) || paramClass.isPrimitive()) // 函数需要的参数类型为Number或标量
		{
			if(paramClassNameInvoked.isPrimitive() || Number.class.isAssignableFrom(paramClassNameInvoked)){
				paramObjInvokedNum = ((Number) paramObjInvoked);
			}else if(paramClassNameInvoked.getCanonicalName() == "java.lang.String"){ // 如果是字符串，将其转换为Number
				paramObjInvokedNum = (Number)Double.valueOf((String)paramObjInvoked); // 如果转化失败，会直接抛异常，并返回异常信息至调用端
			}else{
				throw new Exception("failed to convert func parameter. need " + paramClassName + ",get " + paramClassNameInvoked.getName());
			}
			
			if (paramClassName == "boolean" || paramClassName == "java.lang.Boolean")
			{
				ret = paramObjInvokedNum.byteValue() == 1;
			}
			else if (paramClassName == "byte" || paramClassName == "java.lang.Byte")
			{
				ret = paramObjInvokedNum.byteValue();
			}
			else if (paramClassName == "char" || paramClassName == "java.lang.Character")
			{
				ret = (char) paramObjInvokedNum.byteValue();
			}
			else if (paramClassName == "short" || paramClassName == "java.lang.Short")
			{
				ret = paramObjInvokedNum.shortValue();
			}
			else if (paramClassName == "int" || paramClassName == "java.lang.Integer")
			{
				ret = paramObjInvokedNum.intValue();
			}
			else if (paramClassName == "long" || paramClassName == "java.lang.Long")
			{
				ret = paramObjInvokedNum.longValue();
			}
			else if (paramClassName == "float" || paramClassName == "java.lang.Float")
			{
				ret = paramObjInvokedNum.floatValue();
			}
			else if (paramClassName == "double" || paramClassName == "java.lang.Double")
			{
				ret = paramObjInvokedNum.doubleValue();
			}
		}
		else if (paramObjInvoked instanceof ASObject){
			if ((ret = bc.toBean((ASObject) paramObjInvoked, paramClass)) == null){
				throw new Exception("func parameter is mismatch,failed to convert. need " + paramClassName + ",get " + paramClassNameInvoked.getName());
			}
		}else if(paramClass.getCanonicalName() == "java.lang.String"){ // 函数需要的参数是String,尽量满足她
			if(paramClassNameInvoked.isPrimitive() || Number.class.isAssignableFrom(paramClassNameInvoked)){
				ret = String.valueOf(paramObjInvoked);
			}else{
				throw new Exception("func parameter is mismatch,failed to convert. need " + paramClassName + ",get " + paramClassNameInvoked.getName());
			}
		}else{
			throw new Exception("func parameter is mismatch,failed to convert. need " + paramClassName + ",get " + paramClassNameInvoked.getName());
		}

		return ret;
	}
	
	@Override
	protected ArrayList doHandle(byte[] inputRawData,RecvHandlerCallback callback,OutputStream response) throws IOException{
		int inputDataLen = inputRawData.length;
		// 获取头信�?-> header
		ByteArrayInputStream header = new ByteArrayInputStream(inputRawData, 0, HEAD_SIZE);
		DataInputStream headerReader = new DataInputStream(header);
		headerReader.skipBytes(9);
		
		// 读取标志位，tag标识是否�?��读取 client_info
		byte tag = headerReader.readByte();

		// 构�?输入�?
		ByteArrayInputStream mmStream = new ByteArrayInputStream(inputRawData, HEAD_SIZE, inputDataLen - HEAD_SIZE);
		Amf3Input amfReader = new Amf3Input();
		amfReader.setInputStream(mmStream);

		// 读取请求对象
		ArrayList remoteCallList = null;
		try {
			// 读取 clientInfo
			if ((tag & TAG_HAS_CLIENT_INFO) > 0)
			{
				clientInfo = amfReader.readObject();
//				if (!isServerRequestHandler) {
//					Object token = _getClientAttribute("token");
//					if (null == token) {
//						throw new DSException(3, "token is empty");// token无效
//					}
//					Integer userId = checkToken(token);
//					if (null != userId) {
//						((ASObject)clientInfo).put("userid", userId);
//					}
//				}
			}
			remoteCallList = (ArrayList) amfReader.readObject();
		} catch (ClassNotFoundException e) {
			handleError(2, "AmfReadObject: " + e.getMessage(), response);
			return null;
		}
//		catch (DSException e) {
//			handleError(e.getCode(), e.getMessage(), response);
//			return null;
//		}
		
		if (remoteCallList == null)
		{
			handleError(3, "没有找到函数调用数组", response);
			return null;
		}
		// 处理远程调用请求
		return hanldeClientReq(remoteCallList,callback);
	}

	protected Object doRPC(String className,String funcName,ArrayList<Object> paramList) throws Exception{
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
		if (paramList != null && paramList.size() != paramTypeList.length)
		{
			throw new DSException(1100, "func need " + paramTypeList.length + "params, argument count is mismatch");
		}

		if (paramList != null)
		{
			for (int index = 0; index < paramList.size(); index++)
			{
				paramList.set(index, typeConvert(paramList.get(index), paramTypeList[index]));
			}
		}
		
		return func.invoke(object, paramList != null ? paramList.toArray() : null);
	}
	// 执行客户端的请求
	public ArrayList hanldeClientReq(ArrayList clientReqs,RecvHandlerCallback callback)
	{
		ArrayList resps = new ArrayList();
		String className = null;
		String funcName = null;
		ArrayList paramList = null;
		
		for (int i = 0; i < clientReqs.size(); i++)
		{// 依次执行多个请求
			try
			{
				ASObject rpcObj = (ASObject) clientReqs.get(i);

				if (rpcObj != null)
				{
					className = (String) rpcObj.get("domain");
					funcName = (String) rpcObj.get("foo").toString();
					paramList = (ArrayList<?>) rpcObj.get("params");
					
					//DSLogger.info("className:" + className + "; funName:" + funcName + "; paramList:" + paramList.toString());
					
					InvokeResult retObj = new InvokeResult();
					retObj.setCode(0);
					retObj.setRet(doRPC(className,funcName,paramList));
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
					callback.onError(errorObj,clientReqs.get(i),e);
				}
			}
			catch (IllegalArgumentException e)
			{
				InvokeError errorObj = new InvokeError();

				errorObj.setCode(1011);
				errorObj.setWhat(className + "." + funcName + " error : " + e.getMessage());
				resps.add(errorObj);
				
				if(callback != null){
					callback.onError(errorObj,clientReqs.get(i),e);
				}
			}
			catch (InvocationTargetException e)
			{
				InvokeError errorObj = new InvokeError();
				Throwable t = e.getTargetException();
				if(t instanceof DSException){
					DSException d = (DSException)t;
					errorObj.setWhat(d.getMessage());
					errorObj.setCode(d.getCode());
				}else{
					errorObj.setWhat(t.getMessage());
					errorObj.setCode(-1);
				}
				// 捕获到了数据库连接异常，但上传失败的数据已被写文件，即历史数据虽然没有成功存入数据库，但数据已被以文件的形式保存了下来，故直接返�?code = 0
				// 表示通信成功，则采集器会继续上传新的数据，�?不是重新上传旧数据�?以上都是我猜的，不知道好不好使�?
				
				resps.add(errorObj);
				
				if(callback != null){
					callback.onError(errorObj,clientReqs.get(i),e);;
				}
			}
			catch (Exception e)
			{   
				InvokeError errorObj = new InvokeError();
				errorObj.setCode(1012);
				errorObj.setWhat(className + "." + funcName + " unknown error : " + e.getMessage());
				resps.add(errorObj);
				if(callback != null){
					callback.onError(errorObj,clientReqs.get(i),e);
				}
			}finally{
				paramList = null; 
			}
		}

		return resps;
	}
}
