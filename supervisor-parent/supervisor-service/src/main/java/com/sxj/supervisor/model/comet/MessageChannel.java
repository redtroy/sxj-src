package com.sxj.supervisor.model.comet;

public class MessageChannel
{
    
    public static final String TOPIC_NAME = "topic1";
    
    public static final String RECORD_MESSAGE = "record_count_message";
    
    public static final String MEMBER_MESSAGE = "member_count_message";
    
    public static final String MEMBER_PERFECT_MESSAGE = "member_perfect_message";
    
    public static final String MEMBER_PERFECT_MESSAGE_SET = "member_perfect_message_set";
    
    public static final String MEMBER_IMAGECHANGE_MESSAGE = "member_imagechange_message";
    
    public static final String MEMBER_IMAGECHANGE_MESSAGE_SET = "member_imagechange_message_set";
    
    public static final String WEBSITE_RECORD_MESSAGE = "record_push_message_";
    
    public static final String WEBSITE_PAY_MESSAGE = "pay_push_message_";
    
    public static final String WEBSITE_FINANCE_MESSAGE = "finance_push_message_";
    
    public static final String MEMBER_SYSTEM_MESSAGE_COUNT = "MEMBER_SYSTEM_MESSAGE_COUNT_";
    
    public static final String MEMBER_SYSTEM_MESSAGE_INFO = "MEMBER_SYSTEM_MESSAGE_INFO_";
    
    public static final String MEMBERTYPE_SYSTEM_MESSAGE_INFO = "MEMBERTYPE_SYSTEM_MESSAGE_INFO_";
    
    public static final String MEMBER_TRANS_MESSAGE_COUNT = "MEMBER_TRANS_MESSAGE_COUNT_";
    
    public static final String MEMBER_CONTRACT_MESSAGE_COUNT = "MEMBER_CONTRACT_MESSAGE_COUNT_";
    
    public static final String MEMBER_PAY_MESSAGE_COUNT = "MEMBER_PAY_MESSAGE_COUNT_";
    
    public static final String MEMBER_TENDER_MESSAGE_INFO = "MEMBER_TENDER_MESSAGE_INFO_";
    
    public static final String MEMBER_TENDER_MESSAGE_COUNT = "MEMBER_TENDER_MESSAGE_COUNT_";
    
    public static final String MEMBER_READTENDER_MESSAGE_KEYS = "MEMBER_READTENDER_MESSAGE_KEYS";
    
    public static final String MEMBER_UNREAD_TENDER_MESSAGE = "MEMBER_UNREAD_TENDER_MESSAGE_";
    
    private MessageChannel()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    // public static RTopic<String> initTopic() {
    // RedisTopics redis = RedisTopics.create();
    // RTopic<String> topic1 = redis.getTopic("topic1");
    // return topic1;
    // }
    
}
