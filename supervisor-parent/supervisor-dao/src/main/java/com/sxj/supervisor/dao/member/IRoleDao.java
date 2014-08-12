package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.member.RoleEntity;

public interface IRoleDao {
	/**
	 * 添加权限
	 *
	 * @param    roles
	**/
	@Insert
	public void addRoles(RoleEntity[] roles);
	
	/**
	 * 删除权限
	 *
	 * @param    accountId
	**/
	@Delete
	public void deleteRoles(String accountId);
	
	/**
	 * 获取权限列表
	 *
	 * @param    accountId
	**/
	public  List<RoleEntity> getRoles(String accountId);
}
