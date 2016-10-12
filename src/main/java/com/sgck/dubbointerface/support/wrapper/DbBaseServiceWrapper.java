package com.sgck.dubbointerface.support.wrapper;

import java.util.List;
import java.util.Map;

import com.sgck.dubbointerface.base.DbBaseService;
import com.sgck.dubbointerface.base.wrapper.DbBaseWrapperService;
import com.sgck.dubbointerface.domain.DBEntry;
import com.sgck.dubbointerface.exception.BaseServiceException;

public class DbBaseServiceWrapper implements DbBaseWrapperService {

	private DbBaseService dbBaseService;
	private String dbkey;

	public DbBaseServiceWrapper(DbBaseService dbBaseService, String defaultDbKey) {
		this.dbBaseService = dbBaseService;
		dbkey = defaultDbKey;
	}

	public <T> T excute(DBEntry entry) throws BaseServiceException {
		return dbBaseService.excute(entry);
	}

	public void update(String sql, Object... args) throws BaseServiceException {
		dbBaseService.update(dbkey, sql, args);
	}

	public void batchUpdate(String sql, List<Object[]> batchArgs) throws BaseServiceException {
		dbBaseService.batchUpdate(dbkey, sql, batchArgs);
	}

	public <T> T queryForObject(String sql, Class<T> c, Object... args) {
		return dbBaseService.queryForObject(dbkey, sql, c, args);
	}

	public <T> List<T> queryForList(String sql, Class<T> c, Object... args) {
		return dbBaseService.queryForList(dbkey, sql, c, args);
	}

	public void multipleUpdate(List<String> sqls, List<Object[]> batchArgs) throws BaseServiceException {
		dbBaseService.multipleUpdate(dbkey, sqls, batchArgs);
	}

	public void multipleUpdate(Map<String, List<Object[]>> batchArgs) throws BaseServiceException {
		dbBaseService.multipleUpdate(dbkey, batchArgs);
	}

	public <T> T queryForSingleField(String sql, Class<T> c, Object... args) {
		return dbBaseService.querySingleField(dbkey, sql, c, args);
	}

	public <T> List<T> querySingleColumnForList(String sql, Class<T> c, Object... args) {
		return dbBaseService.querySingleColumnForList(dbkey, sql, c, args);
	}

	public void multipleUpdate(String dbkey, Map<String, List<Object[]>> batchArgs) throws BaseServiceException {
		dbBaseService.multipleUpdate(dbkey, batchArgs);
	}

	public <T> List<T> queryForSingleFieldList(String sql, Class<T> c, Object... args) {
		// TODO Auto-generated method stub
		return dbBaseService.querySingleFieldList(dbkey, sql, c, args);
	}

	@Override
	public Long insertObjectAndGetAutoIncreaseId(String sql, String priKey, Object... args) throws BaseServiceException
	{
		return dbBaseService.insertObjectAndGetAutoIncreaseId(dbkey, sql, priKey, args);
	}

}
