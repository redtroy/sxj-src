package com.sxj.supervisor.manage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ActiveProfiles("test")
public class WarMemberDAOTest
{
    @Autowired
    private IMemberDao memberDao;
    
    @Test
    public void testUpdateMember()
    {
        MemberEntity member = new MemberEntity();
        member.setId("1");
        member.setType(MemberTypeEnum.DAWP);
        memberDao.updateMember(member);
        System.out.println(member.getAddress());
    }
    
    @Test
    public void testGetMemeber()
    {
        memberDao.getMember("1");
    }
    
    public void setMemberDao(IMemberDao memberDao)
    {
        this.memberDao = memberDao;
    }
    
}
