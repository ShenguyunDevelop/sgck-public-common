package com.sgck.dubbointerface.base.wrapper;

import java.util.List;
import java.util.Map;

import com.sgck.dubbointerface.exception.BaseServiceException;

public interface RedisBaseWrapperService extends RedisService{
	// 如果timeout<=0 mset批量插入;timeout>0 循环一个个插入并设置超时时间
	public void set(List<String> key, List list, int timeout) throws BaseServiceException;

	public void set(String key, Object value, int timeout) throws BaseServiceException;

	public <T> List<T> get(Class<T> t, String... keys);

	public <T> T get(String key,boolean isString);
	
	public void delete(String... keys) throws BaseServiceException;

	public void hset(String key, String field, Object value) throws BaseServiceException;
	
	public void hset(String key, String field, Object value,int timeout) throws BaseServiceException;

	public <T> void hsetAll(String key, Map<String, T> map, int timeout)
			throws BaseServiceException;

	public <T> T hget(String key, String field);

	public <T> Map<String, T> hgetAll(String key);

	public void hdelete(String key, String... fields) throws BaseServiceException;

	public void expire(String key, int timeout) throws BaseServiceException;

	public <T>T hget(String key, String field, boolean isString);

	public <T> Map<String, T> hgetAll(String key, boolean isString);
	
    public <T> List<T> lrange(String key,int start,int end,boolean isString);
	
	public Long lpushAndLtrim(String key, Object value,int start,int end) throws BaseServiceException;
	
	public Long lpush(String key, Object value) throws BaseServiceException;
	
	public Long decre(String key, long decrement) throws BaseServiceException;
	
	public Long incr(String key, long increment) throws BaseServiceException;
}
