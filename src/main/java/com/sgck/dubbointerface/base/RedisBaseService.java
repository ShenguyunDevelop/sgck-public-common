package com.sgck.dubbointerface.base;

import java.util.List;
import java.util.Map;

import com.sgck.dubbointerface.exception.BaseServiceException;

public interface RedisBaseService {
	// 如果timeout<=0 mset批量插入;timeout>0 循环一个个插入并设置超时时间
	public void set(String redisConfigKey, List<String> key, List list, int timeout) throws BaseServiceException;

	public void set(String redisConfigKey, String key, Object value, int timeout) throws BaseServiceException;

	public <T> List<T> get(String redisConfigKey, Class<T> t, String... keys);

	public <T> T get(String redisConfigKey, String key,boolean isString);
	
	public void delete(String redisConfigKey, String... keys) throws BaseServiceException;

	public void hset(String redisConfigKey, String key, String field, Object value) throws BaseServiceException;

	public void hset(String redisConfigKey, String key, String field, Object value,int timeout) throws BaseServiceException;

	
	public <T> void hsetAll(String redisConfigKey, String key, String[] keys,T[]list, int timeout)
			throws BaseServiceException;

	public <T> T hget(String redisConfigKey, String key, String field);

	public <T> Map<String, T> hgetAll(String redisConfigKey, String key);

	public void hdelete(String sourceId, String key, String... fields) throws BaseServiceException;

	public void expire(String sourceId, String key, int timeout) throws BaseServiceException;

	public <T> T hget(String redisConfigKey, String key, String field, boolean isString);

	public <T> Map<String, T> hgetAll(String redisConfigKey, String key, boolean isString);
	
	/**2016-09-06 add**/
	
	public <T> List<T> lrange(String sourceId, String key,int start,int end,boolean isString);
	
	public Long lpushAndLtrim(String sourceId, String key, Object value,int start,int end) throws BaseServiceException;
	
	public Long lpush(String sourceId, String key, Object value) throws BaseServiceException;
	
	public Long decre(String sourceId, String key, long decrement) throws BaseServiceException;
	
	public Long incr(String sourceId, String key, long increment) throws BaseServiceException;

}
