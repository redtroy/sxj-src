package com.sxj.redis.core.impl;

import redis.clients.jedis.Jedis;

import com.sxj.redis.core.RObject;
import com.sxj.redis.core.exception.RedisException;
import com.sxj.redis.core.provider.RedisProvider;
import com.sxj.redis.core.serializer.JdkSerializer;
import com.sxj.redis.core.serializer.JsonSerializer;
import com.sxj.redis.core.serializer.Serializer;

public class RedisObject implements RObject
{
    
    protected RedisProvider provider;
    
    protected String name;
    
    protected final static Serializer K_SERIALIZER = new JsonSerializer();
    
    protected final static Serializer V_SERIALIZER = new JdkSerializer();
    
    public RedisObject(RedisProvider provider, String name)
    {
        super();
        this.provider = provider;
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
        Jedis jedis = provider.getResource();
        boolean broken = false;
        try
        {
            jedis.del(getName());
        }
        catch (Exception e)
        {
            broken = true;
            throw new RedisException("", e);
        }
        finally
        {
            provider.returnResource(jedis, broken);
        }
    }
    
    public static Serializer getKSerializer()
    {
        return K_SERIALIZER;
    }
    
    public static Serializer getVSerializer()
    {
        return V_SERIALIZER;
    }
    
}
