package com.sxj.science.entity.export;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IItemDao;

@Entity(mapper = IItemDao.class)
@Table(name = "S_ITEM")
public class ItemEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5530506440954048996L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "PROJECT_ID")
    private String projectId;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "FILE_PATH")
    private String filePath;
    
    @Column(name = "COUNT")
    private Integer count;
    
    @Column(name = "STATE")
    private Integer state;
    
    @Column(name = "UPLOAD_TIME")
    private Date uploadTime;
    
    @Column(name="IS_SHOW")
    private Integer isShow;
    
    public Integer getIsShow()
    {
        return isShow;
    }

    public void setIsShow(Integer isShow)
    {
        this.isShow = isShow;
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
    
    public Date getUploadTime()
    {
        return uploadTime;
    }
    
    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
}
