package org.rfid.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import com.sxj.supervisor.model.rfid.RfidLog;

public class RfidLogTest
{
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    public void testEquals()
    {
        RfidLog log1 = new RfidLog();
        log1.setDate("1");
        log1.setId(1);
        log1.setState("s");
        RfidLog log2 = new RfidLog();
        log2.setDate("2");
        log2.setId(1);
        log2.setState("s");
        System.out.println(log1.equals(log2));
    }
    
    @Test
    public void testList()
    {
        RfidLog log1 = new RfidLog();
        log1.setDate("1");
        log1.setId(1);
        log1.setState("s");
        RfidLog log2 = new RfidLog();
        log2.setDate("2");
        log2.setId(2);
        log2.setState("s");
        List<RfidLog> logs = new ArrayList<RfidLog>();
        logs.add(log1);
        logs.add(log2);
        RfidLog log3 = new RfidLog();
        log3.setDate("3");
        log3.setId(1);
        log3.setState("s");
        logs.remove(log3);
        System.out.println(logs.size());
    }
    
}
