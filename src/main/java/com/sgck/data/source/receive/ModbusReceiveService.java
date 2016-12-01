package com.sgck.data.source.receive;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.serotonin.modbus4j.Modbus.StartedListener;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.ModbusMasterProfie;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.ProcessImage;
import com.serotonin.modbus4j.ProcessImageListener;
import com.serotonin.modbus4j.SgckBasicProcessImage;
import com.serotonin.modbus4j.code.DataSourceErrorCode;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;
import com.serotonin.modbus4j.exception.ModbusConnectionExeption;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.log.DSLogger;
import com.sgck.data.source.consts.ModbusDeviceType;
import com.sgck.data.source.service.ProcessReaderListener;
import com.sgck.data.source.service.RemoveConfigListener;
import com.sgck.request.ModbusRequestUtil;

/**
 * 基于modbus封装
 * 
 * @author 杨浩 2016年7月8日上午11:21:53
 */
public abstract class ModbusReceiveService extends ReceiveService {

	protected Map<String, ModbusSlaveSetHold> modbusSlaveSetHoldMapping = Maps.newConcurrentMap();
	protected Map<String, ModbusMasterHold> modbusMasterHoldMapping = Maps.newConcurrentMap();

	//private final int maxRetryTimes = 3;
	
	private final boolean isopenRetry = false;

	// public abstract void doCreateSlaveAndReceive(ReadConfig config);

	// public abstract void doCreateMasterAndReceive(ReadConfig config);

	public abstract ModbusSlaveSet createModbusSlaveSet(ReadConfig config);

	public abstract ModbusMaster createModbusMaster(ReadConfig config);

	public void doCreateSlaveAndReceive(ReadConfig config) {
		ModbusSlaveSet modbusSlaveSet = createModbusSlaveSet(config);
		registerModbusSlaveSetHold(config, modbusSlaveSet);
	}

	public void doCreateMasterAndReceive(ReadConfig config) {
		ModbusMaster master = createModbusMaster(config);
		registerModbusMasterSetHold(config, master);
	}

	@Override
	public void receive(ReadConfig config) {
		Integer isMaster = (Integer) config.getInterfaceConfig().get("isMaster");
		if (isMaster == ModbusDeviceType.MASTER) {
			receiveByModbusMaster(config);
		} else {
			receiveByModbusSlave(config);
		}
	}

	public void initRegister(ReadConfig config) {
		Integer isMaster = (Integer) config.getInterfaceConfig().get("isMaster");
		if (isMaster == ModbusDeviceType.MASTER) {
			if (!modbusMasterHoldMapping.containsKey(config.getInterfaceId())) {
				doCreateMasterAndReceive(config);
			}
			addReceiveModbusListener(config, modbusMasterHoldMapping.get(config.getInterfaceId()));
		} else {
			if (!modbusSlaveSetHoldMapping.containsKey(config.getInterfaceId())) {
				doCreateSlaveAndReceive(config);
			}
			addReceiveModbusListener(config, modbusSlaveSetHoldMapping.get(config.getInterfaceId()));
		}
	}

	public void registerModbusSlaveSetHold(ReadConfig config, final ModbusSlaveSet modbusSlaveSet) {
		final ModbusSlaveSetHold modbusSlaveSetHold = new ModbusSlaveSetHold();
		modbusSlaveSetHold.setModbusSlaveSet(modbusSlaveSet);
		modbusSlaveSetHoldMapping.put(config.getInterfaceId(), modbusSlaveSetHold);

		synchronized (modbusSlaveSetHold) {
			this.threadPool.executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						modbusSlaveSetHold.setModbusSlaveSet(modbusSlaveSet);
						modbusSlaveSet.start(new StartedListener() {
							@Override
							public void afterSatrt(boolean isStarted) {
								modbusSlaveSetHold.setStarted(isStarted);
							}
						});
					} catch (ModbusInitException e) {
						e.printStackTrace();
					}
				}
			});
			// readModbusDataBySlave(config, modbusSlaveSetHold);
		}
	}

	public void stopModbusSlave(String interfaceId) {
		if (modbusSlaveSetHoldMapping.containsKey(interfaceId)) {
			ModbusSlaveSetHold record = modbusSlaveSetHoldMapping.get(interfaceId);
			synchronized (record) {
				if(record.isStarted()){
					record.stop();
					//record.getModbusSlaveSet().stop();
				}
				record.getModbusSlaveSet().emptyProcessImage();
				modbusSlaveSetHoldMapping.remove(interfaceId);
			}
		}
	}

	public synchronized void removeModbusSlaveSetHold(String interfaceId) {
		if (modbusSlaveSetHoldMapping.containsKey(interfaceId)) {
			stopModbusSlave(interfaceId);
		}
	}

	public synchronized void removeModbusSlaveSetHold(final String interfaceId, int slaveId) {
		if (modbusSlaveSetHoldMapping.containsKey(interfaceId)) {
			ModbusSlaveSetHold record = modbusSlaveSetHoldMapping.get(interfaceId);
			final ModbusSlaveSet modbusSlaveSet = record.getModbusSlaveSet();
			modbusSlaveSet.removeProcessImage(slaveId);
			removeAfter(new RemoveConfigListener() {
				@Override
				public void after() {
					if (modbusSlaveSet.isProcessImageEmpty()) {
						removeModbusSlaveSetHold(interfaceId);
					}
				}
			});
		}
	}

	public ModbusSlaveSetHold getModbusSlaveSetHold(String interfaceId) {
		if (modbusSlaveSetHoldMapping.containsKey(interfaceId)) {
			return modbusSlaveSetHoldMapping.get(interfaceId);
		}
		return null;
	}

	public void receiveByModbusSlave(final ReadConfig config) {

		final ModbusSlaveSetHold modbusSlaveSetHold = modbusSlaveSetHoldMapping.get(config.getInterfaceId());
		if (null == modbusSlaveSetHold) {
			DSLogger.info("该串口配置已经被删除!接口ID:" + config.getInterfaceId());
			return;
		}
		// 判断是否open成功
		if (modbusSlaveSetHold.isStarted()) {
			// 数据接收
			readModbusDataBySlave(config, modbusSlaveSetHold);
			return;
		}
		// 如果open失败则加锁，进行重新设值
		synchronized (modbusSlaveSetHold) {
			if (!modbusSlaveSetHold.isStarted()) {

				//modbusSlaveSetHold.addRetryTimes();
				ModbusSlaveSet modbusSlaveSet = modbusSlaveSetHold.getModbusSlaveSet();
				if (null == modbusSlaveSet) {
					modbusSlaveSet = createModbusSlaveSet(config);
					modbusSlaveSetHold.setModbusSlaveSet(modbusSlaveSet);
					addReceiveModbusListener(config, modbusSlaveSetHold);
				}
				this.threadPool.executorService.execute(new Runnable() {
					@Override
					public void run() {
						try {
							modbusSlaveSetHold.getModbusSlaveSet().start(new StartedListener() {
								@Override
								public void afterSatrt(boolean isStarted) {
									modbusSlaveSetHold.setStarted(isStarted);
								}
							});
						} catch (ModbusInitException e) {
							e.printStackTrace();
						}
					}
				});
				readModbusDataBySlave(config, modbusSlaveSetHold);
			}
		}
	}

	/**
	 * 
	 */
	public void registerModbusMasterSetHold(ReadConfig config, ModbusMaster modbusMaster) {
		ModbusMasterHold modbusMasterHold = new ModbusMasterHold();
		modbusMasterHold.setModbusMaster(modbusMaster);
		modbusMasterHoldMapping.put(config.getInterfaceId(), modbusMasterHold);
		try {
			modbusMaster.init();
			modbusMasterHold.setStarted(true);
		} catch (ModbusInitException e) {
			e.printStackTrace();
		}
		// readModbusDataByMaster(config, modbusMasterHold);
	}

	public void stopModbusMaster(String interfaceId) {
		if (modbusMasterHoldMapping.containsKey(interfaceId)) {
			ModbusMasterHold record = modbusMasterHoldMapping.get(interfaceId);
			synchronized (record) {
				if(record.isStarted()){
					record.stop();
					//record.getModbusMaster().destroy();
				}
				modbusMasterHoldMapping.remove(interfaceId);
			}
		}
	}

	public synchronized void removeModbusMasterHold(final String interfaceId, int devNo) {

		if (modbusMasterHoldMapping.containsKey(interfaceId)) {
			final ModbusMaster modbusMaster = modbusMasterHoldMapping.get(interfaceId).getModbusMaster();
			modbusMaster.removeProfile(devNo);
			removeAfter(new RemoveConfigListener() {
				@Override
				public void after() {
					if (modbusMaster.isProfieEmpty()) {
						stopModbusMaster(interfaceId);
					}
				}
			});
		}

	}

	public ModbusMasterHold getModbusMasterHold(String interfaceId) {
		if (modbusMasterHoldMapping.containsKey(interfaceId)) {
			return modbusMasterHoldMapping.get(interfaceId);
		}
		return null;
	}

	public void receiveByModbusMaster(ReadConfig config) {
		ModbusMasterHold modbusMasterHold = modbusMasterHoldMapping.get(config.getInterfaceId());
		if (null == modbusMasterHold) {
			DSLogger.info("该串口配置已经被删除!接口ID:" + config.getInterfaceId());
			return;
		}
		if (modbusMasterHold.isStarted()) {
			// 接收数据
			readModbusDataByMaster(config, modbusMasterHold);
			return;
		}
		// 如果串口没有opn成功，重试成功后，进行数据接收
		synchronized (modbusMasterHold) {
			if (!modbusMasterHold.isStarted()) {
				modbusMasterHold.addRetryTimes();
				try {
					ModbusMaster modbusMaster = modbusMasterHold.getModbusMaster();
					if (null == modbusMaster) {
						modbusMaster = createModbusMaster(config);
						modbusMasterHold.setModbusMaster(modbusMaster);
						addReceiveModbusListener(config, modbusMasterHold);
					}
					modbusMasterHold.getModbusMaster().init();
					modbusMasterHold.setStarted(true);
					readModbusDataByMaster(config, modbusMasterHold);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 
	 * 2016年3月3日
	 * 
	 * @Description 监听modebus，作为主
	 * @param serialParameters
	 * @param slaveId
	 * @param modbusDataType
	 * @return
	 */
	public void addReceiveModbusListener(final ReadConfig config, final ModbusMasterHold modbusMasterHold) {
		int slaveId = (int) config.getDataSourceConfig().get("devNo");
		if (!modbusMasterHold.getModbusMaster().isContainProfile(slaveId)) {
			ModbusMasterProfie profile = new ModbusMasterProfie(slaveId,
					(int) config.getDataSourceConfig().get("dataAddr"),
					(int) config.getDataSourceConfig().get("funCode"),
					(int) config.getDataSourceConfig().get("valueType"));
			modbusMasterHold.getModbusMaster().addProfile(profile);
		}
	}

	/**
	 * 
	 * 2016年3月3日
	 * 
	 * @Description 监听modebus，作为从
	 * @param serialParameters
	 * @param slaveId
	 * @param modbusDataType
	 * @return
	 */
	public void addReceiveModbusListener(final ReadConfig config, final ModbusSlaveSetHold modbusSlaveSetHold) {
		int slaveId = (int) config.getDataSourceConfig().get("devNo");
		if (!modbusSlaveSetHold.getModbusSlaveSet().isContainProcessImage(slaveId)) {
			SgckBasicProcessImage processImage = createProcessImage(config);
			modbusSlaveSetHold.getModbusSlaveSet().addProcessImage(processImage);
		}
	}

	/**
	 * 
	 * 2016年3月2日
	 * 
	 * @Description 创建作为从的时候的监听
	 * @param slaveId
	 * @return
	 */
	public SgckBasicProcessImage createProcessImage(final ReadConfig config) {
		int slaveId = (int) config.getDataSourceConfig().get("devNo");
		SgckBasicProcessImage processImage = new SgckBasicProcessImage(slaveId);
		processImage.setAllowInvalidAddress(true);
		processImage.setInvalidAddressValue(Short.MIN_VALUE);
		processImage.setExceptionStatus((byte) 151);
		processImage.addListener(new ProcessImageListener() {

			@Override
			public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
			}

			@Override
			public void coilWrite(int offset, boolean oldValue, boolean newValue) {
			}

		});
		return processImage;
	}

	public void readModbusDataBySlave(final ReadConfig config, final ModbusSlaveSetHold modbusSlaveSetHold) {
		Future<?> future = threadPool.executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					int slaveId = config.getDataSourceConfig().getInt("devNo");
					ProcessImage processImage = modbusSlaveSetHold.getModbusSlaveSet().getProcessImage(slaveId);
					if (null == processImage) {
						DSLogger.info(
								"该设备号对应的数据源信息已经被删除![slaveId:" + slaveId + ",datasourceId:" + config.getDataSourceId());
						return;
					}
					synchronized (processImage) {
						SgckBasicProcessImage sgckProcessImage = (SgckBasicProcessImage) processImage;
						Number number = sgckProcessImage.getRegister(config.getDataSourceConfig().getInt("funCode"),
								config.getDataSourceConfig().getInt("dataAddr"),
								config.getDataSourceConfig().getInt("valueType"));
						if (null != number) {
							afterSuccessResult(config, number);
						} else {
							afterFailResult(config, DataSourceErrorCode.READ_COMMON_ERROR, "读取数据为空!");
						}
					}
				} catch (IllegalDataAddressException e) {
					afterFailResult(config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
				}
			}
		});
		try {
			future.get((int) config.getInterfaceConfig().get("timeout"), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			afterFailResult(config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
		} catch (ExecutionException e) {
			afterFailResult(config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
		} catch (TimeoutException e) {
			afterFailResult(config, DataSourceErrorCode.READ_TIMEOUT, e.getMessage());
		}
	}

	public void readModbusDataBySlaveForTest(final ProcessReaderListener processReadTestListener,
			final ReadConfig config, final ModbusSlaveSet modbusSlaveSet) {
		Future<?> future = threadPool.executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					int slaveId = config.getDataSourceConfig().getInt("devNo");
					ProcessImage processImage = modbusSlaveSet.getProcessImage(slaveId);
					Assert.notNull(processImage,
							"未初始化从设备监听对象sourceID:" + config.getDataSourceId() + ",slaveId:" + slaveId);
					synchronized (processImage) {
						SgckBasicProcessImage sgckProcessImage = (SgckBasicProcessImage) processImage;
						Number number = sgckProcessImage.getRegister(config.getDataSourceConfig().getInt("funCode"),
								config.getDataSourceConfig().getInt("dataAddr"),
								config.getDataSourceConfig().getInt("valueType"));
						if (null != number) {
							afterSuccessResult(processReadTestListener, config, number);
						} else {
							afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, "读取数据为空!");
						}
					}
				} catch (IllegalDataAddressException e) {
					afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
				}
			}
		});
		try {
			future.get((int) config.getInterfaceConfig().get("timeout"), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
		} catch (ExecutionException e) {
			afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
		} catch (TimeoutException e) {
			afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_TIMEOUT, e.getMessage());
		}
	}

	public void readModbusDataBySlaveForTest(final ProcessReaderListener processReadTestListener,
			final ReadConfig config, final SgckBasicProcessImage sgckProcessImage) {
		Future<?> future = threadPool.executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					int slaveId = config.getDataSourceConfig().getInt("devNo");
					Number number = sgckProcessImage.getRegister(config.getDataSourceConfig().getInt("funCode"),
							config.getDataSourceConfig().getInt("dataAddr"),
							config.getDataSourceConfig().getInt("valueType"));
					if (null != number) {
						afterSuccessResult(processReadTestListener, config, number);
					} else {
						afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, "读取数据为空!");
					}
				} catch (IllegalDataAddressException e) {
					afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
				}
			}
		});
		try {
			future.get((int) config.getInterfaceConfig().get("timeout"), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
		} catch (ExecutionException e) {
			afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
		} catch (TimeoutException e) {
			afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_TIMEOUT, e.getMessage());
		}
	}

	public void readModbusDataByMaster(final ReadConfig config, final ModbusMasterHold modbusMasterHold) {
		Future<?> future = threadPool.executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					// 读取数据放入到result
					Number[] number = ModbusRequestUtil.readModbusData(modbusMasterHold.getModbusMaster(),
							(int) config.getDataSourceConfig().get("devNo"),
							(int) config.getDataSourceConfig().get("dataAddr"),
							(int) config.getDataSourceConfig().get("funCode"),
							(int) config.getDataSourceConfig().get("valueType"));
					afterSuccessResult(config, number[0]);
				} catch (ModbusTransportException e) {
					afterFailResult(config, DataSourceErrorCode.READ_COMMON_ERROR, e.getSlaveId() + "-" + e.getMessage());
				}
				catch(ModbusConnectionExeption e){
					afterFailResult(config, e.getCode(), (int) config.getDataSourceConfig().get("devNo") + "- ErrorCode :" + e.getCode() + ";ErrorMessage:" + e.getMessage());
				}
			}
		});
		try {
			future.get((int) config.getInterfaceConfig().get("timeout"), TimeUnit.SECONDS); // 任务处理超时时间
		} catch (Exception e) {
			if (e instanceof TimeoutException) {
				afterFailResult(config, DataSourceErrorCode.READ_TIMEOUT, e.getMessage());
			} else {
				afterFailResult(config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
			}
		}
	}

	public void readModbusDataByMasterForTest(final ProcessReaderListener processReadTestListener,
			final ReadConfig config, final ModbusMaster modbusMaster) {
		Future<?> future = threadPool.executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					// 读取数据放入到result
					Number[] number = ModbusRequestUtil.readModbusData(modbusMaster,
							(int) config.getDataSourceConfig().get("devNo"),
							(int) config.getDataSourceConfig().get("dataAddr"),
							(int) config.getDataSourceConfig().get("funCode"),
							(int) config.getDataSourceConfig().get("valueType"));
					afterSuccessResult(processReadTestListener, config, number[0]);
				} catch (ModbusTransportException e) {
					afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR,
							e.getSlaveId() + "-" + e.getMessage());
				}catch(ModbusConnectionExeption e){
					afterFailResult(processReadTestListener,config, e.getCode(), (int) config.getDataSourceConfig().get("devNo") + "- ErrorCode :" + e.getCode() + ";ErrorMessage:" + e.getMessage());
				}
			}
		});
		try {
			future.get((int) config.getInterfaceConfig().get("timeout"), TimeUnit.SECONDS); // 任务处理超时时间
		} catch (Exception e) {
			if (e instanceof TimeoutException) {
				afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_TIMEOUT, e.getMessage());
			} else {
				afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR, e.getMessage());
			}
		}
	}

	@Override
	public void remove(ReadConfig config) {
		Integer isMaster = (Integer) config.getInterfaceConfig().get("isMaster");
		if (isMaster == ModbusDeviceType.MASTER) {
			removeModbusMasterHold(config.getInterfaceId(), config.getDataSourceConfig().getInt("devNo"));
		} else {
			removeModbusSlaveSetHold(config.getInterfaceId(), config.getDataSourceConfig().getInt("devNo"));
		}
	}

	// 测试连接 入参配置信息 第二个参数表示是否保存配置信息 （如果为否 则不会把新增的配置加入到配置池）
	public synchronized void receiveForTest(final ReadConfig config,
			final ProcessReaderListener processReadTestListener,boolean isSave) {

		Integer isMaster = (Integer) config.getInterfaceConfig().get("isMaster");
		if (isMaster == ModbusDeviceType.MASTER) {
			// 先从配置池查找是否已经存在的信息
			if (modbusMasterHoldMapping.containsKey(config.getInterfaceId())) {
				ModbusMasterHold modbusMasterHold = modbusMasterHoldMapping.get(config.getInterfaceId());
				if (modbusMasterHold.isStarted()) {
					readModbusDataByMasterForTest(processReadTestListener, config, modbusMasterHold.getModbusMaster());
					return;
				}
				//if (modbusMasterHold.getRetryTimes() <= maxRetryTimes) {
					synchronized (modbusMasterHold) {
						ModbusMaster modbusMaster = modbusMasterHold.getModbusMaster();
						if (null == modbusMaster) {
							modbusMaster = createModbusMaster(config);
							modbusMasterHold.setModbusMaster(modbusMaster);
						}
						startModbusMasterHold(config, processReadTestListener, modbusMasterHold);
						return;
					}
				//}
			}

			ModbusMasterHold modbusMasterHold = new ModbusMasterHold();
			ModbusMaster modbusMaster = createModbusMaster(config);
			modbusMasterHold.setModbusMaster(modbusMaster);
			modbusMasterHoldMapping.put(config.getInterfaceId(), modbusMasterHold);
			startModbusMasterHold(config, processReadTestListener, modbusMasterHold);
		} else {
			if (modbusSlaveSetHoldMapping.containsKey(config.getInterfaceId())) {
				final ModbusSlaveSetHold modbusSlaveSetHold = modbusSlaveSetHoldMapping.get(config.getInterfaceId());
				if (modbusSlaveSetHold.isStarted()) {
					recieveTestData(modbusSlaveSetHold, config, processReadTestListener,isSave);
					return;
				}
				//if (modbusSlaveSetHold.getRetryTimes() <= maxRetryTimes) {
					ModbusSlaveSet modbusSlaveSet = modbusSlaveSetHold.getModbusSlaveSet();
					if (null == modbusSlaveSet) {
						modbusSlaveSet = createModbusSlaveSet(config);
						modbusSlaveSetHold.setModbusSlaveSet(modbusSlaveSet);
					}
					synchronized (modbusSlaveSetHold) {
						startModbusSlaveSet(config, processReadTestListener, modbusSlaveSetHold,isSave);
						return;
					}
				//}
			}

			final ModbusSlaveSet modbusSlaveSet = createModbusSlaveSet(config);
			final ModbusSlaveSetHold modbusSlaveSetHold = new ModbusSlaveSetHold();
			modbusSlaveSetHold.setModbusSlaveSet(modbusSlaveSet);
			modbusSlaveSetHoldMapping.put(config.getInterfaceId(), modbusSlaveSetHold);
			synchronized (modbusSlaveSetHold) {
				startModbusSlaveSet(config, processReadTestListener, modbusSlaveSetHold,isSave);
			}
		}
	}

	private void recieveTestData(ModbusSlaveSetHold hold, ReadConfig config,
			final ProcessReaderListener processReadTestListener,boolean isSave) {
		ModbusSlaveSet modbusSlaveSet = hold.getModbusSlaveSet();
		try {
			addReceiveModbusListener(config, hold);
			readModbusDataBySlaveForTest(processReadTestListener, config, modbusSlaveSet);
		} finally {
			if (null != modbusSlaveSet && !isSave) {
				modbusSlaveSet.removeProcessImage((int) config.getDataSourceConfig().get("devNo"));
			}
		}
		return;
	}

	private void startModbusSlaveSet(final ReadConfig config, final ProcessReaderListener processReadTestListener,
			final ModbusSlaveSetHold modbusSlaveSetHold,final boolean isSave) {
		this.threadPool.executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					modbusSlaveSetHold.getModbusSlaveSet().start(new StartedListener() {
						@Override
						public void afterSatrt(boolean isStarted) {
							modbusSlaveSetHold.addRetryTimes();
							modbusSlaveSetHold.setStarted(isStarted);
							if (isStarted) {
								recieveTestData(modbusSlaveSetHold, config, processReadTestListener,isSave);
							}
						}
					});
				} catch (ModbusInitException e) {
					afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR,
							"从服务启动失败!" + e.getMessage());
				}
			}
		});
	}

	private void startModbusMasterHold(final ReadConfig config, final ProcessReaderListener processReadTestListener,
			final ModbusMasterHold modbusMasterHold) {
		this.threadPool.executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					modbusMasterHold.addRetryTimes();
					modbusMasterHold.getModbusMaster().init();
					modbusMasterHold.setStarted(true);
					readModbusDataByMasterForTest(processReadTestListener, config, modbusMasterHold.getModbusMaster());
				} catch (Exception e) {
					afterFailResult(processReadTestListener, config, DataSourceErrorCode.READ_COMMON_ERROR,
							"主服务启动失败!" + e.getMessage());
				}
			}
		});
	}

}
