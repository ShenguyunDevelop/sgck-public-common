package com.sgck.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sgck.common.log.DSLogger;




/**
 * 数据库操作管理类
 */
public class DBManager
{
	public static long incoming_invokes = 0;
	public static long incoming_query_invokes = 0;
	public static long incoming_update_invokes = 0;
	public static long lastmilliSec = 0;

	private static Boolean useJNDI = false;

	private static DBManager instance = new DBManager();

	public DBManager()
	{

	}

	public static DBManager getInstance()
	{
		return new DBManager();
	}

	//	private final  ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

	private final ThreadLocal<Boolean> autoTrans = new ThreadLocal<Boolean>()
	{
		protected synchronized Boolean initialValue()
		{
			return true;
		}
	};

	private final ThreadLocal<Boolean> autoCloseConn = new ThreadLocal<Boolean>()
	{
		protected synchronized Boolean initialValue()
		{
			return true;
		}
	};

	private final QueryRunner _qr = new QueryRunner();

	public QueryRunner getQR()
	{
		return _qr;
	}

	public void setUseJNDI(Boolean isJNDI)
	{
		useJNDI = isJNDI;
	}

	/**
	 * 查询
	 *
	 * @param <T>
	 * @param beanClass 类名XXX.class
	 * @param sql	   sql字符�?
	 * @param params
	 * @return
	 */
	public <T> List<T> queryList(Class<T> beanClass, String sql, Object... params) throws Exception/*SQLException,NamingException*/
	{
		incoming_invokes++;
		incoming_query_invokes++;
		long nowmilliSec = System.currentTimeMillis();

		if (nowmilliSec - lastmilliSec > 1000 * 60)
		{
			DSLogger.info("DBManager all request number:" + incoming_invokes + ",query numer:" + incoming_query_invokes + ",update number:" + incoming_update_invokes);
			lastmilliSec = nowmilliSec;
		}
		
		List<T> results = null;
		Connection con = getConnection();
		try
		{
			con.setAutoCommit(false);
			results = (List<T>) _qr.query(con, sql, _IsPrimitive(beanClass) ? new ColumnListHandler(beanClass) : new BeanListHandler(beanClass), params);
			con.commit();
		}
		catch (SQLException e)
		{
			if(null != e && (e.getMessage().contains("terminating connection") || e.getMessage().contains("Timed out")
					|| e.getMessage().contains("connection has been closed")))
			{
				DSLogger.error("db exception :" ,e);
				//DBConst.dbexceptioncnt++;					
			}
			throw e;
		}
		finally
		{
			if (con != null)
			{
				con.close();
			}
		}

		return results;
	}

	/**
	 * Read. 读取结果的第�?��记录
	 *
	 * @param <T>       the generic type
	 * @param beanClass the bean class
	 * @param sql	   the sql
	 * @param params	the params
	 * @return the t
	 */
	public <T> T queryItem(Class<T> beanClass, String sql, Object... params) throws Exception
	{
		incoming_invokes++;
		incoming_query_invokes++;
		
		long nowmilliSec = System.currentTimeMillis();

		if (nowmilliSec - lastmilliSec > 1000 * 60)
		{
			DSLogger.info("DBManager all request number:" + incoming_invokes + ",query numer:" + incoming_query_invokes
					+ ",update number:" + incoming_update_invokes);
			lastmilliSec = nowmilliSec;
		}
		
		T results = null;
		Connection con = getConnection();
		try
		{
			con.setAutoCommit(false);
			results = (T) _qr.query(con, sql, _IsPrimitive(beanClass) ? new ScalarHandler(beanClass) : new BeanHandler(beanClass), params);
			con.commit();
		}
		catch (SQLException e)
		{
			if(null != e && (e.getMessage().contains("terminating connection") || e.getMessage().contains("Timed out")
					|| e.getMessage().contains("connection has been closed")))
			{
				DSLogger.error("db exception :",e);
				//DBConst.dbexceptioncnt++;					
			}
			throw e;
		}
		finally
		{
			if (con != null)
			{
				con.close();
			}
		}

		return results;
	}

	/**
	 * 查询
	 *
	 * @param <T>
	 * @param beanClass 类名XXX.class
	 * @param sql	   sql字符�?
	 * @param params
	 * @return
	 */
	private <T> List<T> queryList(Connection con, Class<T> beanClass, String sql, Object... params) throws Exception/*SQLException,NamingException*/
	{
		List<T> results = null;
		con.setAutoCommit(false);
		results = (List<T>) _qr.query(con, sql,
				_IsPrimitive(beanClass) ? new ColumnListHandler(beanClass) : new BeanListHandler(beanClass), params);
		con.commit();

		return results;
	}

	/**
	 * Read. 读取结果的第�?��记录
	 *
	 * @param <T>       the generic type
	 * @param beanClass the bean class
	 * @param sql	   the sql
	 * @param params	the params
	 * @return the t
	 */
	private <T> T queryItem(Connection con, Class<T> beanClass, String sql, Object... params) throws Exception
	{
		T results = null;

		con.setAutoCommit(false);
		// 判断是否为原始类型，如果是就用_g_scaleHandler，否则根据beanClass参数生成�?��BeanHandler
		results = (T) _qr.query(con, sql, _IsPrimitive(beanClass) ? new ScalarHandler(beanClass) : new BeanHandler(beanClass),
				params);
		con.commit();

		return results;
	}

	
	
	public  static void testAtomic(final int orgId)
	{
		try
		{
				DBManager.getInstance().modify(new DBManagerAtomic() {
					public void modify(Connection connection) throws Exception {
						//删除该记录
						QueryRunner _qr = new QueryRunner();
						String sql = "delete from navi_tree where id = ?";
						_qr.update(connection,sql, orgId);
		               //需要更新用户的actions 
						sql = "update auth_user set actions = replace(actions,',"+ orgId + ",','') where POSITION(',"+orgId+ ",' in actions)>0  ";
						System.out.println("sql ->"+sql);
						_qr.update(connection,sql);
						
					}
				});
				
		}catch(Exception e){
			DSLogger.error("RemoveOrganization exception:", e);
		}
	}
	
	
	
	/**
	 * 需要封装事务处理机制
	 */
	
	public void modify(DBManagerAtomic atomic) throws Exception{
		Connection con = null;
		try
		{

			con = getConnection();
			con.setAutoCommit(false);
			
			atomic.modify(con);

			con.commit();

		}
		catch (SQLException e)
		{

			con.rollback();

			throw e;
		}
		finally
		{
			if (con != null)
			{
				con.close();
			}
		}

	}
	
	
	/**
	 * 执行INSERT/UPDATE/DELETE语句
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) throws Exception
	{
		incoming_invokes++;
		incoming_update_invokes++;

		long nowmilliSec = System.currentTimeMillis();

		if (nowmilliSec - lastmilliSec > 1000 * 60)
		{
			DSLogger.info("DBManager all request number:" + incoming_invokes + ",query numer:" + incoming_query_invokes
					+ ",update number:" + incoming_update_invokes);
			lastmilliSec = nowmilliSec;
		}
		
		Connection con = null;
		try
		{

			con = getConnection();
			con.setAutoCommit(false);
			_qr.update(con, sql, params);

			con.commit();

		}
		catch (SQLException e)
		{
			con.rollback();
			if(null != e && (e.getMessage().contains("terminating connection") || e.getMessage().contains("Timed out")
					|| e.getMessage().contains("connection has been closed")))
			{
				DSLogger.error("db exception :" ,e);
				//DBConst.dbexceptioncnt++;					
			}

			throw e;
		}
		finally
		{
			if (con != null)
			{
				con.close();
			}
		}

		return 0;
	}

	public int updateBatch(String sql, Object[][] params) throws Exception
	{
		Connection con = null;
		try
		{

			con = getConnection();
			con.setAutoCommit(false);

			_qr.batch(con, sql, params);

			con.commit();

		}
		catch (SQLException e)
		{

			con.rollback();

			throw e;
		}
		finally
		{
			if (con != null)
			{
				con.close();
			}
		}

		return 0;
	}

	public final Connection getConnection() throws SQLException, NamingException
	{

		Connection conn = null;
		try
		{
		if (!useJNDI)
		{
			return DBPool.getInstance().getConnection();
		}
		}
		catch (Exception e)
		{
			DSLogger.error("",e);
			
		}
//		try
//		{
//			if (conn == null || conn.isClosed())
//			{
//				if (useJNDI)
//				{
//					Context ctx = new InitialContext();
//					DataSource ds = (DataSource) ctx.lookup("oracle-jdbc");
//					conn = ds.getConnection();
//				}
//				else
//				{
//
//					conn = DBPool.getInstance().getConnection();
//				}
//
//				conn.setAutoCommit(false);
//
//				//				conns.set(conn);
//
//				if (conn.isClosed())
//				{
//					DSLogger.error("Java Thread[" + Thread.currentThread().getId() + "] get a closed connection!");
//				}
//
//			}
//		}
//		catch (NamingException ex)
//		{
//			throw ex;
//
//		}
//		catch (SQLException e)
//		{
//			throw e;
//		}

		return conn;
	}

	public final void commit() throws Exception
	{
		try
		{
			Connection con = getConnection();
			con.commit();
		}
		finally
		{
			/* 恢复至事务的默认处理模式 */
			setAutoTrans(true);
		}
	}

//	public final void rollback() throws Exception
//	{
//		try
//		{
//			Connection con = getConnection();
//			con.rollback();
//		}
//		finally
//		{
//			/* 恢复至事务的默认处理模式 */
//			setAutoTrans(true);
//		}
//	}

	public final void setAutoTrans(boolean isAutoTrans)
	{
		autoTrans.set(isAutoTrans);
	}

	public final void setAutoCloseConn(boolean isAutoClosed)
	{
		autoCloseConn.set(isAutoClosed);
	}

	/**
	 * The Constant PrimitiveClasses.
	 */
	private final List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>()
	{
		{
			add(Long.class);
			add(Integer.class);
			add(Float.class);
			add(Double.class);
			add(byte.class);
			add(byte[].class);
			add(Byte.class);
			add(Byte[].class);
			add(String.class);
			add(java.util.Date.class);
			add(java.sql.Date.class);
			add(java.sql.Timestamp.class);
			//add(oracle.sql.BLOB.class);
		}
	};

	// 判断是否为原始类�?
	private final boolean _IsPrimitive(Class<?> cls)
	{
		return cls.isPrimitive() || PrimitiveClasses.contains(cls);
	}

//	/* 该函数只适用于使用了REF Cursor的包函数 */
//	public ResultSet[] executePackageQueryRawCursor(String procName, Object[] parameters) throws Exception
//	{
//		Connection con = getConnection();
//
//		int parameterPoint = 1;
//		List<ProcedureInfo> procedureInfos;
//		ResultSet[] rss = null;
//		CallableStatement cstmt = null;
//
//		procName = procName.toUpperCase();
//
//		/*�?��区分是存储过程还是包函数 */
//		String[] procNameList = procName.split("[.]");
//
//		try
//		{
//			//			DBManager.getInstance().setAutoCloseConn(false);
//			con.setAutoCommit(false);
//
//			if (procNameList.length == 2)
//			{
//				procedureInfos = queryList(con, ProcedureInfo.class,
//						"select in_out as Way,data_type as TypeName from USER_ARGUMENTS where object_name = '" + procNameList[1]
//								+ "' and package_name = '" + procNameList[0] + "'order by Way");
//
//			}
//			else
//			{
//				procedureInfos = new ArrayList<ProcedureInfo>();
//			}
//
//			String procedureCallName = getProcedureCallName(procName, procedureInfos.size());
//
//			cstmt = con.prepareCall(procedureCallName);
//
//			int index = 1;
//
//			ArrayList<Integer> outPutIndexList = new ArrayList<Integer>();
//			ArrayList<Integer> retCurosrList = new ArrayList<Integer>();
//
//			for (ProcedureInfo tempPInfo : procedureInfos)
//			{
//				if ("IN".equals(tempPInfo.getWay()))
//				{
//
//					cstmt.setObject(index, parameters[index - 1]);
//
//				}
//				else
//				{
//					cstmt.registerOutParameter(index, getDataType(tempPInfo.getTypeName()));
//
//					if (!tempPInfo.getTypeName().equals("REF CURSOR"))
//					{
//						outPutIndexList.add(index);
//					}
//					else
//					{
//						retCurosrList.add(index);
//					}
//				}
//
//				index++;
//			}
//
//			cstmt.executeQuery();
//
//			con.commit();
//
//			if (outPutIndexList.size() > 0)
//			{
//				for (int i = 0; i < outPutIndexList.size(); i++)
//				{
//					parameters[outPutIndexList.get(i) - 1] = cstmt.getObject(outPutIndexList.get(i));
//				}
//			}
//
//			if (retCurosrList.size() > 0)
//			{
//				rss = new ResultSet[retCurosrList.size()];
//				for (int i = 0; i < retCurosrList.size(); i++)
//				{
//					rss[i] = (ResultSet) cstmt.getObject(retCurosrList.get(i));
//				}
//			}
//		}
//		catch (Exception e)
//		{
//
//			con.rollback();
//
//			throw e;
//		}
//		finally
//		{
//
//			con.close();
//		}
//
//		return rss;
//	}

//	public ResultSet executeProcedureQueryRaw(String procName, Object[] parameters) throws Exception
//	{
//		Connection con = getConnection();
//		int parameterPoint = 1;
//		List<ProcedureInfo> procedureInfos;
//		ResultSet rs = null;
//		CallableStatement cstmt = null;
//		procName = procName.toUpperCase();
//
//		/* �?��区分是存储过程还是包函数 */
//		String[] procNameList = procName.split("[.]");
//		try
//		{
//			con.setAutoCommit(false);
//
//			if (procNameList.length == 1) /* 存储过程 */
//			{
//				procedureInfos = queryList(con, ProcedureInfo.class,
//						"select in_out as Way,data_type as TypeName from USER_ARGUMENTS where object_name = '" + procNameList[0]
//								+ "' and package_name is null order by Way");
//			}
//			else if (procNameList.length == 2) /* 包函�?*/
//			{
//				procedureInfos = queryList(con, ProcedureInfo.class,
//						"select in_out as Way,data_type as TypeName from USER_ARGUMENTS where object_name = '" + procNameList[1]
//								+ "' and package_name = '" + procNameList[0] + "'order by Way");
//			}
//			else
//			{
//				procedureInfos = new ArrayList<ProcedureInfo>();
//			}
//
//			String procedureCallName = getProcedureCallName(procName, procedureInfos.size());
//
//			cstmt = con.prepareCall(procedureCallName);
//
//			int index = 1;
//			ArrayList<Integer> outPutIndexList = new ArrayList<Integer>();
//			for (ProcedureInfo tempPInfo : procedureInfos)
//			{
//				if ("IN".equals(tempPInfo.getWay()))
//				{
//
//					cstmt.setObject(index, parameters[index - 1]);
//				}
//				else
//				{
//					cstmt.registerOutParameter(index, getDataType(tempPInfo.getTypeName()));
//
//					if (!tempPInfo.getTypeName().equals("REF CURSOR")) /*  OracleTypes.CURSOR */
//					{
//						outPutIndexList.add(index);
//					}
//				}
//
//				index++;
//			}
//
//			rs = cstmt.executeQuery();
//
//			con.commit();
//
//			if (outPutIndexList.size() > 0)
//			{
//				for (int i = 0; i < outPutIndexList.size(); i++)
//				{
//					parameters[outPutIndexList.get(i) - 1] = cstmt.getObject(outPutIndexList.get(i));
//				}
//			}
//		}
//		catch (Exception e)
//		{
//
//			con.rollback();
//
//			throw e;
//		}
//		finally
//		{
//
//			con.close();
//		}
//
//		return rs;
//	}

//	public <T> List<T> executeProcedureQueryList(Class<T> beanClass, String procName, Object[] parameters) throws Exception
//	{
//		ResultSet rs = executeProcedureQueryRaw(procName, parameters);
//
//		BeanListHandler rsh = new BeanListHandler(beanClass);
//
//		List<T> result;
//
//		try
//		{
//			result = rsh.handle(rs);
//		}
//		finally
//		{
//			rs.getStatement().close();
//			rs = null;
//		}
//
//		return result;
//	}

//	public <T> List<T> executePackageQueryList(Class<T> beanClass, String procName, Object[] parameters) throws Exception
//	{
//		ResultSet[] rss = executePackageQueryRawCursor(procName, parameters);
//
//		List<T> result = null;
//		try
//		{
//			if (rss != null && rss.length > 0)
//			{
//				BeanListHandler rsh = new BeanListHandler(beanClass);
//				result = rsh.handle(rss[0]); /* 只取第一个结果集，如果要返回多个结果集，直接调用executePackageQueryRawCursor */
//			}
//		}
//		finally
//		{
//			rss[0].getStatement().close();
//			rss = null;
//		}
//
//		return result;
//	}

//	public <T> T executeProcedureQueryItem(Class<T> beanClass, String procName, Object[] parameters) throws Exception
//	{
//		ResultSet rs = executeProcedureQueryRaw(procName, parameters);
//
//		BeanHandler rsh = new BeanHandler(beanClass);
//
//		T result;
//
//		try
//		{
//			result = (T) rsh.handle(rs);
//		}
//		finally
//		{
//			rs.getStatement().close();
//			rs = null;
//		}
//
//		return result;
//	}

//	public <T> T executePackageeQueryItem(Class<T> beanClass, String procName, Object[] parameters) throws Exception
//	{
//		ResultSet[] rss = executePackageQueryRawCursor(procName, parameters);
//
//		T result = null;
//
//		try
//		{
//			if (rss != null && rss.length > 0)
//			{
//				BeanHandler rsh = new BeanHandler(beanClass);
//				result = (T) rsh.handle(rss[0]); /* 只取第一个结果集，如果要返回多个结果集，直接调用executePackageQueryRawCursor */
//			}
//		}
//		finally
//		{
//			rss[0].getStatement().close();
//			rss = null;
//		}
//
//		return result;
//	}

//	/**
//	 * 执行存储过程(更新，查询数据[�?��查询、非纪录集]，返回输出参数[非纪录集])
//	 *
//	 * @param procName   存储过程名称
//	 * @param parameters 参数对象数组
//	 * @return 输出参数对象数组
//	 * @throws Exception
//	 */
//	public Object[] executeProcedureUpdate(Connection con, String procName, Object[] parameters) throws Exception
//	{
//
//		CallableStatement cs = null;
//		Object[] returnVal = null;
//		List<ProcedureInfo> procedureInfos = null;
//
//		/*List<ProcedureInfo>  procedureInfos =  DBManager.query(ProcedureInfo.class, "select Syscolumns.isoutparam as Way,systypes.name as TypeName from sysobjects,syscolumns,systypes where systypes.xtype=syscolumns.xtype and syscolumns.id=sysobjects.id and sysobjects.name='"
//																+ procName + "' order by Syscolumns.isoutparam");*/
//
//		con.setAutoCommit(false);
//
//		procName = procName.toUpperCase();
//
//		/* �?��区分是存储过程还是包函数 */
//		String[] procNameList = procName.split("[.]");
//
//		if (procNameList.length == 1) /* 存储过程 */
//		{
//			procedureInfos = queryList(con, ProcedureInfo.class,
//					"select in_out as Way,data_type as TypeName from USER_ARGUMENTS where object_name = '" + procNameList[0]
//							+ "' and package_name is null order by Way");
//		}
//		else if (procNameList.length == 2) /* 包函�?*/
//		{
//			procedureInfos = queryList(con, ProcedureInfo.class,
//					"select in_out as Way,data_type as TypeName from USER_ARGUMENTS where object_name = '" + procNameList[0]
//							+ "' and package_name = '" + procNameList[1] + "'order by Way");
//
//		}
//		else
//		{
//			procedureInfos = new ArrayList<ProcedureInfo>();
//		}
//
//		// 获取 存储过程 调用全名
//		String fullPCallName = getProcedureCallName(procName, procedureInfos.size());
//
//		cs = con.prepareCall(fullPCallName);
//
//		int index = 1;
//
//		ArrayList<Integer> outPutIndexList = new ArrayList<Integer>();
//		for (ProcedureInfo tempPInfo : procedureInfos)
//		{
//			if ("IN".equals(tempPInfo.getWay()))
//			{
//				cs.setObject(index, parameters[index - 1]);
//
//			}
//			else
//			{
//				cs.registerOutParameter(index, getDataType(tempPInfo.getTypeName()));
//				outPutIndexList.add(index);
//			}
//			index++;
//		}
//
//		if (!cs.execute())
//		{
//			returnVal = new Object[outPutIndexList.size()];
//			// 取输 出参数的 返回�?
//			for (int i = 0; i < outPutIndexList.size(); i++)
//			{
//				returnVal[i] = cs.getObject(outPutIndexList.get(i));
//			}
//
//		}
//
//		cs.close();
//
//		return returnVal;
//	}

//	/**
//	 * 得到调用存储过程的全�?
//	 *
//	 * @param procName 存储过程名称
//	 * @return 调用存储过程的全�?
//	 * @throws Exception
//	 */
//	private String getProcedureCallName(String procName, int prametCount) throws Exception
//	{
//		String procedureCallName = "{call " + procName;
//		for (int i = 0; i < prametCount; i++)
//		{
//			if (0 == i)
//			{
//				procedureCallName = procedureCallName + "(?";
//			}
//			if (0 != i)
//			{
//				procedureCallName = procedureCallName + ",?";
//			}
//		}
//		procedureCallName = procedureCallName + ")}";
//		return procedureCallName;
//	}

	/**
	 * 得到数据类型的整型�?
	 *
	 * @param typeName 类型名称
	 * @return 数据类型的整型�?
	 */
	private static int getDataType(String typeName)
	{
		if (typeName.equals("varchar") || typeName.equals("varchar2"))
		{
			return Types.VARCHAR;
		}
		if (typeName.equals("nvarchar") || typeName.equals("nvarchar2"))
		{
			return Types.NVARCHAR;
		}
		if (typeName.equals("int"))
		{
			return Types.INTEGER;
		}
		if (typeName.equals("bit"))
		{
			return Types.BIT;
		}
		if (typeName.equals("float"))
		{
			return Types.FLOAT;
		}
		if (typeName.equals("date"))
		{
			return Types.DATE;
		}
		if (typeName.equals("timestamp"))
		{
			return Types.TIMESTAMP;
		}
		/*if (typeName.equals("REF CURSOR"))
		{
			return OracleTypes.CURSOR;
		}*/
		return 0;
	}
}
