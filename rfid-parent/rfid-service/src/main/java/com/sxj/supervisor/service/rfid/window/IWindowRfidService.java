package com.sxj.supervisor.service.rfid.window;

import java.util.List;

import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.util.exception.ServiceException;

public interface IWindowRfidService {
	/**
	 * 根据条件高级查询
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<WindowRfidEntity> queryWindowRfid(WindowRfidQuery query)
			throws ServiceException;
	/**
	 * 更新
	 * @param id
	 * @throws ServiceException
	 */
	public void updateWindowRfid(WindowRfidEntity win) throws ServiceException;
	
	public List<LogModel> getRfidStateLog(String id) throws ServiceException;
	
	public WindowRfidEntity getWindowRfid(String id)
			throws ServiceException;
}
