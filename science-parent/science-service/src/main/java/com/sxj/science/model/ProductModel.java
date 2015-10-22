package com.sxj.science.model;

import java.io.Serializable;
import java.util.Date;

public class ProductModel implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -6414829090025990493L;
    
    private String id;
    
    private Integer index;
    
    private String name;
    
    private String width;
    
    private String height;
    
    private Double area;
    
    private String quantity;
    
    private String windowCode;
    
    private String docId;

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public String getDocId()
    {
        return docId;
    }

    public void setDocId(String docId)
    {
        this.docId = docId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getWidth()
    {
        return width;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public String getHeight()
    {
        return height;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public Double getArea()
    {
        return area;
    }

    public void setArea(Double area)
    {
        this.area = area;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
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
