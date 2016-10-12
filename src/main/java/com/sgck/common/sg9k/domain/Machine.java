package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class Machine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8417265900116556913L;

	private String id;
	private Integer parentid;// 在组织树中的编号
	private Integer type;
	private String name;
	private String xmlConfig;// 附加属性,使用XML扩展
	private String structInfo;// 机组结构信息
	private String backupInfo;// 备份信息
	private Integer status;// 0:正常机组，2:假删除机组
	private Integer displayOrder;// 显示顺序
	private Integer partitionLength = 12;// 子表分区间隔：...
	private String posChannelRelation;// 以xml格式存储的测点与通道的关联关系
	private String posChannelRelationBackUp; // 备份

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXmlConfig() {
		return xmlConfig;
	}

	public void setXmlConfig(String xmlConfig) {
		this.xmlConfig = xmlConfig;
	}

	public String getStructInfo() {
		return structInfo;
	}

	public void setStructInfo(String structInfo) {
		this.structInfo = structInfo;
	}

	public String getBackupInfo() {
		return backupInfo;
	}

	public void setBackupInfo(String backupInfo) {
		this.backupInfo = backupInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getPartitionLength() {
		return partitionLength;
	}

	public void setPartitionLength(Integer partitionLength) {
		this.partitionLength = partitionLength;
	}

	public String getPosChannelRelation() {
		return posChannelRelation;
	}

	public void setPosChannelRelation(String posChannelRelation) {
		this.posChannelRelation = posChannelRelation;
	}

	public String getPosChannelRelationBackUp() {
		return posChannelRelationBackUp;
	}

	public void setPosChannelRelationBackUp(String posChannelRelationBackUp) {
		this.posChannelRelationBackUp = posChannelRelationBackUp;
	}

}
