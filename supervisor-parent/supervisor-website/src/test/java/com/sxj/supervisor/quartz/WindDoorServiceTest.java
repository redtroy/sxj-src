package com.sxj.supervisor.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.service.tasks.IWindDoorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class WindDoorServiceTest
{
    @Autowired
    private IWindDoorService windDoorService;
    
    @Test
    public void testWindDoorGather()
    {
        windDoorService.WindDoorGather();
    }
    
}
