package com.sxj.supervisor.service.rfid.windowRef;

import java.util.List;

import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.supervisor.model.rfid.windowRef.WindowRefQuery;
import com.sxj.util.exception.ServiceException;

public interface IWindowRfidRefService {
	/**
	 * 根据条件高级查询
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<WindowRefEntity> queryWindowRfidRef(WindowRefQuery query)
			throws ServiceException;

	/**
	 * 更新
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	public void updateWindowRfidRef(WindowRefEntity win) throws ServiceException;


	public WindowRefEntity getWindowRfidRef(String id) throws ServiceException;

}
