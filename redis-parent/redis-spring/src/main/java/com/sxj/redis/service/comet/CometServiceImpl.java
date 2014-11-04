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

public class CometServiceImpl implements BeanFactoryPostProcessor {

	private static RedisCollections collections;

	private static RedisConcurrent redisConcurrent;

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		collections = beanFactory.getBean(RedisCollections.class);
		redisConcurrent = beanFactory.getBean(RedisConcurrent.class);
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

	public static void delCount(String key) {
		RAtomicLong atomicLong = redisConcurrent.getAtomicLong(key);
		atomicLong.decrementAndGet();
	}

	public static Long takeCount(String key) {
		RAtomicLong atomicLong = redisConcurrent.getAtomicLong(key);
		long incrementAndGet = atomicLong.incrementAndGet();
		return incrementAndGet;

	}

}
