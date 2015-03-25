package com.sxj.supervisor.service.impl.message;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.message.IMessageConfigDao;
import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class MessageConfigServiceImpl implements IMessageConfigService
{
    
    @Autowired
    private IMessageConfigDao dao;
    
    @Autowired
    private IMemberService memberService;
    
    @Override
    @Transactional
    public void addConfig(String memberNo, List<MessageConfigEntity> config)
            throws ServiceException
    {
        try
        {
            //dao.delConfig(memberNo);
            if (config != null && config.size() > 0)
            {
                dao.addConfigBatch(config);
            }
            
        }
        catch (Exception e)
        {
            SxjLogger.error("设置消息配置错误", e, this.getClass());
            throw new ServiceException("设置消息配置错误", e);
            
        }
        
    }
    
    @Override
    @Transactional
    public void updateConfig(List<MessageConfigEntity> config)
            throws ServiceException
    {
        try
        {
            for (Iterator<MessageConfigEntity> iterator = config.iterator(); iterator.hasNext();)
            {
                MessageConfigEntity messageConfigEntity = (MessageConfigEntity) iterator.next();
                if (messageConfigEntity.getId() == null)
                {
                    dao.addConfig(messageConfigEntity);
                }
                else
                {
                    MessageConfigEntity oldConfig = dao.getConfig(messageConfigEntity.getId());
                    if (oldConfig == null)
                    {
                        dao.addConfig(messageConfigEntity);
                    }
                    else
                    {
                        dao.updateConfig(messageConfigEntity);
                    }
                }
                
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("设置消息配置错误", e, this.getClass());
            throw new ServiceException("设置消息配置错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public List<MessageConfigEntity> queryConfigList(String memberNo)
            throws ServiceException
    {
        try
        {
            List<MessageConfigEntity> list = dao.queryConfigList(memberNo);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("获取消息配置错误", e, this.getClass());
            throw new ServiceException("获取消息配置错误", e);
        }
    }
    
    @Override
    public MessageConfigEntity getConfig(String memberNo,
            MessageTypeEnum messageType) throws ServiceException
    {
        try
        {
            List<MessageConfigEntity> list = queryConfigList(memberNo);
            MessageConfigEntity messageConfig = null;
            if (list != null && list.size() > 0)
            {
                for (MessageConfigEntity messageConfigEntity : list)
                {
                    if (messageConfigEntity == null)
                    {
                        continue;
                    }
                    if (messageConfigEntity.getMessageType()
                            .equals(messageType))
                    {
                        if (messageConfigEntity.getIsAccetp())
                        {
                            messageConfig = messageConfigEntity;
                        }
                    }
                }
            }
            return messageConfig;
        }
        catch (Exception e)
        {
            SxjLogger.error("获取消息配置错误", e, this.getClass());
            throw new ServiceException("获取消息配置错误", e);
        }
    }
    
    @Override
    public void sendMessage(String memberNo, MessageTypeEnum messageType,
            String message) throws ServiceException
    {
        try
        {
            MessageConfigEntity config = getConfig(memberNo, messageType);
            if (config != null)
            {
                memberService.createvalidata(config.getPhone(), message);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("发送消息短信错误", e, this.getClass());
            throw new ServiceException("发送消息短信错误", e);
        }
        
    }
}
