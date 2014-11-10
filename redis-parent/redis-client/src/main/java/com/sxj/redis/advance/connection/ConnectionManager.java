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

import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;

import com.sxj.redis.RedisConnection;
import com.sxj.redis.advance.async.AsyncOperation;
import com.sxj.redis.advance.async.SyncOperation;
import com.sxj.redis.pubsub.RedisPubSubAdapter;

/**
 *
 * @author Nikita Koksharov
 *
 */
//TODO ping support
public interface ConnectionManager
{
    
    public <V> V get(Future<V> future);
    
    public <V, R> R read(SyncOperation<V, R> operation);
    
    public <V, R> R write(SyncOperation<V, R> operation);
    
    public <V, R> R write(AsyncOperation<V, R> asyncOperation);
    
    public <V, T> Future<T> writeAllAsync(AsyncOperation<V, T> asyncOperation);
    
    public <V, T> T read(AsyncOperation<V, T> asyncOperation);
    
    public <V, T> Future<T> readAsync(AsyncOperation<V, T> asyncOperation);
    
    public <V, T> Future<T> writeAsync(AsyncOperation<V, T> asyncOperation);
    
    public <K, V> RedisConnection<K, V> connectionReadOp(int slot);
    
    public PubSubConnectionEntry getEntry(String channelName);
    
    public <K, V> PubSubConnectionEntry subscribe(String channelName);
    
    public <K, V> PubSubConnectionEntry subscribe(
            RedisPubSubAdapter<V> listener, String channelName);
    
    public Future unsubscribe(String channelName);
    
    public void releaseRead(int slot, RedisConnection сonnection);
    
    public void shutdown();
    
    public EventLoopGroup getGroup();
    
}
