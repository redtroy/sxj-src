package com.sxj.science.model;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class ProjectQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8574050307827141220L;
    
    private String memberNo;
    
    private String name;
    
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
    
}
