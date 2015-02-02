package com.sxj.redis.core.pubsub;

import com.sxj.redis.core.RTopic;
import com.sxj.redis.core.RTopics;
import com.sxj.redis.core.provider.RedisProvider;

public class RedisTopics implements RTopics
{
    private RedisProvider provider;
    
    public RedisTopics(String configFile)
    {
        provider = new RedisProvider(configFile);
    }
    
    @Override
    public <M> RTopic<M> getTopic(String name)
    {
        return new RedisTopic<M>(provider, name);
    }
    
}
