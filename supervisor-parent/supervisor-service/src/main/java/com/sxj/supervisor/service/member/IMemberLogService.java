package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.MemberLogEntity;
import com.sxj.supervisor.model.member.LogQuery;
import com.sxj.util.exception.ServiceException;

public interface IMemberLogService
{
    
    public void addLog(MemberLogEntity entity) throws ServiceException;
    
    public List<MemberLogEntity> queryLogs(LogQuery query)
            throws ServiceException;
    
}
