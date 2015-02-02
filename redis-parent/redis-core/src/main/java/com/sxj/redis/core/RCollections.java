package com.sxj.redis.core;


public interface RCollections
{
    
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
     * @param name of map
     * @return
     */
    <V> RSet<V> getSet(String name);
    
}
