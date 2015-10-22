package com.sxj.science.model.result;

import java.io.Serializable;
import java.util.List;

public class OptimizedModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7376994879401143904L;
    
    private String cltongjiPath;
    
    private String cllingliaoPath;
    
    private String xlyouhuaPath;
    
    private Double usagePercent;
    
    private List<OptimizedResult> result;
    
    public String getCltongjiPath()
    {
        return cltongjiPath;
    }
    
    public void setCltongjiPath(String cltongjiPath)
    {
        this.cltongjiPath = cltongjiPath;
    }
    
    public String getCllingliaoPath()
    {
        return cllingliaoPath;
    }
    
    public void setCllingliaoPath(String cllingliaoPath)
    {
        this.cllingliaoPath = cllingliaoPath;
    }
    
    public String getXlyouhuaPath()
    {
        return xlyouhuaPath;
    }
    
    public void setXlyouhuaPath(String xlyouhuaPath)
    {
        this.xlyouhuaPath = xlyouhuaPath;
    }
    
    public List<OptimizedResult> getResult()
    {
        return result;
    }
    
    public void setResult(List<OptimizedResult> result)
    {
        this.result = result;
    }
    
    public Double getUsagePercent()
    {
        usagePercent = 0d;
        if (this.result != null && this.result.size() > 0)
        {
            for (OptimizedResult optimizedResult : result)
            {
                Double usg = optimizedResult.getUsagePercent();
                usagePercent = usagePercent + usg;
            }
        }
        return usagePercent;
    }
    
}
