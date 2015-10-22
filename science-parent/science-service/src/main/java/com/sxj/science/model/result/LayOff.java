package com.sxj.science.model.result;

import java.util.ArrayList;
import java.util.List;

/**下料方式
 * @author Administrator
 *
 */
public class LayOff
{
    /**
     * 所需原料根数
     */
    private Long materialNum;
    
    /**
     * 余料
     */
    private Double left;
    
    /**
     * 利用率
     */
    private String usagePercent;
    
    /**
     * 下料规格
     */
    private List<PartSpecification> partSpecifications;
    
    private String partStr = "";
    
    public Long getMaterialNum()
    {
        return materialNum;
    }
    
    public void setMaterialNum(Long materialNum)
    {
        this.materialNum = materialNum;
    }
    
    public Double getLeft()
    {
        return left;
    }
    
    public void setLeft(Double left)
    {
        this.left = left;
    }
    
    public String getUsagePercent()
    {
        
        return usagePercent;
    }
    
    public String getPartStr()
    {
        if (this.partSpecifications != null
                && this.partSpecifications.size() > 0)
        {
            for (PartSpecification partSpecification : this.partSpecifications)
            {
                partStr = partStr + partSpecification.getLength() + "x"
                        + partSpecification.getCount() + " ";
            }
        }
        return partStr;
    }
    
    public List<PartSpecification> getPartSpecifications()
    {
        if (partSpecifications == null)
            partSpecifications = new ArrayList<>();
        return partSpecifications;
    }
    
    public void setPartSpecifications(List<PartSpecification> partSpecifications)
    {
        this.partSpecifications = partSpecifications;
    }
    
}
