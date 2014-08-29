package com.sxj.supervisor.website.controller.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.enu.member.MemberAuthorityEnum;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/member/account")
public class AccountController extends BaseController {

	@Autowired
	IAccountService accountService;

	/**
	 * 查询子账户列表
	 * 
	 * @param query
	 * @param map
	 * @return
	 */
	@RequestMapping("accountList")
	public String accountList(AccountQuery query, ModelMap map) {
		String parentId = "1";
		query.setMemberId(parentId);
		MemberAuthorityEnum[] ma = MemberAuthorityEnum.values();// 权限
		List<AccountEntity> list = accountService.queryAccounts(query);
		map.put("list", list);
		map.put("ma", ma);
		return "site/member/edit-account";
	}

	/**
	 * 增加子账户页面
	 * 
	 * @return
	 */
	@RequestMapping("new_account")
	public String new_account() {
		return "site/member/add_account";
	}

	/**
	 * 添加子账户
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping("add_account")
	public @ResponseBody Map<String, String> add_account(AccountEntity account) {
		accountService.addAccount(account);
		return null;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("save_pwd")
	public @ResponseBody Map<String, String> save_pwd(AccountEntity account) {
		accountService.modifyAccount(account);
		return null;
	}
}
