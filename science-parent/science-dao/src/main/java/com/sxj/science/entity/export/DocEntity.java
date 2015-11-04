package com.sxj.science.entity.export;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IDocDao;

/**
     * 
     */
@Entity(mapper = IDocDao.class)
@Table(name = "S_DOC")
public class DocEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5277783147370329047L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "WINDOW_ID")
    private String windowId;
    
    @Column(name = "PROJECT_ID")
    private String projectId;
    
    @Column(name = "PROJECT_NAME")
    private String projectName;
    
    @Column(name = "ITEM_ID")
    private String itemId;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "FILE_PATH")
    private String filePath;
    
    @Column(name = "WINDOW_CODE")
    private String windowCode;
    
    @Column(name = "COLOR")
    private String color;
    
    @Column(name = "QUANTITY")
    private String quantity;
    
    @Column(name = "SERIES")
    private String series;
    
    @Column(name = "WINDOW_FACA")
    private String windowFaca;
    
    @Column(name = "WIDTH")
    private String width;
    
    @Column(name = "HEIGHT")
    private String height;
    
    @Column(name = "HTML_DATA")
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
