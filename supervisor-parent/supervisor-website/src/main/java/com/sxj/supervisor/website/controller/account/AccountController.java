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

import com.sxj.redis.advance.topic.RedisTopics;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.enu.member.AccountStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.Constraints;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/member/account")
public class AccountController extends BaseController {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IMemberFunctionService functionService;

	@Autowired
	private IMemberRoleService roleService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private RedisTopics topics;

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
		SupervisorPrincipal userBaen = getLoginInfo(session);
		if (userBaen == null) {
			return LOGIN;
		}
		query.setMemberNo(userBaen.getMember().getMemberNo());
		int flag = 0;
		if (MemberTypeEnum.DAWP.equals(userBaen.getMember().getType())) {
			flag = 1;
		} else {
			flag = 2;
		}
		List<AccountEntity> list = accountService.queryAccounts(query);
		List<MemberFunctionEntity> functionList = functionService
				.queryChildrenFunctions(null, flag);
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
	public String new_account(String memberNo, ModelMap map) {
		map.put("memberNo", memberNo);
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
			@RequestParam("functionIds") String functionIds)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (!password_confirm.equals(account.getPassword())) {
				throw new WebException("两次密码不一致");
			}
			SupervisorPrincipal userInfo = getLoginInfo(session);
			if (userInfo != null) {
				String[] ids = null;
				if (StringUtils.isNotEmpty(functionIds)) {
					ids = functionIds.split(",");
				}
				account.setState(AccountStatesEnum.NORMAL);
				account.setRegDate(new Date());
				account.setParentId(userInfo.getMember().getMemberNo());
				accountService.addAccount(account, ids);
				map.put("isOK", "ok");
			}

		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;

	}

	/**
	 * 修改密码
	 */
	@RequestMapping("save_pwd")
	public @ResponseBody Map<String, String> save_pwd(String id, String password)
			throws WebException {
		try {
			accountService.editPwd(id, password);
			topics.getTopic(Constraints.WEBSITE_CHANNEL_NAME).publish(
					"del," + id);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
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
		topics.getTopic(Constraints.WEBSITE_CHANNEL_NAME).publish(
				"update," + account.getId());
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
		if (state == AccountStatesEnum.STOP.getId()) {
			topics.getTopic(Constraints.WEBSITE_CHANNEL_NAME).publish(
					"del," + id);
		}
		return map;
	}

	@RequestMapping("get_role_function")
	public String getRloeFunctions(String memberNo, String accountId,
			String type, HttpSession session, ModelMap map) {
		AccountEntity account = accountService.getAccount(accountId);
		if (account != null) {
			memberNo = account.getParentId();
		}
		MemberEntity member = memberService.memberInfo(memberNo);
		if (member == null) {
			member = getLoginInfo(session).getMember();
		}
		int flag = 0;
		if (MemberTypeEnum.DAWP.equals(member.getType())) {
			flag = 1;
		} else {
			flag = 2;
		}
		if ("get".equals(type)) {
			List<MemberFunctionModel> list = roleService
					.getRoleFunctions(accountId);
			map.put("list", list);
			return "site/member/role_function";
		} else if ("add".equals(type)) {
			List<MemberFunctionModel> allList = functionService
					.queryFunctions(flag);
			map.put("allList", allList);
			return "site/member/edit_role";

		} else if ("edit".equals(type)) {
			List<MemberFunctionEntity> list = roleService
					.getAllRoleFunction(accountId);
			List<MemberFunctionModel> allList = functionService
					.queryFunctions(flag);
			map.put("allList", allList);
			map.put("roleList", list);
			return "site/member/edit_role";
		} else {
			return null;
		}

	}

	/**
	 * 判断自会员帐号是否存在
	 */
	@RequestMapping("check_account")
	public @ResponseBody Map<String, String> check_account(String old,
			String param, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(old)) {
			if (old.equals(param)) {
				map.put("status", "y");
				map.put("info", "用户名还未被使用，可以注册！");
				return map;
			}
		}
		SupervisorPrincipal userBaen = getLoginInfo(session);
		if (accountService.getAccountByName(param, userBaen.getMember()
				.getMemberNo()) == null) {
			map.put("status", "y");
			map.put("info", "用户名还未被使用，可以注册！");
			return map;
		}
		map.put("status", "n");
		map.put("info", "该帐号已存在！");
		return map;
	}
}
