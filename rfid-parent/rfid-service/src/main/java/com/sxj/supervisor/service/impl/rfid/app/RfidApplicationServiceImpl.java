package com.sxj.supervisor.service.impl.rfid.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.supervisor.dao.rfid.apply.IRfidApplicationDao;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
public class RfidApplicationServiceImpl implements IRfidApplicationService {
	@Autowired
	IRfidApplicationDao appDao;

	@Override
	public List<RfidApplicationEntity> query(RfidApplicationQuery query)
			throws ServiceException {
		// TODO Auto-generated method stub
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

}
