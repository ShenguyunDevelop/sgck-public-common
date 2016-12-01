package com.sgck.data.source.service;

import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.task.ExtDsTestTask;

public interface ReadDataTestService {

	public void readTest(ReadConfig readConfig,boolean isSave);
}
