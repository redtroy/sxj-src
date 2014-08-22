package com.sxj.cache.core.serializer;

public class CacheObject
{
    private Class<?> type;
    
    private String value;
    
    public Class<?> getType()
    {
        return type;
    }
    
    public void setType(Class<?> type)
    
    {
        this.type = type;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
}
