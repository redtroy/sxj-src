package com.sxj.redis.core.provider;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.sxj.redis.core.RProvider;
import com.sxj.redis.core.exception.RedisException;
import com.sxj.spring.modules.util.ClassLoaderUtil;

/**
 * Redis 缓存实现
 * @author Winter Lau
 */
public class RedisProvider implements RProvider
{
    
    private static String host;
    
    private static int port;
    
    private static int timeout;
    
    private static String password;
    
    private static int database;
    
    private static JedisPool pool;
    
    private String configFile;
    
    public RedisProvider(String configFile)
    {
        this.configFile = configFile;
        initRedisProvider();
    }
    
    @Override
    public String name()
    {
        return "redis";
    }
    
    public void initRedisProvider()
    {
        try
        {
            InputStream configStream = ClassLoaderUtil.getResource(configFile);
            Properties props = new Properties();
            props.load(configStream);
            configStream.close();
            start(getProviderProperties(props));
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
    
    /**
     * 释放资源
     * @param jedis
     * @param broken
     */
    public void returnResource(Jedis jedis, boolean broken)
    {
        if (null == jedis)
            return;
        if (broken)
        {
            pool.returnBrokenResource(jedis);
            jedis = null;
        }
        else
            pool.returnResource(jedis);
    }
    
    public Jedis getResource()
    {
        return pool.getResource();
    }
    
    @Override
    public void start(Properties props) throws RedisException
    {
        JedisPoolConfig config = new JedisPoolConfig();
        
        host = getProperty(props, "host", "127.0.0.1");
        password = props.getProperty("password", null);
        
        port = getProperty(props, "port", 6379);
        timeout = getProperty(props, "timeout", 2000);
        database = getProperty(props, "database", 0);
        
        config.setMaxIdle(getProperty(props, "maxIdle", 10));
        config.setMinIdle(getProperty(props, "minIdle", 5));
        config.setTestWhileIdle(getProperty(props, "testWhileIdle", false));
        config.setTestOnBorrow(getProperty(props, "testOnBorrow", true));
        config.setTestOnReturn(getProperty(props, "testOnReturn", false));
        config.setNumTestsPerEvictionRun(getProperty(props,
                "numTestsPerEvictionRun",
                10));
        config.setMinEvictableIdleTimeMillis(getProperty(props,
                "minEvictableIdleTimeMillis",
                1000));
        config.setSoftMinEvictableIdleTimeMillis(getProperty(props,
                "softMinEvictableIdleTimeMillis",
                10));
        config.setTimeBetweenEvictionRunsMillis(getProperty(props,
                "timeBetweenEvictionRunsMillis",
                10));
        pool = new JedisPool(config, host, port, timeout, password, database);
        
    }
    
    @Override
    public void stop()
    {
        pool.destroy();
    }
    
    private static String getProperty(Properties props, String key,
            String defaultValue)
    {
        return props.getProperty(key, defaultValue).trim();
    }
    
    private static int getProperty(Properties props, String key,
            int defaultValue)
    {
        try
        {
            return Integer.parseInt(props.getProperty(key,
                    String.valueOf(defaultValue)).trim());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }
    
    private static boolean getProperty(Properties props, String key,
            boolean defaultValue)
    {
        return "true".equalsIgnoreCase(props.getProperty(key,
                String.valueOf(defaultValue)).trim());
    }
}
