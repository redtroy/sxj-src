package com.sxj.supervisor.dao.member;

import com.sxj.supervisor.entity.member.AccountEntity;

public interface IAccountDao {
	/**
	 * 添加子账户
	 *
	 * @param    account
	**/
	public void addAccount(AccountEntity account);
	
	/**
	 * 修改子账户
	 *
	 * @param    account
	**/
	public void updateAccount(AccountEntity account);
	
	/**
	 * 获取子账户信息
	 *
	 * @param    id
	**/
	public AccountEntity getAccount(String id);
	
	/**
	 * 删除子账户
	 *
	 * @param    id
	**/
	public void deleteAccount(String id);
	
	/**
	 * 子账户高级查询
	 *
	 * @param    account
	**/
	public java.util.List<AccountEntity> queryAccount(AccountEntity account);
}
