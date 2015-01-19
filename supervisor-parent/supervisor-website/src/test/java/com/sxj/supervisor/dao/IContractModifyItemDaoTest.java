package com.sxj.supervisor.dao;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.dao.contract.IContractModifyItemDao;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class IContractModifyItemDaoTest
{
    @Autowired
    private IContractModifyItemDao dao;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test()
    {
        String[] modifyIds = new String[] { "A", "B" };
        List<ModifyItemEntity> queryItemsByModifyIds = dao.queryItemsByModifyIds(modifyIds);
    }
    
}
