package com.sxj.supervisor.model.countManage;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

/**
     * 
     */
public class DocEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5277783147370329047L;
    
    private String id;
    
    private String windowId;
    
    private String projectId;
    
    private String projectName;
    
    private String itemId;
    
    private String memberNo;
    
    private String filePath;
    
    private String windowCode;
    
    private String color;
    
    private String quantity;
    
    private String series;
    
    private String windowFaca;
    
    private String width;
    
    private String height;
    
    private String htmlData;
    
    public String getWidth()
    {
        return width;
    }
    
    public void setWidth(String width)
    {
        this.width = width;
    }
    
    public String getHeight()
    {
        return height;
    }
    
    public void setHeight(String height)
    {
        this.height = height;
    }
    
    public String getWindowId()
    {
        return windowId;
    }
    
    public void setWindowId(String windowId)
    {
        this.windowId = windowId;
    }
    
    public String getProjectName()
    {
        return projectName;
    }
    
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public String getWindowCode()
    {
        return windowCode;
    }
    
    public void setWindowCode(String windowCode)
    {
        this.windowCode = windowCode;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public void setColor(String color)
    {
        this.color = color;
    }
    
    public String getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getSeries()
    {
        return series;
    }
    
    public void setSeries(String series)
    {
        this.series = series;
    }
    
    public String getWindowFaca()
    {
        return windowFaca;
    }
    
    public void setWindowFaca(String windowFaca)
    {
        this.windowFaca = windowFaca;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
    
    public String getItemId()
    {
        return itemId;
    }
    
    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }
    
    public String getHtmlData()
    {
        return htmlData;
    }
    
    public void setHtmlData(String htmlData)
    {
        this.htmlData = htmlData;
    }
    
    @Override
    public String toString()
    {
        return getColor();
    }
    
}
