package com.sxj.science.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 优化结果
 * @author Administrator
 *
 */
public class OptimizedResult
{
    /**
     * 型材名称
     */
    private String sectionName;
    
    /**
     * 规格型号
     */
    private String sectionType;
    
    /**
     * 优化参数
     */
    private MaterialParam param;
    
    /**
     * 原料总根数
     */
    private Long materialNum;
    
    /**
     * 数量
     */
    private Double quantity;
    
    /**
     * 利用率
     */
    private Double usagePercent;
    
    /**
     * 下料规格
     */
    private List<PartSpecification> partSpecifications;
    
    /**
     * 下料方式
     */
    private List<LayOff> layOffs;
    
    private List<LayOff> layOffs2;
    
    public String getSectionName()
    {
        return sectionName;
    }
    
    public void setSectionName(String sectionName)
    {
        this.sectionName = sectionName;
    }
    
    public MaterialParam getParam()
    {
        return param;
    }
    
    public void setParam(MaterialParam param)
    {
        this.param = param;
    }
    
    public Long getMaterialNum()
    {
        return materialNum;
    }
    
    public void setMaterialNum(Long materialNum)
    {
        this.materialNum = materialNum;
    }
    
    public Double getUsagePercent()
    {
        return usagePercent;
    }
    
    public void setUsagePercent(Double usagePercent)
    {
        this.usagePercent = usagePercent;
    }
    
    public List<PartSpecification> getPartSpecifications()
    {
        if (partSpecifications == null)
            partSpecifications = new ArrayList<>();
        return partSpecifications;
    }
    
    public void setPartSpecifications(List<PartSpecification> specifications)
    {
        this.partSpecifications = specifications;
    }
    
    public List<LayOff> getLayOffs()
    {
        if (layOffs == null)
            layOffs = new ArrayList<>();
        return layOffs;
    }
    
    public void setLayOffs(List<LayOff> layOffs)
    {
        this.layOffs = layOffs;
    }
    
    public String getSectionType()
    {
        return sectionType;
    }
    
    public void setSectionType(String sectionType)
    {
        this.sectionType = sectionType;
    }
    
    public Double getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }
    
    public List<LayOff> getLayOffs2()
    {
        return getLayOffs();
    }
    
}
