package com.sgck.data.source.service;

import java.util.List;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.read.RemovalConfigListener;
import com.sgck.data.source.receive.ReceiveService;

public interface ReadPluginService extends Runnable {
	public void read(List<ReadConfig> list, ReceiveService receiveService, Integer freshFrequency);

	// 根据数据源ID集合移除配置
	public void removeConfig(List<String> dataSources);

	// 根据接口ID移除配置
	public void removeConfig(String dataSourceId);

	// 新增数据源
	public void addConfig(ReadConfig readConfig);

	// 新增数据源
	public void addConfigAll(List<ReadConfig> list);

	// 修改数据源配置
	public void editConfig(ReadConfig readConfig);

	// 获取数据源信息
	public ReadConfig getReadConfig(String dataSourceId);

	// 获取所有的配置信息
	public List<ReadConfig> getAllReadConfig();

	// 判断数据源配置是否为空
	public boolean isReadConfigEmpty();

	// 中断线程
	public void stop();

	// 判断是否包含此设备号
	public boolean isContainDevNo(int devNo);

	// 新增移除监听
	public void addRemoveListener(RemovalConfigListener<String, ReadConfig> listener);

}
