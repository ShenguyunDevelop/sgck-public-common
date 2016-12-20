package com.sgck.data.source.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.serotonin.modbus4j.code.DataSourceErrorCode;
import com.google.common.collect.Lists;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.extdatasource.domain.ReadResult;
import com.sgck.common.log.DSLogger;
import com.sgck.common.log.SGLogger;
import com.sgck.core.exception.DSException;
import com.sgck.core.task.TaskStatus;
import com.sgck.data.source.cache.ExtDataSourceCacheIntegerface;
import com.sgck.data.source.task.ExtDsTestTask;

import flex.messaging.io.amf.ASObject;

@Component("defualProcessReaderListener")
public class DefualProcessReaderListener extends DefualProcessReaderListenerAdapter {

	@Resource
	private ExtDataSourceCacheIntegerface extDataSourceCache;
	
	@Resource
	private SGLogger SGLogger;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private int maxTmpSize = 10;

	private TmpDataLocker locker = new TmpDataLocker();
	
	
	@Override
	public void requestResult(ReadConfig config, ReadResult result) throws DSException {
		int state = result.getState();
		// 超时
		if (state == DataSourceErrorCode.READ_TIMEOUT) {
			throw new DSException(state, "主动读取数据超时!");
		} else if (state == DataSourceErrorCode.READ_COMMON_ERROR) {
			throw new DSException(state, result.getMessage() == null ? "主动读取数据失败!" : result.getMessage());
		}else if(DataSourceErrorCode.isBelongConExc(state)){
			throw new DSException(state, result.getMessage() == null ? "主动读取数据异常!" : result.getMessage());
		}
				
		computeResult(config, result);
		Date datatime = (Date)result.getResults().get("datatime");
		
		SGLogger.info("requestResult:"+config.getDataSourceId() + ";返回结果:[" + sdf.format(datatime) + "|" + result.getResults().get("data"));
		extDataSourceCache.putDataSourceCacheBySourceId(config.getDataSourceId(), result.getResults());
		saveTmp(config, result);

	}

	private void saveTmp(ReadConfig config, ReadResult result) {
		// 处理返回
		List<ASObject> queue = extDataSourceCache.getTmpData(config.getDataSourceId());
		synchronized (locker.fetchLockObject(config.getDataSourceId())) {
			if (null == queue) {
				queue = Lists.newArrayList();
			}
			if (queue.size() >= maxTmpSize) {
				// 清空
				queue.clear();
			}
			// 存入
			queue.add(result.getResults());
			extDataSourceCache.putTmpData(config.getDataSourceId(), queue);
		}
	}

	final class TmpDataLocker {
		private ConcurrentHashMap<String, Object> lockTable = new ConcurrentHashMap<String, Object>();

		public Object fetchLockObject(final String gpidAndStatType) {
			Object objLock = this.lockTable.get(gpidAndStatType);
			if (null == objLock) {
				objLock = new Object();
				Object prevLock = this.lockTable.putIfAbsent(gpidAndStatType, objLock);
				if (prevLock != null) {
					objLock = prevLock;
				}
			}
			return objLock;
		}
	}

}
