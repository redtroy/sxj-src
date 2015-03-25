package com.sxj.supervisor.jsoup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sxj.supervisor.service.tasks.impl.WindDoorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
public class SpringJsoupTest
{
    @Autowired
    WindDoorServiceImpl service;
    
    @Test
    public void test()
    {
        service.WindDoorGather();
    }
    
}
