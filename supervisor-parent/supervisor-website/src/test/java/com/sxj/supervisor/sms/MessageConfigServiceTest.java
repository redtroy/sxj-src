package com.sxj.supervisor.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.service.message.IMessageConfigService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class MessageConfigServiceTest
{
    @Autowired
    private IMessageConfigService messageConfigService;
    
    @Test
    public void testSendAllMessage()
    {
        messageConfigService.sendAllMessage("您有一条新的开发商招标信息");
    }
    
}
