package com.sxj.supervisor.dao.contract;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IContractPayDao {

	/**
	 * 付款管理高级查询 甲方
	 * 
	 * @param function
	 **/
	public List<PayRecordEntity> queryPayContractA(
			QueryCondition<PayRecordEntity> query);

	/**
	 * 付款管理高级查询 乙方
	 * 
	 * @param function
	 **/
	public List<PayRecordEntity> queryPayContractB(
			QueryCondition<PayRecordEntity> query);

	/**
	 * 付款管理高级查询后台
	 *
	 * @param function
	 **/
	public List<PayRecordEntity> queryManagerPayContract(
			QueryCondition<PayRecordEntity> query);

	/**
	 * 修改更新付款管理
	 */
	@Update
	public void updatePay(PayRecordEntity pay);

	/**
	 * 根据ID 查询
	 */
	@Get
	public PayRecordEntity getPayRecordEntity(String id);

	/**
	 * 新增付款单
	 */
	@Insert
	public void addPay(PayRecordEntity pay);

	/**
	 * 根据RFIDNO查询实体
	 */
	public PayRecordEntity getEntityByRfidNo(String rfidNo);

	/**
	 * 根据支付单号修改融资状态
	 */
	public void changeState(@Param("payNo") String payNo,
			@Param("state") String state);

	/**
	 * 生成合同定金
	 * 
	 * @param pay
	 */
	public int addContractPay(PayRecordEntity pay);

	/**
	 * 根据payNo查询实体
	 */
	public PayRecordEntity getEntityByPayNo(String payNo);
	/**
	 * 更新支付单金额
	 * @param contract
	 * @throws SQLException
	 */
	public void updatePayAmountByContractNo(ContractEntity contract)throws SQLException;
}
