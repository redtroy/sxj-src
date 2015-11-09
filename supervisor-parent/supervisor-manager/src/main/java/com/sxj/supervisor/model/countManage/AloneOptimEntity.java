package com.sxj.supervisor.model.countManage;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.pagination.Pagable;

public class AloneOptimEntity extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -4084761970899604505L;
    
    private String id;
    
    private String memberNo;
    
    private String fileName;
    
    private String filePath;
    
    private Date uploadTime;
    
    private Integer isShow;
    
    public Integer getIsShow()
    {
        return isShow;
    }

    public void setIsShow(Integer isShow)
    {
        this.isShow = isShow;
    }

    private String projectId;
    
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
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public Date getUploadTime()
    {
        return uploadTime;
    }
    
    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
    }
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
}
