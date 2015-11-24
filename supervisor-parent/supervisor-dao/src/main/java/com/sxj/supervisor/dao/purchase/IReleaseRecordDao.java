package com.sxj.supervisor.dao.purchase;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.purchase.ReleaseRecordEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IReleaseRecordDao {

	@Insert
	public void insertReleaseRecord(ReleaseRecordEntity releaseRecord)
			throws SQLException;

	public List<ReleaseRecordEntity> queryReleaseRecordList(
			QueryCondition<ReleaseRecordEntity> query) throws SQLException;
}
