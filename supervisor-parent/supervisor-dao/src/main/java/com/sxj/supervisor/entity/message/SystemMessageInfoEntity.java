package com.sxj.supervisor.entity.message;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.message.ISystemInfoDao;

@Table(name = "M_SYSTEM_INFO")
@Entity(mapper = ISystemInfoDao.class)
public class SystemMessageInfoEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7453661734788919159L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "TITLE")
    private String title;
    
    @Column(name = "MESSAGE")
    private String message;
    
    @Column(name = "SEND_DATE")
    private Date sendDate;
    
    @Column(name = "MEMBER_LIST")
    private String memberList;
    
    @Column(name = "MEMBER_TYPE_LIST")
    private String memberTypeList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public String getMemberList()
    {
        return memberList;
    }
    
    public void setMemberList(String memberList)
    {
        this.memberList = memberList;
    }
    
    public String getMemberTypeList()
    {
        return memberTypeList;
    }
    
    public void setMemberTypeList(String memberTypeList)
    {
        this.memberTypeList = memberTypeList;
    }
    
}
