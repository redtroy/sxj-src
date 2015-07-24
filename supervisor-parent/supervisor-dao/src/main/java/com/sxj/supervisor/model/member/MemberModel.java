package com.sxj.supervisor.model.member;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.entity.member.MemberEntity;

public class MemberModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -6712020475413882586L;
    
    private MemberEntity memberA;
    
    private MemberEntity memberB;

    public MemberEntity getMemberA()
    {
        return memberA;
    }

    public void setMemberA(MemberEntity memberA)
    {
        this.memberA = memberA;
    }

    public MemberEntity getMemberB()
    {
        return memberB;
    }

    public void setMemberB(MemberEntity memberB)
    {
        this.memberB = memberB;
    }
    
}
