package com.sgck.common.sg9k.domain;

import java.io.Serializable;
import java.util.Date;

public class DevStatus implements Serializable {

	private static final long serialVersionUID = -3164726638267328156L;
	
	private Long devid;
	private Date stime;
	private Date etime;
	private Integer status;
	private Long updateseq;
	private Integer event_status;
	
	public Long getDevid() {
		return devid;
	}
	public void setDevid(Long devid) {
		this.devid = devid;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getUpdateseq() {
		return updateseq;
	}
	public void setUpdateseq(Long updateseq) {
		this.updateseq = updateseq;
	}
	public Integer getEvent_status() {
		return event_status;
	}
	public void setEvent_status(Integer event_status) {
		this.event_status = event_status;
	}
	
	
}
