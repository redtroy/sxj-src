package com.sxj.redis.core.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import com.sxj.redis.core.exception.RedisException;
import com.sxj.redis.core.provider.RedisProvider;

public class RedisSetIterator<V> implements Iterator<V>
{
    Iterator<V> iterator;
    
    private V value;
    
    private boolean removed;
    
    private RedisProvider provider;
    
    private RedisSet<V> redisSet;
    
    public RedisSetIterator(RedisProvider provider, RedisSet<V> redisSet)
    {
        super();
        this.provider = provider;
        this.redisSet = redisSet;
    }
    
    @Override
    public boolean hasNext()
    {
        scan();
        return iterator.hasNext();
    }
    
    private Iterator<V> scanIterator(Jedis jedis, final int start)
    {
        ScanResult<String> sscan = jedis.sscan(redisSet.getName(), start);
        List<String> results = sscan.getResult();
        List<V> retValue = new ArrayList<V>();
        for (String result : results)
        {
            retValue.add((V) redisSet.getVSerializer().deserialize(result));
        }
        return retValue.iterator();
    }
    
    private void scan()
    {
        if (iterator == null)
        {
            final Jedis jedis = provider.getResource();
            boolean broken = false;
            try
            {
                
                iterator = scanIterator(jedis, 0);
            }
            catch (Exception e)
            {
                broken = true;
                throw new RedisException("", e);
            }
            finally
            {
                provider.returnResource(jedis, broken);
            }
        }
    }
    
    @Override
    public V next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No such element at index");
        }
        removed = false;
        value = iterator.next();
        return value;
    }
    
    @Override
    public void remove()
    {
        scan();
        iterator.remove();
        redisSet.remove(value);
        removed = true;
    }
    
}
