package com.sxj.supervisor.service.impl.member;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IRoleDao;
import com.sxj.supervisor.entity.member.RoleEntity;
import com.sxj.supervisor.service.member.IRoleService;
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	@Override
	public void addRoles(List<RoleEntity> roles) {
		int size = roles.size();
		RoleEntity[] roleArr = (RoleEntity[])roles.toArray(new RoleEntity[size]);
		roleDao.addRoles(roleArr);

	}

	@Override
	public void removeRoles(String accountId) {
		roleDao.deleteRoles(accountId);

	}

	@Override
	public List<RoleEntity> getRoles(String accountId) {
		
		return roleDao.getRoles(accountId);

	}

}
