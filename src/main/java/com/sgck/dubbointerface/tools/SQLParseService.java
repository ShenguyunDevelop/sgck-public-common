package com.sgck.dubbointerface.tools;

import com.sgck.dubbointerface.domain.DBEntry;

import flex.messaging.io.amf.ASObject;

public interface SQLParseService {

	public void parseInsertSelective(String tableName, ASObject obj, DBEntry entry);

	public void parseInsertSelective(String tableName, ASObject obj, DBEntry entry, SQLFieldConvert fieldConvert);

	public void parseUpdateSelective(String tableName, String paramKey, ASObject obj, DBEntry entry,
			SQLFieldConvert fieldConvert);

	public void parseUpdateSelective(String tableName, String paramKey, ASObject obj, DBEntry entry);
}
