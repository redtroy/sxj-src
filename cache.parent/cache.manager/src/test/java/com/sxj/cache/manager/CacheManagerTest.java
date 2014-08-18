package com.sxj.cache.manager;

import org.junit.Before;
import org.junit.Test;

public class CacheManagerTest
{
    
    private CacheManager cacheManager;
    
    @Before
    public void setUp()
    {
        cacheManager.initCacheProvider(null);
    }
    
    @Test
    public void testL1Cache()
    {
        cacheManager.set(1, "testL1Cache", "demokey", "demovalue");
        Object object = cacheManager.get(1, "testL1Cache", "demokey");
        System.out.println(object.toString());
    }
    
}
