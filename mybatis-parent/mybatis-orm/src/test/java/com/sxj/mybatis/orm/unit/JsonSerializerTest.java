package com.sxj.mybatis.orm.unit;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxj.cache.core.util.CacheObject;
import com.sxj.cache.core.util.ClassTypeAdapter;
import com.sxj.mybatis.orm.model.Function;

public class JsonSerializerTest
{
    
    @Test
    public void test()
    {
        String value = "[{\"functionId\":\"A6D7ZXo6oDMKbOtbY5vp6RF6nJXSbAK\",\"functionName\":\"abc\"}]";
        CacheObject object = new CacheObject();
        object.setType(Function.class);
        object.setValue(value);
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class,
                new ClassTypeAdapter()).create();
        gson.fromJson(object.getValue(), new ArrayList<Function>().getClass());
        fail("Not yet implemented");
    }
}
