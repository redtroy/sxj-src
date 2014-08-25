package com.sxj.supervisor.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.system.ISystemAccountDao;
import com.sxj.supervisor.entity.system.RoleEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.system.SysAccountQuery;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;
import com.sxj.util.persistent.ResultListImpl;

@Service
@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService {

	@Autowired
	private ISystemAccountDao accountDao;

	@Autowired
	private IRoleService roleServce;

	@Override
	@Transactional
	public void addAccount(SystemAccountEntity account, String[] functionIds)
			throws ServiceException {
		try {
			accountDao.addSystemAccount(account);
			if (functionIds != null && functionIds.length > 0) {
				List<RoleEntity> roles = new ArrayList<RoleEntity>();
				for (int i = 0; i < functionIds.length; i++) {
					if (functionIds[i] == null) {
						continue;
					}
					if ("none".equals(functionIds[i])) {
						continue;
					}
					RoleEntity role = new RoleEntity();
					role.setAccountId(account.getId());
					role.setFunctionId(functionIds[i]);
					roles.add(role);
				}
				if (roles.size() > 0) {
					roleServce.addRoles(roles);
				}
			}
		} catch (Exception e) {
			throw new ServiceException("添加系统用户信息错误", e);
		}

	}

	@Override
	@Transactional
	public void modifyAccount(SystemAccountEntity account, String[] functionIds)
			throws ServiceException {
		try {
			if (account == null) {
				return;
			}
			if (functionIds != null && functionIds.length > 0) {
				List<RoleEntity> roles = new ArrayList<RoleEntity>();
				for (int i = 0; i < functionIds.length; i++) {
					if (functionIds[i] == null) {
						continue;
					}
					if ("none".equals(functionIds[i])) {
						continue;
					}
					RoleEntity role = new RoleEntity();
					role.setAccountId(account.getId());
					role.setFunctionId(functionIds[i]);
					roles.add(role);
				}
				if (roles.size() > 0) {
					roleServce.removeRoles(account.getId());
					roleServce.addRoles(roles);
				}
			}

			accountDao.updateSystemAccount(account);
		} catch (Exception e) {
			throw new ServiceException("修改系统用户信息错误", e);
		}

	}

	@Override
	public void deleteAccount(String id) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public SystemAccountEntity getAccount(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SystemAccountEntity getAccountByAccount(String account)
			throws ServiceException {
		try {
			QueryCondition<SystemAccountEntity> condition = new QueryCondition<SystemAccountEntity>();
			condition.addCondition("account", account);
			List<SystemAccountEntity> list = accountDao
					.querySystemAccount(condition);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException("查询系统用户信息失败", e);
		}
	}

	@Override
	public ResultList<SystemAccountEntity> queryAccounts(SysAccountQuery query)
			throws ServiceException {
		try {

			QueryCondition<SystemAccountEntity> condition = new QueryCondition<SystemAccountEntity>();
			if (query != null) {
				condition.addCondition("id", query.getId());
				condition.addCondition("name", query.getName());
				condition.addCondition("account", query.getAccount());
				condition.addCondition("functionId", query.getFunctionId());
			}
			List<SystemAccountEntity> list = accountDao
					.querySystemAccount(condition);
			ResultList<SystemAccountEntity> res = new ResultListImpl<SystemAccountEntity>();
			res.setResults(list);
			return res;
		} catch (Exception e) {
			throw new ServiceException("查询系统用户错误", e);
		}
	}

}
