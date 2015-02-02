package com.sxj.redis.core.pubsub;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;

import com.sxj.redis.core.MessageListener;
import com.sxj.redis.core.RTopic;
import com.sxj.redis.core.exception.RedisException;
import com.sxj.redis.core.impl.RedisObject;
import com.sxj.redis.core.provider.RedisProvider;

public class RedisTopic<M> extends RedisObject implements RTopic<M>
{
    
    private Map<Integer, TopicThread<M>> pubsubs = new HashMap<Integer, TopicThread<M>>();
    
    public RedisTopic(RedisProvider provider, String name)
    {
        super(provider, name);
    }
    
    @Override
    public long publish(M message)
    {
        
        Jedis jedis = provider.getResource();
        boolean broken = false;
        try
        {
            return jedis.publish(name, V_SERIALIZER.serialize(message));
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
    
    @Override
    public int addListener(final MessageListener<M> listener)
    {
        Jedis jedis = provider.getResource();
        boolean broken = false;
        try
        {
            RedisMessageListenerWrapper<M> wrapper = new RedisMessageListenerWrapper<M>(
                    listener, name);
            TopicThread<M> topicThread = new TopicThread<M>(jedis, wrapper);
            ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(1);
            topicThread.setService(newCachedThreadPool);
            newCachedThreadPool.execute(topicThread);
            int hashCode = wrapper.hashCode();
            pubsubs.put(hashCode, topicThread);
            return hashCode;
        }
        catch (Exception e)
        {
            broken = true;
            throw new RedisException("", e);
        }
        finally
        {
            //            provider.returnResource(jedis, broken);
        }
    }
    
    @Override
    public void removeListener(int listenerId)
    {
        Jedis jedis = null;
        try
        {
            synchronized (pubsubs)
            {
                if (pubsubs.containsKey(listenerId))
                {
                    TopicThread<M> thread = pubsubs.get(listenerId);
                    jedis = thread.getJedis();
                    thread.getService().shutdown();
                    pubsubs.remove(listenerId);
                    thread.getWrapper().unsubscribe(name);
                    if (pubsubs.size() == 0)
                        provider.returnResource(jedis, false);
                }
            }
            
        }
        catch (Exception e)
        {
            if (jedis != null)
                provider.returnResource(jedis, true);
            throw new RedisException("", e);
        }
    }
    
    public static class TopicThread<M> implements Runnable
    {
        
        private RedisMessageListenerWrapper<M> wrapper;
        
        private Jedis jedis;
        
        private ExecutorService service;
        
        public TopicThread(Jedis jedis, RedisMessageListenerWrapper<M> wrapper)
        {
            this.jedis = jedis;
            this.wrapper = wrapper;
        }
        
        @Override
        public void run()
        {
            jedis.subscribe(wrapper, wrapper.getChannel());
        }
        
        private ExecutorService getService()
        {
            return service;
        }
        
        private void setService(ExecutorService service)
        {
            this.service = service;
        }
        
        private RedisMessageListenerWrapper<M> getWrapper()
        {
            return wrapper;
        }
        
        private Jedis getJedis()
        {
            return jedis;
        }
        
    }
    
}
