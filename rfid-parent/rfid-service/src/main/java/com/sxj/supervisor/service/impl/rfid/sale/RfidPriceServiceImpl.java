package com.sxj.supervisor.service.impl.rfid.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.sale.IRfidPriceDao;
import com.sxj.supervisor.entity.rfid.sale.RfidPriceEntity;
import com.sxj.supervisor.service.rfid.sale.IRfidPriceService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class RfidPriceServiceImpl implements IRfidPriceService
{
    
    @Autowired
    private IRfidPriceDao priceDao;
    
    @Override
    public void addPrice(RfidPriceEntity entity) throws ServiceException
    {
        try
        {
            priceDao.insertPrice(entity);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增RFID价格错误", e);
        }
        
    }
    
    @Override
    public void modifyPrice(RfidPriceEntity entity) throws ServiceException
    {
        try
        {
            priceDao.updatePrice(entity);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("更新RFID价格错误", e);
        }
        
    }
    
    @Override
    public List<RfidPriceEntity> queryPrice() throws ServiceException
    {
        try
        {
            return priceDao.queryPrice(null);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查詢RFID价格错误", e);
        }
    }
    
}
