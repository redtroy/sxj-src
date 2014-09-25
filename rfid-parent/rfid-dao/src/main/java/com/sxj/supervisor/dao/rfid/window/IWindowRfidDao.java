package com.sxj.supervisor.dao.rfid.window;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.util.persistent.QueryCondition;
/**
 * 门窗RFID管理Dao
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
	 * 新增门窗RFID
	 *
	 * @param member
	 **/
	@Insert
	public void addWindowRfid(WindowRfidEntity window)
			throws SQLException;

	/**
	 * 更新门窗RFID
	 *
	 * @param member
	 **/
	@Update
	public void updateWindowRfid(WindowRfidEntity window)
			throws SQLException;

	/**
	 * 获取门窗RFID信息
	 *
	 * @param id
	 **/
	@Get
	public WindowRfidEntity getWindowRfid(String id);
}
