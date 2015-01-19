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

import java.util.Collection;

import com.sxj.redis.RedisAsyncConnection;
import com.sxj.redis.advance.async.ResultOperation;
import com.sxj.redis.advance.connection.manager.ConnectionManager;
import com.sxj.redis.advance.core.RHyperLogLog;

public class RedisHyperLogLog<V> extends RedisObject implements RHyperLogLog<V>
{
    
    RedisHyperLogLog(ConnectionManager connectionManager, String name)
    {
        super(connectionManager, name);
    }
    
    @Override
    public long add(V obj)
    {
        return getConnectionManager().get(addAsync(obj));
    }
    
    @Override
    public long addAll(Collection<V> objects)
    {
        return getConnectionManager().get(addAllAsync(objects));
    }
    
    @Override
    public long count()
    {
        return getConnectionManager().get(countAsync());
    }
    
    @Override
    public long countWith(String... otherLogNames)
    {
        return getConnectionManager().get(countWithAsync(otherLogNames));
    }
    
    @Override
    public long mergeWith(String... otherLogNames)
    {
        return getConnectionManager().get(mergeWithAsync(otherLogNames));
    }
    
    @Override
    public Future<Long> addAsync(final V obj)
    {
        return getConnectionManager().writeAsync(new ResultOperation<Long, V>()
        {
            @Override
            protected Future<Long> execute(RedisAsyncConnection<Object, V> async)
            {
                return async.pfadd(getName(), obj);
            }
        });
    }
    
    @Override
    public Future<Long> addAllAsync(final Collection<V> objects)
    {
        return getConnectionManager().writeAsync(new ResultOperation<Long, Object>()
        {
            @Override
            protected Future<Long> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.pfadd(getName(), objects.toArray());
            }
        });
    }
    
    @Override
    public Future<Long> countAsync()
    {
        return getConnectionManager().writeAsync(new ResultOperation<Long, Object>()
        {
            @Override
            protected Future<Long> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.pfcount(getName());
            }
        });
    }
    
    @Override
    public Future<Long> countWithAsync(final String... otherLogNames)
    {
        return getConnectionManager().writeAsync(new ResultOperation<Long, Object>()
        {
            @Override
            protected Future<Long> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.pfcount(getName(), otherLogNames);
            }
        });
    }
    
    @Override
    public Future<Long> mergeWithAsync(final String... otherLogNames)
    {
        return getConnectionManager().writeAsync(new ResultOperation<Long, Object>()
        {
            @Override
            protected Future<Long> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.pfmerge(getName(), otherLogNames);
            }
        });
    }
    
}
