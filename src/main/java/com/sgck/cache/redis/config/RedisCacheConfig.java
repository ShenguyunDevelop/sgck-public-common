package com.sgck.cache.redis.config;

import java.util.Map;

public interface RedisCacheConfig {
	public int getPort();
	public String getHost();
	public String getPwd();
	public int getMaxIdle();
	
	public int getDefaultCacheExpiration();
	public Map<String, Long> getCacheExpirations();
}
