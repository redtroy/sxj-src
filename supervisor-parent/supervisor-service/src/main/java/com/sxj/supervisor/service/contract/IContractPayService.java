package com.sxj.supervisor.service.contract;

import java.util.List;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.model.contract.ContractPayModel;
import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.util.exception.ServiceException;

public interface IContractPayService {
	/**
	 * 查询合同列表
	 */
	public List<PayRecordEntity> queryPayListA(ContractPayModel query)
			throws ServiceException;

	/**
	 * 查询合同列表
	 */
	public List<PayRecordEntity> queryPayListB(ContractPayModel query)
			throws ServiceException;

	/**
	 * 查询合同列表
	 */
	public List<PayRecordEntity> queryManagerPayList(ContractPayModel query)
			throws ServiceException;

	/**
	 * 更改收款、付款状态
	 */
	public String updateState(PayRecordEntity re, String flag)
			throws ServiceException;

	/**
	 * 
	 * @param id
	 * @param event
	 * @throws ServiceException
	 */
	public String updateMode(String id, String event) throws ServiceException;

	// /**
	// * 甲方付款
	// */
	// public String pay(String id, Double payReal) throws ServiceException;
	//
	/**
	 * 乙方确认收款
	 */
	public String payOk(PayRecordEntity re, String flag)
			throws ServiceException;

	/**
	 * 财务统计查询
	 */
	public List<AccountingModel> query_finance(AccountingModel query,
			String startDate, String endDate, String memberNo)
			throws ServiceException;

	/**
	 * 根据ID 查询
	 */
	public PayRecordEntity getPayRecordEntity(String id)
			throws ServiceException;

	/**
	 * 新增付款单
	 */
	public void addPayRecordEntity(PayRecordEntity pay) throws ServiceException;

	/**
	 * 根据RFIDNO查询支付单号
	 */
	public String getPayNoByRfidNo(String rfidNo) throws ServiceException;

	/**
	 * 根据支付单号，获取支付单实体
	 */
	public PayRecordEntity getPayNoBypayNo(String payNo)
			throws ServiceException;
	
	/**
	 * 更新支付金额
	 * @param contract
	 * @throws ServiceException
	 */
	public void updatePayAmountByContractNo(ContractEntity contract) throws ServiceException;
}
