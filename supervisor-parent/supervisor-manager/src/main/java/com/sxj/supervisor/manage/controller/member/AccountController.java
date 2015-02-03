package com.sxj.supervisor.manage.controller.member;

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

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.enu.member.AccountStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.supervisor.service.member.IMemberService;
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
	private IMemberService memberService;

	@Autowired
	private IMemberRoleService roleService;

	@Autowired
	private RedisTopics topics;

	@RequestMapping("accountList")
	public String accountList(AccountQuery query, ModelMap map) {
		if (query != null) {
			query.setPagable(true);
		}
		AccountStatesEnum[] states = AccountStatesEnum.values();
		List<AccountEntity> list = accountService.queryAccounts(query);
		List<MemberFunctionEntity> functionList = functionService
				.queryChildrenFunctions(null, null);
		map.put("functions", functionList);
		map.put("states", states);
		map.put("list", list);
		map.put("query", query);
		return "manage/member/account";
	}

	@RequestMapping("get_role_function")
	public String getRloeFunctions(String memberNo, String accountId,
			String type, ModelMap map) {
		AccountEntity account = accountService.getAccount(accountId);
		if (account != null) {
			memberNo = account.getParentId();
		}
		MemberEntity member = memberService.memberInfo(memberNo);
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
			return "manage/member/role_function";
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
			return "manage/member/edit_role";
		} else {
			return null;
		}

	}

	/**
	 * 根据ID删除子帐号
	 * 
	 * @param delAccount
	 * @return
	 */
	@RequestMapping("delAccount")
	public @ResponseBody Map<String, String> delAccount(String id) {
		accountService.reomveAccount(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOK", "ok");
		return map;
	}

	/**
	 * 根据更新子帐号信息
	 * 
	 * @param editAccount
	 * @return
	 */
	@RequestMapping("editAccount")
	public @ResponseBody Map<String, String> editAccount(AccountEntity account,
			@RequestParam("functionIds") String functionIds)
			throws WebException {
		try {
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
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new WebException("更新子账户信息错误");
		}

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

	/**
	 * 初始化密码
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("initializePwd")
	public @ResponseBody Map<String, Object> initializePwd(String id) {
		String password = accountService.initializePwd(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isOK", "ok");
		map.put("password", password);
		topics.getTopic(Constraints.WEBSITE_CHANNEL_NAME).publish("del," + id);
		return map;
	}

	/**
	 * 判断自会员帐号是否存在
	 */
	@RequestMapping("check_account")
	public @ResponseBody Map<String, String> check_account(String memerNo,
			String old, String param, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(old)) {
			if (old.equals(param)) {
				map.put("status", "y");
				map.put("info", "用户名还未被使用！");
				return map;
			}
		}
		if (accountService.getAccountByName(param, memerNo) == null) {
			map.put("status", "y");
			map.put("info", "用户名还未被使用！");
			return map;
		}
		map.put("status", "n");
		map.put("info", "该帐号已存在！");
		return map;
	}
}
