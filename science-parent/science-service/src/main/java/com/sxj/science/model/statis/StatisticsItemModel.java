package com.sxj.science.model.statis;

import java.io.Serializable;

public class StatisticsItemModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5477403812415855144L;
    
    private Integer no;
    
    private String name;
    
    private String type;
    
    private String length;
    
    private String quantity;
    
    private String remark;
    
    public Integer getNo()
    {
        return no;
    }
    
    public void setNo(Integer no)
    {
        this.no = no;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getLength()
    {
        return length;
    }
    
    public void setLength(String length)
    {
        this.length = length;
    }
    
    public String getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
