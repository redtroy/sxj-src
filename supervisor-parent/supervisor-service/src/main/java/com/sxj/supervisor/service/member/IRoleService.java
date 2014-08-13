package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.RoleEntity;

public interface IRoleService {
	
	public void addRoles(List<RoleEntity> roles);

	public void removeRoles(String accountId);

	public List<RoleEntity> getRoles(String accountId);
}
