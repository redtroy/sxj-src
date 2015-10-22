package com.sxj.science.model;

import java.io.Serializable;

public class PartsModel implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 6953658975502359948L;
    
    private String id;
    
    private Integer index;
    
    private String name;
    
    private String type;
    
    private String unit;
    
    private String quantity;
    
    private String techonlogy;
    
    private String used;
    
    private String docId;

    public String getDocId()
    {
        return docId;
    }

    public void setDocId(String docId)
    {
        this.docId = docId;
    }

    public String getUsed()
    {
        return used;
    }

    public void setUsed(String used)
    {
        this.used = used;
    }

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

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getTechonlogy()
    {
        return techonlogy;
    }

    public void setTechonlogy(String techonlogy)
    {
        this.techonlogy = techonlogy;
    }
    
}
