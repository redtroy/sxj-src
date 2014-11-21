package com.sxj.supervisor.service.contract;

import java.util.List;

import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.model.contract.ContractPayModel;
import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.util.exception.ServiceException;

public interface IContractPayService {
	/**
	 * 查询合同列表
	 */
	public List<PayRecordEntity> queryPayList(ContractPayModel query)
			throws ServiceException;

	/**
	 * 更改收款、付款状态
	 */
	public void update_state(String id, Integer state) throws ServiceException;

	/**
	 * 甲方付款
	 */
	public String pay(String id, Double payReal) throws ServiceException;

	/**
	 * 乙方确认收款
	 */
	public String pay_ok(String id) throws ServiceException;

	/**
	 * 财务统计查询
	 */
	public List<AccountingModel> query_finance(AccountingModel query,
			String startDate, String endDate) throws ServiceException;

	/**
	 * 根据ID 查询
	 */
	public PayRecordEntity getPayRecordEntity(String id)
			throws ServiceException;

	/**
	 * 新增付款单
	 */
	public void addPayRecordEntity(PayRecordEntity pay) throws ServiceException;

}
