package com.sgck.core.platform;

import java.util.Set;

public interface SubSystemGather {
	public Set<String> getSubSystemNames();
	public void addSubSystem(String name,Object context);
	public Object getSubSystemContext(String systemName);
}
