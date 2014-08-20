package com.sxj.supervisor.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.system.IRoleDao;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.RoleEntity;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.util.exception.ServiceException;
@Service
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

	@Override
	public List<FunctionEntity> getRoleFunction(String accountId)
			throws ServiceException {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
