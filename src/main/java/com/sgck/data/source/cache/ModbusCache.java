package com.sgck.data.source.cache;

import java.util.HashMap;
import java.util.Map;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.sgck.core.cache.CacheService;

public class ModbusCache {

	public CacheService<ModbusHold> modbusCache = new CacheService<ModbusHold>(new RemovalListener<String, ModbusHold>() {

		@Override
		public void onRemoval(RemovalNotification<String, ModbusHold> arg0) {
			arg0.getValue().close();
			CacheService<ModbusProccess> cache = modbusProccessCache.get(arg0.getKey());
			if (null != cache) {
				cache.cleanAll();
			}
			cache.remove(arg0.getKey());
		}

	});

	public Map<Integer, CacheService<ModbusProccess>> modbusProccessCache = new HashMap<Integer, CacheService<ModbusProccess>>();

	public void addHold(ModbusHold modbusHold) throws Exception {

		if (!modbusHold.isStarted()) {
			modbusHold.open();
		}
		if (!modbusHold.isStarted()) {
			throw new RuntimeException("modbus 链接 串口失败！，不加入缓存！" + modbusHold.getCom());
		}
		modbusCache.put(modbusHold.getCom().toString(), modbusHold);
		modbusProccessCache.put(modbusHold.getCom(), new CacheService<ModbusProccess>(new RemovalListener<String, ModbusProccess>() {

			@Override
			public void onRemoval(RemovalNotification<String, ModbusProccess> arg0) {
				System.out.println(arg0.getKey() + "已经被删除了!");
			}

		}));

	}

	public void addModbusProccessCache(ModbusProccess proccess) {

		if (!modbusProccessCache.containsKey(proccess.getCom())) {
			throw new RuntimeException("modbus 链接 串口启动失败：" + proccess.getCom());
		}

		CacheService<ModbusProccess> cache = modbusProccessCache.get(proccess.getCom());
		cache.put(proccess.getSlaveId().toString(), proccess);

	}

	public ModbusProccess getModbusProccess(Integer com, Integer slaveId) {
		if (!modbusProccessCache.containsKey(com)) {
			throw new RuntimeException("modbus 链接 串口启动失败：" + com);
		}
		CacheService<ModbusProccess> cache = modbusProccessCache.get(com);
		return cache.getIfPresent(slaveId.toString());

	}

	public ModbusHold getModbusHold(Integer com) {
		return modbusCache.getIfPresent(com.toString());
	}

	public void removeModbusHold(Integer com) {
		modbusCache.remove(com.toString());
	}

	public void removeModbusProccess(Integer com, Integer slaveId) {
		CacheService<ModbusProccess> cache = modbusProccessCache.get(com);
		if (null != cache) {
			cache.remove(slaveId.toString());
		}
	}

}
