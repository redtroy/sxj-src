package com.sxj.supervisor.service.impl.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.message.ITransMessageDao;
import com.sxj.supervisor.entity.message.TransMessageEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.message.TransMessageQuery;
import com.sxj.supervisor.service.message.ITransMessageService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class TransMessageServiceImpl implements ITransMessageService
{
    
    @Autowired
    private ITransMessageDao dao;
    
    @Override
    public void addMessage(TransMessageEntity message) throws ServiceException
    {
        try
        {
            if (message != null)
            {
                dao.addMessage(message);
                CometServiceImpl.takeCount(MessageChannel.MEMBER_TRANS_MESSAGE_COUNT
                        + message.getMemberNo());
            }
            
        }
        catch (Exception e)
        {
            SxjLogger.error("添加交易消息错误", e, this.getClass());
            throw new ServiceException("添加交易消息错误", e);
        }
        
    }
    
    @Override
    public TransMessageEntity getMessage(String id) throws ServiceException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<TransMessageEntity> queryMessageList(TransMessageQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<TransMessageEntity> condition = new QueryCondition<>();
            if (query != null)
            {
                condition.addCondition("memberNo", query.getMemberNo());
            }
            List<TransMessageEntity> list = dao.queryMessageList(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询交易信息列表错误", e);
        }
    }
    
    @Override
    public void modifyState(String id, MessageStateEnum state)
            throws ServiceException
    {
        try
        {
            TransMessageEntity message = dao.getMessage(id);
            if (message != null)
            {
                message.setState(state);
                dao.updateMessage(message);
                CometServiceImpl.subCount(MessageChannel.MEMBER_TRANS_MESSAGE_COUNT
                        + message.getMemberNo());
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("更新交易消息状态错误", e, this.getClass());
            throw new ServiceException("更新交易消息状态错误", e);
        }
        
    }
}
