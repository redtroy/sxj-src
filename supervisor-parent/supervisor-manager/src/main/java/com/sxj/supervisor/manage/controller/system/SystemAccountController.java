package com.sxj.supervisor.manage.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.redis.advance.topic.RedisTopics;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.OperatorLogEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.supervisor.model.system.LogQuery;
import com.sxj.supervisor.model.system.SysAccountQuery;
import com.sxj.supervisor.service.system.IFunctionService;
import com.sxj.supervisor.service.system.IQueryOperation;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.supervisor.validator.hibernate.AddGroup;
import com.sxj.util.Constraints;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.ResultList;

@Controller
@RequestMapping("/system")
public class SystemAccountController extends BaseController {

	@Autowired
	private ISystemAccountService accountService;

	@Autowired
	private IFunctionService functionService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IQueryOperation queryOpreation;

	@Autowired
	private RedisTopics topics;

	@RequestMapping("account-list")
	public String getSysAccountList(SysAccountQuery query, ModelMap map) {
		if (query != null) {
			query.setPagable(true);
		}
		ResultList<SystemAccountEntity> list = accountService
				.queryAccounts(query);
		List<FunctionEntity> functionList = functionService
				.queryChildrenFunctions("0");
		map.put("list", list.getResults());
		map.put("functions", functionList);
		map.put("query", query);
		return "manage/system/account-list";

	}

	@RequestMapping("account-info")
	public String getSysAccount(String accountId, ModelMap map) {
		SystemAccountEntity account = accountService.getAccount(accountId);
		map.put("account", account);
		return "manage/system/account-info";
	}

	@RequestMapping("to_edit")
	public String toEditAccount(String accountId, ModelMap map) {
		SystemAccountEntity account = accountService.getAccount(accountId);
		map.put("account", account);
		return "manage/system/account-edit";
	}

	@RequestMapping("to_add")
	public String toAddAccount() {
		return "manage/system/account-add";
	}

	@RequestMapping("remove")
	public @ResponseBody Map<String, Object> remove(String accountId) {
		Map<String, Object> map = new HashMap<String, Object>();
		accountService.deleteAccount(accountId);
		map.put("isOK", true);
		topics.getTopic(Constraints.MANAGER_CHANNEL_NAME).publish(
				"del," + accountId);
		return map;
	}

	@RequestMapping("add_account")
	public @ResponseBody Map<String, Object> addAccount(
			@Validated({ AddGroup.class }) SystemAccountEntity account,
			BindingResult result,
			@RequestParam("password_confirm") String password_confirm,
			@RequestParam("functionIds") String functionIds)
			throws WebException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			getValidError(result);
			if (!password_confirm.equals(account.getPassword())) {
				throw new WebException("两次密码不一致");
			}
			if (account != null) {
				String[] ids = null;
				if (StringUtils.isNotEmpty(functionIds)) {
					ids = functionIds.split(",");
				}
				accountService.addAccount(account, ids);
				map.put("isOK", true);
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new WebException(e.getMessage());
		}

	}

	@RequestMapping("init_password")
	public @ResponseBody Map<String, Object> initPassword(String accountId) {
		String password = accountService.initPassword(accountId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isOK", true);
		map.put("password", password);
		topics.getTopic(Constraints.MANAGER_CHANNEL_NAME).publish(
				"del," + accountId);
		return map;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping("edir_pwd")
	public @ResponseBody Map<String, Object> edit_pwd(HttpSession session,
			String password, String password2) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (password.equals(password2)) {
			SystemAccountEntity user = getLoginInfo(session);
			accountService.edit_pwd(user.getId(), password);
			map.put("isOK", "ok");
		} else {
			map.put("erro", "两次密码输入不一样");
		}
		return map;

	}

	@RequestMapping("edit_account")
	public @ResponseBody Map<String, Object> editAccount(
			SystemAccountEntity account,
			@RequestParam("functionIds") String functionIds) {
		String[] ids = null;
		if (StringUtils.isNotEmpty(functionIds)) {
			ids = functionIds.split(",");
		}
		accountService.modifyAccount(account, ids);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isOK", true);
		map.put("account", account);
		map.put("functionIds", functionIds);
		topics.getTopic(Constraints.MANAGER_CHANNEL_NAME).publish(
				"update," + account.getAccountNo());
		return map;
	}

	@RequestMapping("get_role_function")
	public String getRloeFunctions(String accountId, String type, ModelMap map) {
		if ("get".equals(type)) {
			List<FunctionModel> list = roleService.getRoleFunction(accountId);
			map.put("list", list);
			return "manage/system/role_function";
		} else if ("add".equals(type)) {
			List<FunctionModel> allList = functionService.queryFunctions();
			map.put("allList", allList);
			return "manage/system/edit_role";

		} else if ("edit".equals(type)) {
			List<FunctionEntity> list = roleService
					.getAllRoleFunction(accountId);
			List<FunctionModel> allList = functionService.queryFunctions();
			map.put("allList", allList);
			map.put("roleList", list);
			return "manage/system/edit_role";
		} else {
			return null;
		}

	}

	@RequestMapping("query_operation")
	public String query_operation(ModelMap map, LogQuery query) {
		if (query != null) {
			query.setPagable(true);
		}
		List<OperatorLogEntity> list = queryOpreation.query(query);
		map.put("list", list);
		map.put("query", query);
		return "manage/system/admin-view";
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
				map.put("info", "该帐号未被使用！");
				return map;
			}
		}
		SystemAccountEntity account = accountService.getAccountByAccount(param);
		if (account == null) {
			map.put("status", "y");
			map.put("info", "该帐号未被使用！");
			return map;
		}
		map.put("status", "n");
		map.put("info", "该帐号已存在！");
		return map;

	}

	public RedisTopics getTopics() {
		return topics;
	}

	public void setTopics(RedisTopics topics) {
		this.topics = topics;
	}
}
