package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class StatBaseCount implements Serializable {
	
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void addCount() {
		this.count++;
	}
	
	public void computerAvg() {
	}
}
