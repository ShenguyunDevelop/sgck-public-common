package com.sgck.data.source.manage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

@Component
public final class ExtDataThreadPool {

	public ExecutorService executorService = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

}
