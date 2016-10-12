package com.sgck.common.sg9k.domain;

import java.io.Serializable;

public class Sampler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -461046472081263265L;
	private String samplerId;// 型号+批次+序号
	private String ip;	//ip地址
	private String nameFromClient;// 界面上的显示名称
	private String model;// 采集器硬件型号
	private Integer socknum;// 该采集器所拥有的插槽数
	private Integer version;// 程序版本号
	private String descript;// 采集器的详细文本描述
	private String hardwareInfoFromDau;// DAU上传的软硬件信息
	private String hardwareInfoFromClient;// 客户端下发的软硬件信息
	private String basicConfig;// DAU上传的采集器基本配置
	
	private Integer status;// 状态信息

	public String getSamplerId() {
		return samplerId;
	}

	public void setSamplerId(String samplerId) {
		this.samplerId = samplerId;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNameFromClient() {
		return nameFromClient;
	}

	public void setNameFromClient(String nameFromClient) {
		this.nameFromClient = nameFromClient;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getSocknum() {
		return socknum;
	}

	public void setSocknum(Integer socknum) {
		this.socknum = socknum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	
	public String getHardwareInfoFromDau() {
		return hardwareInfoFromDau;
	}

	public void setHardwareInfoFromDau(String hardwareInfoFromDau) {
		this.hardwareInfoFromDau = hardwareInfoFromDau;
	}

	public String getHardwareInfoFromClient() {
		return hardwareInfoFromClient;
	}

	public void setHardwareInfoFromClient(String hardwareInfoFromClient) {
		this.hardwareInfoFromClient = hardwareInfoFromClient;
	}

	public String getBasicConfig() {
		return basicConfig;
	}

	public void setBasicConfig(String basicConfig) {
		this.basicConfig = basicConfig;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
