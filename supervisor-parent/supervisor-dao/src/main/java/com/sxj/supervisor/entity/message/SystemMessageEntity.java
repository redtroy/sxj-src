package com.sxj.supervisor.entity.message;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.message.ISystemMessageDao;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.message.MessageStateEnum;

@Table(name = "M_SYSTEM_MESSAGE")
@Entity(mapper = ISystemMessageDao.class)
public class SystemMessageEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7634490098201261990L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "MEMBER_NAME")
    private String memberName;
    
    @Column(name = "INFO_ID")
    private String infoId;
    
    @Column(name = "MEMBER_TYPE")
    private MemberTypeEnum memberType;
    
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
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getInfoId()
    {
        return infoId;
    }
    
    public void setInfoId(String infoId)
    {
        this.infoId = infoId;
    }
    
    public MemberTypeEnum getMemberType()
    {
        return memberType;
    }
    
    public void setMemberType(MemberTypeEnum memberType)
    {
        this.memberType = memberType;
    }
    
    public MessageStateEnum getState()
    {
        return state;
    }
    
    public void setState(MessageStateEnum state)
    {
        this.state = state;
    }
    
    public String getMemberName()
    {
        return memberName;
    }
    
    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }
    
}
