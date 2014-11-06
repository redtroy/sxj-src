package com.sxj.redis.service.comet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.sxj.redis.advance.RedisCollections;
import com.sxj.redis.advance.RedisConcurrent;
import com.sxj.redis.advance.core.RAtomicLong;
import com.sxj.redis.advance.core.RLock;
import com.sxj.redis.advance.core.RSet;
import com.sxj.redis.advance.core.RTopic;
import com.sxj.redis.advance.topic.RedisTopics;

public class CometServiceImpl implements BeanFactoryPostProcessor {

	private static RedisCollections collections;

	private static RedisConcurrent redisConcurrent;

	private static RedisTopics redisTopics;

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		collections = beanFactory.getBean(RedisCollections.class);
		redisConcurrent = beanFactory.getBean(RedisConcurrent.class);
		redisTopics = beanFactory.getBean(RedisTopics.class);
	}

	public static void add(String key, String obj) {
		RLock lock = redisConcurrent.getLock(key + "_lock");
		try {
			lock.lock();
			RSet<String> set = collections.getSet(key);
			set.add(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}

	}

	public static List<String> get(String key) {
		try {
			List<String> list = new ArrayList<String>();
			RSet<String> set = collections.getSet(key);
			Iterator<String> iterator = set.iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					list.add(iterator.next());
				}
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void remove(String key, String obj) {
		RLock lock = redisConcurrent.getLock(key + "_lock");
		try {
			lock.lock();
			RSet<String> set = collections.getSet(key);
			set.remove(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}

	}

	public static Long getCount(String key) {
		RAtomicLong atomicLong = redisConcurrent.getAtomicLong(key);
		return atomicLong.get();
	}

	public static void setCount(String key, Long count) {
		RAtomicLong atomicLong = redisConcurrent.getAtomicLong(key);
		atomicLong.set(count);
	}

	public static Long takeCount(String key) {
		RAtomicLong atomicLong = redisConcurrent.getAtomicLong(key);
		long incrementAndGet = atomicLong.incrementAndGet();
		return incrementAndGet;

	}

	public static Long subCount(String key) {
		RAtomicLong atomicLong = redisConcurrent.getAtomicLong(key);
		long incrementAndGet = atomicLong.decrementAndGet();
		return incrementAndGet;

	}

	public static void sendMessage(String channel, Object message) {
		RTopic<Object> topic = redisTopics.getTopic(channel);
		topic.publish(message);
	}
}
