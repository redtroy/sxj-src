package com.sxj.redis.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.sxj.redis.core.RMap;
import com.sxj.redis.core.impl.RedisExpirable;
import com.sxj.redis.core.serializer.JdkSerializer;
import com.sxj.redis.core.serializer.JsonSerializer;
import com.sxj.redis.core.serializer.Serializer;

public class RedisMap<K, V> extends RedisExpirable implements RMap<K, V>
{
    
    private final static Serializer K_SERIALIZER = new JsonSerializer();
    
    private final static Serializer V_SERIALIZER = new JdkSerializer();
    
    private RedisMap(Jedis jedis, String name)
    {
        super(jedis, name);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public V putIfAbsent(K key, V value)
    {
        int hsetnx = jedis.hsetnx(name,
                K_SERIALIZER.serialize(key),
                V_SERIALIZER.serialize(value)).intValue();
        switch (hsetnx)
        {
            case 1:
                return value;
                
            default:
                return null;
        }
    }
    
    private boolean isEqual(Transaction multi, Object key, Object object)
    {
        if (multi.exists(K_SERIALIZER.serialize(key)).get())
        {
            String hget = multi.hget(name, K_SERIALIZER.serialize(key)).get();
            Object deserialize = V_SERIALIZER.deserialize(hget);
            return deserialize.equals(object);
        }
        return false;
    }
    
    @Override
    public boolean remove(Object key, Object value)
    {
        Transaction multi = jedis.multi();
        boolean retValue = false;
        if (isEqual(multi, key, value))
        {
            int intValue = multi.hdel(name, K_SERIALIZER.serialize(key))
                    .get()
                    .intValue();
            switch (intValue)
            {
                case 1:
                    retValue = true;
                default:
                    break;
            }
        }
        multi.exec();
        return retValue;
    }
    
    @Override
    public boolean replace(K key, V oldValue, V newValue)
    {
        Transaction multi = jedis.multi();
        boolean retValue = false;
        if (isEqual(multi, key, oldValue))
        {
            int intValue = multi.hset(name,
                    K_SERIALIZER.serialize(key),
                    V_SERIALIZER.serialize(newValue))
                    .get()
                    .intValue();
            switch (intValue)
            {
                case 1:
                    retValue = true;
                default:
                    break;
            }
        }
        
        return retValue;
    }
    
    @Override
    public V replace(K key, V value)
    {
        Transaction multi = jedis.multi();
        int intValue = multi.hset(name,
                K_SERIALIZER.serialize(key),
                V_SERIALIZER.serialize(value))
                .get()
                .intValue();
        switch (intValue)
        {
            case 1:
                return value;
            default:
                return null;
        }
        
    }
    
    @Override
    public int size()
    {
        return jedis.hlen(name).intValue();
    }
    
    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }
    
    @Override
    public boolean containsKey(Object key)
    {
        return jedis.hexists(name, K_SERIALIZER.serialize(key));
    }
    
    @Override
    public boolean containsValue(Object value)
    {
        List<String> hvals = jedis.hvals(name);
        String serialize = V_SERIALIZER.serialize(value);
        return hvals.contains(serialize);
    }
    
    @Override
    public V get(Object key)
    {
        String hget = jedis.hget(name, K_SERIALIZER.serialize(key));
        return (V) V_SERIALIZER.deserialize(hget);
    }
    
    @Override
    public V put(K key, V value)
    {
        int hset = jedis.hset(name,
                K_SERIALIZER.serialize(key),
                V_SERIALIZER.serialize(value)).intValue();
        switch (hset)
        {
            case 1:
                return value;
                
            default:
                return null;
        }
    }
    
    @Override
    public V remove(Object key)
    {
        Transaction multi = jedis.multi();
        V retValue = null;
        String serializeKey = K_SERIALIZER.serialize(key);
        if (multi.exists(serializeKey).get())
        {
            V deserialize = (V) V_SERIALIZER.deserialize(multi.hget(name,
                    serializeKey).get());
            int hdel = multi.hdel(name, serializeKey).get().intValue();
            switch (hdel)
            {
                case 1:
                    retValue = deserialize;
                    
                default:
                    break;
            }
        }
        return retValue;
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        Set<? extends K> keySet = m.keySet();
        Map<String, String> map = new HashMap<String, String>();
        for (K key : keySet)
        {
            map.put(K_SERIALIZER.serialize(key),
                    V_SERIALIZER.serialize(m.get(key)));
        }
        jedis.hmset(name, map);
    }
    
    @Override
    public void clear()
    {
        delete();
        
    }
    
    @Override
    public Set<K> keySet()
    {
        Set<String> hkeys = jedis.hkeys(name);
        Set<K> retValue = new HashSet<K>();
        for (String key : hkeys)
        {
            retValue.add((K) K_SERIALIZER.deserialize(key));
        }
        return retValue;
    }
    
    @Override
    public Collection<V> values()
    {
        List<String> hvals = jedis.hvals(name);
        List<V> retValue = new ArrayList<V>();
        for (String value : hvals)
        {
            retValue.add((V) V_SERIALIZER.deserialize(value));
        }
        return retValue;
    }
    
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        Map<String, String> hgetAll = jedis.hgetAll(name);
        Map<K, V> map = new HashMap<K, V>();
        Set<Entry<String, String>> entrySet = hgetAll.entrySet();
        for (Map.Entry<String, String> entry : entrySet)
        {
            map.put((K) K_SERIALIZER.deserialize(entry.getKey()),
                    (V) V_SERIALIZER.deserialize(entry.getValue()));
        }
        return map.entrySet();
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    //    @Override
    //    public V addAndGet(K key, V delta)
    //    {
    //        // TODO Auto-generated method stub
    //        return null;
    //    }
    
    @Override
    public Map<K, V> getAll(Set<K> keys)
    {
        Set<String> serialzeKeys = new HashSet<String>();
        for (K key : keys)
        {
            serialzeKeys.add(K_SERIALIZER.serialize(key));
        }
        List<String> hmget = jedis.hmget(name,
                serialzeKeys.toArray(new String[serialzeKeys.size()]));
        Map<K, V> map = new HashMap<K, V>();
        int index = 0;
        for (K key : keys)
        {
            map.put(key, (V) V_SERIALIZER.deserialize(hmget.get(index)));
        }
        return map;
    }
    
}
