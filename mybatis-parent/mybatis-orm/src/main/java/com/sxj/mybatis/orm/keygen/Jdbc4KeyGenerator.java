package com.sxj.mybatis.orm.keygen;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;

import com.sxj.mybatis.orm.ConfigurationProperties;

public class Jdbc4KeyGenerator extends Jdbc3KeyGenerator
{
    
    @Override
    public void processBefore(Executor executor, MappedStatement ms,
            Statement stmt, Object parameter)
    {
        super.processBefore(executor, ms, stmt, parameter);
        try
        {
            SnGenerator snGenerator = new SnGenerator();
            snGenerator.generateSn(executor.getTransaction().getConnection(),
                    parameter,
                    ConfigurationProperties.getDialect(ms.getConfiguration()));
        }
        catch (SQLException sqle)
        {
            throw new RuntimeException(sqle);
        }
    }
    
}
