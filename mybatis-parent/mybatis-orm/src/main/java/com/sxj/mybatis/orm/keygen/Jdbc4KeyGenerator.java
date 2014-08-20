package com.sxj.mybatis.orm.keygen;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;

public class Jdbc4KeyGenerator extends Jdbc3KeyGenerator
{
    
    @Override
    public void processBefore(Executor executor, MappedStatement ms,
            Statement stmt, Object parameter)
    {
        super.processBefore(executor, ms, stmt, parameter);
        try
        {
            new SnGenerator().generateSn(executor, ms, parameter);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    
}
