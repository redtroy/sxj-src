package com.sxj.supervisor.service.impl.rfid.ref;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.redis.service.comet.CometServiceImpl;
import com.sxj.supervisor.dao.rfid.ref.ILogisticsRefDao;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.model.comet.RfidChannel;
import com.sxj.supervisor.model.rfid.ref.LogisticsRefQuery;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.service.rfid.ref.ILogisticsRefService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class LogisticsRefServiceImpl implements ILogisticsRefService {

	@Autowired
	private ILogisticsRefDao refDao;

	@Autowired
	private ILogisticsRfidService rfidService;

	@Override
	@Transactional(readOnly = true)
	public List<LogisticsRefEntity> query(LogisticsRefQuery query)
			throws ServiceException {
		try {
			QueryCondition<LogisticsRefEntity> condition = new QueryCondition<LogisticsRefEntity>();
			if (query != null) {
				condition.addCondition("rfidRefNo", query.getRfidNo());
				condition.addCondition("rfidNo", query.getRfidNo());
				condition.addCondition("memberName", query.getMemberName());
				condition.addCondition("contractNo", query.getContractNo());
				condition.addCondition("replenishRfid",
						query.getReplenishRfid());
				condition.addCondition("rfidType", query.getRfidType());
				condition.addCondition("type", query.getType());
				condition.addCondition("state", query.getState());
				condition.addCondition("starApplyDate",
						query.getStarApplyDate());// RFID类型
				condition.addCondition("endApplyDate", query.getEndApplyDate());// RFID类型
				condition.addCondition("delstate", query.getDelstate());
				condition.setPage(query);
			}
			List<LogisticsRefEntity> list = refDao.queryList(condition);
			query.setPage(condition);
			return list;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("物流RFID关联申请管理查询错误");
		}
	}

	@Override
	@Transactional
	public void update(LogisticsRefEntity model) throws ServiceException {
		try {
			refDao.update(model);
			CometServiceImpl
					.subCount(RfidChannel.RFID_MANAGER_LOGISTICS_MESSGAGE_REF);
			RfidChannel.initTopic().publish(
					RfidChannel.RFID_MANAGER_LOGISTICS_MESSGAGE_REF);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("物流RFID关联申请管理更新错误");
		}

	}

	/**
	 * 删除
	 */
	@Override
	@Transactional
	public void del(String id) throws ServiceException {
		try {
			LogisticsRefEntity model = refDao.get(id);
			refDao.delete(id);
			// model.setId(id);
			// model.setDelstate(true);
			// refDao.update(model);
			// 修改状态
			LogisticsRfidEntity l = rfidService.getLogisticsByNo(model
					.getRfidNo());
			l.setRfidState(RfidStateEnum.unused);
			rfidService.updateLogistics(l);

		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("物流RFID关联申请管理删除错误");
		}

	}

	@Override
	@Transactional
	public void add(LogisticsRefEntity model) throws ServiceException {
		try {
			refDao.add(model);
			CometServiceImpl
					.takeCount(RfidChannel.RFID_MANAGER_LOGISTICS_MESSGAGE_REF);
			RfidChannel.initTopic().publish(
					RfidChannel.RFID_MANAGER_LOGISTICS_MESSGAGE_REF);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("物流RFID关联申请管理新增错误");
		}

	}

}
