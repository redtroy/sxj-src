package com.sxj.supervisor.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.system.IOperatorLogDao;
import com.sxj.supervisor.entity.system.OperatorLogEntity;
import com.sxj.supervisor.model.system.LogQuery;
import com.sxj.supervisor.service.system.IqueryOperation;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class QueryOperation implements IqueryOperation {
	@Autowired
	private IOperatorLogDao opreatorlog;

	@Override
	@Transactional
	public List<OperatorLogEntity> query(LogQuery query)
			throws ServiceException {
		try {
			QueryCondition<OperatorLogEntity> condition = new QueryCondition<OperatorLogEntity>();
			condition.addCondition("accountNo", query.getAccountNo());
			condition.setPage(query);
			List<OperatorLogEntity> list = opreatorlog.queryLogs(condition);
			query.setPage(condition);
			return list;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), this.getClass());
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
