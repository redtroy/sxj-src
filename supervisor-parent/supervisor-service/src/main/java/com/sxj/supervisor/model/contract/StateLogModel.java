package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.Date;

public class StateLogModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8223133461217484753L;
    
    private Date modifyDate;
    
    private Integer state;
    
    private String stateTitle;
    
    public Date getModifyDate()
    {
        return modifyDate;
    }
    
    public void setModifyDate(Date modifyDate)
    {
        this.modifyDate = modifyDate;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    public String getStateTitle()
    {
        return stateTitle;
    }
    
    public void setStateTitle(String stateTitle)
    {
        this.stateTitle = stateTitle;
    }
}
