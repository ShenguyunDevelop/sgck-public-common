package com.sgck.core.platform;

import java.util.Map;

public interface ServletProvider {
	
	public String getURI();
	/**
	 * 如果没有默认参数，可以返回Null
	 * @return
	 */
	public Map<String, String> getInitParams();
	
	
}
