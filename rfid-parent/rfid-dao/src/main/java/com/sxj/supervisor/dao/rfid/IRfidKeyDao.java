package com.sxj.supervisor.dao.rfid;

import java.sql.SQLException;

import com.sxj.supervisor.entity.rfid.RfidKeyEntity;

public interface IRfidKeyDao {

	public void getKey(RfidKeyEntity entity) throws SQLException;
}
