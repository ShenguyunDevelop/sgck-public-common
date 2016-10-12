package com.sgck.dubbointerface.base.wrapper;

import java.util.List;
import java.util.Map;

import com.sgck.dubbointerface.domain.DBEntry;
import com.sgck.dubbointerface.exception.BaseServiceException;

public interface DbBaseWrapperService extends DbService{

	public void update(String sql, Object... args) throws BaseServiceException;
	// 保留原有的调用规则
	public <T> T excute(DBEntry entry) throws BaseServiceException;
	
	public void batchUpdate(String sql, List<Object[]> batchArgs) throws BaseServiceException;

	public <T> T queryForObject(String sql, Class<T> c, Object... args);

	public <T> List<T> queryForList(String sql, Class<T> c, Object... args);

	public void multipleUpdate(List<String> sqls, List<Object[]> batchArgs) throws BaseServiceException;

	public void multipleUpdate(Map<String, List<Object[]>> batchArgs) throws BaseServiceException;
	
	public <T> List<T> queryForSingleFieldList(String sql, Class<T> c, Object... args);

	public <T> List<T> querySingleColumnForList(String sql, Class<T> c, Object... args);

	public <T> T queryForSingleField(String sql, Class<T> c, Object... args);

	public void multipleUpdate(String dbkey, Map<String, List<Object[]>> batchArgs) throws BaseServiceException;
	
	//新增插入一个对象，并返回这个对象的自增id 
	public Long insertObjectAndGetAutoIncreaseId(String sql, String priKey, Object... args) throws BaseServiceException;
}
