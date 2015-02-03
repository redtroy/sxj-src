package com.sxj.redis.core;

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
    
    @Test
    public void test()
    {
        RQueue<String> queue = collections.getQueue(QUEUE_NAME);
        queue.offer("A");
        queue.offer("B");
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
    
}
