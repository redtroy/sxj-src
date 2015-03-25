package com.sxj.supervisor.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
public class SpringQuartzTest
{
    
    @Test
    public void test() throws InterruptedException
    {
        while (true)
        {
            Thread.currentThread().sleep(10000);
        }
    }
    
}
