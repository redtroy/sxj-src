package com.sxj.supervisor.model.member;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class LogQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1305713025441918429L;
    
    private String memberNo;
    
    private String nowPage;
    
    private String prePage;
    
    private String nextpage;
    
    private String startTime;
    
    private String endTime;
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getNowPage()
    {
        return nowPage;
    }
    
    public void setNowPage(String nowPage)
    {
        this.nowPage = nowPage;
    }
    
    public String getPrePage()
    {
        return prePage;
    }
    
    public void setPrePage(String prePage)
    {
        this.prePage = prePage;
    }
    
    public String getNextpage()
    {
        return nextpage;
    }
    
    public void setNextpage(String nextpage)
    {
        this.nextpage = nextpage;
    }
    
    public String getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
}
