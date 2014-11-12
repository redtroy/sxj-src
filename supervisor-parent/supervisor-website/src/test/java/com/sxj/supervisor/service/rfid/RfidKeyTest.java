package com.sxj.supervisor.service.rfid;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class RfidKeyTest
{
    @Autowired
    private IRfidKeyService rfidKeyService;
    
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
        Long key1 = rfidKeyService.getKey(100);
        Long key2 = rfidKeyService.getKey(250);
        Long key3 = rfidKeyService.getKey(360);
        System.out.println(key1);
        System.out.println(key2);
        System.out.println(key3);
    }
    
}
