package com.sxj.supervisor.model.login;

import java.io.Serializable;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;

public class SupervisorPrincipal implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7928061127084410598L;
    
    private MemberEntity member;
    
    private AccountEntity account;
    
    public MemberEntity getMember()
    {
        return member;
    }
    
    public void setMember(MemberEntity member)
    {
        this.member = member;
    }
    
    public AccountEntity getAccount()
    {
        return account;
    }
    
    public void setAccount(AccountEntity account)
    {
        this.account = account;
    }
    
}
