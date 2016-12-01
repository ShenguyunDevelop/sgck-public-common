package com.sgck.data.source.consts;

import com.sgck.common.log.DSLogger;

/**
 * 操作系统类型
 * 
 * @author zhijun_xiao
 * @date 2016年7月12日 下午6:22:49
 */
public class LocalSystemType {
	// com口前缀，linux和linux不同
	public final static int environment;// 环境1：windows,2:linux(涉及pdf2swf路径问题)

	static {
		String osName = System.getProperty("os.name").toLowerCase();
		// 判断是否来自win
		if (osName.indexOf("win") != -1) {
			environment = 1;
		} else {
			environment = 2;
		}
	}

	/**
	 * 获取串口号
	 * 
	 * @param port
	 * @return
	 */
	public final static String getCommPort(int port) {
		String commortPort;
		if (environment == 1) {
			commortPort = "COM" + port;
		} else {
			commortPort = "/dev/ttyS0" + (--port);
			// if(port<=10){
			// commortPort = "/dev/ttyS0" + (--port);
			// }else{
			// commortPort = "/dev/ttyS0" + (--port);
			// }

		}
		DSLogger.info("串口号" + commortPort);
		return commortPort;
	}
}
