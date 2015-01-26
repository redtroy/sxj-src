package com.sxj.supervisor.service.rfid.sale;

import java.util.List;

import com.sxj.supervisor.entity.rfid.sale.RfidPriceEntity;
import com.sxj.util.exception.ServiceException;

public interface IRfidPriceService
{
    
    public void addPrice(RfidPriceEntity entity) throws ServiceException;
    
    public void modifyPrice(RfidPriceEntity entity) throws ServiceException;
    
    public List<RfidPriceEntity> queryPrice() throws ServiceException;
}
