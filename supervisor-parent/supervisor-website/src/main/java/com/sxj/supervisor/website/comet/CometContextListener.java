package com.sxj.supervisor.website.comet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

public class CometContextListener implements ServletContextListener {

	public static final String RECORD_MESSAGE = "record_message";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		CometContext cc = CometContext.getInstance();
		cc.registChannel(RECORD_MESSAGE);// 注册应用的channel
		CometEngine engine = cc.getEngine();
		engine.addConnectListener(new MessageConnectListener(engine));
		engine.addDropListener(new MessageDropListener());
	}

}
