package com.sgck.data.source.read.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import com.serotonin.modbus4j.code.DataSourceErrorCode;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.extdatasource.domain.ReadResult;
import com.sgck.common.log.DSLogger;
import com.sgck.core.exception.DSException;
import com.sgck.core.task.SGTaskService;
import com.sgck.core.task.TaskStatus;
import com.sgck.data.source.service.DefualProcessReaderListenerAdapter;
import com.sgck.data.source.task.ExtDsTestTask;
import flex.messaging.io.amf.ASObject;

@Component("defualProcessReaderFroTestListener")
public class DefualProcessReaderFroTestListener extends DefualProcessReaderListenerAdapter {
	@Autowired
	private SGTaskService sgTaskService;
	
	
	@Override
	public void requestResult(ReadConfig config, ReadResult result) throws DSException {
		ExtDsTestTask dsTask = (ExtDsTestTask)sgTaskService.getTask(config.getDataSourceId());
		ASObject taskResult = (ASObject) dsTask.getResult();
		
		if(dsTask == null){
			dsTask.setStatus(TaskStatus.STATUS_INVALID);
			taskResult.put("status", DataSourceErrorCode.ERROR);
			dsTask.setOnTest(false);
			return;
		}

		// 自己封装返回

		int state = result.getState();
		// 超时
		if (state == DataSourceErrorCode.READ_TIMEOUT || state == DataSourceErrorCode.READ_COMMON_ERROR) {
			dsTask.setStatus(TaskStatus.STATUS_TIMEOUT);
			taskResult.put("status", DataSourceErrorCode.TIMEOUT);
			taskResult.put("value", DataSourceErrorCode.TIMEOUT);
			dsTask.setOnTest(false);
			DSLogger.error("defualProcessReaderFroTestListener:READ_COMMON_ERROR:"+result.getMessage());
			return;
		} 
//		else if (state == DataSourceErrorCode.READ_COMMON_ERROR) {
//			dsTask.setStatus(TaskStatus.STATUS_FAILED);
//			taskResult.put("status", DataSourceErrorCode.OTHER);
//			taskResult.put("value", DataSourceErrorCode.OTHER);
//			dsTask.setOnTest(false);
//			DSLogger.error("defualProcessReaderFroTestListener:READ_COMMON_ERROR:"+result.getMessage());
//			return;
//		}
		else if(DataSourceErrorCode.isBelongConExc(state)){
			dsTask.setStatus(TaskStatus.STATUS_FAILED);
			taskResult.put("status", state);
			taskResult.put("value", state);
			dsTask.setOnTest(false);
			DSLogger.error("defualProcessReaderFroTestListener:MODBUS_CONNECTION_ERROR:"+result.getMessage());
			return;
		}
		computeResult(config, result);
		dsTask.setStatus(TaskStatus.STATUS_FINISH);
		taskResult.put("status", DataSourceErrorCode.NORMAL);
		taskResult.put("value", result.getResults().get("data"));
		dsTask.setOnTest(false);
	}

}
