package com.sxj.cache.manager;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sxj.cache.core.Cache;
import com.sxj.cache.core.CacheException;
import com.sxj.cache.core.CacheExpiredListener;
import com.sxj.cache.core.CacheProvider;
import com.sxj.cache.core.NullCacheProvider;
import com.sxj.cache.ehcache.EhCacheProvider;
import com.sxj.cache.redis.RedisCacheProvider;
import com.sxj.spring.modules.util.ClassLoaderUtil;

/**
 * 缓存管理器
 */
public class HierarchicalCacheManager
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(HierarchicalCacheManager.class);
    
    private final static String CONFIG_FILE = "cache.properties";
    
    private String configFile;
    
    private String databaseId;
    
    private static CacheProvider l1_provider;
    
    private static CacheProvider l2_provider;
    
    private static CacheExpiredListener listener;
    
    public void initCacheProvider()
    {
        initCacheProvider(null);
    }
    
    public void initCacheProvider(CacheExpiredListener listener)
    {
        initCacheProvider(CONFIG_FILE, listener);
    }
    
    public void initCacheProvider(String configFile,
            CacheExpiredListener listener)
    {
        initCacheProvider(configFile, 1, listener);
    }
    
    public void initCacheProvider(String configFile, int databaseId,
            CacheExpiredListener listener)
    {
        final String path = getConfigFile() == null ? configFile
                : getConfigFile();
        InputStream configStream = null;
        try
        {
            configStream = ClassLoaderUtil.getResource(path);
            Properties props = new Properties();
            HierarchicalCacheManager.listener = listener;
            props.load(configStream);
            props.setProperty("redis.database",
                    props.getProperty("redis.database",
                            String.valueOf(databaseId)));
            configStream.close();
            if (props.getProperty("cache.L1.provider_class") == null
                    && props.getProperty("cache.L2.provider_class") == null)
                throw new CacheException(
                        "At lease one provider_class should be defined!");
            startL1Provider(props);
            startL2Provider(props);
            configStream.close();
        }
        catch (Exception e)
        {
            throw new CacheException("Unabled to initialize cache providers", e);
        }
    }
    
    private void startL2Provider(Properties props)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        if (props.getProperty("cache.L2.provider_class") != null)
        {
            HierarchicalCacheManager.l2_provider = getProviderInstance(props.getProperty("cache.L2.provider_class"));
            HierarchicalCacheManager.l2_provider.start(getProviderProperties(props,
                    HierarchicalCacheManager.l2_provider));
            LOGGER.info("Using L2 CacheProvider : "
                    + l2_provider.getClass().getName());
        }
    }
    
    private void startL1Provider(Properties props)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        if (props.getProperty("cache.L1.provider_class") != null)
        {
            HierarchicalCacheManager.l1_provider = getProviderInstance(props.getProperty("cache.L1.provider_class"));
            HierarchicalCacheManager.l1_provider.start(getProviderProperties(props,
                    HierarchicalCacheManager.l1_provider));
            LOGGER.info("Using L1 CacheProvider : "
                    + l1_provider.getClass().getName());
        }
    }
    
    private final static CacheProvider getProviderInstance(String value)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        if ("ehcache".equalsIgnoreCase(value))
            return new EhCacheProvider();
        if ("redis".equalsIgnoreCase(value))
            return new RedisCacheProvider();
        if ("none".equalsIgnoreCase(value))
            return new NullCacheProvider();
        return (CacheProvider) Class.forName(value).newInstance();
    }
    
    private final static Properties getProviderProperties(Properties props,
            CacheProvider provider)
    {
        Properties tmp = new Properties();
        Enumeration<Object> keys = props.keys();
        String prefix = provider.name() + '.';
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            if (key.startsWith(prefix))
                tmp.setProperty(key.substring(prefix.length()),
                        props.getProperty(key));
        }
        return tmp;
    }
    
    private final static Cache getCache(int level, String cacheName,
            boolean autoCreate)
    {
        switch (level)
        {
            case 1:
                return l1_provider.buildCache(cacheName, autoCreate, listener);
            case 2:
                return l2_provider.buildCache(cacheName, autoCreate, listener);
            default:
                return new NullCacheProvider().buildCache(cacheName,
                        autoCreate,
                        listener);
        }
    }
    
    public final static void shutdown(int level)
    {
        ((level == 1) ? l1_provider : l2_provider).stop();
    }
    
    /**
     * 获取缓存中的数据
     * @param level
     * @param name
     * @param key
     * @return
     */
    public final static Object get(int level, String name, Object key)
    {
        if (name != null && key != null)
        {
            Cache cache = getCache(level, name, false);
            if (cache != null)
                return cache.get(key);
        }
        return null;
    }
    
    /**
     * 获取缓存中的数据
     * @param <T>
     * @param level
     * @param resultClass
     * @param name
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public final static <T> T get(int level, Class<T> resultClass, String name,
            Object key)
    {
        if (name != null && key != null)
        {
            Cache cache = getCache(level, name, false);
            if (cache != null)
            {
                Object object = cache.get(key);
                if (resultClass.isInstance(object))
                    throw new CacheException("Class mismatched!!");
                return (T) object;
            }
        }
        return null;
    }
    
    /**
     * 写入缓存
     * @param level
     * @param name
     * @param key
     * @param value
     */
    public final static void set(int level, String name, Object key,
            Object value)
    {
        if (name != null && key != null && value != null)
        {
            Cache cache = getCache(level, name, true);
            if (cache != null)
                cache.put(key, value);
        }
    }
    
    public final static void set(int level, String name, Object key,
            Object value, int seconds)
    {
        if (name != null && key != null && value != null)
        {
            Cache cache = getCache(level, name, true);
            if (cache != null)
                if (seconds <= 0)
                    cache.put(key, value);
                else
                    cache.put(key, value, seconds);
        }
    }
    
    /**
     * 清除缓存中的某个数据
     * @param level
     * @param name
     * @param key
     */
    public final static void evict(int level, String name, Object key)
    {
        if (name != null && key != null)
        {
            Cache cache = getCache(level, name, false);
            if (cache != null)
                cache.evict(key);
        }
    }
    
    public final static Boolean exists(int level, String name, Object key)
    {
        if (name != null && key != null)
        {
            Cache cache = getCache(level, name, false);
            if (cache != null)
                return cache.exists(key);
        }
        return false;
    }
    
    /**
     * 批量删除缓存中的一些数据
     * @param level
     * @param name
     * @param keys
     */
    @SuppressWarnings("rawtypes")
    public final static void batchEvict(int level, String name, List keys)
    {
        if (name != null && CollectionUtils.isNotEmpty(keys))
        {
            Cache cache = getCache(level, name, false);
            if (cache != null)
                cache.evict(keys);
        }
    }
    
    /**
     * Clear the cache
     */
    public final static void clear(int level, String name)
            throws CacheException
    {
        Cache cache = getCache(level, name, false);
        if (cache != null)
            cache.clear();
    }
    
    @SuppressWarnings("rawtypes")
    public final static List keys(int level, String name) throws CacheException
    {
        Cache cache = getCache(level, name, false);
        return (cache != null) ? cache.keys() : null;
    }
    
    public final static Long size(int level, String name) throws CacheException
    {
        Cache cache = getCache(level, name, false);
        return (cache != null) ? cache.size() : 0L;
    }
    
    public final static List values(int level, String name)
            throws CacheException
    {
        Cache cache = getCache(level, name, false);
        return (cache != null) ? cache.values() : null;
    }
    
    public String getConfigFile()
    {
        return configFile;
    }
    
    public void setConfigFile(String configFile)
    {
        this.configFile = configFile;
    }
    
    public String getDatabaseId()
    {
        return databaseId;
    }
    
    public void setDatabaseId(String databaseId)
    {
        this.databaseId = databaseId;
    }
    
}
