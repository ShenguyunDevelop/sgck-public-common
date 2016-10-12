package com.sgck.dubbointerface.support;

import java.util.List;

public interface BaseServiceProxyFactory {

	public <T> void registerPool(String poolPath, T t);

	public <T> T getPool(String poolPath);

	public <T> List<T> getMQProviderList();

	public boolean ckeckKey(String key);

}
