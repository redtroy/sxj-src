package com.sxj.supervisor.dao.system;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.supervisor.entity.system.OperatorLogEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IOperatorLogDao {

	@BatchInsert
	public void addLogs(List<OperatorLogEntity> log) throws SQLException;

	public List<OperatorLogEntity> queryLogs(
			QueryCondition<OperatorLogEntity> query) throws SQLException;

}
