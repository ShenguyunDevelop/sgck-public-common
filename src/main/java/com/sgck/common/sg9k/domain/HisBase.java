package com.sgck.common.sg9k.domain;

import java.io.Serializable;
import java.util.Date;

public class HisBase implements Serializable {

	private String gpid;// 测点id
	private Date datatime;// 数据时间
	private Integer datatype;// 数据类型 0:历史数据 1:报警数据 2:启停机数据 3:黑匣子数据

	public String getGpid() {
		return gpid;
	}

	public void setGpid(String gpid) {
		this.gpid = gpid;
	}

	public Date getDatatime() {
		return datatime;
	}

	public void setDatatime(Date datatime) {
		this.datatime = datatime;
	}

	public Integer getDatatype() {
		return datatype;
	}

	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}

}
