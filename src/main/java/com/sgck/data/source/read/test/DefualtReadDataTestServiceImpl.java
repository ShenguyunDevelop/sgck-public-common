package com.sgck.data.source.read.test;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.service.ProcessReaderListener;
import com.sgck.data.source.service.ReadConfigService;
import com.sgck.data.source.service.ReadDataTestService;
import com.sgck.data.source.service.ReadService;
import com.sgck.data.source.task.ExtDsTestTask;

/**
 * 此接口用于测试连接 需要配合defualProcessReaderFroTestListener自己封装对数据的返回
 * 
 * @author 杨浩 2016年7月8日下午8:58:26
 */
@Component
public class DefualtReadDataTestServiceImpl implements ReadDataTestService {

	@Autowired
	private ReadConfigService readConfigService;
	@Resource
	private ProcessReaderListener defualProcessReaderFroTestListener;

	@Override
	public void readTest(ReadConfig readConfig,boolean isSave) {
		ReadService readService = readConfigService.getReadService(readConfig.getInterfaceType());
		readService.startReadTest(readConfig, defualProcessReaderFroTestListener,isSave);
	}

}
