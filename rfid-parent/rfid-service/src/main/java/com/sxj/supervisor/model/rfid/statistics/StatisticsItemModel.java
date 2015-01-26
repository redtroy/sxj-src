package com.sxj.supervisor.model.rfid.statistics;

import java.io.Serializable;
import java.util.List;

public class StatisticsItemModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4237596274156476050L;
    
    private Double countSum;
    
    private List<String> dateList;
    
    private List<Double> countList;
    
    public Double getCountSum()
    {
        return countSum;
    }
    
    public void setCountSum(Double countSum)
    {
        this.countSum = countSum;
    }
    
    public List<String> getDateList()
    {
        return dateList;
    }
    
    public void setDateList(List<String> dateList)
    {
        this.dateList = dateList;
    }
    
    public List<Double> getCountList()
    {
        return countList;
    }
    
    public void setCountList(List<Double> countList)
    {
        this.countList = countList;
    }
    
}
