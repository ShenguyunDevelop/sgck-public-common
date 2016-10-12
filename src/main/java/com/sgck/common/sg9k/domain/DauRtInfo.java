package com.sgck.common.sg9k.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * DAU缓存对象(与一个dauid对应)  -- 实时变化更新的值：最近通信时间等
 */
public class DauRtInfo implements Serializable {

	private static final long serialVersionUID = -7560096921663846029L;
	private Set<String> taskIds;	//广播时的前端任务id数组
	private Date lastComnunicationTime;	//最近的通信时间   现在只在上传硬件信息时记录了一下

	public Set<String> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(Set<String> taskIds) {
		this.taskIds = taskIds;
	}

	public Date getLastComnunicationTime() {
		return lastComnunicationTime;
	}

	public void setLastComnunicationTime(Date lastComnunicationTime) {
		this.lastComnunicationTime = lastComnunicationTime;
	}

}
