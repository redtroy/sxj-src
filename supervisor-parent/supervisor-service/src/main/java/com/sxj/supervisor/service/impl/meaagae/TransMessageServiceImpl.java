package com.sxj.supervisor.service.impl.meaagae;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.message.ITransMessageDao;
import com.sxj.supervisor.entity.message.TransMessageEntity;
import com.sxj.supervisor.model.message.TransMessageQuery;
import com.sxj.supervisor.service.meaagae.ITransMessageService;
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
        // TODO Auto-generated method stub
        
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
}
