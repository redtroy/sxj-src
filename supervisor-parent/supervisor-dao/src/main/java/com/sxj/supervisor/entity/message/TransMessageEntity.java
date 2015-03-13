package com.sxj.supervisor.entity.message;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.enu.message.MessageTypeEnum;

@Table(name = "M_TRANS_MESSAGE")
public class TransMessageEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7809693090864480363L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "TYPE")
    private MessageTypeEnum type;
    
    @Column(name = "STATE")
    private MessageStateEnum state;
    
    @Column(name = "MESSAGE")
    private String message;
    
    @Column(name = "CONTRACT_NO")
    private String contractNo;
    
    @Column(name = "BATCH_NO")
    private String batchNo;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "STATE_MESSAGE")
    private String stateMessage;
    
    @Column(name = "SEND_DATE")
    private Date sendDate;
    
    @Column(name = "VERSION_LOCK")
    private Integer version;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public MessageTypeEnum getType()
    {
        return type;
    }
    
    public void setType(MessageTypeEnum type)
    {
        this.type = type;
    }
    
    public MessageStateEnum getState()
    {
        return state;
    }
    
    public void setState(MessageStateEnum state)
    {
        this.state = state;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getContractNo()
    {
        return contractNo;
    }
    
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getStateMessage()
    {
        return stateMessage;
    }
    
    public void setStateMessage(String stateMessage)
    {
        this.stateMessage = stateMessage;
    }
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public Integer getVersion()
    {
        return version;
    }
    
    public void setVersion(Integer version)
    {
        this.version = version;
    }
    
}
