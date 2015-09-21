package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 会员关系
 * @author nishaotang
 *
 */
public interface IMemberToMemberDao
{
    /**
     * 添加会员关系
     *
     * @param roles
     **/
    @Insert
    public void addTo(MemberToMemberEntity entity);
    
    /**
     * 删除会员关系
     *
     * @param roles
     **/
    @Delete
    public void deleteInfo(String id);
    
    /**
     * 获取会员关系列表
     *
     * @param accountId
     **/
    public List<MemberToMemberEntity> query(
            QueryCondition<MemberToMemberEntity> query);
    
    public void del(String memberNo);
    
}
