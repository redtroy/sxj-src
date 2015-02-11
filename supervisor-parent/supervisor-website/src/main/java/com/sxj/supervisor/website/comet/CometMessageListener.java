package com.sxj.supervisor.website.comet;

import java.util.List;

import com.sxj.redis.core.MessageListener;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.comet.RfidChannel;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.logger.SxjLogger;

public class CometMessageListener implements MessageListener<String> {

	private MessageThread cometServer;

	public CometMessageListener(MessageThread cometServer) {
		this.cometServer = cometServer;
	}

	@Override
	public void onMessage(String msg) {
		if (msg.equals(MessageChannel.RECORD_MESSAGE)
				|| msg.equals(RfidChannel.RFID_APPLY_MESSAGE)
				|| msg.equals(MessageChannel.MEMBER_MESSAGE)) {
			Long count = CometServiceImpl.getCount(msg);
			SxjLogger.debug("Sending Message to Comet Client:" + count,
					getClass());
			if (count > 0) {
				cometServer.send(msg, count);
			}
		} else {
			List<String> cache = CometServiceImpl.get(msg);
			SxjLogger.debug("Sending Message to Comet Client:" + cache,
					getClass());
			if (cache != null && cache.size() > 0) {
				cometServer.send(msg, cache);
			}
		}
	}
}
