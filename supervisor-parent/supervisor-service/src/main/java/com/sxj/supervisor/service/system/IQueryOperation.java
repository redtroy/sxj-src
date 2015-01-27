package com.sxj.supervisor.service.system;

import java.util.List;

import com.sxj.supervisor.entity.system.OperatorLogEntity;
import com.sxj.supervisor.model.system.LogQuery;
import com.sxj.util.exception.ServiceException;

public interface IQueryOperation
{
    /**
     * 查询操作记录
     * 
     * @param accountNo
     * @return
     */
    public List<OperatorLogEntity> query(LogQuery query)
            throws ServiceException;
    
    public void addOperatorLog(OperatorLogEntity logs) throws ServiceException;
    
}
