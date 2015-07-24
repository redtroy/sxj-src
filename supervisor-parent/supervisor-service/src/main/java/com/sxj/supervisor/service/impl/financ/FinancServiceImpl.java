package com.sxj.supervisor.service.impl.financ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.financ.IFinancDao;
import com.sxj.supervisor.entity.financ.FinancEntity;
import com.sxj.supervisor.service.financ.IFinancService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class FinancServiceImpl implements IFinancService
{
    
    @Autowired
    private IFinancDao financDao;
    
    @Override
    @Transactional
    public void addFinance(FinancEntity financ) throws ServiceException
    {
        try
        {
            financDao.addFinanc(financ);
        }
        catch (Exception e)
        {
            SxjLogger.error("新增融资单错误", e, this.getClass());
            throw new ServiceException("新增融资单错误", e);
        }
        
    }
    
}
