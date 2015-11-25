package com.sxj.supervisor.model.countManage;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.pagination.Pagable;

public class ProjectModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -430141560499434336L;
    
    private String id;
    
    private String projectNo;
    
    private String memberNo;
    
    private String memberName;
    
    private String name;
    
    private String zhaoBiaoNo;
    
    private String beiAnNo;
    
    private Integer fileCount;
    
    private Integer batchCount;
    
    private Date uploadTime;
    
    private Integer state;
    
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

    public String getProjectNo()
    {
        return projectNo;
    }

    public void setProjectNo(String projectNo)
    {
        this.projectNo = projectNo;
    }

    public String getMemberNo()
    {
        return memberNo;
    }

    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getZhaoBiaoNo()
    {
        return zhaoBiaoNo;
    }

    public void setZhaoBiaoNo(String zhaoBiaoNo)
    {
        this.zhaoBiaoNo = zhaoBiaoNo;
    }

    public String getBeiAnNo()
    {
        return beiAnNo;
    }

    public void setBeiAnNo(String beiAnNo)
    {
        this.beiAnNo = beiAnNo;
    }

    public Integer getFileCount()
    {
        return fileCount;
    }

    public void setFileCount(Integer fileCount)
    {
        this.fileCount = fileCount;
    }

    public Integer getBatchCount()
    {
        return batchCount;
    }

    public void setBatchCount(Integer batchCount)
    {
        this.batchCount = batchCount;
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
