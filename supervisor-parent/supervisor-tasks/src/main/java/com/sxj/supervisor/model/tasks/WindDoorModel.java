package com.sxj.supervisor.model.tasks;

import com.sxj.supervisor.entity.gather.WindDoorEntity;

public class WindDoorModel extends WindDoorEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -6646912373367372232L;
    
    private String starDate;
    
    private String endDate;
    
    public String getStarDate()
    {
        return starDate;
    }
    
    public void setStarDate(String starDate)
    {
        this.starDate = starDate;
    }
    
    public String getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    
}
