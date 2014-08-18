package com.sxj.cache.core;

public interface Serializer
{
    public String serialize(final Object obj);
    
    public Object deserialize(final String str);
}
