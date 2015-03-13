package com.sxj.supervisor.entity.message;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.enu.message.MessageTypeEnum;

@Table(name = "M_MESSAGE_CONFIG")
public class MessageConfigEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5000262880259450715L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "IS_ACCETP")
    private Boolean isAccetp;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "MESSAGE_TYPE")
    private MessageTypeEnum messageType;
    
    @Column(name = "PHONE")
    private String phone;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Boolean getIsAccetp()
    {
        return isAccetp;
    }
    
    public void setIsAccetp(Boolean isAccetp)
    {
        this.isAccetp = isAccetp;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public MessageTypeEnum getMessageType()
    {
        return messageType;
    }
    
    public void setMessageType(MessageTypeEnum messageType)
    {
        this.messageType = messageType;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
}
