package com.sxj.mybatis.shard.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.sxj.mybatis.shard.MybatisConfiguration;
import com.sxj.mybatis.shard.mapper.MapperScanConfigurator;

public class MybatisMapperProcessor implements
        ApplicationListener<ContextRefreshedEvent>
{
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        try
        {
            for (String clsName : MapperScanConfigurator.getMapperInterfaces())
            {
                MybatisConfiguration.getConfiguration().addMapper(Class.forName(clsName));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        
    }
    
}
