package com.sxj.finance.service.impl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.finance.dao.finance.FinanceDao;
import com.sxj.finance.entity.FinanceEntity;
import com.sxj.finance.model.finance.FinanceModel;
import com.sxj.finance.service.finance.IFinanceService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
public class FinanceServiceImpl implements IFinanceService {

	@Autowired
	private FinanceDao financeDao;

	@Override
	public List<FinanceEntity> query(FinanceModel query)
			throws ServiceException {
		try {
			QueryCondition<FinanceEntity> condition = new QueryCondition<FinanceEntity>();
			if (query != null) {
				condition.addCondition("payNo", query.getPayNo());// 供应商ID
				condition.addCondition("contractNo", query.getContractNo());// 供应商名称
				condition.setPage(query);
			}
			List<FinanceEntity> list = financeDao.query(condition);
			query.setPage(condition);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error(e.getMessage(), e, this.getClass());
		}
		return null;
	}
}
