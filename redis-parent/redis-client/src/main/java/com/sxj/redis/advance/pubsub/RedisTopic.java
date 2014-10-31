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
package com.sxj.redis.advance.pubsub;

import io.netty.util.concurrent.Future;

import com.sxj.redis.RedisAsyncConnection;
import com.sxj.redis.advance.RedisObject;
import com.sxj.redis.advance.async.ResultOperation;
import com.sxj.redis.advance.connection.ConnectionManager;
import com.sxj.redis.advance.connection.PubSubConnectionEntry;
import com.sxj.redis.advance.core.MessageListener;
import com.sxj.redis.advance.core.RTopic;

/**
 * Distributed topic implementation. Messages are delivered to all message listeners across Redis cluster.
 *
 * @author Nikita Koksharov
 *
 * @param <M> message
 */
public class RedisTopic<M> extends RedisObject implements RTopic<M>
{
    
    public RedisTopic(ConnectionManager connectionManager, String name)
    {
        super(connectionManager, name);
    }
    
    @Override
    public long publish(M message)
    {
        return getConnectionManager().get(publishAsync(message));
    }
    
    @Override
    public Future<Long> publishAsync(final M message)
    {
        return getConnectionManager().writeAsync(new ResultOperation<Long, M>()
        {
            @Override
            protected Future<Long> execute(RedisAsyncConnection<Object, M> async)
            {
                return async.publish(getName(), message);
            }
        });
    }
    
    @Override
    public int addListener(MessageListener<M> listener)
    {
        RedisPubSubTopicListenerWrapper<M> pubSubListener = new RedisPubSubTopicListenerWrapper<M>(
                listener, getName());
        return addListener(pubSubListener);
    }
    
    private int addListener(RedisPubSubTopicListenerWrapper<M> pubSubListener)
    {
        PubSubConnectionEntry entry = getConnectionManager().subscribe(getName());
        synchronized (entry)
        {
            if (entry.isActive())
            {
                entry.addListener(getName(), pubSubListener);
                return pubSubListener.hashCode();
            }
        }
        // entry is inactive trying add again
        return addListener(pubSubListener);
    }
    
    @Override
    public void removeListener(int listenerId)
    {
        PubSubConnectionEntry entry = getConnectionManager().getEntry(getName());
        if (entry == null)
        {
            return;
        }
        synchronized (entry)
        {
            if (entry.isActive())
            {
                entry.removeListener(getName(), listenerId);
                if (entry.getListeners(getName()).isEmpty())
                {
                    getConnectionManager().unsubscribe(getName());
                }
                return;
            }
        }
        
        // entry is inactive trying add again
        removeListener(listenerId);
    }
    
    @Override
    public void delete()
    {
        // nothing to delete
    }
    
}
