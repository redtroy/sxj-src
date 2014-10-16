package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IAccountDao {
	/**
	 * 添加子账户
	 *
	 * @param account
	 **/
	@Insert
	public void addAccount(AccountEntity account);

	/**
	 * 修改子账户
	 *
	 * @param account
	 **/
	@Update
	public void updateAccount(AccountEntity account);

	/**
	 * 获取子账户信息
	 *
	 * @param id
	 **/
	@Get
	public AccountEntity getAccount(String id);

	/**
	 * 获取子账户信息
	 *
	 * @param accountName
	 **/

	public List<AccountEntity> getAccountByAccountName(String accountName,
			String id);

	/**
	 * 删除子账户
	 *
	 * @param id
	 **/
	@Delete
	public void deleteAccount(String id);

	/**
	 * 子账户高级查询
	 *
	 * @param account
	 **/
	public List<AccountEntity> queryAccount(
			QueryCondition<AccountEntity> account);
}
