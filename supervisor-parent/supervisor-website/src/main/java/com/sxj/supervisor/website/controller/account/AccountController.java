package com.sxj.supervisor.website.controller.account;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.enu.member.AccountStatesEnum;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.supervisor.website.login.SupervisorPrincipal;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/member/account")
public class AccountController extends BaseController {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IMemberFunctionService functionService;

	@Autowired
	private IMemberRoleService roleService;

	/**
	 * 查询子账户列表
	 * 
	 * @param query
	 * @param map
	 * @return
	 */
	@RequestMapping("accountList")
	public String accountList(AccountQuery query, HttpSession session,
			ModelMap map) {
		if (query != null) {
			query.setPagable(true);
		}
		SupervisorPrincipal userBaen = getLoginInfo(session);
		if (userBaen == null) {
			return LOGIN;
		}
		query.setMemberNo(userBaen.getMember().getMemberNo());
		List<AccountEntity> list = accountService.queryAccounts(query);
		List<MemberFunctionEntity> functionList = functionService
				.queryChildrenFunctions("0");
		map.put("list", list);
		map.put("functions", functionList);
		map.put("query", query);
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
	public @ResponseBody Map<String, String> add_account(HttpSession session,
			AccountEntity account,
			@RequestParam("password_confirm") String password_confirm,
			@RequestParam("functionIds") String[] functionIds)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (!password_confirm.equals(account.getPassword())) {
				throw new WebException("两次密码不一致");
			}
			SupervisorPrincipal userInfo = getLoginInfo(session);
			if (userInfo != null) {
				account.setState(AccountStatesEnum.normal);
				account.setRegDate(new Date());
				account.setParentId(userInfo.getMember().getMemberNo());
				accountService.addAccount(account, functionIds);
				map.put("isOK", "ok");
			}
			return map;
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}

	}

	/**
	 * 修改密码
	 */
	@RequestMapping("save_pwd")
	public @ResponseBody Map<String, String> save_pwd(AccountEntity account) {
		accountService.modifyAccount(account, null);
		return null;
	}

	/**
	 * 根据更新子帐号信息
	 * 
	 * @param editAccount
	 * @return
	 */
	@RequestMapping("editAccount")
	public @ResponseBody Map<String, String> editAccount(AccountEntity account,
			@RequestParam("functionIds") String functionIds) {
		String[] ids = null;
		if (StringUtils.isNotEmpty(functionIds)) {
			ids = functionIds.split(",");
		}
		accountService.modifyAccount(account, ids);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOK", "ok");
		return map;
	}

	/**
	 * 修改子会员状态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("editState")
	public @ResponseBody Map<String, String> editState(String id, Integer state) {
		String stateName = accountService.editState(id, state);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOK", "ok");
		map.put("state", stateName);
		return map;
	}

	@RequestMapping("get_role_function")
	public String getRloeFunctions(String accountId, String type, ModelMap map) {
		if ("get".equals(type)) {
			List<MemberFunctionModel> list = roleService
					.getRoleFunctions(accountId);
			map.put("list", list);
			return "site/member/role_function";
		} else if ("add".equals(type)) {
			// List<FunctionModel> allList = functionService.queryFunctions();
			// map.put("allList", allList);
			return "manage/system/edit_role";

		} else if ("edit".equals(type)) {
			List<MemberFunctionEntity> list = roleService
					.getAllRoleFunction(accountId);
			List<MemberFunctionModel> allList = functionService
					.queryFunctions();
			map.put("allList", allList);
			map.put("roleList", list);
			return "site/member/edit_role";
		} else {
			return null;
		}

	}
}
