package com.sxj.finance.service.finance;

import java.util.List;

import com.sxj.finance.entity.FinanceEntity;
import com.sxj.finance.model.finance.FinanceModel;
import com.sxj.util.exception.ServiceException;

public interface IFinanceService {

	public List<FinanceEntity> query(FinanceModel query)
			throws ServiceException;

}
