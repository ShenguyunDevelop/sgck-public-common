package com.sgck.data.source.service;

import java.util.List;

import com.sgck.common.extdatasource.domain.ReadConfig;

//读取管理类接口
public interface ReadService {

	public void start();

	public void startRead(List<ReadConfig> list);

	public void removeConfig(Integer freshFrequency, ReadConfig readConfig) throws IllegalArgumentException;

	// public void removeConfig(List<String> dataSourceIds, Integer
	// freshFrequency) throws IllegalArgumentException;

	public void editConfig(ReadConfig readConfig) throws IllegalArgumentException;

	public void editFreshFrequencyOnly(Integer freshFrequency, Integer oldFreshFrequency) throws IllegalArgumentException;

	// 新增数据源
	public void addConfig(ReadConfig readConfig) throws IllegalArgumentException;

	public void startReadTest(ReadConfig readConfig, ProcessReaderListener processReadTestListener, boolean isSave);

}
