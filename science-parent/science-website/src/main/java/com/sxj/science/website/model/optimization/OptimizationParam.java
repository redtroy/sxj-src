package com.sxj.science.website.model.optimization;

import java.io.Serializable;

public class OptimizationParam implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -3911195122924154130L;
    
    private String name;
    
    private String type;
    
    private String ylength;
    
    /**
     * 长度
     */
    private String[] length;
    
    /**
     * 数量
     */
    private String[] quantity;
    
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
    
    public String[] getLength()
    {
        return length;
    }
    
    public void setLength(String[] length)
    {
        this.length = length;
    }
    
    public String[] getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String[] quantity)
    {
        this.quantity = quantity;
    }
    
    public String getYlength()
    {
        return ylength;
    }
    
    public void setYlength(String ylength)
    {
        this.ylength = ylength;
    }
    
}
