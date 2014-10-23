package com.sxj.supervisor.website.comet;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

public class MessageDropListener extends DropListener {

	private MessageConnectListener lis;

	// public MessageDropListener(MessageConnectListener lis) {
	// super();
	// this.lis = lis;
	//
	// }

	@Override
	public boolean handleEvent(DropEvent arg0) {
		System.out.println("dropId-----------" + arg0.getConn().getId());
		// String connectId = lis.getConnectId();
		String dropId = arg0.getConn().getId();
		// if (dropId.equals(connectId)) {
		Map<String, ExecutorService> map = MessageConnectListener.threadsMap;
		ExecutorService th = map.get(dropId);
		th.shutdown();

		// lis.getAppModule().interrupt();
		// }
		return true;
	}

}
