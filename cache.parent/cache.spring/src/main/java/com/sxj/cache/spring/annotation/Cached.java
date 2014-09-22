package com.sxj.cache.spring.annotation;

import com.sxj.cache.manager.CacheLevel;

public @interface Cached
{
    String name() default "methodCache";
    
    int timeToLive() default 5;
    
    CacheLevel level() default CacheLevel.NONE;
}
