package com.sxj.supervisor.service.financ;

import java.util.List;

import com.sxj.supervisor.entity.financ.FinancEntity;
import com.sxj.supervisor.model.financ.FinanceQuery;
import com.sxj.util.exception.ServiceException;

public interface IFinancService
{
    
    public void addFinance(FinancEntity financ) throws ServiceException;

    public List<FinancEntity> financeList(FinanceQuery query);
    
}
