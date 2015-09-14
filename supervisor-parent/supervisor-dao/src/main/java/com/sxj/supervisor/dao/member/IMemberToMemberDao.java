package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IMemberToMemberDao
{
    /**
     * 添加权限
     *
     * @param roles
     **/
    @Insert
    public void addTo(MemberToMemberEntity entity);
    
    /**
     * 获取权限列表
     *
     * @param accountId
     **/
    public List<MemberToMemberEntity> query(
            QueryCondition<MemberToMemberEntity> query);
    
}
