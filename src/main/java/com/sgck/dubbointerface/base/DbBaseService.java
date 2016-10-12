package com.sgck.dubbointerface.base;

import java.util.List;
import java.util.Map;

import com.sgck.dubbointerface.domain.DBEntry;
import com.sgck.dubbointerface.exception.BaseServiceException;

public interface DbBaseService{
	public static final int MODIFY_TYPE = 1;
	public static final int SINGLE_QUERY_TYPE = 2;
	public static final int MULTIPLE_QUERY_TYPE = 3;

	public void update(String dbkey, String sql, Object... args) throws BaseServiceException;

	public void batchUpdate(String dbkey, String sql, List<Object[]> batchArgs) throws BaseServiceException;

	public <T> T queryForObject(String dbkey, String sql, Class<T> c, Object... args);

	public <T> List<T> queryForList(String dbkey, String sql, Class<T> c, Object... args);

	public void multipleUpdate(String dbkey, List<String> sqls, List<Object[]> batchArgs) throws BaseServiceException;

	public <T> T querySingleField(String dbkey, String sql, Class<T> c, Object... args);
	
	public <T> List<T> querySingleFieldList(String dbkey, String sql, Class<T> c, Object... args);

	// 保留原有的调用规则
		public <T> T excute(DBEntry entry) throws BaseServiceException;
		
	public void multipleUpdate(String dbkey, Map<String, List<Object[]>> batchArgs) throws BaseServiceException;

	public <T> List<T> querySingleColumnForList(String dbkey, String sql, Class<T> c, Object... args);
	
	//新增插入一个对象，并返回这个对象的自增id 
	public Long insertObjectAndGetAutoIncreaseId(String dbkey, String sql, String priKey, Object... args) throws BaseServiceException;
}
