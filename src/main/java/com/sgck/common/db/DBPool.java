package com.sgck.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.sgck.common.log.DSLogger;

public class DBPool
{
	// postgreSQL....
	private static String _driver_class = "org.postgresql.Driver";
	private static String _URL;
	private static String _USER_NAME;
	private static String _PASSWORD;

	private BoneCP dbpool = null;

	private static DBPool instance;

	private DBPool()
	{
		//		java.net.URL properurl = Thread.currentThread().getContextClassLoader().getResource("com.sgck.db.properties");
		//			Properties p = new Properties();
		//			p.load(properurl.openStream()); //由URL载入配置文件 
		//			_URL = p.getProperty("url");
		//			_USER_NAME = p.getProperty("username");
		//			_PASSWORD = p.getProperty("pass");

		//			_URL = "jdbc:oracle:thin:@10.0.99.25:1521:gr8k";
		//			_USER_NAME = "gr8k";
		//			_PASSWORD = "2012sgck";
		/*_URL = InitConfig.getInstance().getPostgreConnStr();
		_USER_NAME = InitConfig.getInstance().getDBUserName();
		_PASSWORD = InitConfig.getInstance().getDBPwd();*/	
	}

	public static void initConfig(String driver,String url,String name,String pwd){
		DBPool._driver_class = driver;
		DBPool._URL = url;
		DBPool._USER_NAME = name;
		DBPool._PASSWORD = pwd;
	}
	
	public static synchronized DBPool getInstance()
	{
		if (instance == null)
		{
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			instance = new DBPool();
			try {
				instance.init();
			} catch (Exception e) {
				DSLogger.error("DBPool exception:",e);
			}
			if(TimeZone.getDefault().getID() != "UTC")
			{	
				DSLogger.error("timezone defalut is not utc");			
			}
		}
		
		return instance;
	}

	private void init() throws Exception
	{
		try
		{
			// load the database driver (make sure this is in your classpath!)
			Class.forName(_driver_class);
		}
		catch (Exception e)
		{
			DSLogger.error("DBPool init exception:",e);
			return;
		}
		try
		{
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(_URL); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername(_USER_NAME);
			config.setPassword(_PASSWORD);
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(50);
			config.setPartitionCount(2);
			config.setConnectionTimeoutInMs(1000);
			dbpool = new BoneCP(config);
		}
		catch (Exception e)
		{
			DSLogger.error("DBPool init exception:",e);
		}
	}

	public Connection getConnection() throws SQLException
	{
		return dbpool.getConnection();//设置了超时时间为1秒，如果分配资源失败则会返回�?��空连�?
		//return DriverManager.getConnection("jdbc:postgresql://10.0.99.72:5466/server_main", _USER_NAME, _PASSWORD);
	}
}
