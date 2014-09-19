package com.sxj.supervisor.manage.comet.rfid.apply;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.manage.comet.MessageChannel;
import com.sxj.supervisor.manage.comet.MessageThread;

public class RfidApplyMessage extends MessageThread {

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

			Long count = null;
			Object cache = HierarchicalCacheManager.get(2, "comet_rfid_apply",
					"rfid_apply_message");
			if (cache instanceof Long) {
				count = (Long) cache;
			}
			System.out.println("#################" + count);
			if (count != null && count > 0) {
				// 开始发送
				getEngine().sendToAll(MessageChannel.RFID_APPLY_MESSAGE, count);
				HierarchicalCacheManager.evict(2, "comet_rfid_apply",
						"rfid_apply_message");
			}

		}

	}
}
