package com.sxj.supervisor.website.comet;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.comet4j.core.CometEngine;
import org.comet4j.core.event.BeforeConnectEvent;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import com.sxj.supervisor.website.comet.record.RecordThread;

public class MessageConnectListener extends ConnectListener {
	public static Map<String, ExecutorService> threadsMap = new WeakHashMap<>();

	public MessageConnectListener(CometEngine engine) {
		super();
		this.engine = engine;
	}

	private MessageThread appModule;

	private AtomicInteger count = new AtomicInteger(0);

	private CometEngine engine;

	private String connectId;


	public MessageThread getAppModule() {
		return appModule;
	}


	public String getConnectId() {
		return connectId;
	}


	@Override
	public boolean handleEvent(ConnectEvent arg0) {
		// connectId = arg0.getConn().getId();
//		String param = (String) arg0.getConn().getRequest().getSession()
//				.getAttribute("commetParam");
		System.out.println("connectId-----------" + arg0.getConn().getId());
		// 是否启动
			if (appModule != null) {
				this.getAppModule().setFlat(false);
				this.getAppModule().interrupt();
			}
			MessageThread appModule = new RecordThread();
			//appModule.setParam(param);
			appModule.setEngine(engine);
			appModule.setFlat(true);
			appModule.setDaemon(true);

			// 启动线程
			 ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
			 newScheduledThreadPool.scheduleAtFixedRate(appModule, 0, 2, TimeUnit.SECONDS);
			threadsMap.put(arg0.getConn().getId(), newScheduledThreadPool);
//			count.getAndIncrement();
//		setCount(count + 1);
		return true;
	}
}
