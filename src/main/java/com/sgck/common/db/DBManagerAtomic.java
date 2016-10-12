package com.sgck.common.db;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

public interface DBManagerAtomic {

	public void modify(Connection connection) throws Exception ;
}
