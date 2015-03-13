package com.sxj.supervisor.service.meaagae;

import java.util.List;

import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.model.message.SystemMessageModel;
import com.sxj.util.exception.ServiceException;

public interface ISystemMessageService
{
    
    public void addSystemInfo(List<String> memberNos,
            List<MemberTypeEnum> types, SystemMessageInfoEntity info)
            throws ServiceException;
    
    public List<SystemMessageModel> querySystemMessageList()
            throws ServiceException;
    
}
