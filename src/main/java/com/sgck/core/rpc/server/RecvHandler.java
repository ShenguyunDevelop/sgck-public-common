package com.sgck.core.rpc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;

import com.sgck.core.exception.DSException;

public abstract class RecvHandler 
{
	protected Object clientInfo;
	
	static private ConcurrentHashMap<Long, RecvHandler> pid_rpcContext_map = new ConcurrentHashMap<Long, RecvHandler>();
	static private Map<String, Object> classObjMap = new HashMap<String, Object>();
	static private Map<String, Method> methodMap = new HashMap<String, Method>();

	static public String handlerPkgName = "handler";
	
	protected boolean isServerRequestHandler = false;
	
	// 根据当前的线程id获取运行上下�?
	// 返回 上下�?AmfRPCHandler)对象
	// 如果从其他线程调用，则返�?null
	public static RecvHandler getCurrentContext()
	{
		return pid_rpcContext_map.get(Thread.currentThread().getId());
	}
	
	public static void setHandlerPkgName(String pkgName){
		handlerPkgName = pkgName;
	}
	
	// 根据类创建实�?
	protected Object getClassObj(Class<?> c) throws Exception
	{
		Object ret = null;

		synchronized (classObjMap)
		{
			ret = classObjMap.get(c.getName());
			if (ret != null)
			{
				return ret;
			}

			ret = c.newInstance();

			classObjMap.put(c.getName(), ret);
		}

		return ret;
	}

	// 获取类的方法对象
	protected Method getMethodByName(Class<?> c, String funcName) throws Exception
	{
		Method mt = null;
		String qualifiedName = c.getName() + "." + funcName;

		synchronized (methodMap)
		{
			mt = methodMap.get(qualifiedName);

			if (mt != null)
			{// 如果已经缓存，则直接返回
				return mt;
			}

			Method[] allMethod = c.getMethods();
			String internedName = funcName.intern();
			for (int i = 0; i < allMethod.length; i++)
			{
				if (allMethod[i].getName() == internedName)
				{// 返回第一个，不支持重�?
					mt = allMethod[i];
					break;
				}
			}

			if (mt != null)
			{
				methodMap.put(qualifiedName, mt);
			}
		}

		return mt;
	}

	protected void writeReturn(OutputStream respWriter, Object ret)
	{
	}	
	
	// 根据返回码�?错误内容，填充返回协�?
	protected void handleError(int errCode, String errString, OutputStream respWriter)
	{
	}
	
	protected Integer checkToken(Object token) throws DSException{
		return null;
	}

	protected ArrayList doHandle(byte[] inputRawData,RecvHandlerCallback callback,OutputStream response) throws IOException{
		return new ArrayList();
	}
	
	public void process(InputStream inputStream, int inputDataLen,OutputStream response,RecvHandlerCallback callback) throws IOException
	{
		byte[] inputRawData = new byte[inputDataLen];

		// 从inputStream接受数据 -> inputRawData
		int readedDataLen = 0;
		int tempLen = 0;
		while (readedDataLen < inputDataLen)
		{
			tempLen = inputStream.read(inputRawData, readedDataLen, inputDataLen - readedDataLen);
			if(tempLen < 0 )
			{
				break;
			}
			else
			{
				readedDataLen += tempLen;
			}
		}

		try
		{
			// MD5校验头信�?
			/*byte[] targetData = RemoteObject.CreateMD5(inputRawData, 0, HEAD_SIZE);
			for (int i = 0; i < 16; i++)
			{
				if (targetData[i] != inputRawData[i + 16])
				{
					handleError(2, "数据格式不正�? MD5校验错误", response);
					return;
				}
			}*/

			// 保存本次请求的上下文
			pid_rpcContext_map.put(Thread.currentThread().getId(), this);
			
			// 处理远程调用请求
			ArrayList resps = doHandle(inputRawData,callback,response);

			if(resps != null){// 输出处理结果
				writeReturn (response, resps);
			}
		}
		catch (Exception e)
		{
			handleError(4, e.getMessage(), response);
		}
		finally
		{
			// 释放 pid->rpcContext 映射
			if (pid_rpcContext_map != null)
			{
				pid_rpcContext_map.remove(Thread.currentThread().getId());
			}
		}
	}

	public static Object getClientAttribute(String key)
	{
		RecvHandler recvHandler = pid_rpcContext_map.get(Thread.currentThread().getId());
		if(recvHandler != null){
			return recvHandler._getClientAttribute(key);
		}else{
			return null;
		}
	}
	
	public Object _getClientAttribute(String key){
		if(clientInfo != null){
			if(HashMap.class.isAssignableFrom(clientInfo.getClass())){
				return ((HashMap)clientInfo).get(key);
			}else{
				try {
					return BeanUtils.getProperty(clientInfo,key);
				} catch (Exception e) {
					return null;
				}
			}
			
		}else{
			return null;
		}
	}

	public boolean isServerRequestHandler() {
		return isServerRequestHandler;
	}

	public void setServerRequestHandler(boolean isServerRequestHandler) {
		this.isServerRequestHandler = isServerRequestHandler;
	}
}
