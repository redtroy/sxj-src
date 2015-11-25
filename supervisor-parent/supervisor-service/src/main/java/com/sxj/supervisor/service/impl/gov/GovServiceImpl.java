package com.sxj.supervisor.service.impl.gov;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.gov.IGovDao;
import com.sxj.supervisor.entity.gov.GovEntity;
import com.sxj.supervisor.service.gov.IGovService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
public class GovServiceImpl implements IGovService
{
    @Autowired
    private IGovDao govDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<GovEntity> queryGovList(GovEntity query)
            throws ServiceException
    {
        try
        {
            if (query == null)
            {
                return null;
            }
            QueryCondition<GovEntity> condition = new QueryCondition<GovEntity>();
            condition.addCondition("title", query.getTitle());// 开发商名称
            condition.setPage(query);
            List<GovEntity> recordList = govDao.query(condition);
            query.setPage(condition);
            return recordList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ServiceException("查询政务信息错误", e);
        }
        
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGov(GovEntity entity) throws ServiceException
    {
        try
        {
            govDao.addGov(entity);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增政务信息错误", e);
        }
        
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateGov(GovEntity entity) throws ServiceException
    {
        try
        {
            govDao.updateGov(entity);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("修改政务信息错误", e);
        }
        
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delGov(String id) throws ServiceException
    {
        try
        {
            govDao.delGov(id);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("修改政务信息错误", e);
        }
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public GovEntity getGov(String id) throws ServiceException
    {
        GovEntity record = govDao.getGov(id);
        return record;
    }
    
}
