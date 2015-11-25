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
import com.sxj.science.dao.export.IHistoryDao;

@Entity(mapper = IHistoryDao.class)
@Table(name = "S_HISTORY")
public class HistoryEntity extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -7631732172511598480L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "HISTORY_NO")
    @Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValue = "LS")
    private String historyNo;
    
    @Column(name="PROJECT_NO")
    private String projectNo;
    
    @Column(name="MEMBER_NO")
    private String memberNo;
    
    @Column(name="MEMBER_NAME")
    private String memberName;
    
    @Column(name="UPLOAD_TIME")    
    private Date uploadTime;
    
    @Column(name="TYPE")
    private String type;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getHistoryNo()
    {
        return historyNo;
    }

    public void setHistoryNo(String historyNo)
    {
        this.historyNo = historyNo;
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

    public Date getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    
}
