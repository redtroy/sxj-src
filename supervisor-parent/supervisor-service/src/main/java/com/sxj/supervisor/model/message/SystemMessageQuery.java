package com.sxj.supervisor.model.message;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class SystemMessageQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2178688936121244264L;
    
    private String memberNo;
    
    private String startDate;
    
    private String endDate;
    
    public String getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
    
    public String getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
}
