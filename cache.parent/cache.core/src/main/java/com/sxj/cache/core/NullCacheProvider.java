package com.sxj.cache.core;

import java.util.Properties;

public class NullCacheProvider implements CacheProvider
{
    
    private final static NullCache CACHE = new NullCache();
    
    @Override
    public String name()
    {
        return "none";
    }
    
    /* (non-Javadoc)
     * @see net.oschina.j2cache.CacheProvider#buildCache(java.lang.String, boolean, net.oschina.j2cache.CacheExpiredListener)
     */
    @Override
    public Cache buildCache(String regionName, boolean autoCreate,
            CacheExpiredListener listener) throws CacheException
    {
        return CACHE;
    }
    
    /* (non-Javadoc)
     * @see net.oschina.j2cache.CacheProvider#start()
     */
    @Override
    public void start(Properties props) throws CacheException
    {
        return;
    }
    
    /* (non-Javadoc)
     * @see net.oschina.j2cache.CacheProvider#stop()
     */
    @Override
    public void stop()
    {
        return;
    }
}
