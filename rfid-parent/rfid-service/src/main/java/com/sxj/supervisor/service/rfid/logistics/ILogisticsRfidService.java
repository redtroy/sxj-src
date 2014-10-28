package com.sxj.supervisor.service.rfid.logistics;

import java.util.List;

import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.util.exception.ServiceException;

public interface ILogisticsRfidService {
	/**
	 * 根据条件高级查询
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<LogisticsRfidEntity> queryLogistics(LogisticsRfidQuery query)
			throws ServiceException;

	/**
	 * 更新
	 * 
	 * @param rfid
	 * @throws ServiceException
	 */
	public void updateLogistics(LogisticsRfidEntity rfid)
			throws ServiceException;

	/**
	 * 更新
	 * 
	 * @param rfids
	 * @throws ServiceException
	 */
	public void batchAddLogistics(LogisticsRfidEntity[] rfids)
			throws ServiceException;

	public List<LogModel> getRfidStateLog(String id) throws ServiceException;

	public LogisticsRfidEntity getLogistics(String id) throws ServiceException;
}
