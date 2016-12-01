package com.sgck.data.source.receive;

import java.util.Date;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.extdatasource.domain.ReadResult;
import com.sgck.core.exception.DSException;
import com.sgck.data.source.manage.ExtDataThreadPool;
import com.sgck.data.source.service.ProcessReaderListener;
import com.sgck.data.source.service.RemoveConfigListener;

import flex.messaging.io.amf.ASObject;

public abstract class ReceiveService {
	protected ExtDataThreadPool threadPool;
	protected ProcessReaderListener processReadListener;

	// 测试连接 入参配置信息 第三个参数表示是否保存配置信息 （如果为否 则不会把新增的配置加入到配置池）
	public abstract void receiveForTest(ReadConfig config, ProcessReaderListener processReadTestListener,boolean isSave);

	public abstract void receive(ReadConfig config);

	public abstract void remove(ReadConfig config);

	public abstract void initRegister(ReadConfig config);

	public void removeAfter(RemoveConfigListener listener) {
		listener.after();
	}

	public ReadResult setErrorResult(int state, String msg) {
		ReadResult readResult = new ReadResult();
		readResult.setMessage(msg);
		readResult.setState(state);
		return readResult;
	}

	public ReadResult setSuccessResult(Object data) {
		ReadResult readResult = new ReadResult();
		ASObject result = new ASObject();
		result.put("data", data);
		result.put("datatime", new Date());
		readResult.setResults(result);
		return readResult;
	}

	public void afterSuccessResult(final ReadConfig config, Object data) {
		try {
			processReadListener.requestResult(config, setSuccessResult(data));
		} catch (DSException e) {
			e.printStackTrace();
		}
	}

	public void afterFailResult(final ReadConfig config, int state, String msg) {
		try {
			processReadListener.requestResult(config, setErrorResult(state, msg));
		} catch (DSException e) {
			e.printStackTrace();
		}
	}

	public void afterSuccessResult(ProcessReaderListener processReadTestListener, final ReadConfig config,
			Object data) {
		try {
			processReadTestListener.requestResult(config, setSuccessResult(data));
		} catch (DSException e) {
			e.printStackTrace();
		}
	}

	public void afterFailResult(ProcessReaderListener processReadTestListener, final ReadConfig config, int state,
			String msg) {
		try {
			processReadTestListener.requestResult(config, setErrorResult(state, msg));
		} catch (DSException e) {
			e.printStackTrace();
		}
	}

}
