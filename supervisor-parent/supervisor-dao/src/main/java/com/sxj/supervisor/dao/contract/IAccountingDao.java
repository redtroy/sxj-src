package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.util.persistent.QueryCondition;

public interface IAccountingDao {
	/**
	 * 查询财务统计
	 */
	public List<AccountingModel> query(QueryCondition<AccountingModel> query);

	public Double queryAmountItem(String contractNo);

	public Double queryAmountModifyItem(String contractNo);

	public Double queryAmountBatch(String contractNo);

	public Double queryAmountModifyBatch(String contractNo);
}
