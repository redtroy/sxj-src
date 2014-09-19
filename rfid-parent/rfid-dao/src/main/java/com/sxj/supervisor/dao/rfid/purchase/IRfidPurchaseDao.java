package com.sxj.supervisor.dao.rfid.purchase;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRfidPurchaseDao {
	/**
	 * 查询采购申请单列表
	 *
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<RfidPurchaseEntity> queryList(
			QueryCondition<RfidPurchaseEntity> query) throws SQLException;

	/**
	 * 新增采购申请单
	 *
	 * @param member
	 **/
	@Insert
	public void addRfidPurchase(RfidPurchaseEntity purchase)
			throws SQLException;

	/**
	 * 更新采购申请单
	 *
	 * @param member
	 **/
	@Update
	public void updateRfidPurchase(RfidPurchaseEntity purchase)
			throws SQLException;

	/**
	 * 获取采购申请单
	 *
	 * @param id
	 **/
	@Get
	public RfidPurchaseEntity getRfidPurchase(String id);
}
