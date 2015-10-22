package com.sxj.science.model.result;

/**
 * 下料规格
 * @author Administrator
 *
 */
public class PartSpecification
{
    /**
     * 长度
     */
    private Double length;
    
    /**
     * 数量
     */
    private Long count;
    
    public PartSpecification(Double length, Long count)
    {
        super();
        this.length = length;
        this.count = count;
    }
    
    public Double getLength()
    {
        return length;
    }
    
    public void setLength(Double length)
    {
        this.length = length;
    }
    
    public Long getCount()
    {
        return count;
    }
    
    public void setCount(Long count)
    {
        this.count = count;
    }
    
}
