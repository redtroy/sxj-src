package com.sxj.supervisor.jsoup;

import java.io.FileNotFoundException;

import org.jsoup.nodes.Entities;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.sxj.spring.modules.util.Reflections;
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
    
    @Test
    public void testClassLoader() throws FileNotFoundException,
            InstantiationException, IllegalAccessException
    {
        ResourcePatternUtils.getResourcePatternResolver(new PathMatchingResourcePatternResolver())
                .getResource("classpath*:/entities-base.properties");
        Reflections.invokeMethodByName(Entities.class.newInstance(),
                "loadProperties",
                new String[] { "entities-base.properties" });
    }
}
