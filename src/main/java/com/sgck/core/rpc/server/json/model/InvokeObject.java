package com.sgck.core.rpc.server.json.model;

public class InvokeObject {
	ClientInfo clientInfo;
	FuncCallInfo[] rpcInfo;
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	public FuncCallInfo[] getRpcInfo() {
		return rpcInfo;
	}
	public void setRpcInfo(FuncCallInfo[] rpcInfo) {
		this.rpcInfo = rpcInfo;
	}
}
