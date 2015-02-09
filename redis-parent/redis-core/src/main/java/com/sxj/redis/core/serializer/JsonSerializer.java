package com.sxj.redis.core.serializer;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxj.redis.core.exception.RedisException;

public class JsonSerializer implements Serializer
{
    
    @Override
    public String serialize(final Object obj)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            String valueAsString = mapper.writeValueAsString(obj);
            JsonObject cacheObject = new JsonObject();
            cacheObject.setType(obj.getClass());
            cacheObject.setValue(valueAsString);
            return mapper.writeValueAsString(cacheObject);
        }
        catch (Exception e)
        {
            throw new RedisException(e);
        }
    }
    
    @Override
    public Object deserialize(final String str)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            JsonObject readValue = mapper.readValue(str, JsonObject.class);
            Object readValue2 = mapper.readValue(readValue.getValue(),
                    readValue.getType());
            return readValue2;
        }
        catch (IOException e)
        {
            throw new RedisException(e);
        }
    }
    
}
