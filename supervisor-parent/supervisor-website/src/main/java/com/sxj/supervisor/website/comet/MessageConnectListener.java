package com.sxj.supervisor.website.comet;

import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

public class MessageConnectListener extends ConnectListener {

	private static MessageThread appModule;

	private Class<?> threddClass;

	private static int count = 0;

	private CometEngine engine;
	
	private String param;

	public MessageConnectListener(CometEngine engine, Class<?> threddClass,String param) {
		super();
		this.engine = engine;
		this.threddClass = threddClass;
		this.param=param;
	}

	public static MessageThread getAppModule() {
		return appModule;
	}

	public static int getCount() {
		return count;
	}

	public synchronized static void setCount(int count) {
		MessageConnectListener.count = count;
	}

	@Override
	public boolean handleEvent(ConnectEvent arg0) {
		// 是否启动
		if (count == 0) {
			if (appModule != null) {
				MessageConnectListener.getAppModule().setFlat(false);
				MessageConnectListener.getAppModule().interrupt();
			}
			try {
				appModule = (MessageThread) threddClass.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			appModule.setParam(param);
			appModule.setEngine(engine);
			appModule.setFlat(true);
			appModule.setDaemon(true);
			// 启动线程
			appModule.start();
		}
		setCount(count + 1);
		return true;
	}
}
