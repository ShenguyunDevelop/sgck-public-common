package com.sgck.core.rpc.server.json.model;

import java.util.List;

public class FuncCallInfo {
	private String domain;
	private String foo;
	private String params;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getFoo() {
		return foo;
	}
	public void setFoo(String foo) {
		this.foo = foo;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
}
