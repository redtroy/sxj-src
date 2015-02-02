package com.sxj.redis.core.pubsub;

import java.util.HashMap;
import java.util.Map;

import com.sxj.redis.core.RTopic;
import com.sxj.redis.core.RTopics;
import com.sxj.redis.core.provider.RedisProvider;

public class RedisTopics implements RTopics
{
    private RedisProvider provider;
    
    private static Map<String, RedisTopic> topics = new HashMap<String, RedisTopic>();
    
    public RedisTopics(String configFile)
    {
        provider = new RedisProvider(configFile);
    }
    
    @Override
    public <M> RTopic<M> getTopic(String name)
    {
        if (topics.containsKey(name))
            return (RTopic<M>) topics.get(name);
        else
        {
            RedisTopic<M> redisTopic = new RedisTopic<M>(provider, name);
            topics.put(name, redisTopic);
            return redisTopic;
        }
    }
    
}
