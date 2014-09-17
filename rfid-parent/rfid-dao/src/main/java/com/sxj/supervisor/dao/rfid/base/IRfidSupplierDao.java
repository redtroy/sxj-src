package com.sxj.supervisor.dao.rfid.base;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRfidSupplierDao {
	/**
	 * 查询供应商列表
	 *
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<RfidSupplierEntity> queryList(
			QueryCondition<RfidSupplierEntity> query) throws SQLException;

	/**
	 * 新增供应商
	 *
	 * @param member
	 **/
	@Insert
	public void addRfidSupplier(RfidSupplierEntity supplier)
			throws SQLException;

	/**
	 * 更新供应商
	 *
	 * @param member
	 **/
	@Update
	public void updateRfidSupplier(RfidSupplierEntity supplier)
			throws SQLException;

	/**
	 * 获取供应商信息
	 *
	 * @param id
	 **/
	@Get
	public RfidSupplierEntity getRfidSupplier(String id);
}
