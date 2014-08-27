package com.sxj.supervisor.manage.login;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

public class ShiroRedisCacheManager extends AbstractCacheManager
{
    
    private int level = 2;
    
    @Override
    protected Cache createCache(String cacheName) throws CacheException
    {
        return new ShiroRedisCache<String, Object>(getLevel(), cacheName);
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
}
