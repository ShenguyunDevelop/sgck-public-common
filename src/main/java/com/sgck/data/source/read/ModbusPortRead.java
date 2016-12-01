package com.sgck.data.source.read;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.cache.ExtDataSourceCache;
import com.sgck.data.source.consts.ReadTypeConsts;
import com.sgck.data.source.manage.ExtDataThreadPool;
import com.sgck.data.source.receive.ReceiveService;
import com.sgck.data.source.service.ReadConfigService;

//modbus读取管理类
@Component
public class ModbusPortRead extends ReadAdapter {

	@Resource
	private ExtDataSourceCache extDataSourceCache;
	@Resource
	private ReceiveService modbusPortReceive;
	@Resource
	private ExtDataThreadPool managerThreadPool;
	@Resource
	private ReadConfigService readConfigService;

	@Override
	public void start() {
		List<ReadConfig> cacheList = extDataSourceCache.getAllDataSourceConditionList(ReadTypeConsts.ModBusType);
		startRead(cacheList);
	}

	@PostConstruct
	public void initRegister() {
		readConfigService.addReaderManagerService(ReadTypeConsts.ModBusType, this);
		extDataSourceCache.deleteDataSourceConditionList(ReadTypeConsts.ModBusType);
		receiveService = modbusPortReceive;
		threadPool = managerThreadPool;
	}

}