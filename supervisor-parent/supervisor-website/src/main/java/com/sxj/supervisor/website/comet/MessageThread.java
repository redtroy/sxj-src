package com.sxj.supervisor.website.comet;

import org.comet4j.core.CometEngine;

public abstract class MessageThread extends Thread {

	private CometEngine engine;

	private boolean flat = true;
	
	private String param;

	public CometEngine getEngine() {
		return engine;
	}

	public void setEngine(CometEngine engine) {
		this.engine = engine;
	}

	public boolean isFlat() {
		return flat;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	
}
