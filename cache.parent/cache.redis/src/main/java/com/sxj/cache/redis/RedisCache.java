package com.sxj.cache.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.sxj.cache.core.Cache;
import com.sxj.cache.core.CacheException;
import com.sxj.cache.core.Serializer;
import com.sxj.cache.core.util.JsonSerializer;

/**
 * Redis 缓存实现
 * 
 * @author winterlau
 */
public class RedisCache implements Cache
{
    
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(RedisCache.class);
    
    private final static Serializer SERIALIZER = new JsonSerializer();
    
    private String region;
    
    public RedisCache(String region)
    {
        this.region = region;
    }
    
    private Object deserialize(String json)
    {
        return SERIALIZER.deserialize(json);
    }
    
    /**
     * 生成缓存的 key
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String serializeObject(Object object)
    {
        
        return SERIALIZER.serialize(object);
    }
    
    private String serializeKey(Object key)
    {
        return region + ":" + SERIALIZER.serialize(key);
    }
    
    public static void main(String[] args)
    {
        RedisCache cache = new RedisCache("user");
        System.out.println(cache);
        JsonSerializer serializer = new JsonSerializer();
        String key = serializer.serialize(cache);
        System.out.println(key);
        cache = (RedisCache) serializer.deserialize(key);
        System.out.println(cache);
    }
    
    @Override
    public Object get(Object key) throws CacheException
    {
        Object obj = null;
        boolean broken = false;
        Jedis cache = RedisCacheProvider.getResource();
        try
        {
            if (null == key)
                return null;
            byte[] b = cache.get(serializeKey(key).getBytes());
            if (b != null)
                obj = deserialize(new String(b));
        }
        catch (Exception e)
        {
            log.error("Error occured when get data from L2 cache", e);
            broken = true;
            if (e instanceof IOException)
                evict(key);
        }
        finally
        {
            RedisCacheProvider.returnResource(cache, broken);
        }
        return obj;
    }
    
    @Override
    public void put(Object key, Object value) throws CacheException
    {
        if (value == null)
            evict(key);
        else
        {
            boolean broken = false;
            Jedis cache = RedisCacheProvider.getResource();
            try
            {
                cache.set(serializeKey(key).getBytes(),
                        serializeObject(value).getBytes());
            }
            catch (Exception e)
            {
                broken = true;
                throw new CacheException(e);
            }
            finally
            {
                RedisCacheProvider.returnResource(cache, broken);
            }
        }
    }
    
    @Override
    public void update(Object key, Object value) throws CacheException
    {
        put(key, value);
    }
    
    @Override
    public void evict(Object key) throws CacheException
    {
        boolean broken = false;
        Jedis cache = RedisCacheProvider.getResource();
        try
        {
            cache.del(serializeKey(key));
        }
        catch (Exception e)
        {
            broken = true;
            throw new CacheException(e);
        }
        finally
        {
            RedisCacheProvider.returnResource(cache, broken);
        }
    }
    
    /* (non-Javadoc)
     * @see net.oschina.j2cache.Cache#batchRemove(java.util.List)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void evict(List keys) throws CacheException
    {
        if (keys == null || keys.size() == 0)
            return;
        boolean broken = false;
        Jedis cache = RedisCacheProvider.getResource();
        try
        {
            String[] okeys = new String[keys.size()];
            for (int i = 0; i < okeys.length; i++)
            {
                okeys[i] = serializeKey(keys.get(i));
            }
            cache.del(okeys);
        }
        catch (Exception e)
        {
            broken = true;
            throw new CacheException(e);
        }
        finally
        {
            RedisCacheProvider.returnResource(cache, broken);
        }
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public List keys() throws CacheException
    {
        Jedis cache = RedisCacheProvider.getResource();
        boolean broken = false;
        try
        {
            List<String> keys = new ArrayList<String>();
            keys.addAll(cache.keys(region + ":*"));
            for (int i = 0; i < keys.size(); i++)
            {
                keys.set(i, keys.get(i).substring(region.length() + 3));
            }
            return keys;
        }
        catch (Exception e)
        {
            broken = true;
            throw new CacheException(e);
        }
        finally
        {
            RedisCacheProvider.returnResource(cache, broken);
        }
    }
    
    @Override
    public void clear() throws CacheException
    {
        Jedis cache = RedisCacheProvider.getResource();
        boolean broken = false;
        try
        {
            cache.del(region + ":*");
        }
        catch (Exception e)
        {
            broken = true;
            throw new CacheException(e);
        }
        finally
        {
            RedisCacheProvider.returnResource(cache, broken);
        }
    }
    
    @Override
    public void destroy() throws CacheException
    {
        this.clear();
    }
}
