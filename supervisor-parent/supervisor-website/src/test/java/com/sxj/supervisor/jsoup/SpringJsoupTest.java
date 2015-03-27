package com.sxj.supervisor.jsoup;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.supervisor.service.tasks.impl.WindDoorServiceImpl;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
public class SpringJsoupTest
{
    @Autowired
    WindDoorServiceImpl service;
    
    public void test()
    {
        service.WindDoorGather();
    }
    
    public void testClassLoader() throws IOException
    {
    }
}
