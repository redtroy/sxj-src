package com.sxj.mybatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

import com.sxj.cache.manager.HierarchicalCacheManager;

public class HierarchicalCache implements Cache
{
    
    //    private static final CacheManager CACHE_MANAGER = CacheManager.create();
    
    private String cacheId;
    
    private int level = 0;
    
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
        putObject(0, key, value);
    }
    
    private void putObject(int level, Object key, Object value)
    {
        
        HierarchicalCacheManager.set(level, this.cacheId, key, value);
        if ((level + 1) <= this.level)
            putObject(level + 1, key, value);
    }
    
    @Override
    public Object getObject(Object key)
    {
        
        return getObject(0, key);
    }
    
    private Object getObject(int level, Object key)
    {
        Object object = HierarchicalCacheManager.get(level, this.cacheId, key);
        if (object != null)
            return object;
        if ((level + 1) <= this.level)
            return getObject(level + 1, key);
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
    
    public int getLevel()
    {
        return level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
}
