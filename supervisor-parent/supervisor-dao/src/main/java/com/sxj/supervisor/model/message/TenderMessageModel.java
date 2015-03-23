package com.sxj.supervisor.model.message;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.entity.message.TenderMessageEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;

public class TenderMessageModel extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5123146449337291079L;
    
    private WindDoorEntity info = new WindDoorEntity();
    
    private TenderMessageEntity message = new TenderMessageEntity();
    
    public String getId()
    {
        return message.getId();
    }
    
    public void setId(String id)
    {
        message.setId(id);
    }
    
    public String getInfoId()
    {
        return message.getInfoId();
    }
    
    public void setInfoId(String infoId)
    {
        message.setInfoId(infoId);
    }
    
    public MessageStateEnum getState()
    {
        return message.getState();
    }
    
    public void setState(MessageStateEnum state)
    {
        message.setState(state);
    }
    
    public String getXmmc()
    {
        return info.getXmmc();
    }
    
    public void setXmmc(String xmmc)
    {
        info.setXmmc(xmmc);
    }
    
    public String getMemberNo()
    {
        return message.getMemberNo();
    }
    
    public void setMemberNo(String memberNo)
    {
        message.setMemberNo(memberNo);
    }
    
    public Date getNowDate()
    {
        return info.getNowDate();
    }
    
    public void setNowDate(Date nowDate)
    {
        info.setNowDate(nowDate);
    }
    
}
