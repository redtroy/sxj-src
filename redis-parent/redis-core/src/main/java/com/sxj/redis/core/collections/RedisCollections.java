package com.sxj.redis.core.collections;

import com.sxj.redis.core.RCollections;
import com.sxj.redis.core.RMap;
import com.sxj.redis.core.RSet;
import com.sxj.redis.core.provider.RedisProvider;

public class RedisCollections implements RCollections
{
    private RedisProvider provider;
    
    public RedisCollections(String configFile)
    {
        provider = new RedisProvider(configFile);
    }
    
    @Override
    public <K, V> RMap<K, V> getMap(String name)
    {
        return new RedisMap<K, V>(provider, name);
    }
    
    public <V> RSet<V> getSet(String name)
    {
        return new RedisSet<V>(provider, name);
    }
    
}
