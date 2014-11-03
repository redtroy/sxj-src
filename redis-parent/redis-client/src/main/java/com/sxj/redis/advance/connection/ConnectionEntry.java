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
package com.sxj.redis.advance.connection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import com.sxj.redis.RedisCli;
import com.sxj.redis.RedisConnection;

public class ConnectionEntry
{
    
    private volatile boolean freezed;
    
    private final RedisCli client;
    
    private final Queue<RedisConnection> connections = new ConcurrentLinkedQueue<RedisConnection>();
    
    private final Semaphore connectionsSemaphore;
    
    public ConnectionEntry(RedisCli client, int poolSize)
    {
        this.client = client;
        this.connectionsSemaphore = new Semaphore(poolSize);
    }
    
    public RedisCli getClient()
    {
        return client;
    }
    
    public boolean isFreezed()
    {
        return freezed;
    }
    
    public void setFreezed(boolean freezed)
    {
        this.freezed = freezed;
    }
    
    public Semaphore getConnectionsSemaphore()
    {
        return connectionsSemaphore;
    }
    
    public Queue<RedisConnection> getConnections()
    {
        return connections;
    }
    
}
