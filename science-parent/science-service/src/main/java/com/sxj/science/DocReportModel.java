package com.sxj.science;

import java.io.Serializable;

public class DocReportModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5017873160323714127L;

    private String id;
    
    private Integer index;
    
    private String series;
    
    private String windowCode;
    
    private String width;
    
    private String height;
    
    private String quantity;
    
    private Double sumArea;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
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

    public String getWidth()
    {
        return width;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public Double getSumArea()
    {
        return sumArea;
    }

    public void setSumArea(Double sumArea)
    {
        this.sumArea = sumArea;
    }
    
    public String getHeight()
    {
        return height;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }
}
