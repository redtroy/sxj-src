package com.sxj.supervisor.service.rfid;

import java.sql.SQLException;

import com.sxj.supervisor.entity.rfid.RfidKeyEntity;
import com.sxj.util.exception.ServiceException;

public interface IRfidKeyService
{
    
    public Long getKey() throws ServiceException;
    
    public Long getKey(Integer step) throws ServiceException;
    
    public RfidKeyEntity refetchKey(Integer step) throws SQLException;
    
}
