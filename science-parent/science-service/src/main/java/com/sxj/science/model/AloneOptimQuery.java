package com.sxj.science.model;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class AloneOptimQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2963414536971990392L;
    
    private String memberNo;
    
    private String projectId;
    
    private String[] ids;
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
    
    public String[] getIds()
    {
        return ids;
    }
    
    public void setIds(String[] ids)
    {
        this.ids = ids;
    }
    
}
