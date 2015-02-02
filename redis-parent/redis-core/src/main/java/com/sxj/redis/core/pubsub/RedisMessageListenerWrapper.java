package com.sxj.redis.core.pubsub;

import redis.clients.jedis.JedisPubSub;

import com.sxj.redis.core.MessageListener;
import com.sxj.redis.core.serializer.JdkSerializer;
import com.sxj.redis.core.serializer.Serializer;

public class RedisMessageListenerWrapper<M> extends JedisPubSub
{
    private MessageListener<M> listener;
    
    private String channel;
    
    protected final static Serializer V_SERIALIZER = new JdkSerializer();
    
    public RedisMessageListenerWrapper(MessageListener<M> listener,
            String channel)
    {
        super();
        this.listener = listener;
        this.channel = channel;
    }
    
    @Override
    public void onMessage(String channel, String message)
    {
        listener.onMessage((M) V_SERIALIZER.deserialize(message));
    }
    
    @Override
    public void onPMessage(String pattern, String channel, String message)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onSubscribe(String channel, int subscribedChannels)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels)
    {
        // TODO Auto-generated method stub
        
    }
    
    public String getChannel()
    {
        return channel;
    }
}
