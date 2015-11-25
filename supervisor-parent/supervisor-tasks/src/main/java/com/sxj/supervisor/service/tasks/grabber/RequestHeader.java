package com.sxj.supervisor.service.tasks.grabber;

import java.util.Map;

public class RequestHeader
{
    private String viewState;
    
    private String eventValidation;
    
    private String eventTarget;
    
    private String eventArgument;
    
    private Map<String, String> cookies;
    
    private Integer pages;
    
    private Integer pageNo;
    
    public RequestHeader(String viewState, String eventValidation)
    {
        super();
        this.viewState = viewState;
        this.eventValidation = eventValidation;
    }
    
    public RequestHeader(String viewState, String eventValidation,
            Map<String, String> cookies)
    {
        super();
        this.viewState = viewState;
        this.eventValidation = eventValidation;
        this.cookies = cookies;
    }
    
    public String getViewState()
    {
        return viewState;
    }
    
    public void setViewState(String viewState)
    {
        this.viewState = viewState;
    }
    
    public String getEventValidation()
    {
        return eventValidation;
    }
    
    public void setEventValidation(String eventValidation)
    {
        this.eventValidation = eventValidation;
    }
    
    public Map<String, String> getCookies()
    {
        return cookies;
    }
    
    public void setCookies(Map<String, String> cookies)
    {
        this.cookies = cookies;
    }
    
    public String getEventTarget()
    {
        return eventTarget;
    }
    
    public void setEventTarget(String eventTarget)
    {
        this.eventTarget = eventTarget;
    }
    
    public String getEventArgument()
    {
        return eventArgument;
    }
    
    public void setEventArgument(String eventArgument)
    {
        this.eventArgument = eventArgument;
    }
    
    public Integer getPages()
    {
        return pages;
    }
    
    public void setPages(Integer pages)
    {
        this.pages = pages;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    @Override
    public String toString()
    {
        return "RequestHeader [eventTarget=" + eventTarget + ", eventArgument="
                + eventArgument + "]";
    }
    
}
