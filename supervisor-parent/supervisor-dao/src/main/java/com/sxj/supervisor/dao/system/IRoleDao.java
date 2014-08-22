package com.sxj.supervisor.dao.system;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.RoleEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRoleDao {
	/**
	 * 添加权限
	 *
	 * @param roles
	 **/
	@Insert
	public void addRoles(RoleEntity[] roles);

	/**
	 * 删除权限
	 *
	 * @param accountId
	 **/
	@Delete
	public void deleteRoles(String accountId);

	/**
	 * 获取权限列表
	 *
	 * @param accountId
	 **/
	public List<RoleEntity> getRoles(String accountId);

	/**
	 * 获取授权操作列表
	 * 
	 * @return
	 * @throws SQLException2
	 */
	public List<FunctionEntity> getRoleFunction(
			QueryCondition<FunctionEntity> query) throws SQLException;

	/**
	 * 获取授权操作列表
	 * 
	 * @return
	 * @throws SQLException2
	 */
	public List<FunctionEntity> getAllRoleFunction(String accountId) throws SQLException;
}
