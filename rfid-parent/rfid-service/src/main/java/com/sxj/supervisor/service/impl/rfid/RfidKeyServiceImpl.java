package com.sxj.supervisor.service.impl.rfid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    
    @Autowired
    private ApplicationContext context;
    
    private IRfidKeyService self;
    
    @PostConstruct
    public void post()
    {
        self = context.getBean(IRfidKeyService.class);
    }
    
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
    @Transactional
    public Long getKey(Integer step) throws ServiceException
    {
        if (step == null || step <= 0)
            throw new ServiceException("RFID KEY 自增步长不能小于等于0");
        try
        {
            
            RfidKeyEntity oldKey = self.refetchKey(step);
            if (oldKey == null)
            {
                oldKey = new RfidKeyEntity(0L, "rfidNo");
                dao.insertKeyEntity(oldKey);
            }
            oldKey.setStep(step);
            while (dao.updateKeyEntity(oldKey) < 1)
            {
                oldKey = self.refetchKey(step);
            }
            return oldKey.getId() + step;
        }
        catch (SQLException sqle)
        {
            SxjLogger.error(sqle.getMessage(), sqle, this.getClass());
            throw new ServiceException(sqle.getMessage(), sqle);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RfidKeyEntity refetchKey(Integer step) throws SQLException
    {
        RfidKeyEntity key = dao.getKeyEntity("rfidNo");
        if (key == null)
            return null;
        key.setStep(step);
        return key;
    }
    
    public static void main(String... args)
    {
        List<String> test = new ArrayList<String>();
        test.add("a");
        System.out.println(test.subList(0, 1));
    }
    
}
