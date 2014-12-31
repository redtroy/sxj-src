package com.sxj.supervisor.service.rfid;

import java.io.ByteArrayInputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.mybatis.shard.datasource.DataSourceFactory;
import com.sxj.spring.modules.util.Identities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class MysqlLoaderTest
{
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void test()
    {
        DataSource writeDs = DataSourceFactory.getDataNodes()
                .get(0)
                .getWriteNodes()
                .get(0);
        Connection connection = DataSourceUtils.getConnection(writeDs);
        try
        {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 100000; i++)
            {
                String value = Identities.randomBase62(32) + ","
                        + Identities.randomBase62(12) + "中文";
                sb.append(value);
                sb.append(System.getProperty("line.separator"));
            }
            ByteArrayInputStream bis = new ByteArrayInputStream(sb.toString()
                    .getBytes("UTF-8"));
            System.out.println(new Date());
            
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);
            PreparedStatement prepareStatement = connection.prepareStatement("LOAD DATA LOCAL INFILE 'tmp.csv' IGNORE INTO TABLE TEST_FUNCTION fields terminated by ',' (ID,TITLE) ");
            if (prepareStatement.isWrapperFor(com.mysql.jdbc.PreparedStatement.class))
            {
                com.mysql.jdbc.PreparedStatement unwrap = prepareStatement.unwrap(com.mysql.jdbc.PreparedStatement.class);
                //                unwrap.setLocalInfileInputStream(pis);
                //                boolean rows = unwrap.execute();
                
                unwrap.setLocalInfileInputStream(bis);
                int rows = unwrap.executeUpdate();
                System.out.println(rows);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println(new Date());
            DataSourceUtils.releaseConnection(connection, writeDs);
        }
    }
}
