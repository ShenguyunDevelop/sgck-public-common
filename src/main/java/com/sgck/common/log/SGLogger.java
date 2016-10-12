package com.sgck.common.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class SGLogger {
	private Logger logger;
	private String configPath;

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public void init() {
		Properties configProp = new Properties();
		try {
			configProp.load(new FileInputStream(configPath));
		} catch (Exception e) {
		}
		String logFilePath = configProp.getProperty("log4j.appender.f.File", "/data/logs/sgck_web/sgck_web.log");
		logger = Logger.getLogger(logFilePath);
		RollingFileAppender appender = new RollingFileAppender();
		try {
			appender.setFile(logFilePath, true, true, 100);
			appender.setBufferSize(Integer.valueOf(configProp.getProperty("log4j.appender.f.BufferSize", "100")));
			appender.setMaxBackupIndex(
					Integer.valueOf(configProp.getProperty("log4j.appender.f.MaxBackupIndex", "50")));
			appender.setMaxFileSize(configProp.getProperty("log4j.appender.f.MaxFileSize", "10MB"));
			appender.setThreshold(Level.toLevel(configProp.getProperty("log4j.appender.f.Threshold", "INFO")));
			appender.setImmediateFlush(
					Boolean.valueOf(configProp.getProperty("log4j.appender.f.ImmediateFlush", "true")));
			appender.setAppend(Boolean.valueOf(configProp.getProperty("log4j.appender.f.Append", "true")));
			appender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss}  [CLASS]:%c[MESSAGE]:%m%n"));
			//appender.setName("f");
			logger.addAppender(appender);

			String rootLogger = configProp.getProperty("log4j.rootLogger", "info,console");
			String level = rootLogger.split(",")[0];
			logger.setLevel(Level.toLevel(level));
			if (rootLogger.contains("console")) {
				//
				ConsoleAppender consoleAppender = new ConsoleAppender(
						new PatternLayout("%d{yyyy-MM-dd HH:mm:ss}  [CLASS]:%c[MESSAGE]:%m%n"),
						configProp.getProperty("log4j.appender.console.Target", "System.out"));
				//consoleAppender.setName("console");
				logger.addAppender(consoleAppender);
			}

		} catch (Exception e) {
		}

	}

	public void info(String msg) {
		if (logger != null) {
			logger.info(msg);
		}
	}

	public void debug(String msg) {

		if (logger != null) {
			logger.debug(msg);
		}
	}

	public void warn(String msg) {
		if (logger != null) {
			logger.warn(msg);
		}
	}

	public void error(String msg) {
		if (logger != null) {
			logger.error(msg);
		}
	}

	public void error(String msg, Exception e) {
		if (logger != null) {
			logger.error(msg, e);
		}
	}

}
