package com.sxj.cache.manager;

import junit.framework.Assert;

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
    
    public void testL1Cache()
    {
        cacheManager.set(1, "testL1Cache", "demokey", "demovalue");
        Object object = cacheManager.get(1, "testL1Cache", "demokey");
        Assert.assertEquals("demovalue", object.toString());
    }
    
    @Test
    public void testL2Cache()
    {
        cacheManager.set(2, "testL2Cache", "demokey", "demovalue");
        Object object = cacheManager.get(2, "testL2Cache", "demokey");
        Assert.assertEquals("demovalue", object.toString());
    }
    
}
