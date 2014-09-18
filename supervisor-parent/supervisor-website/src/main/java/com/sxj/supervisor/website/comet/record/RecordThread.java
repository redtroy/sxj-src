package com.sxj.supervisor.website.comet.record;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.website.comet.MessageChannel;
import com.sxj.supervisor.website.comet.MessageThread;

public class RecordThread extends MessageThread {

	@Override
	public void run() {
		while (isFlat()) {
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
					getEngine().sendToAll(MessageChannel.RECORD_MESSAGE, message);
					iterator.remove();
					HierarchicalCacheManager.set(2, "comet_record",
							"record_push_message", messageList);
				}
				// HierarchicalCacheManager.evict(2, "comet_record",
				// "record_id");
			}

		}

	}

}
