package com.sxj.mybatis.orm.mapper;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.model.Function;

public interface FunctionMapper
{
    @Insert
    int insert(Function function);
    
    @Get
    Function getFunction(String functionId);
}
