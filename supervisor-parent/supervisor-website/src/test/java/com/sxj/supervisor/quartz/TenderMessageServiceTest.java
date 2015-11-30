package com.sxj.supervisor.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.service.message.ITenderMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TenderMessageServiceTest
{
    @Autowired
    private ITenderMessageService tenderMessageService;
    
    public void testFetchUnreads()
    {
        tenderMessageService.fetchUnreads("MEM000001");
    }
    
    @Test
    public void testCountUnreads()
    {
        Long countUnreads = tenderMessageService.countUnreads("MEM000001");
        System.err.println(countUnreads);
    }
    
    public void testModifyState()
    {
        tenderMessageService.modifyState("0Y6SENjj6ie2S110qfsOhfoH8CsCxkPC",
                MessageStateEnum.READ);
    }
}
