package com.sxj.supervisor.service.system;

import java.util.List;

import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.RoleEntity;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.util.exception.ServiceException;

public interface IRoleService {

	public void addRoles(List<RoleEntity> roles) throws ServiceException;

	public void removeRoles(String accountId) throws ServiceException;

	public List<RoleEntity> getRoles(String accountId) throws ServiceException;

	public List<FunctionModel> getRoleFunction(String accountId)
			throws ServiceException;

	public List<FunctionEntity> getAllRoleFunction(String accountId)
			throws ServiceException;
}
