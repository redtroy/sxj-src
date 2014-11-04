package com.sxj.supervisor.website.comet;

import java.util.List;

import com.sxj.redis.advance.core.MessageListener;
import com.sxj.redis.service.comet.CometServiceImpl;
import com.sxj.util.logger.SxjLogger;

public class CometMessageListener implements MessageListener<String> {

	private MessageThread cometServer;

	public CometMessageListener(MessageThread cometServer) {
		this.cometServer = cometServer;
	}

	@Override
	public void onMessage(String msg) {
		List<String> cache = CometServiceImpl.get(msg);
		SxjLogger.debug("Sending Message to Comet Client:" + cache, getClass());
		if (cache != null && cache.size() > 0) {
			cometServer.send(msg, cache);
		}

	}
}
