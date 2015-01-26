package com.sxj.supervisor.model.contract;

import java.io.Serializable;

public class BatchItemModel implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2539889911747910932L;
    
    /**
     * 主键
    **/
    private String id;
    
    /**
     * 批次ID
    **/
    private String batchId;
    
    /**
     * 产品规格
    **/
    private String productModel;
    
    /**
     * 数量
    **/
    private Float quantity;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getBatchId()
    {
        return batchId;
    }
    
    public void setBatchId(String batchId)
    {
        this.batchId = batchId;
    }
    
    public String getProductModel()
    {
        return productModel;
    }
    
    public void setProductModel(String productModel)
    {
        this.productModel = productModel;
    }
    
    public Float getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(Float quantity)
    {
        this.quantity = quantity;
    }
    
}
