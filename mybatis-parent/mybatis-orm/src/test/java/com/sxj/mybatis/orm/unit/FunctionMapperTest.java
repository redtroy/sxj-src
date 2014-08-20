package com.sxj.mybatis.orm.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.mybatis.orm.mapper.FunctionMapper;
import com.sxj.mybatis.orm.model.Function;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
public class FunctionMapperTest
{
    @Autowired
    private FunctionMapper mapper;
    
    public void setMapper(FunctionMapper mapper)
    {
        this.mapper = mapper;
    }
    
    private Function buildFunction()
    {
        Function function = new Function();
        return function;
    }
    
    public void testInsert()
    {
        Function function = buildFunction();
        mapper.insert(function);
        System.out.println("==============" + function.getFunctionId());
    }
    
    @Test
    public void testGet()
    {
        Function function = mapper.getFunction("A6D7ZXo6oDMKbOtbY5vp6RF6nJXSbAK");
        System.out.println(function.getFunctionName());
    }
    
}
