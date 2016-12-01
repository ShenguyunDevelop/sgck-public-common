package com.sgck.data.source.receive;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.base.ModbusUtils;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.consts.LocalSystemType;
import com.sgck.data.source.manage.ExtDataThreadPool;
import com.sgck.data.source.service.ProcessReaderListener;

import net.sf.json.JSONObject;

/**
 * modbus串口接收数据
 * 
 * @author 杨浩 2016年7月8日上午11:18:23
 */
@Component("modbusPortReceive")
public class ModbusPortReceive extends ModbusReceiveService {
	@Autowired
	ExtDataThreadPool managerThreadPool;
	@Autowired
	ProcessReaderListener defualProcessReaderListener;

	@PostConstruct
	public void init() {
		threadPool = managerThreadPool;
		processReadListener = defualProcessReaderListener;
	}

	
	@Override
	public ModbusSlaveSet createModbusSlaveSet(ReadConfig config) {
		JSONObject interfaceConfig = config.getInterfaceConfig();
		SerialParameters serialParameters = ModbusUtils.createModbusSerialparameter(LocalSystemType.getCommPort((Integer) interfaceConfig.get("com")),
				(Integer) interfaceConfig.get("checkBit"), (Integer) interfaceConfig.get("dataBit"),
				(Integer) interfaceConfig.get("stopBit"), (String) interfaceConfig.get("name"),
				(Integer) interfaceConfig.get("baudRate"));
		ModbusFactory modbusFactory = new ModbusFactory();
		return modbusFactory.createRtuSlave(serialParameters);
	}

	@Override
	public ModbusMaster createModbusMaster(ReadConfig config) {
		JSONObject interfaceConfig = config.getInterfaceConfig();
		SerialParameters serialParameters = ModbusUtils.createModbusSerialparameter(LocalSystemType.getCommPort((Integer) interfaceConfig.get("com")),
				(Integer) interfaceConfig.get("checkBit"), (Integer) interfaceConfig.get("dataBit"),
				(Integer) interfaceConfig.get("stopBit"), (String) interfaceConfig.get("name"),
				(Integer) interfaceConfig.get("baudRate"));
		ModbusFactory modbusFactory = new ModbusFactory();
		return modbusFactory.createRtuMaster(serialParameters);
	}


}
