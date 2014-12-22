package com.sxj.file.controller;

import com.sxj.cache.manager.HierarchicalCacheManager;

public class SupervisorShiroRedisCacheManager {

	private int level = 2;

	public Boolean get(String name, String id) {
		Object dsadas = HierarchicalCacheManager.get(level, name, id);
		System.out.println(dsadas);
		return HierarchicalCacheManager.exists(level, name, id);
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
