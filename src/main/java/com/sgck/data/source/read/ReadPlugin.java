package com.sgck.data.source.read;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.CollectionUtils;

import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sgck.common.extdatasource.domain.ReadConfig;
import com.sgck.data.source.receive.ReceiveService;
import com.sgck.data.source.service.ReadPluginService;

public class ReadPlugin implements ReadPluginService {

	private Map<String, ReadConfig> mapping = Maps.newConcurrentMap();
	private Integer freshFrequency;
	private ReceiveService receiveService;
	private volatile boolean isInterrupted = false;

	private RemovalConfigListener<String, ReadConfig> removalListener;

	@Override
	public void run() {
		try {
			while (!isInterrupted) {
				Thread.sleep(freshFrequency * 1000);
				if (!mapping.isEmpty()) {
					synchronized (this) {
						for (String dataSource : mapping.keySet()) {
							try {
								receiveService.receive(mapping.get(dataSource));
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mapping.clear();
		}
	}

	@Override
	public void read(List<ReadConfig> list, ReceiveService receiveService, Integer freshFrequency) {
		if (!CollectionUtils.isEmpty(list)) {
			for (ReadConfig readConfig : list) {
				addConfig(readConfig);
			}
		}
		this.freshFrequency = freshFrequency;
		this.receiveService = receiveService;
	}

	@Override
	public synchronized void removeConfig(List<String> dataSources) {
		if (!mapping.isEmpty()) {
			synchronized (this) {
				for (String key : dataSources) {
					if (mapping.containsKey(key)) {
						ReadConfig readConfig = mapping.get(key);
						mapping.remove(key);
						this.removalListener.onRemoval(RemovalNotification.create(key, readConfig, RemovalCause.EXPLICIT));
					}
				}
			}
		}
	}

	@Override
	public void removeConfig(String dataSourceId) {
		if (!mapping.isEmpty()) {
			if (mapping.containsKey(dataSourceId)) {
				synchronized (this) {
					ReadConfig readConfig = mapping.get(dataSourceId);
					// receiveService.remove(readConfig);
					mapping.remove(dataSourceId);
					this.removalListener.onRemoval(RemovalNotification.create(dataSourceId, readConfig, RemovalCause.EXPLICIT));
				}
			}
		}
	}

	@Override
	public synchronized void addConfig(ReadConfig readConfig) {
		mapping.put(readConfig.getDataSourceId(), readConfig);
	}

	@Override
	public ReadConfig getReadConfig(String dataSourceId) {
		if (!mapping.isEmpty()) {
			if (mapping.containsKey(dataSourceId)) {
				return mapping.get(dataSourceId);
			}
		}
		return null;
	}

	@Override
	public void editConfig(ReadConfig readConfig) {
		if (!mapping.isEmpty()) {
			if (mapping.containsKey(readConfig.getDataSourceId())) {
				synchronized (this) {
					mapping.put(readConfig.getDataSourceId(), readConfig);
				}
			}
		}
	}

	@Override
	public boolean isReadConfigEmpty() {
		return mapping.isEmpty();
	}

	@Override
	public void stop() {
		if (!isInterrupted) {
			isInterrupted = true;
		}

	}

	@Override
	public List<ReadConfig> getAllReadConfig() {
		return Lists.newArrayList(mapping.values().iterator());
	}

	@Override
	public void addConfigAll(List<ReadConfig> list) {
		if (!CollectionUtils.isEmpty(list)) {
			for (ReadConfig readConfig : list) {
				addConfig(readConfig);
			}
		}
	}

	@Override
	public boolean isContainDevNo(int devNo) {
		Iterator<Entry<String, ReadConfig>> values = mapping.entrySet().iterator();
		while (values.hasNext()) {
			ReadConfig config = values.next().getValue();
			if (config.getDataSourceConfig().getInt("devNo") == devNo) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addRemoveListener(RemovalConfigListener<String, ReadConfig> listener) {
		this.removalListener = listener;
	}

}
