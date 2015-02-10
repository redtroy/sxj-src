package com.sxj.supervisor;

import org.junit.Test;

import com.google.gson.Gson;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.model.open.Bacth;
import com.sxj.supervisor.model.open.BatchModel;

public class JsonMapperTest
{
    
    @Test
    public void test()
    {
        BatchModel root = new BatchModel();
        Bacth batch = new Bacth();
        batch.setState("a");
        root.setBatchList(batch);
        String json = JsonMapper.nonEmptyMapper().toJson(root);
        System.out.println(json);
        BatchModel fromJson = JsonMapper.nonDefaultMapper().fromJson(json,
                BatchModel.class);
        Gson gson = new Gson();
        BatchModel fromJson2 = gson.fromJson(json, BatchModel.class);
        org.junit.Assert.assertEquals("a", fromJson2.getBatchList().getState());
    }
    
}
