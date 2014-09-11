package com.sxj.supervisor.service.system;

import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.system.SysAccountQuery;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.ResultList;

public interface ISystemAccountService {

	/**
	 * 
	 * @param account
	 * @throws ServiceException
	 */
	public void addAccount(SystemAccountEntity account, String[] functionIds)
			throws ServiceException;

	public void modifyAccount(SystemAccountEntity account, String[] functionIds)
			throws ServiceException;

	public void updateLoginTime(String accountId) throws ServiceException;

	public String initPassword(String accountId) throws ServiceException;

	public void deleteAccount(String id) throws ServiceException;

	public SystemAccountEntity getAccount(String id) throws ServiceException;

	public SystemAccountEntity getAccountByAccount(String account)
			throws ServiceException;

	public ResultList<SystemAccountEntity> queryAccounts(SysAccountQuery query)
			throws ServiceException;

	public String edit_pwd(String id, String password) throws ServiceException;
}
