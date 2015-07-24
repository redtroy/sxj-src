package com.sxj.supervisor.service.financ;

import com.sxj.supervisor.entity.financ.FinancEntity;
import com.sxj.util.exception.ServiceException;

public interface IFinancService
{
    
    public void addFinance(FinancEntity financ) throws ServiceException;
    
}
