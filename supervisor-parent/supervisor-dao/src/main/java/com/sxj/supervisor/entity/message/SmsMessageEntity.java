package com.sxj.supervisor.entity.message;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

@Table(name = "M_SMS_MESSAGE")
public class SmsMessageEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8967471914612410521L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "PHONE_NO")
    private String phoneNo;
    
    @Column(name = "MESSAGE_NO")
    private String message;
    
    @Column(name = "SEND_DATE")
    private Date sendDate;
    
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
    
    public String getPhoneNo()
    {
        return phoneNo;
    }
    
    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
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
    
}
