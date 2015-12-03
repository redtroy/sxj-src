package com.sxj.supervisor.model.member;

import java.util.List;

import com.sxj.supervisor.entity.member.RelevanceMember;

public class RelistModel
{
    private List<RelevanceMember> relist;
    
    public List<RelevanceMember> getRelist()
    {
        return relist;
    }
    
    public void setRelist(List<RelevanceMember> relist)
    {
        this.relist = relist;
    }
    
}
