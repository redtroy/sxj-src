package com.sxj.supervisor.website.comet.record;

import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

public class MessageConnectListener extends ConnectListener {

	private static MessageThread helloAppModule;

	private static int count = 0;

	private CometEngine engine;

	public MessageConnectListener(CometEngine engine) {
		super();
		this.engine = engine;
	}

	public static MessageThread getHelloAppModule() {
		return helloAppModule;
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
			if (helloAppModule != null) {
				MessageConnectListener.getHelloAppModule().setFlat(false);
				MessageConnectListener.getHelloAppModule().interrupt();
			}
			helloAppModule = new MessageThread(engine);
			helloAppModule.setFlat(true);
			helloAppModule.setDaemon(true);
			// 启动线程
			helloAppModule.start();
		}
		setCount(count + 1);
		return true;
	}
}
