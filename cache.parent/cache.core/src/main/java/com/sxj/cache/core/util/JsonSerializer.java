package com.sxj.cache.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxj.cache.core.Serializer;

public class JsonSerializer implements Serializer
{
    
    @Override
    public String serialize(final Object obj)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class,
                new ClassTypeAdapter()).create();
        String obJson = gson.toJson(obj);
        CacheObject cacheObject = new CacheObject();
        cacheObject.setType(obj.getClass());
        cacheObject.setValue(obJson);
        return gson.toJson(cacheObject);
    }
    
    @Override
    public Object deserialize(final String str)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class,
                new ClassTypeAdapter()).create();
        CacheObject fromJson = gson.fromJson(str, CacheObject.class);
        return gson.fromJson(fromJson.getValue(), fromJson.getType());
    }
    
}
