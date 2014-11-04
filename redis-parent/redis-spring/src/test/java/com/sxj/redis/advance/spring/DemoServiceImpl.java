package com.sxj.redis.advance.spring;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.redis.advance.RedisCollections;
import com.sxj.redis.advance.RedisConcurrent;
import com.sxj.redis.advance.core.RAtomicLong;
import com.sxj.redis.advance.core.RSet;
import com.sxj.redis.advance.spring.annotation.Lock;

@Service
public class DemoServiceImpl {
	@Autowired
	private RedisCollections collections;

	private static final String SETNAME = "demoSet";

	@Lock(timeOut = 10000)
	public void sayHello(String hello) {
		System.out.println(hello);
	}

	@Lock(name = "messageChannel1")
    public void add(String ele)
    {
    	RedisConcurrent.create().getLock(name)
    	RAtomicLong atomicLong = RedisConcurrent.create().getAtomicLong("sdd");
    	long incrementAndGet = atomicLong.incrementAndGet();
        RSet<String> set = collections.getSet(SETNAME);
        set.add(ele);
    }

	@Lock(name = "messageChannel1")
	public void remove(String ele) {
		RSet<String> set = collections.getSet(SETNAME);
		set.remove(ele);
	}

	@Lock(name = "messageChannel1")
	public void print() {
		RSet<String> set = collections.getSet(SETNAME);
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	public void setCollections(RedisCollections collections) {
		this.collections = collections;
	}
}
