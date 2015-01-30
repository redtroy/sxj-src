package com.sxj.redis.core.impl;

import redis.clients.jedis.Jedis;

import com.sxj.redis.core.RObject;

public class RedisObject implements RObject
{
    
    protected Jedis jedis;
    
    protected String name;
    
    public RedisObject(Jedis jedis, String name)
    {
        super();
        this.jedis = jedis;
        this.name = name;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    @Override
    public void delete()
    {
        jedis.del(getName());
    }
    
}
