package com.sxj.supervisor.service.impl.system;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.system.SysAccountQuery;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.ResultList;

@Service
@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultList<SystemAccountEntity> queryAccounts(SysAccountQuery query)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
