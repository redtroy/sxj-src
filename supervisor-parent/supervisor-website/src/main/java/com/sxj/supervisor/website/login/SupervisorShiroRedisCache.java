package com.sxj.supervisor.website.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.cache.CacheException;

import com.sxj.redis.advance.RedisCollections;
import com.sxj.redis.advance.core.RMap;
import com.sxj.spring.modules.security.shiro.ShiroRedisCache;
import com.sxj.util.Constraints;

public class SupervisorShiroRedisCache<K, V> extends ShiroRedisCache<K, V>
{
    
    private static RedisCollections collections;
    
    public void setCollections(RedisCollections collections)
    {
        this.collections = collections;
    }
    
    public SupervisorShiroRedisCache(int level, String name)
    {
        super(level, name);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public V get(K key) throws CacheException
    {
        return super.get(key);
    }
    
    @Override
    public V put(K key, V value) throws CacheException
    {
        return super.put(key, value);
    }
    
    public static void addToMap(String accountNo, Object authorizationKey)
    {
        RMap<Object, Object> map = collections.getMap(Constraints.SHIRO_MAP_KEY);
        List<Object> object = (List<Object>) map.get(accountNo);
        if (object == null)
        {
            object = new ArrayList<Object>();
        }
        object.add(authorizationKey);
        map.put(accountNo, object);
    }
    
    @Override
    public V remove(K key) throws CacheException
    {
        // TODO Auto-generated method stub
        return super.remove(key);
    }
    
    @Override
    public void clear() throws CacheException
    {
        // TODO Auto-generated method stub
        super.clear();
        RMap<Object, Object> map = collections.getMap(Constraints.SHIRO_MAP_KEY);
        map.clear();
    }
    
}
