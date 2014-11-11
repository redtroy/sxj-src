package com.sxj.supervisor.service.rfid.open;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	int shipped(String rfid) throws ServiceException, SQLException,
			JsonParseException, JsonMappingException, IOException;

	int accepting(String rfid) throws ServiceException, SQLException,
			JsonParseException, JsonMappingException, IOException;

}
