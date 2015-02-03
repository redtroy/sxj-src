package com.sxj.supervisor.model.comet;

public class MessageChannel {

	public static final String TOPIC_NAME = "topic1";

	public static final String RECORD_MESSAGE = "record_count_message";

	public static final String MEMBER_MESSAGE = "member_count_message";

	public static final String WEBSITE_RECORD_MESSAGE = "record_push_message_";

	public static final String WEBSITE_PAY_MESSAGE = "pay_push_message_";

	private MessageChannel() {
		super();
		// TODO Auto-generated constructor stub
	}

	// public static RTopic<String> initTopic() {
	// RedisTopics redis = RedisTopics.create();
	// RTopic<String> topic1 = redis.getTopic("topic1");
	// return topic1;
	// }

}
