package com.sxj.supervisor.service.impl.rfid.logistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.logistics.ILogisticsRfidDao;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
@Service
@Transactional
public class LogisticsRfidServiceImpl implements ILogisticsRfidService {
	
	@Autowired
	private ILogisticsRfidDao logisticsRfidDao;
	
	@Override
	public List<LogisticsRfidEntity> queryLogistics(LogisticsRfidQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<LogisticsRfidEntity> condition = new QueryCondition<LogisticsRfidEntity>();
			condition.addCondition("rfidNo", query.getRfidNo());
			condition.addCondition("contractNo", query.getContractNo());
			condition.addCondition("purchaseNo", query.getPurchaseNo());
			condition.addCondition("type", query.getType());
			condition.addCondition("memberNo", query.getMemberNo());
			condition.addCondition("startImportDate", query.getStartImportDate());
			condition.addCondition("endImportDate", query.getEndImportDate());
			condition.addCondition("rfidState", query.getRfidState());
			condition.addCondition("progressState", query.getProgressState());
			condition.setPage(query);
			List<LogisticsRfidEntity> rfidList=logisticsRfidDao.queryLogisticsRfidList(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询物流RFID错误", e);
		}
	}

	@Override
	public void updateLogistics(LogisticsRfidEntity logistics)
			throws ServiceException {
		try {
			logisticsRfidDao.updateLogisticsRfid(logistics);
		} catch (Exception e) {
			throw new ServiceException("更新物流RFID错误", e);
		}

	}

}
