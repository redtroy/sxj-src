package com.sxj.mybatis.orm.keygen;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.sxj.spring.modules.util.Identities;

public class UuidKeyGenerator implements KeyGenerator
{
    private int length;
    
    public UuidKeyGenerator(int length)
    {
        super();
        this.length = length;
    }
    
    public void setLength(int length)
    {
        this.length = length;
    }
    
    @Override
    public void processBefore(Executor executor, MappedStatement ms,
            Statement stmt, Object parameter)
    {
        
        Configuration configuration = ms.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        String[] keyProperties = ms.getKeyProperties();
        final MetaObject metaParam = configuration.newMetaObject(parameter);
        TypeHandler<?>[] typeHandlers = getTypeHandlers(typeHandlerRegistry,
                metaParam,
                keyProperties);
        try
        {
            populateKeys(metaParam, keyProperties, typeHandlers);
        }
        catch (SQLException e)
        {
            throw new ExecutorException(
                    "Error getting generated key or setting result to parameter object. Cause: "
                            + e, e);
        }
    }
    
    @Override
    public void processAfter(Executor executor, MappedStatement ms,
            Statement stmt, Object parameter)
    {
        
    }
    
    private TypeHandler<?>[] getTypeHandlers(
            TypeHandlerRegistry typeHandlerRegistry, MetaObject metaParam,
            String[] keyProperties)
    {
        TypeHandler<?>[] typeHandlers = new TypeHandler<?>[keyProperties.length];
        for (int i = 0; i < keyProperties.length; i++)
        {
            if (metaParam.hasSetter(keyProperties[i]))
            {
                Class<?> keyPropertyType = metaParam.getSetterType(keyProperties[i]);
                TypeHandler<?> th = typeHandlerRegistry.getTypeHandler(keyPropertyType);
                typeHandlers[i] = th;
            }
        }
        return typeHandlers;
    }
    
    private void populateKeys(MetaObject metaParam, String[] keyProperties,
            TypeHandler<?>[] typeHandlers) throws SQLException
    {
        for (int i = 0; i < keyProperties.length; i++)
        {
            metaParam.setValue(keyProperties[i],
                    Identities.randomBase62(length));
        }
    }
}
