package com.sgck.data.source.cache;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sgck.common.consts.SubSystem;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.log.DSLogger;
import com.sgck.dubbointerface.base.wrapper.RedisBaseWrapperService;
import com.sgck.dubbointerface.pub.PubBusinessService;

import flex.messaging.io.amf.ASObject;

//外部数据源缓存
@Component
public class ExtDataSourceCache {
	@Resource
	protected RedisBaseWrapperService redisBaseWrapperService;

	private static final String EXT_DATA_SOURCE_CACHE_KEY = SubSystem.SYSMON + PubBusinessService.EXT_DATA_SOURCE_CACHE;

	private static final String EXT_DATA_SOURCE_CACHE_LIST_KEY = SubSystem.SYSMON + "DataSourceCacheList::";

	private static final String EXT_DATA_SOURCE_TMP_LIST_KEY = SubSystem.SYSMON + "DATA_SOURCE_TMP_LIST_KEY::";

	// 需要存零时十条数据

	public void putTmpData(String sourceId, List<ASObject> list) {
		redisBaseWrapperService.set(EXT_DATA_SOURCE_TMP_LIST_KEY + sourceId, list, 60 * 30);
	}

	public List<ASObject> getTmpData(String sourceId) {
		return redisBaseWrapperService.get(EXT_DATA_SOURCE_TMP_LIST_KEY + sourceId, false);
	}

	public void deleteTmpData(String sourceId) {
		redisBaseWrapperService.delete(EXT_DATA_SOURCE_TMP_LIST_KEY + sourceId);
		DSLogger.debug("执行清除" + EXT_DATA_SOURCE_TMP_LIST_KEY + "成功!");
	}

	public void putDataSourceCacheBySourceId(String sourceId, ASObject as) {
		redisBaseWrapperService.hset(EXT_DATA_SOURCE_CACHE_KEY, sourceId, as);
	}

	public ASObject getDataSourceCacheBySourceId(String sourceId) {
		return redisBaseWrapperService.hget(EXT_DATA_SOURCE_CACHE_KEY, sourceId);
	}

	public void deleteDataSourceCacheBySourceId(String sourceId) {
		redisBaseWrapperService.hdelete(EXT_DATA_SOURCE_CACHE_KEY, sourceId);
		DSLogger.debug("执行清除" + EXT_DATA_SOURCE_CACHE_KEY + "成功!");
	}

	public List<ReadConfig> getAllDataSourceConditionList(Integer type) {
		Map<String, ReadConfig> list = redisBaseWrapperService.hgetAll(EXT_DATA_SOURCE_CACHE_LIST_KEY + type);
		if (null != list) {
			return Lists.newArrayList(list.values().iterator());
		}
		return null;
	}

	public void putDataSourceConditionList(ReadConfig readConfig) {
		redisBaseWrapperService.hset(EXT_DATA_SOURCE_CACHE_LIST_KEY + readConfig.getInterfaceType(), readConfig.getDataSourceId(), readConfig);
	}

	public void deleteDataSourceConditionList(ReadConfig readConfig) {
		deleteDataSourceConditionList(readConfig.getInterfaceType(), readConfig.getDataSourceId());
	}

	public void deleteDataSourceConditionList(int type, String sourceId) {
		redisBaseWrapperService.hdelete(EXT_DATA_SOURCE_CACHE_LIST_KEY + type, sourceId);
		DSLogger.debug("执行清除" + EXT_DATA_SOURCE_CACHE_LIST_KEY + "成功!");
	}

	public void deleteDataSourceConditionList(int type) {
		redisBaseWrapperService.hdelete(EXT_DATA_SOURCE_CACHE_LIST_KEY + type);
		DSLogger.debug("执行清除" + EXT_DATA_SOURCE_CACHE_LIST_KEY + "成功!");
	}

}
