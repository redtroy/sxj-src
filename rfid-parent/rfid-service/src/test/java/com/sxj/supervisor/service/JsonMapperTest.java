package com.sxj.supervisor.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.model.rfid.RfidLog;

public class JsonMapperTest
{
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
        
    }
    
    @Test
    public void test()
    {
        List<RfidLog> logs = new ArrayList<RfidLog>();
        RfidLog log1 = new RfidLog();
        log1.setState("a");
        log1.setDate("date");
        RfidLog log2 = new RfidLog();
        log2.setState("b");
        log2.setDate("date1");
        logs.add(log1);
        
        logs.add(log2);
        String json = (JsonMapper.nonEmptyMapper().toJson(logs));
        System.out.println(json);
        // json = "[{\"date\":\"2014-11-10 10:51:11\",\"state\":\"未使用\"}]";
        List<RfidLog> fromJson = JsonMapper.nonEmptyMapper().fromJson(json,
                new JsonMapper().contructCollectionType(ArrayList.class,
                        RfidLog.class));
        System.out.println(fromJson.get(0).getState());
    }
}
