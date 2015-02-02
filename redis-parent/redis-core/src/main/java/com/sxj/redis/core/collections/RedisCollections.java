package com.sxj.redis.core.collections;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.sxj.redis.core.RCollections;
import com.sxj.redis.core.RMap;
import com.sxj.redis.core.RSet;
import com.sxj.redis.core.exception.RedisException;
import com.sxj.redis.core.provider.RedisProvider;
import com.sxj.spring.modules.util.ClassLoaderUtil;

public class RedisCollections implements RCollections
{
    private RedisProvider provider = new RedisProvider();
    
    public RedisCollections(String configFile)
    {
        initRedisProvider(configFile);
    }
    
    public void initRedisProvider(String configFile)
    {
        try
        {
            InputStream configStream = ClassLoaderUtil.getResource(configFile);
            Properties props = new Properties();
            props.load(configStream);
            configStream.close();
            provider.start(getProviderProperties(props));
            
        }
        catch (Exception e)
        {
            throw new RedisException("Unabled to initialize cache providers", e);
        }
    }
    
    private final Properties getProviderProperties(Properties props)
    {
        Properties new_props = new Properties();
        Enumeration<Object> keys = props.keys();
        String prefix = "redis.collections.";
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            if (key.startsWith(prefix))
                new_props.setProperty(key.substring(prefix.length()),
                        props.getProperty(key));
        }
        return new_props;
    }
    
    @Override
    public <K, V> RMap<K, V> getMap(String name)
    {
        return new RedisMap<K, V>(provider, name);
    }
    
    public <V> RSet<V> getSet(String name)
    {
        return new RedisSet<V>(provider, name);
    }
    
}
