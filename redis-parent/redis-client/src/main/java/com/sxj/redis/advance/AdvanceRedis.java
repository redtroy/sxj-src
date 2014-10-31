/**
 * Copyright 2014 Nikita Koksharov, Nickolay Borbit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sxj.redis.advance;

import io.netty.util.concurrent.Future;

import java.util.UUID;

import com.sxj.redis.RedisAsyncConnection;
import com.sxj.redis.advance.async.ResultOperation;
import com.sxj.redis.advance.atomic.RedisAtomicLong;
import com.sxj.redis.advance.collections.RedisBucket;
import com.sxj.redis.advance.collections.RedisDeque;
import com.sxj.redis.advance.collections.RedisList;
import com.sxj.redis.advance.collections.RedisMap;
import com.sxj.redis.advance.collections.RedisQueue;
import com.sxj.redis.advance.collections.RedisSet;
import com.sxj.redis.advance.collections.RedisSortedSet;
import com.sxj.redis.advance.concurrent.RedisCountDownLatch;
import com.sxj.redis.advance.concurrent.RedisLock;
import com.sxj.redis.advance.connection.ClusterConnectionManager;
import com.sxj.redis.advance.connection.ConnectionManager;
import com.sxj.redis.advance.connection.MasterSlaveConnectionManager;
import com.sxj.redis.advance.connection.SentinelConnectionManager;
import com.sxj.redis.advance.connection.SingleConnectionManager;
import com.sxj.redis.advance.core.RAtomicLong;
import com.sxj.redis.advance.core.RBucket;
import com.sxj.redis.advance.core.RCountDownLatch;
import com.sxj.redis.advance.core.RDeque;
import com.sxj.redis.advance.core.RHyperLogLog;
import com.sxj.redis.advance.core.RList;
import com.sxj.redis.advance.core.RLock;
import com.sxj.redis.advance.core.RMap;
import com.sxj.redis.advance.core.RQueue;
import com.sxj.redis.advance.core.RSet;
import com.sxj.redis.advance.core.RSortedSet;
import com.sxj.redis.advance.core.RTopic;
import com.sxj.redis.advance.pubsub.RedisTopic;

/**
 * Main infrastructure class allows to get access
 * to all Redisson objects on top of Redis server.
 *
 * @author Nikita Koksharov
 *
 */
public class AdvanceRedis implements RedisClient
{
    
    private final ConnectionManager connectionManager;
    
    private final Config config;
    
    private final UUID id = UUID.randomUUID();
    
    AdvanceRedis(Config config)
    {
        this.config = config;
        Config configCopy = new Config(config);
        if (configCopy.getMasterSlaveServersConfig() != null)
        {
            connectionManager = new MasterSlaveConnectionManager(
                    configCopy.getMasterSlaveServersConfig(), configCopy);
        }
        else if (configCopy.getSingleServerConfig() != null)
        {
            connectionManager = new SingleConnectionManager(
                    configCopy.getSingleServerConfig(), configCopy);
        }
        else if (configCopy.getSentinelServersConfig() != null)
        {
            connectionManager = new SentinelConnectionManager(
                    configCopy.getSentinelServersConfig(), configCopy);
        }
        else if (configCopy.getClusterServersConfig() != null)
        {
            connectionManager = new ClusterConnectionManager(
                    configCopy.getClusterServersConfig(), configCopy);
        }
        else
        {
            throw new IllegalArgumentException(
                    "server(s) address(es) not defined!");
        }
    }
    
    /**
     * Creates an Redisson instance
     *
     * @return Redisson instance
     */
    public static AdvanceRedis create()
    {
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        //        config.useMasterSlaveConnection().setMasterAddress("127.0.0.1:6379").addSlaveAddress("127.0.0.1:6389").addSlaveAddress("127.0.0.1:6399");
        //        config.useSentinelConnection().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26389", "127.0.0.1:26379");
        return create(config);
    }
    
    /**
     * Creates an Redisson instance with configuration
     *
     * @param config
     * @return Redisson instance
     */
    public static AdvanceRedis create(Config config)
    {
        return new AdvanceRedis(config);
    }
    
    /**
     * Returns object holder by name
     *
     * @param name of object
     * @return
     */
    @Override
    public <V> RBucket<V> getBucket(String name)
    {
        return new RedisBucket<V>(connectionManager, name);
    }
    
    /**
     * Returns HyperLogLog object
     *
     * @param name of object
     * @return
     */
    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(String name)
    {
        return new RedisHyperLogLog<V>(connectionManager, name);
    }
    
    /**
     * Returns distributed list instance by name.
     *
     * @param name of the distributed list
     * @return distributed list
     */
    @Override
    public <V> RList<V> getList(String name)
    {
        return new RedisList<V>(connectionManager, name);
    }
    
    /**
     * Returns distributed map instance by name.
     *
     * @param name of the distributed map
     * @return distributed map
     */
    @Override
    public <K, V> RMap<K, V> getMap(String name)
    {
        return new RedisMap<K, V>(connectionManager, name);
    }
    
    /**
     * Returns distributed lock instance by name.
     *
     * @param name of the distributed lock
     * @return distributed lock
     */
    @Override
    public RLock getLock(String name)
    {
        return new RedisLock(connectionManager, name, id);
    }
    
    /**
     * Returns distributed set instance by name.
     *
     * @param name of the distributed set
     * @return distributed set
     */
    @Override
    public <V> RSet<V> getSet(String name)
    {
        return new RedisSet<V>(connectionManager, name);
    }
    
    /**
     * Returns distributed sorted set instance by name.
     *
     * @param name of the distributed set
     * @return distributed set
     */
    @Override
    public <V> RSortedSet<V> getSortedSet(String name)
    {
        return new RedisSortedSet<V>(connectionManager, name);
    }
    
    /**
     * Returns distributed topic instance by name.
     *
     * @param name of the distributed topic
     * @return distributed topic
     */
    @Override
    public <M> RTopic<M> getTopic(String name)
    {
        return new RedisTopic<M>(connectionManager, name);
    }
    
    /**
     * Returns distributed queue instance by name.
     *
     * @param name of the distributed queue
     * @return distributed queue
     */
    @Override
    public <V> RQueue<V> getQueue(String name)
    {
        return new RedisQueue<V>(connectionManager, name);
    }
    
    /**
     * Returns distributed deque instance by name.
     *
     * @param name of the distributed queue
     * @return distributed queue
     */
    @Override
    public <V> RDeque<V> getDeque(String name)
    {
        return new RedisDeque<V>(connectionManager, name);
    }
    
    /**
     * Returns distributed "atomic long" instance by name.
     *
     * @param name of the distributed "atomic long"
     * @return distributed "atomic long"
     */
    @Override
    public RAtomicLong getAtomicLong(String name)
    {
        return new RedisAtomicLong(connectionManager, name);
    }
    
    /**
     * Returns distributed "count down latch" instance by name.
     *
     * @param name of the distributed "count down latch"
     * @return distributed "count down latch"
     */
    @Override
    public RCountDownLatch getCountDownLatch(String name)
    {
        return new RedisCountDownLatch(connectionManager, name, id);
    }
    
    /**
     * Shuts down Redisson instance <b>NOT</b> Redis server
     */
    public void shutdown()
    {
        connectionManager.shutdown();
    }
    
    /**
     * Allows to get configuration provided
     * during Redisson instance creation. Further changes on
     * this object not affect Redisson instance.
     *
     * @return Config object
     */
    public Config getConfig()
    {
        return config;
    }
    
    public void flushdb()
    {
        connectionManager.write(new ResultOperation<String, Object>()
        {
            @Override
            protected Future<String> execute(
                    RedisAsyncConnection<Object, Object> conn)
            {
                return conn.flushdb();
            }
        });
    }
    
}
