package com.sxj.redis.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sxj.redis.core.collections.RedisCollections;

public class RedisQueueTest
{
    RedisCollections collections;
    
    private static final String QUEUE_NAME = "test-queue";
    
    @Before
    public void setUp()
    {
        collections = new RedisCollections(
                "config/redis-collections.properties");
    }
    
    public void testOffer()
    {
        RQueue<Map<String, List<String>>> queue = collections.getQueue(QUEUE_NAME);
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        map.put("demo", list);
        queue.offer(map);
        Map<String, List<String>> map2 = new HashMap<String, List<String>>();
        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("D");
        map2.put("demo", list2);
        queue.offer(map2);
        System.out.println(queue.size());
    }
    
    @Test
    public void test()
    {
        RQueue<Map<String, List<String>>> queue = collections.getQueue(QUEUE_NAME);
        Map<String, List<String>> poll = queue.poll();
        List<String> list3 = poll.get("demo");
        for (String value : list3)
        {
            System.out.println(value);
        }
        poll = queue.poll();
        list3 = poll.get("demo");
        for (String value : list3)
        {
            System.out.println(value);
        }
        System.out.println(queue.size());
    }
    
}
