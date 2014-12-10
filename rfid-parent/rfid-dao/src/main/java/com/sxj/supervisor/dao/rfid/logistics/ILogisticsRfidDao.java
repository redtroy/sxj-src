package com.sxj.supervisor.dao.rfid.logistics;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 物流RFID Dao
 * 
 * @author Ann
 *
 */
public interface ILogisticsRfidDao {
	/**
	 * 查询物流RFID列表
	 *
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<LogisticsRfidEntity> queryLogisticsRfidList(
			QueryCondition<LogisticsRfidEntity> query) throws SQLException;

	/**
	 * 查询物流RFID列表 乙方
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<LogisticsRfidEntity> queryLogisticsRfidList_B(
			QueryCondition<LogisticsRfidEntity> query) throws SQLException;

	/**
	 * 查询物流RFID列表 甲方
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<LogisticsRfidEntity> queryLogisticsRfidList_A(
			QueryCondition<LogisticsRfidEntity> query) throws SQLException;

	/**
	 * 新增物流RFID
	 *
	 * @param member
	 **/
	@Insert
	public void addLogisticsRfid(LogisticsRfidEntity logistics)
			throws SQLException;

	/**
	 * 新增物流RFID
	 *
	 * @param member
	 **/
	@BatchInsert
	public Integer batchAddLogisticsRfid(LogisticsRfidEntity[] logistics)
			throws SQLException;

	/**
	 * 更新物流RFID
	 *
	 * @param member
	 **/
	@BatchUpdate
	public void batchUpdateLogisticsRfid(LogisticsRfidEntity[] logistics)
			throws SQLException;

	/**
	 * 更新物流RFID
	 *
	 * @param member
	 **/
	@Update
	public void updateLogisticsRfid(LogisticsRfidEntity logistics)
			throws SQLException;

	/**
	 * 获取物流RFID信息
	 *
	 * @param id
	 **/
	@Get
	public LogisticsRfidEntity getLogisticsRfid(String id);
	
	public void updateGid(List<LogisticsRfidEntity> list);
}
