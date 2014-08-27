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
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
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
			SystemAccountEntity oldAccount = getAccountByAccount(account
					.getAccount());
			if (oldAccount != null) {
				throw new ServiceException("用户账户已存在");
			}
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
	@Transactional
	public void deleteAccount(String id) throws ServiceException {
		try {
			SystemAccountEntity account = new SystemAccountEntity();
			account.setId(id);
			account.setDelState(true);
			accountDao.updateSystemAccount(account);
			roleServce.removeRoles(id);
		} catch (Exception e) {
			throw new ServiceException("删除系统用户信息错误", e);
		}

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
				query.setPagable(true);
				condition.addCondition("id", query.getId());
				condition.addCondition("accountNo", query.getAccountNo());
				condition.addCondition("delState", query.getDelState());
				condition.addCondition("name", query.getName());
				condition.addCondition("account", query.getAccount());
				condition.addCondition("functionId", query.getFunctionId());
				condition.setPage(query);
			}
			List<SystemAccountEntity> list = accountDao
					.querySystemAccount(condition);
			query.setPage(condition);
			ResultList<SystemAccountEntity> res = new ResultListImpl<SystemAccountEntity>();
			res.setResults(list);
			return res;
		} catch (Exception e) {
			throw new ServiceException("查询系统用户错误", e);
		}
	}

	@Override
	@Transactional
	public String initPassword(String accountId) throws ServiceException {
		try {
			int rondom = NumberUtils.getRandomIntInMax(999999);
			String password = StringUtils.getLengthStr(rondom + "", 6, '0');
			SystemAccountEntity account = new SystemAccountEntity();
			account.setId(accountId);
			account.setPassword(EncryptUtil.md5Hex(password));
			accountDao.updateSystemAccount(account);
			return password;
		} catch (Exception e) {
			throw new ServiceException("初始化系统用户密码错误", e);
		}
	}

}
