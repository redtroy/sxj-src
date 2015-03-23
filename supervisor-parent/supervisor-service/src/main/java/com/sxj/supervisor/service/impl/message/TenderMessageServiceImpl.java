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
        // TODO Auto-generated method stub
        
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
            List<TenderMessageModel> list = dao.queryMessageList(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询超标信息错误", e, this.getClass());
            throw new ServiceException("查询超标信息错误", e);
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
                dao.updateMessage(message);
                CometServiceImpl.subCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
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
