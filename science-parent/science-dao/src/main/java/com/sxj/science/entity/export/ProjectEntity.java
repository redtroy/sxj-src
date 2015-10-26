package com.sxj.science.entity.export;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IProjectDao;

@Entity(mapper = IProjectDao.class)
@Table(name = "S_PROJECT")
public class ProjectEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8410314125777298517L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "PROJECT_NO")
    @Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValueProperty = "noType")
    private String projectNo;
    
    private String noType;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "FILE_COUNT")
    private Integer fileCount;
    
    @Column(name = "UPLOAD_TIME")
    private Date uploadTime;
    
    @Column(name = "STATE")
    private Integer state;
    
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
    
}
