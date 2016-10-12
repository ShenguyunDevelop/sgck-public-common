package com.sgck.core.rpc.client;

import java.util.ArrayList;
import java.util.Collection;


/// <summary>
/// 用于发送给DS的 远程调用 列表
/// 每一个元素为一个要请求的 远程调用
///
/// 格式：
/// [
///     RemoteMethodObject1, RemoteMethodObject2, RemoteMethodObject3 ...
/// ]
/// </summary>
public class RemoteMethodArray
{
	/// <summary>
	/// 远程调用 列表
	///
	/// 格式：
	/// [
	///     RemoteMethodObject1, RemoteMethodObject2, RemoteMethodObject3 ...
	/// ]
	/// </summary>
	private ArrayList<Object> _methodArray;

	/// <summary>
	/// 构造函数
	/// </summary>
	public RemoteMethodArray()
	{
		_methodArray = new ArrayList();
	}

	/// <summary>
	/// 构造函数,把FLEX设置好的DS远程调用列表直接设置进该对象
	/// </summary>
	public RemoteMethodArray(Collection<Object> callList)
	{
		_methodArray = new ArrayList(callList);
	}

	/// <summary>
	/// 给RemoteMethodArray加一个Object对像
	/// 如果同时包含多个函数调用，则必须多次调用本函数
	/// </summary>
	/// <param name="o">RemoteMethodObject 对像</param>
	public void Add(Object o)
	{
		_methodArray.add(o);
	}


	/// <summary>
	/// 输出Array数组
	/// </summary>
	/// <returns>格式：[ RemoteMethodObject1, RemoteMethodObject2, RemoteMethodObject3 ... ]</returns>
	public Object[] GetArray() throws Exception
	{
		if (_methodArray.size() == 0)
		{
			throw new Exception("Call to the DS Remote Method Array is Null");
		}
		return _methodArray.toArray();
	}

}
