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

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.sxj.redis.RedisAsyncConnection;
import com.sxj.redis.advance.async.ResultOperation;
import com.sxj.redis.advance.connection.ConnectionManager;
import com.sxj.redis.advance.core.RExpirable;

public abstract class RedisExpirable extends RedisObject implements RExpirable
{
    
    public RedisExpirable(ConnectionManager connectionManager, String name)
    {
        super(connectionManager, name);
    }
    
    @Override
    public boolean expire(final long timeToLive, final TimeUnit timeUnit)
    {
        return getConnectionManager().write(new ResultOperation<Boolean, Object>()
        {
            @Override
            protected Future<Boolean> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.expire(getName(), timeUnit.toSeconds(timeToLive));
            }
        });
    }
    
    @Override
    public boolean expireAt(final long timestamp)
    {
        return getConnectionManager().write(new ResultOperation<Boolean, Object>()
        {
            @Override
            protected Future<Boolean> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.expireat(getName(), timestamp);
            }
        });
    }
    
    @Override
    public boolean expireAt(final Date timestamp)
    {
        return getConnectionManager().write(new ResultOperation<Boolean, Object>()
        {
            @Override
            protected Future<Boolean> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.expireat(getName(), timestamp);
            }
        });
    }
    
    @Override
    public boolean clearExpire()
    {
        return getConnectionManager().write(new ResultOperation<Boolean, Object>()
        {
            @Override
            protected Future<Boolean> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.persist(getName());
            }
        });
    }
    
    @Override
    public long remainTimeToLive()
    {
        return getConnectionManager().write(new ResultOperation<Long, Object>()
        {
            @Override
            protected Future<Long> execute(
                    RedisAsyncConnection<Object, Object> async)
            {
                return async.ttl(getName());
            }
        });
    }
    
}
