package com.sxj.supervisor.dao.rfid;

import java.sql.SQLException;

public interface IRfidKeyDao {

	public Long getKey(String name) throws SQLException;
}
