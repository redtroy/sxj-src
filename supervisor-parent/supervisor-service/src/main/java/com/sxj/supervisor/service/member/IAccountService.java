package com.sxj.supervisor.service.member;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.model.AccountQuery;
import com.sxj.util.persistent.ResultList;

public interface IAccountService {
	
	
	public void addAccount(AccountEntity account);

	public void modifyAccount(AccountEntity account);

	public AccountEntity getAccount(String id);

	public ResultList<AccountEntity> queryAccounts(AccountQuery query);

	public void reomveAccount(String id);
}
