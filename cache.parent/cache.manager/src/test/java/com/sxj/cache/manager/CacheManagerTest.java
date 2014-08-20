package com.sxj.cache.manager;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;

public class CacheManagerTest
{
    
    private HierarchicalCacheManager cacheManager;
    
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
    
    public void testL2Cache()
    {
        List<String> result = new ArrayList<String>();
        result.add("a");
        result.add("b");
        result.add("c");
        result.add("d");
        result.add("e");
        cacheManager.set(2, "L2List", "ListString", result);
        List<String> object = (List<String>) cacheManager.get(2,
                "L2List",
                "ListString");
        for (String obj : object)
        {
            System.out.println(obj);
        }
        //        //        cacheManager.set(2, "testL2Cache", "demokey2", "demovalue");
        //        Object object = cacheManager.get(2, "testL2Cache", "demokey");
        //        Assert.assertEquals("demovalue2", object.toString());
    }
    
}
