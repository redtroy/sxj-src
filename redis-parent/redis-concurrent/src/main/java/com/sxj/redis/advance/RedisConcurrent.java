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
import com.sxj.redis.advance.concurrent.RedisCountDownLatch;
import com.sxj.redis.advance.concurrent.RedisLock;
import com.sxj.redis.advance.connection.ClusterConnectionManager;
import com.sxj.redis.advance.connection.ConnectionManager;
import com.sxj.redis.advance.connection.MasterSlaveConnectionManager;
import com.sxj.redis.advance.connection.SentinelConnectionManager;
import com.sxj.redis.advance.connection.SingleConnectionManager;
import com.sxj.redis.advance.core.RAtomicLong;
import com.sxj.redis.advance.core.RCountDownLatch;
import com.sxj.redis.advance.core.RLock;

/**
 * Main infrastructure class allows to get access to all Redisson objects on top
 * of Redis server.
 *
 * @author Nikita Koksharov
 *
 */
public class RedisConcurrent implements ConcurrentRedisClient
{
    
    private final ConnectionManager connectionManager;
    
    private final Config config;
    
    private final UUID id = UUID.randomUUID();
    
    RedisConcurrent(Config config)
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
    public static RedisConcurrent create()
    {
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        // config.useMasterSlaveConnection().setMasterAddress("127.0.0.1:6379").addSlaveAddress("127.0.0.1:6389").addSlaveAddress("127.0.0.1:6399");
        // config.useSentinelConnection().setMasterName("mymaster").addSentinelAddress("127.0.0.1:26389",
        // "127.0.0.1:26379");
        return create(config);
    }
    
    /**
     * Creates an Redisson instance with configuration
     *
     * @param config
     * @return Redisson instance
     */
    public static RedisConcurrent create(Config config)
    {
        return new RedisConcurrent(config);
    }
    
    /**
     * Returns distributed lock instance by name.
     *
     * @param name
     *            of the distributed lock
     * @return distributed lock
     */
    @Override
    public RLock getLock(String name)
    {
        return new RedisLock(connectionManager, name, id);
    }
    
    /**
     * Returns distributed "atomic long" instance by name.
     *
     * @param name
     *            of the distributed "atomic long"
     * @return distributed "atomic long"
     */
    @Override
    public RAtomicLong getAtomicLong(String name)
    {
        return new RedisAtomicLong(connectionManager, name);
    }
    
    @Override
    public RAtomicLong getAtomicLong(String name, long second)
    {
        return new RedisAtomicLong(connectionManager, name, second);
    }
    
    /**
     * Returns distributed "count down latch" instance by name.
     *
     * @param name
     *            of the distributed "count down latch"
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
     * Allows to get configuration provided during Redisson instance creation.
     * Further changes on this object not affect Redisson instance.
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
