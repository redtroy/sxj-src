package com.sxj.supervisor.dao.rfid.app;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRfidApplicationDao {
	/**
	 * 查询RFID申请单
	 */
	public List<RfidApplicationEntity> queryList(
			QueryCondition<RfidApplicationEntity> query) throws SQLException;

	/**
	 * 更新
	 */
	@Update
	public void updateRfidApplication(RfidApplicationEntity app)
			throws SQLException;

}
