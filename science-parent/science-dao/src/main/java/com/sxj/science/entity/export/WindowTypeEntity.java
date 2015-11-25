package com.sxj.science.entity.export;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IWindowTypeDao;
/**
     * 
     */
@Entity(mapper = IWindowTypeDao.class)
@Table(name = "S_WINDOW_TYPE")
public class WindowTypeEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5000421224563274525L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "WINID")
    @Sn(pattern = "000000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValue = "XL")
    private String winId;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "MODEL_PATH")
    private String modelPath;
    
    @Column(name="AREA")
    private String area;
    
    @Column(name="COMPANY_NAME")
    private String companyName;
    
    @Column(name="TYPE")
    private String type;
    
    @Column(name="SERIES")
    private String series;
    
    @Column(name="IMAGE_SRC")
    private String imageSrc;
    
    private String searchStr;
    
    @Column(name="HTMLDATA")
    private String htmlData;
    
    @Column(name="HTMLDATABACKUP")
    private String htmlDataBackup;
    
    @Column(name="FINISH")
    private String finish;
    
    public String getFinish()
    {
        return finish;
    }

    public void setFinish(String finish)
    {
        this.finish = finish;
    }

    public String getHtmlDataBackup()
    {
        return htmlDataBackup;
    }

    public void setHtmlDataBackup(String htmlDataBackup)
    {
        this.htmlDataBackup = htmlDataBackup;
    }

    public String getHtmlData()
    {
        return htmlData;
    }

    public void setHtmlData(String htmlData)
    {
        this.htmlData = htmlData;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    private String areaName;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getWinId()
    {
        return winId;
    }

    public void setWinId(String winId)
    {
        this.winId = winId;
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

    public String getSearchStr()
    {
        return searchStr;
    }

    public void setSearchStr(String searchStr)
    {
        this.searchStr = searchStr;
    }
}
