package com.sgck.dubbointerface.domain;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class DBEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 数据源ID
	private String dataSourceId;
	// 查询的sql语句
	private String sql;
	// 对应sql对应的ID 此ID只在sql为空时候到后台自动匹配sql
	private Integer sqlId;
	// 动作类型 1表示更新(新增、更新、删除) 2表示查询单个数据 3表示查询列表数据
	private int actionType;
	// 可以有多个参数(在批量新增有用到)
	private List<ParamEntry> params = Lists.newArrayList();
	// 返回类型(如果没有设置 默认返回json)
	private Class rtnClass;
	
	public DBEntry(String dataSourceId, String sql, Integer sqlId, int actionType,Class rtnClass) {
		this.dataSourceId = dataSourceId;
		this.sql = sql;
		this.sqlId = sqlId;
		this.actionType = actionType;
		this.rtnClass = rtnClass;
	}
	
	public DBEntry(String dataSourceId, String sql, Integer sqlId, int actionType, List<ParamEntry> params,
			Class rtnClass) {
		this.dataSourceId = dataSourceId;
		this.sql = sql;
		this.sqlId = sqlId;
		this.actionType = actionType;
		this.params = params;
		this.rtnClass = rtnClass;
	}

	public DBEntry() {

	}

	public DBEntry(String dataSourceId, String sql, int actionType) {
		this.dataSourceId = dataSourceId;
		this.sql = sql;
		this.actionType = actionType;
	}

	public DBEntry(String dataSourceId, String sql, int actionType, Class rtnClass) {
		this.dataSourceId = dataSourceId;
		this.sql = sql;
		this.actionType = actionType;
		this.rtnClass = rtnClass;
	}

	public DBEntry(String dataSourceId, String sql, int actionType, List<ParamEntry> params) {
		this.dataSourceId = dataSourceId;
		this.sql = sql;
		this.actionType = actionType;
		this.params = params;
	}

	public DBEntry(String dataSourceId, String sql, int actionType, List<ParamEntry> params, Class rtnClass) {
		this.dataSourceId = dataSourceId;
		this.sql = sql;
		this.actionType = actionType;
		this.params = params;
		this.rtnClass = rtnClass;
	}

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public List<ParamEntry> getParams() {
		return params;
	}

	public void setParams(List<ParamEntry> params) {
		this.params = params;
	}

	public void add(ParamEntry paramEntry) {
		this.params.add(paramEntry);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getSqlId() {
		return sqlId;
	}

	public void setSqlId(Integer sqlId) {
		this.sqlId = sqlId;
	}

	public Class getRtnClass() {
		return rtnClass;
	}

	public void setRtnClass(Class rtnClass) {
		this.rtnClass = rtnClass;
	}

}
