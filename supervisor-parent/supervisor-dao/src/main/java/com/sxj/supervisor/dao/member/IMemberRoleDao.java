package com.sxj.supervisor.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.entity.member.MemberRoleEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IMemberRoleDao {
	/**
	 * 添加权限
	 *
	 * @param roles
	 **/
	@BatchInsert
	public void addRoles(MemberRoleEntity[] roles);

	/**
	 * 删除权限
	 *
	 * @param accountId
	 **/
	public void deleteRolesByAccount(String accountId);

	/**
	 * 删除权限
	 *
	 * @param accountId
	 **/
	public void deleteRolesByMember(String memberId);

	/**
	 * 获取权限列表
	 *
	 * @param accountId
	 **/
	public List<MemberRoleEntity> getRoles(
			QueryCondition<MemberRoleEntity> query);

	/**
	 * 获取授权操作列表
	 * 
	 * @return
	 * @throws SQLException2
	 */
	public List<MemberFunctionEntity> getRoleFunctions(
			QueryCondition<MemberFunctionEntity> query) throws SQLException;

	/**
	 * 获取授权操作列表
	 * 
	 * @return
	 * @throws SQLException2
	 */
	public List<MemberFunctionEntity> getAllRoleFunction(String accountId)
			throws SQLException;

}
