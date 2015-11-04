package com.sxj.science.website.model.optimization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OptimizationParamParent implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -3470785313946559548L;
    
    private String projectId;
    
    private String kerf;
    
    private String minLength;
    
    private String maxLength;
    
    private String step;
    
    private List<OptimizationParam> paramList = new ArrayList<OptimizationParam>();
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
    
    public String getKerf()
    {
        return kerf;
    }
    
    public void setKerf(String kerf)
    {
        this.kerf = kerf;
    }
    
    public String getMinLength()
    {
        return minLength;
    }
    
    public void setMinLength(String minLength)
    {
        this.minLength = minLength;
    }
    
    public String getMaxLength()
    {
        return maxLength;
    }
    
    public void setMaxLength(String maxLength)
    {
        this.maxLength = maxLength;
    }
    
    public String getStep()
    {
        return step;
    }
    
    public void setStep(String step)
    {
        this.step = step;
    }
    
    public List<OptimizationParam> getParamList()
    {
        return paramList;
    }
    
    public void setParamList(List<OptimizationParam> paramList)
    {
        this.paramList = paramList;
    }
    
}
