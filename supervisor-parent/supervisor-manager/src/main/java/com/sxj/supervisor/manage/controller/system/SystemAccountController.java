package com.sxj.supervisor.manage.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.function.FunctionModel;
import com.sxj.supervisor.model.system.SysAccountQuery;
import com.sxj.supervisor.service.system.IFunctionService;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
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

	@RequestMapping("account-list")
	public String getSysAccountList(SysAccountQuery query, ModelMap map) {
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
	public String getgetSysAccount(String accountId, ModelMap map) {
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

	@RequestMapping("edit_account")
	public @ResponseBody Map<String, Boolean> editAccount(
			SystemAccountEntity account,
			@RequestParam("functionIds") String[] functionIds) {
		accountService.modifyAccount(account, functionIds);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("isOK", true);
		return map;
	}

	@RequestMapping("get_role_function")
	public String getRloeFunctions(String accountId, String type, ModelMap map) {
		if ("get".equals(type)) {
			List<FunctionModel> list = roleService.getRoleFunction(accountId);
			map.put("list", list);
			return "manage/system/role_function";
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
}
