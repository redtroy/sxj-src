package com.sxj.science.model.statis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sxj.mybatis.pagination.Pagable;

public class StatisticsModel extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -182763003183469961L;
    
    private String projectName;
    
    private String date;
    
    private List<StatisticsItemModel> items = new ArrayList<>();
    
    public String getProjectName()
    {
        return projectName;
    }
    
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public List<StatisticsItemModel> getItems()
    {
        return items;
    }
    
    public void setItems(List<StatisticsItemModel> items)
    {
        this.items = items;
    }
    
}
