package com.sxj.supervisor.service.impl.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		QueryCondition<AccountEntity> qc = new QueryCondition<AccountEntity>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("parentId", query.getMemberNo());// 父会员号
		condition.put("id", query.getAccountId());// 子会员ＩＤ
		condition.put("accountName", query.getAccountName());// 子会员名称
		condition.put("state", query.getState());// 子账户状态
		condition.put("delstate", query.getDelstate());// 删除标记
		condition.put("startDate", query.getStartDate());// 开始时间
		condition.put("endDate", query.getEndDate());// 结束时间
		condition.put("roleId", query.getRoleId());// 权限ＩＤ
		qc.setCondition(condition);
		List<AccountEntity> aacountList = accountDao.queryAccount(qc);
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
	public String editState(String id) {
		AccountEntity account = accountDao.getAccount(id);
		if (account.getState().getId().intValue() == MemberStatesEnum.normal
				.getId().intValue()) {
			account.setState(MemberStatesEnum.stop);
			accountDao.updateAccount(account);
			return MemberStatesEnum.stop.getName();
		} else {
			account.setState(MemberStatesEnum.normal);
			accountDao.updateAccount(account);
			return MemberStatesEnum.normal.getName();
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
