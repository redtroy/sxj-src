package com.sxj.redis.core.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

import com.sxj.redis.core.RExpirable;

public class RedisExpirable extends RedisObject implements RExpirable
{
    
    public RedisExpirable(Jedis jedis, String name)
    {
        super(jedis, name);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public String getName()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void delete()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean expire(long timeToLive, TimeUnit timeUnit)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean expireAt(long timestamp)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean expireAt(Date timestamp)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean clearExpire()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public long remainTimeToLive()
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
