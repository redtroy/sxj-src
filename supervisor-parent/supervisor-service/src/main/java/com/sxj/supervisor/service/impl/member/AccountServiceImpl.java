package com.sxj.supervisor.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IAccountDao;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
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
		AccountEntity ae = accountDao.getAccount(account.getId());
		ae.setName(account.getName());
		ae.setAccountName(account.getAccountName());
		accountDao.updateAccount(ae);

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
	@Transactional(readOnly = true)
	public List<AccountEntity> queryAccounts(AccountQuery query) {
		QueryCondition<AccountEntity> condition = new QueryCondition<AccountEntity>();
		if (query != null) {
			condition.addCondition("parentId", query.getMemberId());// 父会员号
			condition.addCondition("id", query.getAccountId());// 子会员ＩＤ
			condition.addCondition("accountName", query.getAccountName());// 子会员名称
			condition.addCondition("state", query.getState());// 子账户状态
			condition.addCondition("delstate", query.getDelstate());// 删除标记
			condition.addCondition("startDate", query.getStartDate());// 开始时间
			condition.addCondition("endDate", query.getEndDate());// 结束时间
			condition.addCondition("roleId", query.getRoleId());// 权限ＩＤ
			condition.setPage(query);
		}
		List<AccountEntity> aacountList = accountDao.queryAccount(condition);
		query.setPage(condition);
		return aacountList;
	}

	/**
	 * 删除子会员
	 */
	@Override
	public void reomveAccount(String id) {
		AccountEntity account = getAccount(id);
		account.setDelstate(true);
		accountDao.updateAccount(account);
		// accountDao.deleteAccount(id);
	}

	/**
	 * 更新自会员状态
	 */
	@Override
	public String editState(String id, Integer state) {
		AccountEntity account = new AccountEntity();
		account.setId(id);
		if (state == MemberStatesEnum.normal.getId().intValue()) {
			account.setState(MemberStatesEnum.normal);
			accountDao.updateAccount(account);
			return MemberStatesEnum.normal.getName();
		} else if (state == MemberStatesEnum.stop.getId().intValue()) {
			account.setState(MemberStatesEnum.stop);
			accountDao.updateAccount(account);
			return MemberStatesEnum.stop.getName();
		} else {
			return null;
		}
	}

	/**
	 * 初始化密码
	 */
	@Override
	@Transactional
	public String initializePwd(String id) {
		try {
			AccountEntity account = getAccount(id);
			// 随机密码
			String password = "123456";
			account.setPassword(password);
			modifyAccount(account);
			return password;
		} catch (Exception e) {
			throw new ServiceException("初始化密码错误", e.getMessage());
		}
	}

}
