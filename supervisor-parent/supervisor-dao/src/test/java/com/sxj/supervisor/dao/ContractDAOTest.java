package com.sxj.supervisor.dao;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.entity.contract.ContractEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
public class ContractDAOTest
{
    @Autowired
    IContractDao contractDAO;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void test()
    {
        ContractEntity contract = contractDAO.getContract("1");
        System.out.println(contract.getContractNo());
        
    }
    
    public void setContractDAO(IContractDao contractDAO)
    {
        this.contractDAO = contractDAO;
    }
    
}
