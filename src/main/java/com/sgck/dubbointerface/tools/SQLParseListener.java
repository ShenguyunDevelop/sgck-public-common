package com.sgck.dubbointerface.tools;

import java.util.List;

import com.sgck.dubbointerface.domain.DBEntry;

public interface SQLParseListener {

	public <T> void afterParse(DBEntry entry, String sql, List<T> list);
}
