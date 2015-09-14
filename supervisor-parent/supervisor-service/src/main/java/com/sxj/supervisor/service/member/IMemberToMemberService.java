package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.util.exception.ServiceException;

public interface IMemberToMemberService
{
    /**
     * 添加会员关系
     * @param member
     * @throws ServiceException
     */
    public void addMemberToMember(MemberToMemberEntity member)
            throws ServiceException;
    
    /**
     * 获取会员关系列表
     * @param query
     * @return
     * @throws ServiceException
     */
    public List<MemberToMemberEntity> query(MemberToMemberEntity query)
            throws ServiceException;
}
