package com.sxj.supervisor.website.comet;

import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

public class MessageDropListener extends DropListener {

	@Override
	public boolean handleEvent(DropEvent arg0) {
		int count = MessageConnectListener.getCount();
		if (count <= 0) {
			MessageConnectListener.setCount(0);
		} else {
			MessageConnectListener.setCount(count - 1);
		}
		if (MessageConnectListener.getCount() == 0) {
			MessageConnectListener.getHelloAppModule().setFlat(false);
			MessageConnectListener.getHelloAppModule().interrupt();
		}
		return true;
	}

}
