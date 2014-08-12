package com.sxj.supervisor.dao.member;

import com.sxj.supervisor.entity.member.RoleEntity;

public interface IRoleDao {
	/**
	 * 添加权限
	 *
	 * @param    roles
	**/
	public void addRoles(RoleEntity[] roles);
	
	/**
	 * 删除权限
	 *
	 * @param    accountId
	**/
	public void deleteRoles(String accountId);
	
	/**
	 * 获取权限列表
	 *
	 * @param    accountId
	**/
	public java.util.List<RoleEntity> getRoles(String accountId);
}
