package com.sxj.supervisor.entity.message;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.enu.message.MessageStateEnum;

@Table(name = "M_TENDER_MESSAGE")
public class TenderMessageEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8538018961805674389L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "INFO_ID")
    private String infoId;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "STATE")
    private MessageStateEnum state;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getInfoId()
    {
        return infoId;
    }
    
    public void setInfoId(String infoId)
    {
        this.infoId = infoId;
    }
    
    public MessageStateEnum getState()
    {
        return state;
    }
    
    public void setState(MessageStateEnum state)
    {
        this.state = state;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
}
