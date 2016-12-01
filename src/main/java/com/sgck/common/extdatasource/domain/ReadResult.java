package com.sgck.common.extdatasource.domain;

import java.io.Serializable;

import flex.messaging.io.amf.ASObject;

public class ReadResult implements Serializable {

	private int state;
	private String message;
	private ASObject results;

	public void setResults(Number data) {
		ASObject results = new ASObject();
		results.put("data", data);
		this.results = results;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ASObject getResults() {
		return results;
	}

	public void setResults(ASObject results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ReadResult [state=" + state + ", message=" + message + ", results=" + results + "]";
	}

}
