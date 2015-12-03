package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.member.RelevanceMember;

public interface IRelevanceMemberDao
{
    /**
     * 根据会员号查询
     * @param memberNo
     * @return
     */
    public List<RelevanceMember> getEnityBymemberNo(String memberNo);
    
    /**
     * 插入
     * @param re
     */
    @Insert
    public void add(RelevanceMember re);
    
    /**
     * 根据会员号删除
     */
    public void del(String memberNo);
}
