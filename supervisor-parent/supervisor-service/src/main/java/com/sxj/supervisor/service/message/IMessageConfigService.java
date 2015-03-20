package com.sxj.supervisor.service.message;

import java.util.List;

import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.util.exception.ServiceException;

public interface IMessageConfigService
{
    public void addConfig(String memberNo, List<MessageConfigEntity> config)
            throws ServiceException;
    
    public void updateConfig(List<MessageConfigEntity> config)
            throws ServiceException;
    
    public List<MessageConfigEntity> queryConfigList(String memberNo)
            throws ServiceException;
}
