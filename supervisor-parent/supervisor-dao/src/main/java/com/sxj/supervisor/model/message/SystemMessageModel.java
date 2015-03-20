package com.sxj.supervisor.model.message;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.entity.message.SystemMessageEntity;
import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.message.MessageStateEnum;

public class SystemMessageModel extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7537614474809857668L;
    
    private SystemMessageEntity message = new SystemMessageEntity();
    
    private SystemMessageInfoEntity info = new SystemMessageInfoEntity();
    
    public String getMemberNo()
    {
        return message.getMemberNo();
    }
    
    public void setMemberNo(String memberNo)
    {
        message.setMemberNo(memberNo);
    }
    
    public String getInfoId()
    {
        return message.getInfoId();
    }
    
    public void setInfoId(String infoId)
    {
        message.setInfoId(infoId);
    }
    
    public MemberTypeEnum getMemberType()
    {
        return message.getMemberType();
    }
    
    public void setMemberType(MemberTypeEnum memberType)
    {
        message.setMemberType(memberType);
    }
    
    public MessageStateEnum getState()
    {
        return message.getState();
    }
    
    public void setState(MessageStateEnum state)
    {
        message.setState(state);
    }
    
    public String getId()
    {
        return message.getId();
    }
    
    public void setId(String id)
    {
        message.setId(id);
    }
    
    public String getTitle()
    {
        return info.getTitle();
    }
    
    public void setTitle(String title)
    {
        info.setTitle(title);
    }
    
    public String getMessage()
    {
        return info.getMessage();
    }
    
    public void setMessage(String message)
    {
        info.setMessage(message);
    }
    
    public Date getSendDate()
    {
        return info.getSendDate();
    }
    
    public void setSendDate(Date sendDate)
    {
        info.setSendDate(sendDate);
    }
    
}
