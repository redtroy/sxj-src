package com.sxj.redis.advance.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/applicationContext-lock.xml" })
public class MethodLockAspectJTest
{
    @Autowired
    private DemoServiceImpl service;
    
    @Test
    public void test()
    {
        service.sayHello("abc");
    }
}
