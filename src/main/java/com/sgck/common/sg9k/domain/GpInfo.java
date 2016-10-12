package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class GpInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8887049757482638138L;

	private String gpid;
	private String machineid;
	private Integer status;
	private Integer gptype;
	private Integer maindatatype;

	public Integer getMaindatatype() {
		return maindatatype;
	}

	public void setMaindatatype(Integer maindatatype) {
		this.maindatatype = maindatatype;
	}

	public String getGpid() {
		return gpid;
	}

	public void setGpid(String gpid) {
		this.gpid = gpid;
	}

	public String getMachineid() {
		return machineid;
	}

	public void setMachineid(String machineid) {
		this.machineid = machineid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getGptype() {
		return gptype;
	}

	public void setGptype(Integer gptype) {
		this.gptype = gptype;
	}

	@Override
	public String toString() {
		return "GpInfo [gpid=" + gpid + ", machineid=" + machineid + ", status=" + status + ", gptype=" + gptype
				+ ", maindatatype=" + maindatatype + "]";
	}
	
	
}
