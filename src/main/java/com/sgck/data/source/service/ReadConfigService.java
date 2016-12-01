package com.sgck.data.source.service;

import java.util.List;

import flex.messaging.io.amf.ASObject;

public interface ReadConfigService {

	public void start();

	public void initReadConfigCache(ASObject interfaceConfig, List<ASObject> dataSourceConfigList);

	public void addReadConfig(ASObject interfaceConfig, ASObject dataSourceConfig);

	public void deleteReadConfig(Integer freshFrequency, ASObject interfaceConfig, ASObject dataSourceConfig);

	public void editReadConfig(ASObject interfaceConfig, ASObject dataSourceConfig);

	public void addReaderManagerService(Integer key, ReadService readerManagerService);

	public void editReadConfigByInterfaceConfig(Integer freshFrequency, ASObject interfaceConfig, List<ASObject> dataSourceConfigList);

	public void editReadConfigOnlyFreshFreq(Integer oldfreshFrequency, Integer newfreshFrequency, ASObject interfaceConfig, List<ASObject> datasources);

	public ReadService getReadService(Integer readType);

}
