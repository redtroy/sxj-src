package com.sxj.mybatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

import com.sxj.cache.manager.CacheLevel;

public class HierarchicalCache implements Cache
{
    
    //    private static final CacheManager CACHE_MANAGER = CacheManager.create();
    
    private String cacheId;
    
    private CacheLevel level = CacheLevel.EHCACHE;
    
    public HierarchicalCache(String cacheId)
    {
        //        if (cacheId == null)
        //        {
        //            throw new IllegalArgumentException("Cache instances require an ID");
        //        }
        //        if (!CACHE_MANAGER.cacheExists(cacheId))
        //        {
        //            CACHE_MANAGER.addCache(cacheId);
        //        }
        //        this.cache = CACHE_MANAGER.getCache(cacheId);
        this.cacheId = cacheId;
    }
    
    @Override
    public String getId()
    {
        return this.cacheId;
    }
    
    @Override
    public void putObject(Object key, Object value)
    {
        // TODO Auto-generated method stub
        System.out.println("putting object");
    }
    
    @Override
    public Object getObject(Object key)
    {
        System.out.println("getting object");
        return null;
    }
    
    @Override
    public Object removeObject(Object key)
    {
        System.out.println("removing object");
        return null;
    }
    
    @Override
    public void clear()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public int getSize()
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public ReadWriteLock getReadWriteLock()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public String getCacheId()
    {
        return cacheId;
    }
    
    public void setCacheId(String cacheId)
    {
        this.cacheId = cacheId;
    }
    
    public CacheLevel getLevel()
    {
        return level;
    }
    
    public void setLevel(CacheLevel level)
    {
        this.level = level;
    }
    
}
