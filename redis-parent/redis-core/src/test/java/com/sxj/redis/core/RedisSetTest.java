package com.sxj.redis.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sxj.redis.core.collections.RedisCollections;

public class RedisSetTest
{
    RedisCollections collections;
    
    private static final String SET_NAME = "test-set";
    
    @Before
    public void setUp()
    {
        collections = new RedisCollections(
                "config/redis-collections.properties");
        testAdd();
    }
    
    public void testAdd()
    {
        RSet<String> set = collections.getSet(SET_NAME);
        set.add("A");
        set.add("B");
    }
    
    @Test
    public void testSize()
    {
        RSet<String> set = collections.getSet(SET_NAME);
        System.out.println(set.size());
    }
    
    @Test
    public void testContains()
    {
        RSet<String> set = collections.getSet(SET_NAME);
        Assert.assertTrue(set.contains("A"));
    }
    
    @Test
    public void testContainsAll()
    {
        RSet<String> set = collections.getSet(SET_NAME);
        List<String> values = new ArrayList<String>();
        values.add("A");
        Assert.assertTrue(set.containsAll(values));
    }
    
    @Test
    public void testRemove()
    {
        RSet<String> set = collections.getSet(SET_NAME);
        set.remove("A");
        Assert.assertEquals(1, set.size());
    }
    
    @Test
    public void testRemoveAll()
    {
        RSet<String> set = collections.getSet(SET_NAME);
        List<String> values = new ArrayList<String>();
        values.add("A");
        set.removeAll(values);
        Assert.assertEquals(1, set.size());
    }
    
    @Test
    public void testExpire()
    {
        RSet<String> set = collections.getSet(SET_NAME);
        set.expire(10, TimeUnit.SECONDS);
    }
    
}
