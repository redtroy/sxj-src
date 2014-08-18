package com.sxj.cache.core.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ClassTypeAdapter implements JsonSerializer<Class<?>>,
        JsonDeserializer<Class<?>>
{
    
    @Override
    public Class<?> deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException
    {
        String className = json.getAsString();
        try
        {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            throw new JsonParseException(e);
        }
    }
    
    @Override
    public JsonElement serialize(Class<?> src, Type typeOfSrc,
            JsonSerializationContext context)
    {
        return context.serialize(src.getName());
    }
    
}
