package com.sxj.supervisor.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.member.MemberLogEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IMemberLogDao {

	@Insert
	public void addLog(MemberLogEntity entity) throws SQLException;

	public List<MemberLogEntity> queryLogs(QueryCondition<MemberLogEntity> query)
			throws SQLException;

}
