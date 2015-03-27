package com.sxj.supervisor.service.impl.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.message.ISystemInfoDao;
import com.sxj.supervisor.dao.message.ISystemMessageDao;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.message.SystemMessageEntity;
import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.model.message.SystemMessageModel;
import com.sxj.supervisor.model.message.SystemMessageQuery;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.supervisor.service.message.ISystemMessageService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class SystemMessageServiceImpl implements ISystemMessageService
{
    @Autowired
    private ISystemMessageDao messageDao;
    
    @Autowired
    private ISystemInfoDao infoDao;
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IMessageConfigService configService;
    
    @Override
    @Transactional
    public void addSystemInfo(String[] memberNos, String[] memberNames,
            List<MemberTypeEnum> types, SystemMessageInfoEntity info)
            throws ServiceException
    {
        try
        {
            if (memberNos != null && memberNos.length > 0)
            {
                String memberList = "";
                for (int i = 1; i <= memberNos.length; i++)
                {
                    if (i == memberNos.length)
                    {
                        memberList = memberList + memberNos[i - 1] + ":"
                                + memberNames[i - 1];
                    }
                    else
                    {
                        memberList = memberList + memberNos[i - 1] + ":"
                                + memberNames[i - 1] + ",";
                    }
                }
                info.setMemberList(memberList);
            }
            if (types != null && types.size() > 0)
            {
                String typeList = "";
                int i = 1;
                for (Iterator<MemberTypeEnum> iterator = types.iterator(); iterator.hasNext();)
                {
                    MemberTypeEnum memberTypeEnum = iterator.next();
                    if (i == types.size())
                    {
                        typeList = typeList + memberTypeEnum.toString();
                    }
                    else
                    {
                        typeList = typeList + memberTypeEnum.toString() + ",";
                    }
                    i++;
                }
                info.setMemberTypeList(typeList);
            }
            infoDao.addMessageInfo(info);
            if (memberNos != null && memberNos.length > 0)
            {
                for (int i = 0; i < memberNos.length; i++)
                {
                    CometServiceImpl.add(MessageChannel.MEMBER_SYSTEM_MESSAGE_INFO
                            + memberNos[i],
                            info.getId());
                    CometServiceImpl.takeCount(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                            + memberNos[i]);
                    
                    configService.sendMessage(memberNos[i],
                            MessageTypeEnum.SYSTEM,
                            info.getTitle());
                }
            }
            if (types != null && types.size() > 0)
            {
                for (Iterator<MemberTypeEnum> iterator = types.iterator(); iterator.hasNext();)
                {
                    MemberTypeEnum memberTypeEnum = iterator.next();
                    MemberQuery query = new MemberQuery();
                    query.setMemberType(memberTypeEnum.getId());
                    List<MemberEntity> memberList = memberService.queryMembers(query);
                    if (memberList != null && memberList.size() > 0)
                    {
                        for (Iterator<MemberEntity> iterator2 = memberList.iterator(); iterator2.hasNext();)
                        {
                            MemberEntity memberEntity = (MemberEntity) iterator2.next();
                            CometServiceImpl.add(MessageChannel.MEMBER_SYSTEM_MESSAGE_INFO
                                    + memberEntity.getMemberNo(),
                                    info.getId());
                            CometServiceImpl.takeCount(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                                    + memberEntity.getMemberNo());
                            
                            configService.sendMessage(memberEntity.getMemberNo(),
                                    MessageTypeEnum.SYSTEM,
                                    "您有一条未读的系统信息");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("发送系统消息错误", e, this.getClass());
            throw new ServiceException("发送系统消息错误", e);
        }
        
    }
    
    @Override
    public List<SystemMessageModel> querySystemMessageList(
            SystemMessageQuery query) throws ServiceException
    {
        try
        {
            
            QueryCondition<SystemMessageModel> condition = new QueryCondition<>();
            condition.addCondition("memberNo", query.getMemberNo());
            condition.setPage(query);
            List<SystemMessageModel> list = messageDao.queryMessageModel(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("获取系统消息错误", e, this.getClass());
            throw new ServiceException("获取系统消息错误", e);
        }
    }
    
    @Override
    @Transactional
    public List<SystemMessageInfoEntity> querySystemInfoList(
            SystemMessageQuery query) throws ServiceException
    {
        try
        {
            QueryCondition<SystemMessageInfoEntity> query2 = new QueryCondition<>();
            query2.addCondition("startDate", query.getStartDate());
            query2.addCondition("endDate", query.getEndDate());
            query2.setPage(query);
            List<SystemMessageInfoEntity> list = infoDao.queryMessageInfo(query2);
            query.setPage(query2);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询系统消息错误", e, this.getClass());
            throw new ServiceException("查询系统消息错误", e);
        }
    }
    
    @Override
    public SystemMessageInfoEntity getSystemInfo(String id)
            throws ServiceException
    {
        try
        {
            return infoDao.getMessageInfo(id);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询系统消息错误", e, this.getClass());
            throw new ServiceException("查询系统消息错误", e);
        }
    }
    
    @Override
    public List<SystemMessageEntity> querySystemMessage(String infoId)
            throws ServiceException
    {
        try
        {
            return messageDao.queryMessage(infoId);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询系统消息错误", e, this.getClass());
            throw new ServiceException("查询系统消息错误", e);
        }
    }
    
    @Override
    @Transactional
    public void addSystemMessage(String memberNo, String memberName,
            MemberTypeEnum type, List<String> infoIdList)
            throws ServiceException
    {
        try
        {
            if (infoIdList != null && infoIdList.size() > 0)
            {
                List<SystemMessageEntity> messageList = new ArrayList<SystemMessageEntity>();
                for (Iterator<String> iterator = infoIdList.iterator(); iterator.hasNext();)
                {
                    String infoId = iterator.next();
                    SystemMessageEntity message = new SystemMessageEntity();
                    message.setInfoId(infoId);
                    message.setMemberNo(memberNo);
                    message.setState(MessageStateEnum.UNREAD);
                    message.setMemberName(memberName);
                    message.setMemberType(type);
                    messageList.add(message);
                }
                messageDao.addMessage(messageList);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("新增系统消息关联错误", e, this.getClass());
            throw new ServiceException("新增系统消息关联错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void modifyState(String id, MessageStateEnum state)
            throws ServiceException
    {
        try
        {
            SystemMessageEntity message = messageDao.getSystemMessage(id);
            if (message != null)
            {
                message.setState(state);
                messageDao.updateMessage(message);
                CometServiceImpl.subCount(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                        + message.getMemberNo());
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("更新系统消息状态错误", e, this.getClass());
            throw new ServiceException("更新系统消息状态错误", e);
        }
        
    }
    
}
