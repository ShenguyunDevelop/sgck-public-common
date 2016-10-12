package com.sgck.common.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MyBeanProcessor
{

	public static Object processColumn(ResultSet rs, int index, int propType, int scale) throws SQLException
	{

		if (rs.getObject(index) == null)
		{
			return null;
		}
		//
		switch (propType)
		{

			case Types.VARCHAR:
				return rs.getString(index);

			case Types.NUMERIC:
				switch (scale)
				{
					case 0:
						return rs.getInt(index);
					case -127:
						return rs.getDouble(index);

					default:
						return rs.getDouble(index);

				}

			case Types.FLOAT:
			case Types.DOUBLE:
			case Types.DECIMAL:
			{
				double v = rs.getDouble(index);
				if (v == (int) (v))
				{
					return (int) (v);
				}
				else
				{
					return v;
				}
			}

			case Types.TIME:
			case Types.TIMESTAMP:

				return rs.getTimestamp(index);

			default:

				return rs.getObject(index);
		}

	}

	//TOSEE 
	//get string datatype from int datatype
	private static String getDataType(int type, int scale)
	{
		String dataType = "";

		switch (type)
		{
			case Types.LONGVARCHAR: //-1
				dataType = "Long";
				break;
			case Types.CHAR: //1
				dataType = "Character";
				break;
			case Types.NUMERIC: //2
				switch (scale)
				{
					case 0:
						dataType = "Number";
						break;
					case -127:
						dataType = "Float";
						break;
					default:
						dataType = "Number";
				}
				break;
			case Types.VARCHAR: //12
				dataType = "String";
				break;
			case Types.DATE: //91
				dataType = "Date";
				break;
			case Types.TIMESTAMP: //93
				dataType = "Date";
				break;
			case Types.BLOB:
				dataType = "Blob";
				break;
			default:
				dataType = "String";
		}
		return dataType;
	}

}
