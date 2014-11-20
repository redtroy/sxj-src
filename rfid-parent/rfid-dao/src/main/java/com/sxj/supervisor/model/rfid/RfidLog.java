package com.sxj.supervisor.model.rfid;

import java.io.Serializable;

public class RfidLog implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8476956461963902070L;
    
    private Integer id;
    
    private String state;
    
    private String date;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    @Override
    public int hashCode()
    {
        
        return (id == null ? 0 : id)
                + (state == null ? "".hashCode() : state.hashCode());
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (hashCode() == obj.hashCode())
            return true;
        return super.equals(obj);
    }
    
}
