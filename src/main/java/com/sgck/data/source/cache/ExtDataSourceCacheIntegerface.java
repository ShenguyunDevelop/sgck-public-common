package com.sgck.data.source.cache;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sgck.common.consts.SubSystem;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.log.DSLogger;

import flex.messaging.io.amf.ASObject;

//外部数据源缓存

public interface ExtDataSourceCacheIntegerface {

	// 需要存零时十条数据

	public void putTmpData(String sourceId, List<ASObject> list);

	public List<ASObject> getTmpData(String sourceId);
	public void deleteTmpData(String sourceId) ;

	public void putDataSourceCacheBySourceId(String sourceId, ASObject as) ;
	public ASObject getDataSourceCacheBySourceId(String sourceId) ;
	public void deleteDataSourceCacheBySourceId(String sourceId) ;

	public List<ReadConfig> getAllDataSourceConditionList(Integer type);

	public void putDataSourceConditionList(ReadConfig readConfig) ;
	public void deleteDataSourceConditionList(ReadConfig readConfig) ;
	public void deleteDataSourceConditionList(int type, String sourceId);

	public void deleteDataSourceConditionList(int type);

}
