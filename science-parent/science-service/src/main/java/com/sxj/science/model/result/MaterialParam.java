package com.sxj.science.model.result;

/**
 * 优化原料参数
 * @author Administrator
 *
 */
public class MaterialParam
{
    /**
     * 原料长度
     */
    private Double length;
    
    /**
     * 锯缝
     */
    private Double kerf;
    
    public Double getLength()
    {
        return length;
    }
    
    public void setLength(Double length)
    {
        this.length = length;
    }
    
    public Double getKerf()
    {
        return kerf;
    }
    
    public void setKerf(Double kerf)
    {
        this.kerf = kerf;
    }
}
