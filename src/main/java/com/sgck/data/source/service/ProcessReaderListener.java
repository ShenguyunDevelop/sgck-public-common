package com.sgck.data.source.service;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.extdatasource.domain.ReadResult;
import com.sgck.core.exception.DSException;

public interface ProcessReaderListener {

	public void requestResult(ReadConfig config, ReadResult result) throws DSException;

	public void computeResult(ReadConfig config, ReadResult result);

}
