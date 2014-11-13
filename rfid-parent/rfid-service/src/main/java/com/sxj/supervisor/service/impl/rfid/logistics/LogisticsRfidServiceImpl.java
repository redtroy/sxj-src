package com.sxj.supervisor.service.impl.rfid.logistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.rfid.logistics.ILogisticsRfidDao;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class LogisticsRfidServiceImpl implements ILogisticsRfidService {

	@Autowired
	private ILogisticsRfidDao logisticsRfidDao;

	@Override
	@Transactional(readOnly = true)
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
			condition.addCondition("startImportDate",
					query.getStartImportDate());
			condition.addCondition("endImportDate", query.getEndImportDate());
			condition.addCondition("rfidState", query.getRfidState());
			condition.addCondition("progressState", query.getProgressState());
			condition.setPage(query);
			List<LogisticsRfidEntity> rfidList = logisticsRfidDao
					.queryLogisticsRfidList(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询物流RFID错误", e);
		}
	}

	// 甲方
	@Override
	@Transactional(readOnly = true)
	public List<LogisticsRfidEntity> queryLogistics_A(LogisticsRfidQuery query)
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
			condition.addCondition("startImportDate",
					query.getStartImportDate());
			condition.addCondition("endImportDate", query.getEndImportDate());
			condition.addCondition("rfidState", query.getRfidState());
			condition.addCondition("progressState", query.getProgressState());
			condition.setPage(query);
			List<LogisticsRfidEntity> rfidList = logisticsRfidDao
					.queryLogisticsRfidList_A(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询物流RFID错误", e);
		}
	}

	// 乙方
	@Override
	@Transactional(readOnly = true)
	public List<LogisticsRfidEntity> queryLogistics_B(LogisticsRfidQuery query)
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
			condition.addCondition("startImportDate",
					query.getStartImportDate());
			condition.addCondition("endImportDate", query.getEndImportDate());
			condition.addCondition("rfidState", query.getRfidState());
			condition.addCondition("progressState", query.getProgressState());
			condition.setPage(query);
			List<LogisticsRfidEntity> rfidList = logisticsRfidDao
					.queryLogisticsRfidList_B(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询物流RFID错误", e);
		}
	}

	@Override
	@Transactional
	public void updateLogistics(LogisticsRfidEntity logistics)
			throws ServiceException {
		try {
			logisticsRfidDao.updateLogisticsRfid(logistics);
		} catch (Exception e) {
			throw new ServiceException("更新物流RFID错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<LogModel> getRfidStateLog(String id) throws ServiceException {
		try {
			List<LogModel> logList = new ArrayList<LogModel>();
			LogisticsRfidEntity log = logisticsRfidDao.getLogisticsRfid(id);
			if (log.getLog() != null) {
				try {
					logList = JsonMapper
							.nonEmptyMapper()
							.getMapper()
							.readValue(log.getLog(),
									new TypeReference<List<LogModel>>() {
									});
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return logList;
		} catch (Exception e) {
			throw new ServiceException("获取stateLog错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public LogisticsRfidEntity getLogistics(String id) throws ServiceException {
		try {
			LogisticsRfidEntity entity = logisticsRfidDao.getLogisticsRfid(id);
			return entity;
		} catch (Exception e) {
			throw new ServiceException("获取物流RFID错误", e);
		}
	}

	@Override
	@Transactional
	public Integer batchAddLogistics(LogisticsRfidEntity[] rfids)
			throws ServiceException {
		try {
			if (rfids != null) {
				return logisticsRfidDao.batchAddLogisticsRfid(rfids);
			} else {
				return 0;
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("批量新增物流RFID错误", e);
		}

	}

	@Override
	@Transactional
	public void batchUpdateLogistics(LogisticsRfidEntity[] rfids)
			throws ServiceException {
		try {
			if (rfids != null) {
				logisticsRfidDao.batchUpdateLogisticsRfid(rfids);
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("批量更新物流RFID错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public LogisticsRfidEntity getLogisticsByNo(String rfidNo)
			throws ServiceException {
		try {
			LogisticsRfidQuery query = new LogisticsRfidQuery();
			query.setRfidNo(rfidNo);
			List<LogisticsRfidEntity> list = queryLogistics(query);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("更急rfid获取信息错误", e);
		}
	}
}
