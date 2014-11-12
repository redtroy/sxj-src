package com.sxj.supervisor.dao.rfid.ref;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.util.persistent.QueryCondition;

public interface ILogisticsRefDao {
	/**
	 * 查询物流RFID关联申请管理列表
	 *
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<LogisticsRefEntity> queryList(
			QueryCondition<LogisticsRefEntity> query) throws SQLException;

	/**
	 * 新增物流RFID关联申请
	 *
	 * @param member
	 **/
	@Insert
	public void add(LogisticsRefEntity model) throws SQLException;

	/**
	 * 更新物流RFID关联申请
	 *
	 * @param member
	 **/
	@Update
	public void update(LogisticsRefEntity model) throws SQLException;

	/**
	 * 获取物流RFID关联申请
	 *
	 * @param id
	 **/
	@Get
	public LogisticsRefEntity get(String id);

	/**
	 * 根据ID进行删除
	 */
	@Delete
	public void delete(String id);
}
