package com.sxj.supervisor.service.impl.meaagae;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.model.message.SystemMessageModel;
import com.sxj.supervisor.service.meaagae.ISystemMessageService;
import com.sxj.util.exception.ServiceException;

@Service
public class SystemMessageServiceImpl implements ISystemMessageService
{
    
    @Override
    public void addSystemInfo(List<String> memberNos,
            List<MemberTypeEnum> types, SystemMessageInfoEntity info)
            throws ServiceException
    {
        try
        {
            
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        
    }
    
    @Override
    public List<SystemMessageModel> querySystemMessageList()
            throws ServiceException
    {
        try
        {
            
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return null;
    }
    
}
