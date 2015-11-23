package com.sxj.science.model;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class ProjectQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8574050307827141220L;
    
    private String id;
    
    private String projectNo;
    
    private String memberNo;
    
    private String memberName;
    
    private String name;
    
    private String zhaoBiaoNo;
    
    private String beiAnNo;
    
    private Integer isShow;
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getProjectNo()
    {
        return projectNo;
    }
    
    public void setProjectNo(String projectNo)
    {
        this.projectNo = projectNo;
    }
    
    public String getMemberName()
    {
        return memberName;
    }
    
    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }
    
    public String getZhaoBiaoNo()
    {
        return zhaoBiaoNo;
    }
    
    public void setZhaoBiaoNo(String zhaoBiaoNo)
    {
        this.zhaoBiaoNo = zhaoBiaoNo;
    }
    
    public String getBeiAnNo()
    {
        return beiAnNo;
    }
    
    public void setBeiAnNo(String beiAnNo)
    {
        this.beiAnNo = beiAnNo;
    }
    
    public Integer getIsShow()
    {
        return isShow;
    }
    
    public void setIsShow(Integer isShow)
    {
        this.isShow = isShow;
    }
    
}
