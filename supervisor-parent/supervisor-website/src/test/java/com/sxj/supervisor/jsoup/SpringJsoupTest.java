package com.sxj.supervisor.jsoup;

import java.io.FileNotFoundException;

import org.jsoup.nodes.Entities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
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
    
    public void testClassLoader() throws FileNotFoundException
    {
        ResourcePatternUtils.getResourcePatternResolver(new PathMatchingResourcePatternResolver())
                .getResource("/entities-base.properties");
        Entities.class.getResource("/entities-base.properties");
    }
}