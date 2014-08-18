package com.sxj.cache.core;

public interface Serializer
{
    public String serialize(Object obj);
    
    public Object deserialize(String str);
}
