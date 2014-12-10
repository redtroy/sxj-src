package com.sxj.finance.service.impl.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.finance.dao.member.IAccountDao;
import com.sxj.finance.dao.member.IMemberDao;
import com.sxj.finance.entity.member.AccountEntity;
import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.finance.enu.member.AccountStatesEnum;
import com.sxj.finance.model.member.AccountQuery;
import com.sxj.finance.service.member.IAccountService;
import com.sxj.finance.service.member.IMemberService;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private IMemberDao memberDao;

	@Autowired
	private IMemberService memberService;


	/**
	 * 查询子会员
	 */
	@Override
	@Transactional(readOnly = true)
	public AccountEntity getAccount(String id) throws ServiceException {
		try {
			AccountEntity account = accountDao.getAccount(id);
			return account;
		} catch (Exception e) {
			SxjLogger.error("子会员查询错误", e, this.getClass());
			throw new ServiceException("子会员查询错误错误", e);
		}

	}

	/**
	 * 子会员高级查询
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AccountEntity> queryAccounts(AccountQuery query)
			throws ServiceException {
		try {
			QueryCondition<AccountEntity> condition = new QueryCondition<AccountEntity>();
			if (query != null) {
				condition.addCondition("name", query.getName());// 姓名
				condition.addCondition("parentId", query.getMemberNo());// 父会员号
				condition.addCondition("accountNo", query.getAccountNo());// 子会员
				condition.addCondition("accountName", query.getAccountName());// 子会员名称
				condition.addCondition("state", query.getState());// 子账户状态
				condition.addCondition("delstate", query.getDelstate());// 删除标记
				condition.addCondition("startDate", query.getStartDate());// 开始时间
				condition.addCondition("endDate", query.getEndDate());// 结束时间
				condition.addCondition("functionId", query.getFunctionId());// 权限ＩＤ
				condition.setPage(query);
			}
			List<AccountEntity> aacountList = accountDao
					.queryAccount(condition);
			query.setPage(condition);
			return aacountList;
		} catch (Exception e) {
			SxjLogger.error("子会员高级查询错误", e, this.getClass());
			throw new ServiceException("子会员高级查询错误错误", e);
		}

	}



	@Override
	@Transactional(readOnly = true)
	public AccountEntity getAccountByName(String accountName, String memberNo)
			throws ServiceException {
		try {
			if (StringUtils.isEmpty(accountName)) {
				return null;
			}
			QueryCondition<AccountEntity> condition = new QueryCondition<AccountEntity>();
			condition.addCondition("parentId", memberNo);// 父会员号
			condition.addCondition("accountName", accountName);// 子会员名称
			List<AccountEntity> list = accountDao
					.getAccountByAccountName(condition);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			SxjLogger.error("根据子账号查询信息错误", e, this.getClass());
			throw new ServiceException("根据子账号查询信息错误", e.getMessage());
		}
	}

	@Override
	@Transactional
	public String edit_pwd(String id, String pwd) throws ServiceException {
		try {
			AccountEntity account = new AccountEntity();
			account.setId(id);
			account.setPassword(EncryptUtil.md5Hex(pwd));
			account.setVersion(accountDao.getAccount(id).getVersion());
			accountDao.updateAccount(account);
		} catch (Exception e) {
			SxjLogger.error("密码更新错误", e, this.getClass());
			throw new ServiceException("密码更新错误", e.getMessage());
		}
		return null;
	}

	@Override
	@Transactional
	public void edit_Login(String id) throws ServiceException {
		try {
			AccountEntity account = new AccountEntity();
			account.setId(id);
			account.setLastLogin(new Date());
			account.setVersion(accountDao.getAccount(id).getVersion());
			accountDao.updateAccount(account);
		} catch (Exception e) {
			SxjLogger.error("登陆时间更新错误", e, this.getClass());
			throw new ServiceException("登陆时间更新错误", e.getMessage());
		}

	}
}
