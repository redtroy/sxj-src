package com.sxj.supervisor.model.countManage;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.pagination.Pagable;

public class WindowTypeModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -6724207152478900404L;
    
    private String id;
    
    private String winId;
    
    private String name;
    
    private String modelPath;
    
    private String area;
    
    private String companyName;
    
    private String type;
    
    private String series;
    
    private String imageSrc;
    
    private String areaName;
    
    private String htmlData;
    
    public String getHtmlData()
    {
        return htmlData;
    }

    public void setHtmlData(String htmlData)
    {
        this.htmlData = htmlData;
    }

    public String getWinId()
    {
        return winId;
    }

    public void setWinId(String winId)
    {
        this.winId = winId;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getModelPath()
    {
        return modelPath;
    }
    
    public void setModelPath(String modelPath)
    {
        this.modelPath = modelPath;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSeries()
    {
        return series;
    }

    public void setSeries(String series)
    {
        this.series = series;
    }

    public String getImageSrc()
    {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc)
    {
        this.imageSrc = imageSrc;
    }
    
}
