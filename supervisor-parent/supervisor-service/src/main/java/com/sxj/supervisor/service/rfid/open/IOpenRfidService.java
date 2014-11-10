package com.sxj.supervisor.service.rfid.open;

import java.sql.SQLException;

import com.sxj.supervisor.model.open.BatchModel;
import com.sxj.supervisor.model.open.WinTypeModel;
import com.sxj.util.exception.ServiceException;

public interface IOpenRfidService {

	/**
	 * 根据rfid获取批次
	 * @param rfid
	 * @return
	 * @throws SQLException
	 */
	BatchModel getBatchByRfid(String rfid) throws ServiceException, SQLException;

	WinTypeModel getWinTypeByRfid(String rfid) throws ServiceException,
			SQLException;

	String getAddress(String contractNo) throws ServiceException, SQLException;

}
