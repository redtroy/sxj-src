package com.sxj.supervisor.model.comet;

import com.sxj.redis.advance.core.RTopic;
import com.sxj.redis.advance.topic.RedisTopics;

public class RfidChannel {

	public static final String RFID_APPLY_MESSAGE = "rfid_apply_message";

	public static final String RFID_MANAGER_LOGISTICS_MESSGAGE_REF = "rfid_manage_logistics_message_ref";

	public static RTopic<String> initTopic() {
		RedisTopics redis = RedisTopics.create();
		RTopic<String> topic1 = redis.getTopic("topic1");
		return topic1;
	}

}
