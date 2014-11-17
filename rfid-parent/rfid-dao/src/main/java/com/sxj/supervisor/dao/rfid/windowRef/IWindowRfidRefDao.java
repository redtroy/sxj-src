package com.sxj.supervisor.dao.rfid.windowRef;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 门窗RFID关联Dao
 * 
 * @author Ann
 *
 */
public interface IWindowRfidRefDao {
	/**
	 * 查询门窗RFID列表关联
	 *
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<WindowRefEntity> queryWindowRfidRefList(
			QueryCondition<WindowRefEntity> query) throws SQLException;

	/**
	 * 新增门窗RFID关联
	 *
	 * @param member
	 **/
	@Insert
	public void addWindowRfidRef(WindowRefEntity window) throws SQLException;

	/**
	 * 更新门窗RFID关联
	 *
	 * @param member
	 **/
	@Update
	public void updateWindowRfidRef(WindowRefEntity window) throws SQLException;

	/**
	 * 获取门窗RFID关联信息
	 *
	 * @param id
	 **/
	@Get
	public WindowRefEntity getWindowRfidRef(String id);

	/**
	 * 获取门窗RFID关联信息
	 *
	 * @param id
	 **/
	@Delete
	public void deleteRfidRef(String id);
}
