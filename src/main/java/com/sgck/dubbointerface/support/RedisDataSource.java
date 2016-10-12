package com.sgck.dubbointerface.support;

public interface RedisDataSource {

	public abstract <T> T getRedisClient(String sourceId);

	public <T> void returnResource(String sourceId, T jedis);

	public <T> void returnResource(String sourceId, T shardedJedis, boolean broken);
}
