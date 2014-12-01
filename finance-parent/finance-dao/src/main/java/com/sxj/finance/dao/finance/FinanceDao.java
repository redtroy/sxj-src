package com.sxj.finance.dao.finance;

import java.sql.SQLException;
import java.util.List;

import com.sxj.finance.entity.FinanceEntity;
import com.sxj.util.persistent.QueryCondition;

public interface FinanceDao {

	/**
	 * 根据条件查询
	 * 
	 * @return
	 */
	public List<FinanceEntity> query(QueryCondition<FinanceEntity> query)
			throws SQLException;
}
