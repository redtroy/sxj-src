package com.sxj.mybatis.orm.unit;

import java.util.ArrayList;
import java.util.List;

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
    
    private List<Function> buildFunctions()
    {
        List<Function> functions = new ArrayList<Function>();
        Function buildFunction = buildFunction();
        buildFunction.setFunctionName("list1");
        functions.add(buildFunction);
        buildFunction = buildFunction();
        buildFunction.setFunctionName("list2");
        functions.add(buildFunction);
        return functions;
        
    }
    
    private Function[] buildArrayFunctions()
    {
        Function[] functions = new Function[2];
        Function buildFunction = buildFunction();
        buildFunction.setFunctionName("list1");
        functions[0] = buildFunction;
        buildFunction = buildFunction();
        buildFunction.setFunctionName("list2");
        functions[1] = buildFunction;
        return functions;
        
    }
    
    public void testInsert()
    {
        Function function = buildFunction();
        mapper.insert(function);
        System.out.println("==============" + function.getFunctionId());
    }
    
    public void testGet()
    {
        Function function = mapper.getFunction("A6D7ZXo6oDMKbOtbY5vp6RF6nJXSbAK");
        mapper.getFunction("A6D7ZXo6oDMKbOtbY5vp6RF6nJXSbAK");
        System.out.println(function.getFunctionName());
    }
    
    public void testBatchInsert()
    {
        mapper.batchInsert(buildFunctions());
    }
    
    public void testBatchInsertArray()
    {
        mapper.batchInsert(buildArrayFunctions());
    }
    
    public void testBatchDelete()
    {
        List<String> functionIds = new ArrayList<String>();
        functionIds.add("4ilOonHRGS6xupOoBzHgORGawUcxOSJ");
        functionIds.add("CMqrrU1Pbei0W187LbeGYbTiHGrJLF1");
        mapper.batchDelete(functionIds);
    }
    
    @Test
    public void testBatchDeleteArray()
    {
        String[] functionIds = new String[2];
        functionIds[0] = "szkaQLTo5oQxuymp0GewlvUeGndJQh3";
        functionIds[1] = "UBpFPlk3wIdVKezAu96QP4t0AAVmx2B";
        mapper.batchDelete(functionIds);
    }
}
