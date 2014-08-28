package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.model.member.AccountQuery;

public interface IAccountService {

	public void addAccount(AccountEntity account);

	public void modifyAccount(AccountEntity account);

	public AccountEntity getAccount(String id);

	public List<AccountEntity> queryAccounts(AccountQuery query);

	public void reomveAccount(String id);

	public String editState(String id, Integer state);

	public String initializePwd(String id);
}
