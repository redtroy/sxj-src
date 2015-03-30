package com.sxj.supervisor.service.message;

import java.util.List;

import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.util.exception.ServiceException;

public interface IMessageConfigService
{
    public void addConfig(String memberNo, List<MessageConfigEntity> config)
            throws ServiceException;
    
    public void updateConfig(List<MessageConfigEntity> config)
            throws ServiceException;
    
    public List<MessageConfigEntity> queryConfigList(String memberNo)
            throws ServiceException;
    
    public MessageConfigEntity getConfig(String memberNo,
            MessageTypeEnum messageType) throws ServiceException;
    
    public void sendMessage(String memberNo, MessageTypeEnum messageType,
            String message) throws ServiceException;
    
    public void sendAllMessage(String message) throws ServiceException;
}
