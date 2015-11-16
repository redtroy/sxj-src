package com.sxj.science.model;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class DocQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8100226291164881872L;
    
    private String itemId;
    
    private String[] itemIds;
    
    private String[] ids;
    
    private String series;
    
    private String windowCode;
    
    public String getItemId()
    {
        return itemId;
    }
    
    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }
    
    public String[] getItemIds()
    {
        return itemIds;
    }
    
    public void setItemIds(String[] itemIds)
    {
        this.itemIds = itemIds;
    }
    
    public String[] getIds()
    {
        return ids;
    }
    
    public void setIds(String[] ids)
    {
        this.ids = ids;
    }
    
    public String getSeries()
    {
        return series;
    }
    
    public void setSeries(String series)
    {
        this.series = series;
    }
    
    public String getWindowCode()
    {
        return windowCode;
    }
    
    public void setWindowCode(String windowCode)
    {
        this.windowCode = windowCode;
    }
    
}
