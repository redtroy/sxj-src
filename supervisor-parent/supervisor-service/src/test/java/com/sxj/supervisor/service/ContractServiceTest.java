package com.sxj.supervisor.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.service.contract.IContractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-shard.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ContractServiceTest
{
    @Autowired
    IContractService contractService;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void testAddContract()
    {
        ContractEntity contract = new ContractEntity();
        contract.setAddress("测试的地址hahahahahahahahaha");
        ContractItemEntity item = new ContractItemEntity();
        item.setProductName("测试的产品hahahahahahaha");
        List<ContractItemEntity> itemList = new ArrayList<ContractItemEntity>();
        itemList.add(item);
        item = new ContractItemEntity();
        item.setProductName("2测试的产品hahahahahahahaah");
        itemList.add(item);
        contractService.addContract(contract, itemList, "123");
    }
    
    public void testModifyContract()
    {
    }
    
    public void testGetContract()
    {
    }
    
    public void testRecordIdArr()
    {
    }
    
    public void testQueryContracts()
    {
    }
    
    public void testDeleteContract()
    {
    }
    
    public void testChangeContract()
    {
    }
    
    public void testSuppContract()
    {
    }
    
    public void testModifyState()
    {
    }
    
    public void testAddStateLog()
    {
    }
    
    public void testModifyCheckState()
    {
    }
    
    public void testGetContractByContractNo()
    {
    }
    
    public void testGetContractModelByContractNo()
    {
    }
    
    public void setContractService(IContractService contractService)
    {
        this.contractService = contractService;
    }
    
}
