package com.sxj.mybatis.shard.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.sxj.mybatis.shard.mapper.MapperScanConfigurator;
import com.sxj.mybatis.shard.util.ConfigUtil;

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
                ConfigUtil.getConfiguration().addMapper(Class.forName(clsName));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        
    }
    
}
