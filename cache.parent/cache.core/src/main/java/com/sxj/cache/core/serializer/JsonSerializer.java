package com.sxj.cache.core.serializer;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxj.cache.core.CacheException;

public class JsonSerializer implements Serializer
{
    
    @Override
    public String serialize(final Object obj)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            String valueAsString = mapper.writeValueAsString(obj);
            CacheObject cacheObject = new CacheObject();
            cacheObject.setType(obj.getClass());
            cacheObject.setValue(valueAsString);
            return mapper.writeValueAsString(cacheObject);
        }
        catch (Exception e)
        {
            throw new CacheException(e);
        }
    }
    
    @Override
    public Object deserialize(final String str)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            CacheObject readValue = mapper.readValue(str, CacheObject.class);
            return mapper.readValue(readValue.getValue(), readValue.getType());
        }
        catch (IOException e)
        {
            throw new CacheException(e);
        }
    }
    
}
