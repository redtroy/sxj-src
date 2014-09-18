package com.sxj.supervisor.manage.comet;

import org.comet4j.core.CometEngine;

public abstract class MessageThread extends Thread {

	private CometEngine engine;

	private boolean flat = true;

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
}
