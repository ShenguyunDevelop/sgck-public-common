package com.sgck.common.sg9k.domain;

import java.io.Serializable;
import java.util.Date;

public class DevEvent implements Serializable{
	private Long eventid;
	private Long devid;
	private Long posid;	//
	private Long groupid;
	private Date eventtime_begin;
	private Integer eventtype;
	private Integer eventcode;
	private String eventdesc;
	private Date datatime_begin;
	private Date datatime_end;
	private Integer sysstate;
	private Long updateseq;
	private String confirmor;
	private Date confirm_time;
	private Integer event_status;
	private String remark;
	private String trace_number;
	
	public Long getEventid() {
		return eventid;
	}
	public void setEventid(Long eventid) {
		this.eventid = eventid;
	}
	public Long getDevid() {
		return devid;
	}
	public void setDevid(Long devid) {
		this.devid = devid;
	}
	public Long getPosid() {
		return posid;
	}
	public void setPosid(Long posid) {
		this.posid = posid;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	public Date getEventtime_begin() {
		return eventtime_begin;
	}
	public void setEventtime_begin(Date eventtime_begin) {
		this.eventtime_begin = eventtime_begin;
	}
	public Integer getEventtype() {
		return eventtype;
	}
	public void setEventtype(Integer eventtype) {
		this.eventtype = eventtype;
	}
	public Integer getEventcode() {
		return eventcode;
	}
	public void setEventcode(Integer eventcode) {
		this.eventcode = eventcode;
	}
	public String getEventdesc() {
		return eventdesc;
	}
	public void setEventdesc(String eventdesc) {
		this.eventdesc = eventdesc;
	}
	public Date getDatatime_begin() {
		return datatime_begin;
	}
	public void setDatatime_begin(Date datatime_begin) {
		this.datatime_begin = datatime_begin;
	}
	public Date getDatatime_end() {
		return datatime_end;
	}
	public void setDatatime_end(Date datatime_end) {
		this.datatime_end = datatime_end;
	}
	public Integer getSysstate() {
		return sysstate;
	}
	public void setSysstate(Integer sysstate) {
		this.sysstate = sysstate;
	}
	public Long getUpdateseq() {
		return updateseq;
	}
	public void setUpdateseq(Long updateseq) {
		this.updateseq = updateseq;
	}
	public String getConfirmor() {
		return confirmor;
	}
	public void setConfirmor(String confirmor) {
		this.confirmor = confirmor;
	}
	public Date getConfirm_time() {
		return confirm_time;
	}
	public void setConfirm_time(Date confirm_time) {
		this.confirm_time = confirm_time;
	}
	public Integer getEvent_status() {
		return event_status;
	}
	public void setEvent_status(Integer event_status) {
		this.event_status = event_status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTrace_number() {
		return trace_number;
	}
	public void setTrace_number(String trace_number) {
		this.trace_number = trace_number;
	}
	
	
}
