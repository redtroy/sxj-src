package com.sxj.supervisor.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.system.IRoleDao;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.RoleEntity;
import com.sxj.supervisor.model.function.FunctionModel;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Override
	public void addRoles(List<RoleEntity> roles) {
		int size = roles.size();
		RoleEntity[] roleArr = (RoleEntity[]) roles
				.toArray(new RoleEntity[size]);
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
	public List<FunctionModel> getRoleFunction(String accountId)
			throws ServiceException {
		try {
			QueryCondition<FunctionEntity> query = new QueryCondition<FunctionEntity>();
			query.addCondition("parentId", 0);
			query.addCondition("accountId", accountId);
			List<FunctionEntity> functionList = roleDao.getRoleFunction(query);
			List<FunctionModel> list = new ArrayList<FunctionModel>();
			for (FunctionEntity functionEntity : functionList) {
				if (functionEntity == null) {
					continue;
				}
				QueryCondition<FunctionEntity> childrenQuery = new QueryCondition<FunctionEntity>();
				childrenQuery.addCondition("parentId", functionEntity.getId());
				childrenQuery.addCondition("accountId", accountId);
				List<FunctionEntity> childrenList = roleDao
						.getRoleFunction(childrenQuery);
				FunctionModel model = new FunctionModel();
				model.setFunction(functionEntity);
				model.setChildren(childrenList);
				list.add(model);
			}
			return list;
		} catch (Exception e) {
			throw new ServiceException("查询权限菜单错误", e);
		}
	}

	@Override
	public List<FunctionEntity> getAllRoleFunction(String accountId)
			throws ServiceException {
		try {
			List<FunctionEntity> list = roleDao.getAllRoleFunction(accountId);
			return list;
		} catch (Exception e) {
			throw new ServiceException("查询所有权限菜单错误", e);
		}
	}

}
