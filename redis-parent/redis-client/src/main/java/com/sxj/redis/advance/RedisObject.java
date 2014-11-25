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
import io.netty.util.concurrent.Promise;

import com.sxj.redis.RedisAsyncConnection;
import com.sxj.redis.advance.async.ResultOperation;
import com.sxj.redis.advance.connection.ConnectionManager;
import com.sxj.redis.advance.core.RObject;

/**
 * Base Redisson object
 *
 * @author Nikita Koksharov
 * 
 */
public abstract class RedisObject implements RObject {

	private final ConnectionManager connectionManager;

	private final String name;

	public RedisObject(ConnectionManager connectionManager, String name) {
		this.connectionManager = connectionManager;
		this.name = name;
	}

	protected <V> Promise<V> newPromise() {
		return connectionManager.getGroup().next().<V> newPromise();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void delete() {
		delete(getName());
	}

	void delete(String name) {
		connectionManager.write(new ResultOperation<Long, Object>() {
			@Override
			protected Future<Long> execute(
					RedisAsyncConnection<Object, Object> async) {
				return async.del(getName());
			}
		});
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

}
