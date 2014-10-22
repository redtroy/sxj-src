package com.sxj.supervisor.website.comet.record;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.website.comet.MessageChannel;
import com.sxj.supervisor.website.comet.MessageThread;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

public class RecordThreadB extends MessageThread {

	private List<String> oldMessage;

	@Override
	public void run() {
			// 获取消息内容

			if(oldMessage==null){
				oldMessage=new ArrayList<String>();
			}
			List<String> messageList = null;
			String key = "record_push_message_b_";
			if (StringUtils.isNotEmpty(getParam())) {
				key = key + getParam();
			}
			Object cache = HierarchicalCacheManager.get(2, "comet_record", key);
			if (cache instanceof ArrayList) {
				messageList = (List<String>) cache;
			}
			if (messageList != null) {
				// System.out.println("--------------------" +
				// messageList.size());
			}
			SxjLogger.info("key=" + key, this.getClass());
			SxjLogger.info("##############" + messageList, this.getClass());
			// System.out.println("##############" + messageList);
			if (messageList != null) {
				for (Iterator<String> iterator = messageList.iterator(); iterator
						.hasNext();) {
					String message = iterator.next();
					SxjLogger.info("------------" + message, this.getClass());
					boolean flag = oldMessage.contains(message+key);
					SxjLogger.info("=============" + flag, this.getClass());
					// 开始发送
					if(!flag){
						SxjLogger.info("+++++++++++++++" + flag, this.getClass());
					getEngine().sendToAll(MessageChannel.RECORD_MESSAGE_B,
								message);
					oldMessage.add(message+key);
					}
					// iterator.remove();
					// HierarchicalCacheManager.set(2, "comet_record",
					// key, messageList);
				}
				// HierarchicalCacheManager.evict(2, "comet_record",
				// "record_id");

		}

	}

}
