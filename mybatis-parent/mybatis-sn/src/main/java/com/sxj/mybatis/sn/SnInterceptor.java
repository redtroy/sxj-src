package com.sxj.mybatis.sn;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;

import com.sxj.mybatis.dialect.Dialect;
import com.sxj.mybatis.dialect.MySql5Dialect;
import com.sxj.mybatis.dialect.OracleDialect;
import com.sxj.mybatis.dialect.SN;
import com.sxj.mybatis.sn.annotations.Sn;
import com.sxj.spring.modules.util.ReflectUtils;
import com.sxj.spring.modules.util.Reflections;

public class SnInterceptor implements Interceptor
{
    private Map<String, List<Field>> cachedSnFields = new WeakHashMap<String, List<Field>>();
    
    private String dbType = "MYSQL";
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
        Dialect dialect = getDialect(statementHandler);
        BoundSql boundSql = statementHandler.getBoundSql();
        Object parameter = boundSql.getParameterObject();
        Connection connection = (Connection) invocation.getArgs()[0];
        Statement statement = connection.createStatement();
        Class<?> userClass = Reflections.getUserClass(parameter);
        List<Field> snFields = findSnFields(userClass);
        for (Field field : snFields)
        {
            Sn sn = field.getAnnotation(Sn.class);
            SN snPojo = new SN();
            snPojo.setStep(sn.step());
            snPojo.setStub(sn.stub());
            snPojo.setStubValue(sn.stubValue());
            snPojo.setTableName(sn.table());
            String snSql = dialect.getSnString(snPojo);
            while (statement.executeUpdate(snSql) < 1)
            {
                snPojo.setStubValue(snPojo.getStubValue() + snPojo.getStep());
            }
            DecimalFormat df = new DecimalFormat(sn.pattern());
            Reflections.invokeSetter(parameter,
                    field.getName(),
                    df.format(snPojo.getStubValue() + snPojo.getStep()));
        }
        return invocation.proceed();
    }
    
    private List<Field> findSnFields(Class<?> userClass)
    {
        List<Field> snFields = cachedSnFields.get(userClass.getName());
        if (snFields == null)
        {
            snFields = ReflectUtils.findFieldsAnnotatedWith(userClass, Sn.class);
            cachedSnFields.put(userClass.getName(), snFields);
        }
        return snFields;
    }
    
    private Dialect getDialect(RoutingStatementHandler statementHandler)
    {
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,
                new DefaultObjectFactory(),
                new DefaultObjectWrapperFactory());
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        Dialect.Type databaseType = null;
        try
        {
            dbType = configuration.getVariables()
                    .getProperty("dialect")
                    .toUpperCase();
            databaseType = Dialect.Type.valueOf(dbType);
        }
        catch (Exception e)
        {
            throw new RuntimeException(
                    "the value of the dialect property in configuration.xml is not defined : "
                            + configuration.getVariables()
                                    .getProperty("dialect"));
        }
        Dialect dialect = null;
        switch (databaseType)
        {
            case MYSQL:
                dialect = new MySql5Dialect();
                break;
            case ORACLE:
                dialect = new OracleDialect();
                break;
        
        }
        return dialect;
    }
    
    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(Properties properties)
    {
        // TODO Auto-generated method stub
        
    }
    
}
