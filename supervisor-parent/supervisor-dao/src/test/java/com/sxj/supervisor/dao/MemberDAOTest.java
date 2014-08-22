package com.sxj.supervisor.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.entity.member.MemberEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
public class MemberDAOTest
{
    @Autowired
    private IMemberDao memberDao;
    
    @Test
    public void testGetMember()
    {
        MemberEntity member = memberDao.getMember("1");
        System.out.println(member.getAddress());
    }
    
    public void setMemberDao(IMemberDao memberDao)
    {
        this.memberDao = memberDao;
    }
    
}
