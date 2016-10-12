package com.sgck.dubbointerface.support.wrapper;

import java.util.List;
import java.util.Map;


import com.sgck.dubbointerface.base.RedisBaseService;
import com.sgck.dubbointerface.base.wrapper.RedisBaseWrapperService;
import com.sgck.dubbointerface.exception.BaseServiceException;

public class RedisBaseServiceWrapper implements RedisBaseWrapperService {
	private RedisBaseService redisBaseService;
	private String rediskey;
	
	public RedisBaseServiceWrapper(RedisBaseService redisBaseService,String rediskey){
		this.redisBaseService = redisBaseService;
		this.rediskey = rediskey;
	}

	public void set(List<String> key, List list, int timeout) throws BaseServiceException {
		redisBaseService.set(rediskey, key, list, timeout);
	}

	public void set(String key, Object value, int timeout) throws BaseServiceException {
		redisBaseService.set(rediskey, key, value, timeout);
	}

	public <T> List<T> get(Class<T> t, String... keys) {
		return redisBaseService.get(rediskey, t, keys);
	}

	public <T> T get(String t, boolean isString) {
		return redisBaseService.get(rediskey, t, isString);
	}

	public void delete(String... keys) throws BaseServiceException {
		redisBaseService.delete(rediskey, keys);
		
	}

	public void hset(String key, String field, Object value) throws BaseServiceException {
		redisBaseService.hset(rediskey, key, field, value);
	}

	public <T> void hsetAll(String key, Map<String, T> map, int timeout) throws BaseServiceException {
		if(null != map && !map.isEmpty()){
			redisBaseService.hsetAll(rediskey, key, map.keySet().toArray(new String[]{}),map.values().toArray(), timeout);
		}
	}

	public <T> T hget(String key, String field) {
		return redisBaseService.hget(rediskey, key, field);
	}

	public <T> Map<String, T> hgetAll(String key) {
		return redisBaseService.hgetAll(rediskey, key);
	}

	public void hdelete(String key, String... fields) throws BaseServiceException {
		redisBaseService.hdelete(rediskey, key,fields);
		
	}

	public void expire(String key, int timeout) throws BaseServiceException {
		redisBaseService.expire(rediskey, key, timeout);
	}

	public <T> T hget(String key, String field, boolean isString) {
		return redisBaseService.hget(rediskey, key, field, isString);
	}

	public <T> Map<String, T> hgetAll(String key, boolean isString) {
		return redisBaseService.hgetAll(rediskey, key, isString);
	}

	public void hset(String key, String field, Object value, int timeout) throws BaseServiceException {
		redisBaseService.hset(rediskey, key, field, value,timeout);
		
	}

	@Override
	public <T> List<T> lrange(String key, int start, int end, boolean isString)
	{
		return redisBaseService.lrange(rediskey, key, start, end, isString);
	}

	@Override
	public Long lpushAndLtrim(String key, Object value, int start, int end) throws BaseServiceException
	{
		return redisBaseService.lpushAndLtrim(rediskey, key, value, start, end);
	}

	@Override
	public Long lpush(String key, Object value) throws BaseServiceException
	{
		return redisBaseService.lpush(rediskey, key, value);
	}

	@Override
	public Long decre(String key, long decrement) throws BaseServiceException
	{
		return redisBaseService.decre(rediskey, key, decrement);
	}

	@Override
	public Long incr(String key, long increment) throws BaseServiceException
	{
		return redisBaseService.incr(rediskey, key, increment);
	}
	
}
