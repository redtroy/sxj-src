package com.sxj.redis.core.serializer;

public interface Serializer
{
    public String serialize(final Object obj);
    
    public Object deserialize(final String str);
}
