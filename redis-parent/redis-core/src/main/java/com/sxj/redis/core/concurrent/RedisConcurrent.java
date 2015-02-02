package com.sxj.redis.core.concurrent;

import java.util.Date;

import com.sxj.redis.core.RAtomicLong;
import com.sxj.redis.core.RConcurrent;
import com.sxj.redis.core.provider.RedisProvider;

public class RedisConcurrent implements RConcurrent
{
    private RedisProvider provider;
    
    public RedisConcurrent(String configFile)
    {
        provider = new RedisProvider(configFile);
    }
    
    @Override
    public RAtomicLong getAtomicLong(String name)
    {
        return new RedisAtomicLong(provider, name);
    }
    
    @Override
    public RAtomicLong getAtomicLong(String name, long seconds)
    {
        return new RedisAtomicLong(provider, name, seconds);
    }
    
    @Override
    public RAtomicLong getAtomicLong(String name, Date time)
    {
        return new RedisAtomicLong(provider, name, time);
    }
    
}
