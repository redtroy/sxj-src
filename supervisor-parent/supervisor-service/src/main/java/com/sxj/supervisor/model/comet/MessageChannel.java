package com.sxj.supervisor.model.comet;

import com.sxj.redis.advance.core.RTopic;
import com.sxj.redis.advance.topic.RedisTopics;

public class MessageChannel {

	public static final String RECORD_MESSAGE = "record_count_message";

	public static final String WEBSITE_RECORD_MESSAGE = "record_push_message_";

	public static RTopic<String> initTopic() {
		RedisTopics redis = RedisTopics.create();
		RTopic<String> topic1 = redis.getTopic("topic1");
		return topic1;
	}

}
