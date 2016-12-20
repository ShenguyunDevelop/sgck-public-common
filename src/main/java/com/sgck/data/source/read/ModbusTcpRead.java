package com.sgck.data.source.read;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.cache.ExtDataSourceCacheIntegerface;
import com.sgck.data.source.consts.ReadTypeConsts;
import com.sgck.data.source.manage.ExtDataThreadPool;
import com.sgck.data.source.receive.ReceiveService;
import com.sgck.data.source.service.ReadConfigService;

//modbusTCP读取管理类
@Component
public class ModbusTcpRead extends ReadAdapter {

	@Resource
	private ExtDataSourceCacheIntegerface extDataSourceCache;
	@Resource
	private ReceiveService modbusTcpReceive;
	@Resource
	private ExtDataThreadPool managerThreadPool;
	@Resource
	private ReadConfigService readConfigService;

	@Override
	public void start() {
		List<ReadConfig> cacheList = extDataSourceCache.getAllDataSourceConditionList(ReadTypeConsts.TcpIpType);
		startRead(cacheList);
	}

	@PostConstruct
	public void initRegister() {
		readConfigService.addReaderManagerService(ReadTypeConsts.TcpIpType, this);
		extDataSourceCache.deleteDataSourceConditionList(ReadTypeConsts.TcpIpType);
		receiveService = modbusTcpReceive;
		threadPool = managerThreadPool;
	}

}
