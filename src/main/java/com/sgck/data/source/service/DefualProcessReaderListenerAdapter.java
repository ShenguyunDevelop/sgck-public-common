package com.sgck.data.source.service;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.extdatasource.domain.ReadResult;

import flex.messaging.io.amf.ASObject;

public abstract class DefualProcessReaderListenerAdapter implements ProcessReaderListener {

	/**
	 * 计算返回值
	 * 
	 * @param config
	 * @param result
	 *            TODO
	 */
	public void computeResult(ReadConfig config, ReadResult result) {
		// y=a*x+b
		// 根据扩大倍数magnify和偏移量offset 结合起点带宽startPointTapeWidth
		ASObject results = result.getResults();
		Number data = (Number) results.get("data");

		Number magnify = (Number) config.getDataSourceConfig().get("magnify");
		Number offset = (Number) config.getDataSourceConfig().get("offset");
		Number startPointTapeWidth = (Number) config.getDataSourceConfig().get("startPointTapeWidth");
		Number startPoint = (Number) config.getDataSourceConfig().get("startPoint");

		double cp = magnify.doubleValue() * data.doubleValue() + offset.doubleValue();
		
		double min = Math.abs(startPointTapeWidth.doubleValue()) - Math.abs(startPoint.doubleValue());
		
		double max = Math.abs(startPointTapeWidth.doubleValue()) + Math.abs(startPoint.doubleValue());
		
		
		if(cp >= min && cp <= max){
			results.put("data", startPoint.doubleValue());
		}else{
			results.put("data", cp);
		}

//		if (Math.abs(cp) <= Math.abs(startPointTapeWidth.doubleValue())) {
//			results.put("data", 0);
//		} else {
//			results.put("data", cp);
//		}
		result.setResults(results);
	}

}
