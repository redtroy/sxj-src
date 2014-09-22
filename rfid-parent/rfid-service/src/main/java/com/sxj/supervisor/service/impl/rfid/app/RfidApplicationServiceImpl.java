package com.sxj.supervisor.service.impl.rfid.app;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.dao.rfid.apply.IRfidApplicationDao;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
public class RfidApplicationServiceImpl implements IRfidApplicationService {
	@Autowired
	IRfidApplicationDao appDao;

	@Override
	public List<RfidApplicationEntity> query(RfidApplicationQuery query)
			throws ServiceException {
		try {
			QueryCondition<RfidApplicationEntity> condition = new QueryCondition<RfidApplicationEntity>();
			if (query != null) {
				condition.addCondition("applyNo", query.getApplyNo());// RFID申请单号
				condition.addCondition("memberNo", query.getMemberNo());// 申请人名称
				condition.addCondition("memberName", query.getMemberName());// 申请人名称
				condition.addCondition("contractNo", query.getContractNo());// 招标合同号
				condition.addCondition("rfidType", query.getRfidType());// RFID类型
				condition.addCondition("receiptState", query.getReceiptState());// 收货状态
				condition.addCondition("payState", query.getPayState());// 收款状态
				condition.addCondition("starApplyDate",
						query.getStarApplyDate());// RFID类型
				condition.addCondition("endApplyDate", query.getEndApplyDate());// RFID类型
				condition.addCondition("delstate", query.getDelstate());
				condition.setPage(query);
			}
			List<RfidApplicationEntity> list = appDao.queryList(condition);
			query.setPage(condition);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 申请单更新
	 */
	@Override
	public void updateApp(RfidApplicationEntity app) throws ServiceException {
		try {
			appDao.updateRfidApplication(app);
		} catch (Exception e) {
			SxjLogger.error("申请单更新错误", e, this.getClass());
			throw new ServiceException("申请单更新错误");
		}

	}

	/**
	 * 根据ID逻辑删除
	 */
	@Override
	public void delApp(String id) throws ServiceException {
		try {
			RfidApplicationEntity app = new RfidApplicationEntity();
			app.setDelstate(true);
			app.setId(id);
			appDao.updateRfidApplication(app);
		} catch (Exception e) {
			SxjLogger.error("逻辑删除申请单错误", e, this.getClass());
			throw new ServiceException("逻辑删除申请单错误");
		}

	}

	/**
	 * 新增申请单
	 */
	@Override
	public void addApp(RfidApplicationEntity app) throws ServiceException {
		try {
			Date date = DateTimeUtils.parse(System.currentTimeMillis());
			app.setPayState(PayStateEnum.non_payment);
			app.setApplyDate(date);
			app.setReceiptState(ReceiptStateEnum.shipments);
			appDao.addRfidApplication(app);
			Long messageCount = null;
			Object cache = HierarchicalCacheManager.get(2, "comet_rfid_apply",
					"rfid_apply_message");
			if (cache instanceof Long) {
				messageCount = (Long) cache;
			} else {
				messageCount = 0l;
			}
			messageCount = messageCount + 1;
			HierarchicalCacheManager.set(2, "comet_rfid_apply",
					"rfid_apply_message", messageCount);
		} catch (Exception e) {
			SxjLogger.error("新增申请单错误", e, this.getClass());
			throw new ServiceException("申请单错误");
		}

	}

	/**
	 * 根据申请单号获取
	 */
	@Override
	public RfidApplicationEntity getApplication(String no)
			throws ServiceException {
		try {
			RfidApplicationQuery query = new RfidApplicationQuery();
			query.setApplyNo(no);
			List<RfidApplicationEntity> res = query(query);
			if (res != null && res.size() > 0) {
				RfidApplicationEntity app = res.get(0);
				return app;
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException("获取申请单信息错误", e);
		}
	}

}
