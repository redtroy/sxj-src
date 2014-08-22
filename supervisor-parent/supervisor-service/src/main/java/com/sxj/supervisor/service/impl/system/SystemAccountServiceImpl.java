package com.sxj.supervisor.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.system.ISystemAccountDao;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.system.SysAccountQuery;
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

	@Override
	public void addAccount(SystemAccountEntity account) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAccount(SystemAccountEntity account)
			throws ServiceException {
		// TODO Auto-generated method stub

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
			throw new ServiceException("查询系统用户错误",e);
		}
	}

}
