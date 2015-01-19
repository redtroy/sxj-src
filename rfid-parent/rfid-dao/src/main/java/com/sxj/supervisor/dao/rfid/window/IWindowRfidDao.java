package com.sxj.supervisor.dao.rfid.window;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 门窗RFID管理Dao
 * 
 * @author Ann
 *
 */
public interface IWindowRfidDao {
	/**
	 * 查询门窗RFID列表
	 *
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<WindowRfidEntity> queryWindowRfidList(
			QueryCondition<WindowRfidEntity> query) throws SQLException;

	/**
	 * 
	 * @param contract
	 * @return
	 * @throws SQLException
	 */
	public Long getStartMaxRfidNo(String contractNo) throws SQLException;

	/**
	 * 
	 * @param contract
	 * @return
	 * @throws SQLException
	 */
	public Long getLossMaxRfidNo(String contractNo) throws SQLException;

	/**
	 * 新增门窗RFID
	 *
	 * @param window
	 **/
	@Insert
	public void addWindowRfid(WindowRfidEntity window) throws SQLException;

	/**
	 * 新增门窗RFID
	 *
	 * @param window
	 **/
	@BatchInsert
	public Integer batchAddWindowRfid(WindowRfidEntity[] window)
			throws SQLException;

	/**
	 * 更新门窗RFID
	 *
	 * @param window
	 **/
	@Update
	public void updateWindowRfid(WindowRfidEntity window) throws SQLException;

	/**
	 * 更新门窗RFID
	 *
	 * @param window
	 **/
	@BatchUpdate
	public void batchUpdateWindowRfid(WindowRfidEntity[] windows)
			throws SQLException;

	/**
	 * 获取门窗RFID信息
	 *
	 * @param id
	 **/
	@Get
	public WindowRfidEntity getWindowRfid(String id);

	/**
	 * 根据RFID_NO 查询窗户信息
	 */
	public WindowRfidEntity selectByRfidNo(String rfidNo);

	public void updateGid(List<WindowRfidEntity> winList);

	/**
	 * 质检
	 */
	public void updateTestWindow(WindowRfidEntity window);

	/**
	 * 安装
	 */
	public void updateStepWindow(WindowRfidEntity window);

}
