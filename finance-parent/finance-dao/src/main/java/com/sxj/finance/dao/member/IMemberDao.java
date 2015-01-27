package com.sxj.finance.dao.member;

import java.util.List;

import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;

public interface IMemberDao
{
    
    /**
     * 获取会员信息
     *
     * @param id
     **/
    @Get
    public MemberEntity getMember(String id);
    
    /**
     * 更新会员
     *
     * @param member
     **/
    @Update
    public void updateMember(MemberEntity member);
    
    /**
     * 查询会员
     *
     * @param member
     * @param memberList
     **/
    public List<MemberEntity> queryMembers(QueryCondition<MemberEntity> query);
    
    /**
     * 获取会员信息
     *
     * @param id
     **/
    public MemberEntity getMemberByName(String name);
}
