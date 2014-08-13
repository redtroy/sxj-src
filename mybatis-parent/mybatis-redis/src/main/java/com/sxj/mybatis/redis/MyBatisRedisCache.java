package com.sxj.mybatis.redis;

import java.util.concurrent.locks.ReadWriteLock;

import net.sf.ehcache.hibernate.management.impl.BeanUtils;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.mybatis.caches.ehcache.EhcacheCache;

import com.sxj.spring.modules.nosql.redis.JedisShardedTemplate;

public class MyBatisRedisCache extends LoggingCache
{
    private JedisShardedTemplate jedis;
    
    public void setJedis(JedisShardedTemplate jedis)
    {
        this.jedis = jedis;
    }
    
    public MyBatisRedisCache(final String ehcacheId)
    {
        super(new EhcacheCache(ehcacheId));
    }
    
    @Override
    public String getId()
    {
        return super.getId();
    }
    
    @Override
    public int getSize()
    {
        return super.getSize();
    }
    
    @Override
    public void putObject(Object key, Object object)
    {
        // TODO Auto-generated method stub
        super.putObject(key, object);
    }
    
    @Override
    public Object getObject(Object key)
    {
        String hashCode=HashCodeBuilder.re
        return super.getObject(key);
    }
    
    @Override
    public Object removeObject(Object key)
    {
        // TODO Auto-generated method stub
        return super.removeObject(key);
    }
    
    @Override
    public ReadWriteLock getReadWriteLock()
    {
        // TODO Auto-generated method stub
        return super.getReadWriteLock();
    }
    
}
