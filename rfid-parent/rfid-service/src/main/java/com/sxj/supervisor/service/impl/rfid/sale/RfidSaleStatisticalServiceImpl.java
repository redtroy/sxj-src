package com.sxj.supervisor.service.impl.rfid.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.sale.IRfidSaleStatisticalDao;
import com.sxj.supervisor.entity.rfid.sale.RfidSaleStatisticalEntity;
import com.sxj.supervisor.model.rfid.sale.RfidSaleQuery;
import com.sxj.supervisor.service.rfid.sale.IRfidSaleStatisticalService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class RfidSaleStatisticalServiceImpl implements
        IRfidSaleStatisticalService
{
    
    @Autowired
    private IRfidSaleStatisticalDao dao;
    
    @Override
    public void add(RfidSaleStatisticalEntity entity) throws ServiceException
    {
        try
        {
            dao.insert(entity);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增销售记录错误", e);
        }
        
    }
    
    @Override
    public List<RfidSaleStatisticalEntity> queryList(RfidSaleQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<RfidSaleStatisticalEntity> condition = new QueryCondition<RfidSaleStatisticalEntity>();
            condition.addCondition("startDate", query.getStartDate());
            condition.addCondition("endDate", query.getEndDate());
            if (query.getRfidType() != null)
            {
                condition.addCondition("rfidType", query.getRfidType().getId());
            }
            List<RfidSaleStatisticalEntity> list = dao.queryList(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询销售记录", e);
        }
    }
    
}
