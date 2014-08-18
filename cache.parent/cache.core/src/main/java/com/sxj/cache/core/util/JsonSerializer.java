package com.sxj.cache.core.util;

import com.google.gson.Gson;
import com.sxj.cache.core.Serializer;

public class JsonSerializer implements Serializer
{
    
    @Override
    public String serialize(final Object obj)
    {
        CacheObject cacheObject = new CacheObject();
        cacheObject.setType(obj.getClass());
        cacheObject.setJson(new Gson().toJson(obj));
        return new Gson().toJson(cacheObject);
    }
    
    @Override
    public Object deserialize(final String str)
    {
        CacheObject fromJson = new Gson().fromJson(str, CacheObject.class);
        return new Gson().fromJson(fromJson.getJson(), fromJson.getType());
    }
    
    private class CacheObject
    {
        private Class<?> type;
        
        private String json;
        
        public Class<?> getType()
        {
            return type;
        }
        
        public void setType(Class<?> type)
        {
            this.type = type;
        }
        
        public String getJson()
        {
            return json;
        }
        
        public void setJson(String json)
        {
            this.json = json;
        }
    }
    
}
