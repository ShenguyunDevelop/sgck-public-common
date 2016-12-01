package com.sgck.data.source.read;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Function;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.common.log.DSLogger;
import com.sgck.data.source.consts.ReadTypeConsts;
import com.sgck.data.source.manage.ExtDataThreadPool;
import com.sgck.data.source.receive.ReceiveService;
import com.sgck.data.source.service.ProcessReaderListener;
import com.sgck.data.source.service.ReadPluginService;
import com.sgck.data.source.service.ReadService;

import net.sf.json.JSONObject;

public abstract class ReadAdapter implements ReadService {

	protected ExtDataThreadPool threadPool;
	// 根据组ID 和 组服务建立 映射关系
	public Map<Integer, ReadPluginService> groupReadServiceMapping = Maps.newConcurrentMap();

	protected ReceiveService receiveService;

	@Override
	public void startRead(List<ReadConfig> list) {
		// 从缓存中获取这类型的数据集合
		if (CollectionUtils.isEmpty(list)) {
			DSLogger.debug("接口协议下 暂未配置任何数据源");
			return;
		}
		try {
			// 进行分组逻辑处理
			ImmutableSet<ReadConfig> digits = ImmutableSet.copyOf(list);
			Function<ReadConfig, Integer> lengthFunction = new Function<ReadConfig, Integer>() {
				public Integer apply(ReadConfig config) {
					return (Integer) config.getInterfaceConfig().get("freshFrequency");
				}
			};
			ImmutableListMultimap<Integer, ReadConfig> digitsByFreshTime = Multimaps.index(digits, lengthFunction);

			for (Integer key : digitsByFreshTime.keySet()) {
				ImmutableList<ReadConfig> filterList = digitsByFreshTime.get(key);
				if (groupReadServiceMapping.containsKey(key)) {
					ReadPluginService groupService = groupReadServiceMapping.get(key);
					groupService.read(filterList, receiveService, key);
					for (ReadConfig readConfig : filterList) {
						receiveService.initRegister(readConfig);
					}
				} else {
					for (ReadConfig readConfig : filterList) {
						receiveService.initRegister(readConfig);
					}
					registerGroupReaderService(key, filterList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, ReadConfig> listToMap(List<ReadConfig> list) {
		return Maps.uniqueIndex(list, new Function<ReadConfig, String>() {
			@Override
			public String apply(ReadConfig input) {
				return input.getDataSourceId();
			}
		});
	}

	// 注册并开启组服务
	private void registerGroupReaderService(Integer key, List<ReadConfig> list) {
		final ReadPluginService readPluginService = new ReadPlugin();
		readPluginService.addRemoveListener(new RemovalConfigListener<String, ReadConfig>() {
			@Override
			public void onRemoval(RemovalNotification<String, ReadConfig> removalNotification) {
				ReadConfig old = removalNotification.getValue();
				int oldDevNo = old.getDataSourceConfig().getInt("devNo");
				if (!readPluginService.isContainDevNo(oldDevNo)) {
					receiveService.remove(old);
				}
				DSLogger.info("数据源ID:" + removalNotification.getKey() + ",已经被移除!");
			}
		});
		readPluginService.read(list, receiveService, key);
		groupReadServiceMapping.put(key, readPluginService);
		threadPool.executorService.execute(readPluginService);
	}

	@Override
	public synchronized void removeConfig(Integer freshFrequency, ReadConfig readConfig) throws IllegalArgumentException {
		if (groupReadServiceMapping.containsKey(freshFrequency)) {
			ReadConfig oldReadConfig = groupReadServiceMapping.get(freshFrequency).getReadConfig(readConfig.getDataSourceId());
			// int oldDevNo =
			// oldReadConfig.getDataSourceConfig().getInt("devNo");
			ReadPluginService readPluginService = groupReadServiceMapping.get(freshFrequency);
			readPluginService.removeConfig(readConfig.getDataSourceId());

			// this.receiveService.remove(oldReadConfig);
			// if (!readPluginService.isContainDevNo(oldDevNo)) {
			// receiveService.remove(oldReadConfig);
			// }
			// 如果无任何配置则删除此线程
			if (readPluginService.isReadConfigEmpty()) {
				readPluginService.stop();
				groupReadServiceMapping.remove(freshFrequency);
			}
		} else {
			throw new IllegalArgumentException("未在注册的groupService中 找到此刷新时间的服务！");
		}
	}

	// @Override
	// public synchronized void removeConfig(List<String> dataSourceIds, Integer
	// freshFrequency) throws IllegalArgumentException {
	// if (groupReadServiceMapping.containsKey(freshFrequency)) {
	// ReadPluginService readPluginService =
	// groupReadServiceMapping.get(freshFrequency);
	// readPluginService.removeConfig(dataSourceIds);
	// // 如果无任何配置则删除此线程
	// if (readPluginService.isReadConfigEmpty()) {
	// readPluginService.stop();
	// groupReadServiceMapping.remove(freshFrequency);
	// }
	//
	// } else {
	// throw new IllegalArgumentException("未在注册的groupService中 找到此刷新时间的服务！");
	// }
	// }

	@Override
	public synchronized void addConfig(ReadConfig readConfig) throws IllegalArgumentException {
		Integer freshFrequency = (Integer) readConfig.getInterfaceConfig().get("freshFrequency");
		Assert.notNull(freshFrequency, "刷新间隔为空!");
		if (groupReadServiceMapping.containsKey(freshFrequency)) {
			groupReadServiceMapping.get(freshFrequency).addConfig(readConfig);
			receiveService.initRegister(readConfig);
		} else {
			List<ReadConfig> list = Lists.newArrayList();
			list.add(readConfig);
			registerGroupReaderService(freshFrequency, list);
			receiveService.initRegister(readConfig);
		}
	}

	@Override
	public synchronized void editConfig(ReadConfig readConfig) throws IllegalArgumentException {
		Integer freshFrequency = readConfig.getInterfaceConfig().getInt("freshFrequency");
		if (groupReadServiceMapping.containsKey(freshFrequency)) {
			ReadConfig oldReadConfig = groupReadServiceMapping.get(freshFrequency).getReadConfig(readConfig.getDataSourceId());
			if (null != oldReadConfig && isNeedReLoadConfig(readConfig, oldReadConfig)) {
				removeConfig(freshFrequency, oldReadConfig);
				addConfig(readConfig);
				return;
			}
			groupReadServiceMapping.get(freshFrequency).editConfig(readConfig);
		} else {
			throw new IllegalArgumentException("未在注册的groupService中 找到此刷新时间的服务！");
		}

	}

	public synchronized void editFreshFrequencyOnly(Integer freshFrequency, Integer oldFreshFrequency) throws IllegalArgumentException {
		if (!groupReadServiceMapping.containsKey(oldFreshFrequency)) {
			throw new IllegalArgumentException("未在注册的groupService中 找到此刷新时间的服务！oldFreshFrequency:" + oldFreshFrequency);
		}
		ReadPluginService readPluginService = groupReadServiceMapping.get(oldFreshFrequency);
		List<ReadConfig> allReadConfig = readPluginService.getAllReadConfig();
		readPluginService.stop();
		groupReadServiceMapping.remove(oldFreshFrequency);
		if (!CollectionUtils.isEmpty(allReadConfig)) {
			if (groupReadServiceMapping.containsKey(freshFrequency)) {
				ReadPluginService newReadPluginService = groupReadServiceMapping.get(freshFrequency);
				newReadPluginService.addConfigAll(allReadConfig);
				return;
			}
			registerGroupReaderService(freshFrequency, allReadConfig);
		}
	}

	private boolean isNeedReLoadConfig(ReadConfig newReadConfig, ReadConfig oldReadConfig) {

		if (oldReadConfig.getInterfaceType() == ReadTypeConsts.ModBusType || oldReadConfig.getInterfaceType() == ReadTypeConsts.TcpIpType) {
			JSONObject newDataSource = newReadConfig.getDataSourceConfig();
			JSONObject oldDataSource = oldReadConfig.getDataSourceConfig();
			int newdevNo = newDataSource.getInt("devNo");
			int olddevNo = oldDataSource.getInt("devNo");

			if (newdevNo != olddevNo) {
				return true;
			}
		}
		return false;
	}

	public void startReadTest(ReadConfig readConfig, ProcessReaderListener processReadTestListener, boolean isSave) {
		receiveService.receiveForTest(readConfig, processReadTestListener, isSave);
	}
}
