package com.sxj.supervisor.dao;

import org.apache.shiro.cache.CacheManager;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    
    @Autowired
    @Qualifier("shiroCacheManager")
    CacheManager shiroCacheManager;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    public void test()
    {
        RoleEntity role = new RoleEntity();
        RoleEntity role1 = new RoleEntity();
        RoleEntity[] roles = new RoleEntity[] { role, role1 };
        roleDao.addRoles(roles);
    }
    
    @Test
    public void testDeleteSession()
    {
        shiroCacheManager.getCache("manage.session.cache.name")
                .remove("fe5bac9e-489f-46b8-866b-80e287ff9917");
        
    }
    
}
