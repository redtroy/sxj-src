package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.util.exception.ServiceException;

public interface IAccountService {

	public void addAccount(AccountEntity account) throws ServiceException;

	public void modifyAccount(AccountEntity account) throws ServiceException;

	public AccountEntity getAccount(String id) throws ServiceException;

	public AccountEntity getAccountByNo(String accountNo)
			throws ServiceException;

	public List<AccountEntity> queryAccounts(AccountQuery query)
			throws ServiceException;

	public void reomveAccount(String id) throws ServiceException;

	public String editState(String id, Integer state) throws ServiceException;

	public String initializePwd(String id) throws ServiceException;
}
