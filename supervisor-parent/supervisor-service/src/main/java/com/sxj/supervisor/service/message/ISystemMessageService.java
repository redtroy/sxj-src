package com.sxj.supervisor.service.message;

import java.util.List;

import com.sxj.supervisor.entity.message.SystemMessageEntity;
import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.model.message.SystemMessageModel;
import com.sxj.supervisor.model.message.SystemMessageQuery;
import com.sxj.util.exception.ServiceException;

public interface ISystemMessageService
{
    
    public void addSystemInfo(String[] memberNos, String[] memberNames,
            List<MemberTypeEnum> types, SystemMessageInfoEntity info)
            throws ServiceException;
    
    public void addSystemMessage(String memberNo, String memberName,
            MemberTypeEnum type, List<String> infoIdList)
            throws ServiceException;
    
    public void modifyState(String id, MessageStateEnum state)
            throws ServiceException;
    
    public List<SystemMessageModel> querySystemMessageList(
            SystemMessageQuery query) throws ServiceException;
    
    public SystemMessageInfoEntity getSystemInfo(String id)
            throws ServiceException;
    
    public List<SystemMessageEntity> querySystemMessage(String infoId)
            throws ServiceException;
    
    public List<SystemMessageInfoEntity> querySystemInfoList(
            SystemMessageQuery query) throws ServiceException;
    
}
