package com.sxj.supervisor.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IAccountDao;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.model.AccountQuery;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;

@Transactional
public class AccountServiceImpl implements IAccountService {
	
	@Autowired
	private IAccountDao accountDao;
	
	/**
	 * 新增子会员
	 */
	@Override
	public void addAccount(AccountEntity account) {
		accountDao.addAccount(account);

	}

	/**
	 * 更新子会员
	 */
	@Override
	public void modifyAccount(AccountEntity account) {
		accountDao.updateAccount(account);

	}

	/**
	 * 查询子会员
	 */
	@Override
	public AccountEntity getAccount(String id) {
		AccountEntity account = accountDao.getAccount(id);
		return account;
	}

	/**
	 * 子会员高级查询
	 */
	@Override
	@Transactional(readOnly=true)
	public ResultList<AccountEntity> queryAccounts(AccountQuery query) {
		QueryCondition<AccountEntity> qc = new QueryCondition<AccountEntity>();
		Map<String, Object> condition =new HashMap<String, Object>();
		condition.put("parentId", query.getMemberNo());//父会员号
		condition.put("id", query.getAccountId());//子会员ＩＤ
		condition.put("accountName", query.getAccountName());//子会员名称
		condition.put("state", query.getState());//子账户状态
		condition.put("startDate", query.getStartDate());//开始时间
		condition.put("endDate", query.getEndDate());//结束时间
		condition.put("roleId", query.getRoleId());//权限ＩＤ
		qc.setCondition(condition);
		ResultList<AccountEntity> rl= accountDao.queryAccount(qc);
		return rl;
	}

	/**
	 * 删除子会员
	 */
	@Override
	public void reomveAccount(String id) {
		accountDao.deleteAccount(id);

	}

}
