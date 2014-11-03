package com.sxj.redis.advance;

import com.sxj.redis.advance.core.RBucket;
import com.sxj.redis.advance.core.RDeque;
import com.sxj.redis.advance.core.RHyperLogLog;
import com.sxj.redis.advance.core.RList;
import com.sxj.redis.advance.core.RMap;
import com.sxj.redis.advance.core.RQueue;
import com.sxj.redis.advance.core.RSet;
import com.sxj.redis.advance.core.RSortedSet;
import com.sxj.redis.advance.core.RTopic;

public interface CollectionsRedisClient
{
    /** 
     * Returns object holder by name
     *
     * @param name of object
     * @return
     */
    <V> RBucket<V> getBucket(String name);
    
    /**
     * Returns HyperLogLog object
     *
     * @param name of object
     * @return
     */
    <V> RHyperLogLog<V> getHyperLogLog(String name);
    
    /**
     * Returns list instance by name.
     *
     * @param name of list
     * @return
     */
    <V> RList<V> getList(String name);
    
    /**
     * Returns map instance by name.
     *
     * @param name of map
     * @return
     */
    <K, V> RMap<K, V> getMap(String name);
    
    /**
     * Returns set instance by name.
     *
     * @param name of set
     * @return
     */
    <V> RSet<V> getSet(String name);
    
    /**
     * Returns sorted set instance by name.
     *
     * @param name of sorted set
     * @return
     */
    <V> RSortedSet<V> getSortedSet(String name);
    
    /**
     * Returns topic instance by name.
     *
     * @param name of the distributed topic
     * @return
     */
    <M> RTopic<M> getTopic(String name);
    
    /**
     * Returns queue instance by name.
     *
     * @param name of queue
     * @return
     */
    <V> RQueue<V> getQueue(String name);
    
    /**
     * Returns deque instance by name.
     *
     * @param name of deque
     * @return
     */
    <V> RDeque<V> getDeque(String name);
}
