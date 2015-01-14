package com.sxj.supervisor;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.dao.rfid.logistics.ILogisticsRfidDao;
import com.sxj.supervisor.model.open.BatchModel;
import com.sxj.supervisor.service.rfid.open.IOpenRfidService;
import com.sxj.util.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class LogisticsRfidDAOTest
{
    @Autowired
    private ILogisticsRfidDao dao;
    
    @Autowired
    private IOpenRfidService service;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test() throws ServiceException, SQLException
    {
        BatchModel batchByRfid = service.getBatchByRfid("1010");
        Assert.assertNotNull(batchByRfid);
        
    }
    
    public void setDao(ILogisticsRfidDao dao)
    {
        this.dao = dao;
    }
    
    public void setService(IOpenRfidService service)
    {
        this.service = service;
    }
    
}
