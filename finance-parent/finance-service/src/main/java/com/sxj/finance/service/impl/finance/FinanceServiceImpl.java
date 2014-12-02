package com.sxj.finance.service.impl.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.finance.dao.finance.FinanceDao;
import com.sxj.finance.entity.FinanceEntity;
import com.sxj.finance.enu.finance.PayStageEnum;
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
	@Transactional(readOnly = true)
	public List<FinanceEntity> query(FinanceModel query)
			throws ServiceException {
		try {
			QueryCondition<FinanceEntity> condition = new QueryCondition<FinanceEntity>();
			if (query != null) {
				condition.addCondition("payNo", query.getPayNo());// 供应商ID
				condition.addCondition("contractNo", query.getContractNo());// 供应商名称
				condition.addCondition("state", query.getState());
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

	@Override
	@Transactional
	public Boolean apply(FinanceEntity fe) throws ServiceException {
		try {
			FinanceEntity f = financeDao.getFinanceEntityById(fe.getId());
			if (f.getState().ordinal() == 0) {
				fe.setState(PayStageEnum.Stage2_0);
				financeDao.update(fe);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error(e.getMessage(), e, this.getClass());
			return false;
		}
		return true;
	}

	@Override
	public Boolean pay(FinanceEntity fe) throws ServiceException {
		try {
			FinanceEntity f = financeDao.getFinanceEntityById(fe.getId());
			if (f.getState().ordinal() == 1) {
				fe.setState(PayStageEnum.Stage2_1);
				financeDao.update(fe);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error(e.getMessage(), e, this.getClass());
			return false;
		}
		return true;
	}

	@Override
	public Boolean shelve(FinanceEntity fe) throws ServiceException {
		try {
			FinanceEntity f = financeDao.getFinanceEntityById(fe.getId());
			if (f.getState().ordinal() == 1) {
				fe.setState(PayStageEnum.Stage2_2);
				financeDao.update(fe);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error(e.getMessage(), e, this.getClass());
			return false;
		}
		return true;
	}
}
