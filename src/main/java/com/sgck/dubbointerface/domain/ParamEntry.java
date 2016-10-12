package com.sgck.dubbointerface.domain;

import java.io.Serializable;
import java.util.ArrayList;

//入参集合
public class ParamEntry extends ArrayList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addParam(Object value) {
		this.add(value);
	}

	public ParamEntry() {

	}

	public ParamEntry(Object... objs) {
		super();
		if (null != objs) {
			for (Object obj : objs) {
				this.add(obj);
			}
		}
	}

}
