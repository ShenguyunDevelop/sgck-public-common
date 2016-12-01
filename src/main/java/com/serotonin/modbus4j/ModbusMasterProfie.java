package com.serotonin.modbus4j;

public class ModbusMasterProfie {

	private int slaveId;
	private int dataAddr;
	private int funCode;
	private int valueType;

	public ModbusMasterProfie() {

	}

	public ModbusMasterProfie(int slaveId, int dataAddr, int funCode, int valueType) {
		this.slaveId = slaveId;
		this.dataAddr = dataAddr;
		this.funCode = funCode;
		this.valueType = valueType;
	}

	public int getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(int slaveId) {
		this.slaveId = slaveId;
	}

	public int getDataAddr() {
		return dataAddr;
	}

	public void setDataAddr(int dataAddr) {
		this.dataAddr = dataAddr;
	}

	public int getFunCode() {
		return funCode;
	}

	public void setFunCode(int funCode) {
		this.funCode = funCode;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}
}