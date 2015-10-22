package com.sxj.science.model;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class WindowTypeQuery extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1202541490627723611L;
    
    private String area;
    
    private String[] companyNameArr;
    
    private String[] typeArr;
    
    private String[] seriesArr;

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public String[] getCompanyNameArr()
    {
        return companyNameArr;
    }

    public void setCompanyNameArr(String[] companyNameArr)
    {
        this.companyNameArr = companyNameArr;
    }

    public String[] getTypeArr()
    {
        return typeArr;
    }

    public void setTypeArr(String[] typeArr)
    {
        this.typeArr = typeArr;
    }

    public String[] getSeriesArr()
    {
        return seriesArr;
    }

    public void setSeriesArr(String[] seriesArr)
    {
        this.seriesArr = seriesArr;
    }
    
}
