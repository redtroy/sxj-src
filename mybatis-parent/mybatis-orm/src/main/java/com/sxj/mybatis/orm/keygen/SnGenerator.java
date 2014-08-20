package com.sxj.mybatis.orm.keygen;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.Transaction;

import com.sxj.mybatis.dialect.Dialect;
import com.sxj.mybatis.dialect.MySql5Dialect;
import com.sxj.mybatis.dialect.OracleDialect;
import com.sxj.mybatis.dialect.SN;
import com.sxj.mybatis.sn.annotations.Sn;
import com.sxj.spring.modules.util.ReflectUtils;
import com.sxj.spring.modules.util.Reflections;

public class SnGenerator
{
    private static Map<String, List<Field>> cachedSnFields = new WeakHashMap<String, List<Field>>();
    
    public void generateSn(Executor executor, MappedStatement ms,
            Object parameter) throws SQLException
    {
        Transaction transaction = executor.getTransaction();
        Connection connection = null;
        Statement statement = null;
        try
        {
            connection = transaction.getConnection();
            statement = connection.createStatement();
            new SnGenerator().processBefore(ms, statement, parameter);
            transaction.commit();
        }
        catch (SQLException e)
        {
            transaction.rollback();
        }
        finally
        {
            if (statement != null)
                statement.close();
            //            if (connection != null)
            //                connection.close();
        }
    }
    
    private void processBefore(MappedStatement ms, Statement statement,
            Object parameter)
    {
        try
        {
            Dialect dialect = getDialect(ms.getConfiguration());
            Class<?> userClass = Reflections.getUserClass(parameter);
            List<Field> snFields = findSnFields(userClass);
            for (Field field : snFields)
            {
                Sn sn = field.getAnnotation(Sn.class);
                SN snPojo = new SN();
                snPojo.setStep(sn.step());
                snPojo.setSn(sn.sn());
                snPojo.setStub(sn.stub());
                snPojo.setStubValue(sn.stubValue());
                snPojo.setTableName(sn.table());
                String snSql = dialect.getSnString(snPojo);
                
                initSn(dialect, statement, snPojo);
                
                while (statement.executeUpdate(snSql) < 1)
                {
                    snPojo.setCurrent(snPojo.getCurrent() + snPojo.getStep());
                }
                DecimalFormat df = new DecimalFormat(sn.pattern());
                Reflections.invokeSetter(parameter,
                        field.getName(),
                        snPojo.getStubValue()
                                + df.format(snPojo.getCurrent()
                                        + snPojo.getStep()));
                
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private void initSn(Dialect dialect, Statement statement, SN snPojo)
            throws SQLException
    {
        String snSelectString = dialect.getSnSelectString(snPojo);
        ResultSet rs = null;
        try
        {
            rs = statement.executeQuery(snSelectString);
            rs.last();
            int row = rs.getRow();
            if (row == 0)
            {
                String snInsertString = dialect.getSnInsertString(snPojo);
                statement.executeUpdate(snInsertString);
                snPojo.setCurrent(0);
            }
            else
                snPojo.setCurrent(rs.getLong(1));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (rs != null)
                rs.close();
        }
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
    
    private Dialect getDialect(Configuration configuration)
    {
        Dialect.Type databaseType = null;
        try
        {
            String dbType = configuration.getVariables()
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
    
}
