package com.sgck.dubbointerface.base.wrapper;

import com.sgck.dubbointerface.domain.DBEntry;
import com.sgck.dubbointerface.exception.BaseServiceException;

public interface DbService {
	// 保留原有的调用规则
	public <T> T excute(DBEntry entry) throws BaseServiceException;

}
