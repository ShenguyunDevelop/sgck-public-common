package com.sgck.common.db;

import java.sql.ResultSet;
import java.sql.SQLException;

//import oracle.sql.BLOB;

/**
 * 结果集中某一列的数据存放到List中�?
 *
 * @author wangke
 */
public class ColumnListHandler<T> extends org.apache.commons.dbutils.handlers.ColumnListHandler
{
	private final Class<T> type;

	public ColumnListHandler(Class<T> type)
	{
		super();
		this.type = type;
	}

	protected Object handleRow(ResultSet rs) throws SQLException
	{
		Object obj = super.handleRow(rs);
		if (obj instanceof Number)
		{
			if (type.isAssignableFrom(Integer.class))
			{
				return ((Number) obj).intValue();
			}
			else if (type.isAssignableFrom(Long.class))
			{
				return ((Number) obj).longValue();
			}
			else if (type.isAssignableFrom(Float.class))
			{
				return ((Number) obj).floatValue();
			}
			else if (type.isAssignableFrom(Double.class))
			{
				return ((Number) obj).doubleValue();
			}
			else if (type.isAssignableFrom(Byte.class))
			{
				return ((Number) obj).byteValue();
			}
		}
		else if (type.isAssignableFrom(byte[].class) || type.isAssignableFrom(Byte[].class))
		{
			/*if (obj instanceof BLOB)
			{
				return ((BLOB) obj).getBytes(1, (int) ((BLOB) obj).length());
			}*/
		}
		return obj;
	}
}
