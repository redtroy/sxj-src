package com.sxj.supervisor.service.rfid.sale;

import java.util.List;

import com.sxj.supervisor.entity.rfid.sale.RfidSaleStatisticalEntity;
import com.sxj.supervisor.model.rfid.sale.RfidSaleQuery;
import com.sxj.util.exception.ServiceException;

public interface IRfidSaleStatisticalService
{
    
    public void add(RfidSaleStatisticalEntity entity) throws ServiceException;
    
    public List<RfidSaleStatisticalEntity> queryList(RfidSaleQuery query)
            throws ServiceException;
}
