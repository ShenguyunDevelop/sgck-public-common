package com.sgck.data.source.receive;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.base.ModbusUtils;
import com.serotonin.modbus4j.ip.IpParameters;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.manage.ExtDataThreadPool;
import com.sgck.data.source.service.ProcessReaderListener;

import net.sf.json.JSONObject;

/**
 * modbus tcp通信
 * 
 * @author 杨浩 2016年7月8日上午11:19:21
 */
@Component("modbusTcpReceive")
public class ModbusTcpReceive extends ModbusReceiveService {

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
		ModbusFactory modbusFactory = new ModbusFactory();
		ModbusSlaveSet modbusSlaveSet = modbusFactory.createTcpSlave(config.getInterfaceConfig().getInt("port"),false);
		return modbusSlaveSet;
	}

	@Override
	public ModbusMaster createModbusMaster(ReadConfig config) {
		JSONObject interfaceConfig = config.getInterfaceConfig();
		IpParameters ipParameters = ModbusUtils.createTcpIpparameter((String) interfaceConfig.get("ipAddr"),
				(int) interfaceConfig.get("port"));
		ModbusFactory modbusFactory = new ModbusFactory();
		ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
		return master;
	}

}
