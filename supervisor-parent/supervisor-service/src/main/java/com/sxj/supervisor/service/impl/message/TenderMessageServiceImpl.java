package com.sxj.supervisor.service.impl.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.message.ITenderMessageDao;
import com.sxj.supervisor.entity.message.TenderMessageEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.message.TenderMessageModel;
import com.sxj.supervisor.model.message.TenderMessageQuery;
import com.sxj.supervisor.service.message.ITenderMessageService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class TenderMessageServiceImpl implements ITenderMessageService
{
    @Autowired
    private ITenderMessageDao dao;
    
    @Override
    @Transactional
    public void addMessage(List<TenderMessageEntity> message)
            throws ServiceException
    {
        try
        {
            if (message != null && message.size() > 0)
            {
                dao.addMessage(message);
            }
            
        }
        catch (Exception e)
        {
            SxjLogger.error("添加招标信息错误", e, this.getClass());
            throw new ServiceException("添加招标信息错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public List<TenderMessageModel> queryMessageList(TenderMessageQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<TenderMessageModel> condition = new QueryCondition<>();
            condition.setPage(query);
            condition.addCondition("memberNo", query.getMemberNo());
            condition.addCondition("infoId", query.getInfoId());
            List<TenderMessageModel> list = dao.queryMessageList(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询招标信息错误", e, this.getClass());
            throw new ServiceException("查询招标信息错误", e);
        }
    }
    
    @Override
    @Transactional
    public void modifyState(String id, MessageStateEnum state)
            throws ServiceException
    {
        try
        {
            TenderMessageEntity message = dao.getMessage(id);
            if (message != null)
            {
                message.setState(state);
                Integer count = dao.updateMessage(message);
                if (count <= 0)
                {
                    return;
                }
                String key = MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                        + message.getMemberNo();
                List<String> userKeys = CometServiceImpl.get(MessageChannel.MEMBER_READTENDER_MESSAGE_KEYS);
                Long totalCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT);
                if (userKeys != null && userKeys.size() > 0)
                {
                    if (userKeys.contains(key))
                    {
                        CometServiceImpl.subCount(key);
                    }
                    else
                    {
                        CometServiceImpl.setCount(key, totalCount);
                        CometServiceImpl.subCount(key);
                    }
                }
                else
                {
                    CometServiceImpl.setCount(key, totalCount);
                    CometServiceImpl.subCount(key);
                }
                
                CometServiceImpl.add(MessageChannel.MEMBER_READTENDER_MESSAGE_KEYS,
                        MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                                + message.getMemberNo());
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("更新招标消息状态错误", e, this.getClass());
            throw new ServiceException("更新招标消息状态错误", e);
        }
        
    }
}
