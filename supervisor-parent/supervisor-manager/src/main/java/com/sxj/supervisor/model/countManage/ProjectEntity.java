package com.sxj.supervisor.model.countManage;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.pagination.Pagable;

public class ProjectEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8410314125777298517L;
    
    private String id;
    
    private String projectNo;
    
    private String noType;
    
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

    public String getMemberName()
    {
        return memberName;
    }

    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
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

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getFileCount()
    {
        return fileCount;
    }
    
    public void setFileCount(Integer fileCount)
    {
        this.fileCount = fileCount;
    }
    
    public String getProjectNo()
    {
        return projectNo;
    }
    
    public void setProjectNo(String projectNo)
    {
        this.projectNo = projectNo;
    }
    
    public Date getUploadTime()
    {
        return uploadTime;
    }
    
    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
    }
    
    public String getNoType()
    {
        return noType;
    }
    
    public void setNoType(String noType)
    {
        this.noType = noType;
    }
    
    public Integer getBatchCount()
    {
        return batchCount;
    }
    
    public void setBatchCount(Integer batchCount)
    {
        this.batchCount = batchCount;
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
