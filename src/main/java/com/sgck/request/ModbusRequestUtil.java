package com.sgck.request;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.base.ModbusUtils;
import com.serotonin.modbus4j.code.DataSourceErrorCode;
import com.serotonin.modbus4j.code.FunctionCode;
import com.serotonin.modbus4j.exception.ModbusConnectionExeption;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersRequest;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;

public final class ModbusRequestUtil {

	/**
	 * 
	 * 2016年3月2日
	 * 
	 * @Description 从modbus读取数据
	 * @param master
	 * @param slaveId
	 *            从设备id
	 * @param start
	 *            开始地址
	 * @param len
	 *            地址偏移量
	 * @param functionType
	 *            Function类型
	 * @param modbusDataType
	 *            modbus数据类型
	 * @param isOne
	 *            是否只有一个
	 * @return
	 * @throws ModbusTransportException
	 */
	public static Number[] readModbusData(ModbusMaster master, int slaveId, int start, int functionType,
			int modbusDataType) throws ModbusTransportException,ModbusConnectionExeption {
		Number[] modbusDatas;
		switch (functionType) {
		case FunctionCode.READ_HOLDING_REGISTERS:
			modbusDatas = readHoldingRegisters(master, slaveId, start, ModbusUtils.getLengthByDataType(modbusDataType),
					modbusDataType);
			break;
		default:
			modbusDatas = readInputRegisterTest(master, slaveId, start, ModbusUtils.getLengthByDataType(modbusDataType),
					modbusDataType);
		}
		return modbusDatas;
	}

	public static Number[] readHoldingRegisters(ModbusMaster master, int slaveId, int start, int len,
			int modbusDataType) throws ModbusTransportException , ModbusConnectionExeption{
		ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
		ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
		Byte exceCode = response.getExceptionCode();
		
		if(DataSourceErrorCode.isBelongConExc(exceCode)){
			throw new ModbusConnectionExeption("modbus readData Exception: Error Code:" + exceCode,exceCode);
		}
		
		switch (modbusDataType) {
		case ModbusFieldType.FLOAT:
			return response.getFloat(false);
		case ModbusFieldType.FLOAT_INVERS:
			return response.getFloat(true);
		case ModbusFieldType.LONG:
			return response.getLong(false);
		case ModbusFieldType.LONG_INVERS:
			return response.getLong(true);
		case ModbusFieldType.DOUBLE:
			return response.getDouble(false);
		case ModbusFieldType.DOUBLE_INVERS:
			return response.getDouble(true);
		default:
			return response.getShortDataNumber();
		}
	}

	public static Number[] readInputRegisterTest(ModbusMaster master, int slaveId, int start, int len,
			int modbusDataType) throws ModbusTransportException,ModbusConnectionExeption {
		ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
		ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);
		
		Byte exceCode = response.getExceptionCode();
		if(DataSourceErrorCode.isBelongConExc(exceCode)){
			throw new ModbusConnectionExeption("modbus readData Exception: Error Code:" + exceCode,exceCode);
		}
		
		switch (modbusDataType) {
		case ModbusFieldType.FLOAT:
			return response.getFloat(false);
		case ModbusFieldType.FLOAT_INVERS:
			return response.getFloat(true);
		case ModbusFieldType.LONG:
			return response.getLong(false);
		case ModbusFieldType.LONG_INVERS:
			return response.getLong(true);
		case ModbusFieldType.DOUBLE:
			return response.getDouble(false);
		case ModbusFieldType.DOUBLE_INVERS:
			return response.getDouble(true);
		default:
			return response.getShortDataNumber();
		}
	}
}
