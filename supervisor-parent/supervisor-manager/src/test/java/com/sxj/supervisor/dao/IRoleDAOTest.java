package com.sxj.supervisor.dao;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.dao.system.IRoleDao;
import com.sxj.supervisor.entity.system.RoleEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class IRoleDAOTest
{
    @Autowired
    IRoleDao roleDao;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void test()
    {
        RoleEntity role = new RoleEntity();
        RoleEntity role1 = new RoleEntity();
        RoleEntity[] roles = new RoleEntity[] { role, role1 };
        roleDao.addRoles(roles);
    }
    
}
