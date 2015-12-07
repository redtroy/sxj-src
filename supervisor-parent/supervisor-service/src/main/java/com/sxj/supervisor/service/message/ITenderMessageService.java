package com.sxj.supervisor.service.message;

import java.util.List;

import com.sxj.supervisor.entity.message.TenderMessageEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.model.message.TenderMessageModel;
import com.sxj.supervisor.model.message.TenderMessageQuery;
import com.sxj.util.exception.ServiceException;

public interface ITenderMessageService
{
    public void addMessage(List<TenderMessageEntity> message)
            throws ServiceException;
            
    public List<TenderMessageModel> queryMessageList(TenderMessageQuery query)
            throws ServiceException;
            
    public void modifyState(String id, MessageStateEnum state)
            throws ServiceException;
            
    public Long queryUnread(String memberNo);
    
    public void fetchUnreads(String memberNo);
    
    public Long countUnreads(String memberNo);
    
}
