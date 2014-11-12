package com.sxj.supervisor.service.impl.rfid;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.IRfidKeyDao;
import com.sxj.supervisor.entity.rfid.RfidKeyEntity;
import com.sxj.supervisor.service.rfid.IRfidKeyService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class RfidKeyServiceImpl implements IRfidKeyService
{
    
    @Autowired
    private IRfidKeyDao dao;
    
    @Override
    @Transactional(timeout = 30)
    public Long getKey() throws ServiceException
    {
        try
        {
            RfidKeyEntity entity = new RfidKeyEntity();
            entity.setName("rfidNo");
            dao.getKey(entity);
            return entity.getId();
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public Long getKey(Integer step) throws ServiceException
    {
        if (step == null || step <= 0)
            throw new ServiceException("RFID KEY 自增步长不能小于等于0");
        try
        {
            
            RfidKeyEntity oldKey = dao.getKeyEntity("rfidNo");
            if (oldKey == null)
            {
                oldKey = new RfidKeyEntity(0L, "rfidNo");
                dao.insertKeyEntity(oldKey);
            }
            oldKey.setStep(step);
            while (dao.updateKeyEntity(oldKey) < 1)
            {
                oldKey = dao.getKeyEntity("rfidNo");
                oldKey.setStep(step);
            }
            return oldKey.getId() + step;
        }
        catch (SQLException sqle)
        {
            SxjLogger.error(sqle.getMessage(), sqle, this.getClass());
            throw new ServiceException(sqle.getMessage(), sqle);
        }
    }
    
}
