package com.sxj.supervisor.service.impl.financ;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.financ.IFinancDao;
import com.sxj.supervisor.entity.financ.FinancEntity;
import com.sxj.supervisor.model.financ.FinanceQuery;
import com.sxj.supervisor.service.financ.IFinancService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

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
            financ.setCreateDate(new Date());
            financDao.addFinanc(financ);
        }
        catch (Exception e)
        {
            SxjLogger.error("新增融资单错误", e, this.getClass());
            throw new ServiceException("新增融资单错误", e);
        }
        
    }

    @Override
    public List<FinancEntity> financeList(FinanceQuery query)
    {
        try
        {
            QueryCondition<FinancEntity> condition = new QueryCondition<FinancEntity>();
            condition.addCondition("contactsName", query.getContactsName());// 备案号
            condition.addCondition("contactsTel", query.getContactsTel());// 合同号
            condition.addCondition("projectName", query.getProjectName());// 合同号
            condition.setPage(query);
            List<FinancEntity> list = financDao.financeList(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("融资申请信息查询出错！", e, this.getClass());
            throw new ServiceException("融资申请信息查询出错！", e);
        }
    }
    
}
