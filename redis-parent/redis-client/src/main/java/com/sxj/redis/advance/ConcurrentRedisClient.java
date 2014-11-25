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

import java.util.Date;

import com.sxj.redis.advance.core.RAtomicLong;
import com.sxj.redis.advance.core.RCountDownLatch;
import com.sxj.redis.advance.core.RLock;

public interface ConcurrentRedisClient {

	/**
	 * Returns "atomic long" instance by name.
	 *
	 * @param name
	 *            of the "atomic long"
	 * @return
	 */
	RAtomicLong getAtomicLong(String name);

	/**
	 * 
	 * @param name
	 * @param second
	 * @return
	 */
	RAtomicLong getAtomicLong(String name, long second);

	/**
	 * 
	 * @param name
	 * @param time
	 * @return
	 */
	RAtomicLong getAtomicLong(String name, Date time);

	/**
	 * Returns "count down latch" instance by name.
	 *
	 * @param name
	 *            of the "count down latch"
	 * @return
	 */
	RCountDownLatch getCountDownLatch(String name);

	/**
	 * Returns lock instance by name.
	 *
	 * @param name
	 *            of lock
	 * @return
	 */
	RLock getLock(String name);

}
