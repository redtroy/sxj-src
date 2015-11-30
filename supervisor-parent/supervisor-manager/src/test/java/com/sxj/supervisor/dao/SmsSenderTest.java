package com.sxj.supervisor.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.service.message.IMessageConfigService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class SmsSenderTest
{
    @Autowired
    private IMessageConfigService configService;
    
    @Test
    public void test()
    {
        configService.sendAllMessage("您有一条新的开发商招标信息");
    }
    
}
