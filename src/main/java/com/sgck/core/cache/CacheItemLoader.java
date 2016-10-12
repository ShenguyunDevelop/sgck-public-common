package com.sgck.core.cache;

public interface CacheItemLoader<T> {
	public T load(String key) throws Exception;
}
