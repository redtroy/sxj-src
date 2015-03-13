package com.sxj.supervisor.service.meaagae;

import java.util.List;

import com.sxj.supervisor.entity.message.TransMessageEntity;
import com.sxj.supervisor.model.message.TransMessageQuery;
import com.sxj.util.exception.ServiceException;

public interface ITransMessageService
{
    public void addMessage(TransMessageEntity message) throws ServiceException;
    
    public TransMessageEntity getMessage(String id) throws ServiceException;
    
    public List<TransMessageEntity> queryMessageList(TransMessageQuery query)
            throws ServiceException;
}
