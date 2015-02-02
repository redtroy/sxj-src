package com.sxj.redis.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sxj.redis.core.collections.RedisCollections;

public class RedisMapTest
{
    RedisCollections collections;
    
    private static final String MAP_NAME = "test-map";
    
    @Before
    public void setUp()
    {
        collections = new RedisCollections(
                "config/redis-collections.properties");
        testPut();
    }
    
    public void testPut()
    {
        RMap<String, List<String>> map = collections.getMap(MAP_NAME);
        List<String> values = new ArrayList<String>();
        values.add("A");
        map.put("demo", values);
    }
    
    public void testGet()
    {
        
        RMap<String, List<String>> map = collections.getMap(MAP_NAME);
        List<String> list = map.get("demo");
        org.junit.Assert.assertEquals(1, list.size());
        org.junit.Assert.assertEquals("A", list.get(0));
        System.out.println(list.get(0));
    }
    
    @Test
    public void testSize()
    {
        RMap<String, List<String>> map = collections.getMap(MAP_NAME);
        map.size();
        org.junit.Assert.assertEquals(1, map.size());
    }
    
    @Test
    public void testKeyset()
    {
        RMap<String, List<String>> map = collections.getMap(MAP_NAME);
        Set<String> keySet = map.keySet();
        Assert.assertEquals(1, keySet.size());
        Assert.assertTrue(keySet.contains("demo"));
    }
    
    @Test
    public void testExpire()
    {
        RMap<String, List<String>> map = collections.getMap(MAP_NAME);
        map.expire(10, TimeUnit.SECONDS);
    }
    
    @Test
    public void testTTL()
    {
        RMap<String, List<String>> map = collections.getMap(MAP_NAME);
        System.out.println(map.ttl());
    }
    
}
