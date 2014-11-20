package com.sxj.finance.website.login;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.sxj.redis.advance.RedisCollections;
import com.sxj.spring.modules.security.shiro.ShiroRedisCacheManager;

public class SupervisorShiroRedisCacheManager extends ShiroRedisCacheManager {
	private RedisCollections collections;

	@Override
	protected Cache createCache(String cacheName) throws CacheException {
		// TODO Auto-generated method stub
		SupervisorShiroRedisCache<String, Object> supervisorShiroRedisCache = new SupervisorShiroRedisCache<String, Object>(
				getLevel(), cacheName);
		supervisorShiroRedisCache.setCollections(collections);
		return supervisorShiroRedisCache;
	}

	public void setCollections(RedisCollections collections) {
		this.collections = collections;
	}

}
