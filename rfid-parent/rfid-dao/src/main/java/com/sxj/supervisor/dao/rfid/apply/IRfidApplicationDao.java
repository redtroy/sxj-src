package com.sxj.supervisor.dao.rfid.apply;

import java.util.List;

import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;

public interface IRfidApplicationDao {
	/**
	 * 查询RFID申请单
	 */
	public List<RfidApplicationEntity> queryList();

}
