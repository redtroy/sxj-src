package com.sxj.supervisor.website.comet.record;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.comet4j.core.CometEngine;

import com.sxj.cache.manager.HierarchicalCacheManager;

public class MessageThread extends Thread {

	private CometEngine engine;

	private boolean flat = true;

	public MessageThread(CometEngine engine) {
		super();
		this.engine = engine;
	}

	public boolean isFlat() {
		return flat;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	@Override
	public void run() {
		while (flat) {
			try {
				// 睡眠时间
				Thread.sleep(2000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// 获取消息内容

			List<String> messageList = null;
			Object cache = HierarchicalCacheManager.get(2, "comet_record",
					"record_push_message");
			if (cache instanceof ArrayList) {
				messageList = (List<String>) cache;
			}
			System.out.println("--------------------" + messageList.size());
			if (messageList != null) {
				for (Iterator<String> iterator = messageList.iterator(); iterator
						.hasNext();) {
					String message = iterator.next();
					// 开始发送
					engine.sendToAll(MessageChannel.RECORD_MESSAGE,
							message);
					iterator.remove();
					HierarchicalCacheManager.set(2, "comet_record",
							"record_push_message", messageList);
				}
				//HierarchicalCacheManager.evict(2, "comet_record", "record_id");
			}

		}

	}
}
