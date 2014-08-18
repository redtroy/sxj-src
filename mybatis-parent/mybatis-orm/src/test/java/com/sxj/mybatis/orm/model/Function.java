package com.sxj.mybatis.orm.model;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.orm.mapper.FunctionMapper;

@Entity(mapper = FunctionMapper.class)
@Table(name = "T_FUNCTION")
public class Function
{
    @Id(column = "F_FUNC_ID")
    @GeneratedValue(strategy = GenerationType.UUID, length = 31)
    private String functionId;
    
    @Column(name = "F_FUNC_NAME")
    private String functionName;
    
    public String getFunctionId()
    {
        return functionId;
    }
    
    public void setFunctionId(String functionId)
    {
        this.functionId = functionId;
    }
    
    public String getFunctionName()
    {
        return functionName;
    }
    
    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }
}
