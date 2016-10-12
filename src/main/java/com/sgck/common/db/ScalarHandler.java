package com.sgck.common.db;

import java.sql.ResultSet;
import java.sql.SQLException;

//import oracle.sql.BLOB;

/**
 * 将结果集中某�?��记录的其中某�?��的数据存成Object
 *
 * @author wangke
 */
public class ScalarHandler<T> extends org.apache.commons.dbutils.handlers.ScalarHandler
{
	public ScalarHandler(Class<T> type)
	{
		super();
		this.type = type;
	}

	@Override
	public Object handle(ResultSet rs) throws SQLException
	{
		Object obj = super.handle(rs);
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
//			if (obj instanceof BLOB)
//			{
//				return ((BLOB) obj).getBytes(1, (int) ((BLOB) obj).length());
//			}
		}
		return obj;
	}

	private final Class<T> type;
}
