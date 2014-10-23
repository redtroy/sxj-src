package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IContractPayDao {

	/**
	 * 付款管理高级查询
	 *
	 * @param function
	 **/
	public List<PayRecordEntity> queryPayContract(
			QueryCondition<PayRecordEntity> query);

	/**
	 * 修改更新付款管理
	 */
	@Update
	public void update_pay(PayRecordEntity pay);

}
