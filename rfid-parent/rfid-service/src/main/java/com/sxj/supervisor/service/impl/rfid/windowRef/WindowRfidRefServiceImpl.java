package com.sxj.supervisor.service.impl.rfid.windowRef;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.windowRef.IWindowRfidRefDao;
import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.supervisor.model.rfid.windowRef.WindowRefQuery;
import com.sxj.supervisor.service.rfid.windowRef.IWindowRfidRefService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class WindowRfidRefServiceImpl implements IWindowRfidRefService {
	@Autowired
	private IWindowRfidRefDao windowRfidRefDao;

	@Override
	public List<WindowRefEntity> queryWindowRfidRef(WindowRefQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<WindowRefEntity> condition = new QueryCondition<WindowRefEntity>();
			condition.addCondition("rfidRefNo", query.getRfidRefNo());
			condition.addCondition("rfidRange", query.getRfidRange());
			condition.addCondition("memberNo", query.getMemberNo());
			condition.addCondition("type", query.getType());
			condition.addCondition("windowsNo", query.getWindowsNo());
			condition.addCondition("batchNo", query.getBatchNo());
			condition.addCondition("replenishRfid", query.getReplenishRfid());
			condition.addCondition("state", query.getState());
			condition.addCondition("startDate", query.getStartDate());
			condition.addCondition("endDate", query.getEndDate());
			condition.setPage(query);
			List<WindowRefEntity> rfidList = windowRfidRefDao
					.queryWindowRfidRefList(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询门窗RFID关联错误", e);
		}
	}

	@Override
	public void updateWindowRfidRef(WindowRefEntity win)
			throws ServiceException {
		try {
			windowRfidRefDao.updateWindowRfidRef(win);
		} catch (Exception e) {
			throw new ServiceException("更新门窗RFID关联错误", e);
		}
	}

	@Override
	public WindowRefEntity getWindowRfidRef(String id) throws ServiceException {
		try {
			WindowRefEntity window =windowRfidRefDao.getWindowRfidRef(id);
			return window;
		} catch (Exception e) {
			throw new ServiceException("获取门窗RFID关联错误", e);
		}
	}

}
