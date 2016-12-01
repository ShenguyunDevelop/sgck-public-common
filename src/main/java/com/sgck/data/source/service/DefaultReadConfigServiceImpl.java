package com.sgck.data.source.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.log.DSLogger;
import com.sgck.data.source.cache.ExtDataSourceCache;

import flex.messaging.io.amf.ASObject;
import net.sf.json.JSONObject;

@Component
public class DefaultReadConfigServiceImpl implements ReadConfigService {

	@Autowired
	private ExtDataSourceCache extDataSourceCache;

	private Map<Integer, ReadService> readManagerServiceMapping = Maps.newConcurrentMap();

	private volatile boolean isStarted;

	public synchronized void start() {
		if (isStarted) {
			DSLogger.info("读取外部数据源已经启动!...");
			return;
		}
		Iterator<Entry<Integer, ReadService>> it = readManagerServiceMapping.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, ReadService> entry = it.next();
			ReadService readerManagerService = entry.getValue();
			readerManagerService.start();
		}
		isStarted = true;
	}

	public void initReadConfigCache(ASObject interfaceConfig, List<ASObject> dataSourceConfigList) {
		for (ASObject dataSourceConfig : dataSourceConfigList) {
			ReadConfig readConfig = changeReadConfig(interfaceConfig, dataSourceConfig);
			this.extDataSourceCache.putDataSourceConditionList(readConfig);
		}
	}

	private ReadConfig changeReadConfig(ASObject interfaceConfig, ASObject dataSourceConfig) {
		return changeReadConfig(interfaceConfig, dataSourceConfig, true);
	}

	private ReadConfig changeReadConfig(ASObject interfaceConfig, ASObject dataSourceConfig, boolean needDataSourceConfig) {
		ReadConfig config = new ReadConfig();
		config.setInterfaceId(interfaceConfig.get("id").toString());
		config.setDataSourceId(dataSourceConfig.get("id").toString());
		if (needDataSourceConfig) {
			config.setDataSourceConfig(JSONObject.fromObject(dataSourceConfig.get("config").toString()));
		}
		config.setInterfaceConfig(JSONObject.fromObject(interfaceConfig.get("config").toString()));
		config.setInterfaceType((Integer) interfaceConfig.get("type"));
		return config;
	}

	@Override
	public void addReadConfig(ASObject interfaceConfig, ASObject dataSourceConfig) {
		ReadConfig readConfig = changeReadConfig(interfaceConfig, dataSourceConfig);
		extDataSourceCache.putDataSourceConditionList(readConfig);
		Integer interFaceType = readConfig.getInterfaceType();
		ReadService readerManagerService = readManagerServiceMapping.get(interFaceType);
		Assert.notNull(readerManagerService, "类型KEY:" + interFaceType + "初始化未注册!");
		readerManagerService.addConfig(readConfig);
	}

	@Override
	public void addReaderManagerService(Integer key, ReadService readerManagerService) {
		Assert.notNull(key, "加入ReaderManagerService的KEY为空!");
		Assert.notNull(readerManagerService, "加入ReaderManagerService值为空!");
		readManagerServiceMapping.put(key, readerManagerService);
	}

	@Override
	public void deleteReadConfig(Integer freshFrequency, ASObject interfaceConfig, ASObject dataSourceConfig) {
		ReadConfig readConfig = changeReadConfig(interfaceConfig, dataSourceConfig, false);
		extDataSourceCache.putDataSourceConditionList(readConfig);
		Integer interFaceType = readConfig.getInterfaceType();
		ReadService readerManagerService = readManagerServiceMapping.get(interFaceType);
		Assert.notNull(readerManagerService, "类型KEY:" + interFaceType + "初始化未注册!");
		readerManagerService.removeConfig(freshFrequency, readConfig);
	}

	@Override
	public void editReadConfig(ASObject interfaceConfig, ASObject dataSourceConfig) {
		// 其余的直接修改更新配置即可
		ReadConfig readConfig = changeReadConfig(interfaceConfig, dataSourceConfig);
		extDataSourceCache.putDataSourceConditionList(readConfig);
		Integer interFaceType = readConfig.getInterfaceType();
		ReadService readerManagerService = readManagerServiceMapping.get(interFaceType);
		Assert.notNull(readerManagerService, "类型KEY:" + interFaceType + "初始化未注册!");
		readerManagerService.editConfig(readConfig);
	}

	public void editReadConfigByInterfaceConfig(Integer interFaceType, ASObject interfaceConfig, List<ASObject> dataSourceConfigList) {
		// 针对于刷新时间和主从 需要先删除再新增
		for (ASObject dataSourceConfig : dataSourceConfigList) {
			deleteReadConfig(interFaceType, interfaceConfig, dataSourceConfig);
		}
		
		for (ASObject dataSourceConfig : dataSourceConfigList) {
			addReadConfig(interfaceConfig, dataSourceConfig);
		}
		
		
	}

	@Override
	public ReadService getReadService(Integer interFaceType) {
		ReadService readService = readManagerServiceMapping.get(interFaceType);
		Assert.notNull(readService, "类型KEY:" + interFaceType + "初始化未注册!");
		return readService;
	}

	@Override
	public void editReadConfigOnlyFreshFreq(Integer oldFreshFrequency, Integer newFreshFrequency, ASObject interfaceConfig, List<ASObject> datasources) {
		ReadService readService = readManagerServiceMapping.get((Integer) interfaceConfig.get("type"));
		Assert.notNull(readService, "类型KEY:" + (Integer) interfaceConfig.get("type") + "初始化未注册!");
		// readService.editConfig(readConfig);
		readService.editFreshFrequencyOnly(newFreshFrequency, oldFreshFrequency);
		if (!CollectionUtils.isEmpty(datasources)) {
			for (ASObject datasource : datasources) {
				editReadConfig(interfaceConfig, datasource);
			}
		}

	}

}
