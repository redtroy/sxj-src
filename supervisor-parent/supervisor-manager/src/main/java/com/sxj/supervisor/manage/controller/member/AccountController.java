package com.sxj.supervisor.manage.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.AccountStatesEnum;
import com.sxj.supervisor.entity.member.MemberAuthorityEnum;
import com.sxj.supervisor.entity.member.MemberCheckStateEnum;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberStatesEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.supervisor.service.member.IAccountService;

@Controller
@RequestMapping("/member/account")
public class AccountController extends BaseController {
	@Autowired
	IAccountService accountService;
	
	@RequestMapping("accountList")
	public String memberList(AccountQuery query,ModelMap map) {
		MemberAuthorityEnum[] mas=MemberAuthorityEnum.values();
		AccountStatesEnum[] states = AccountStatesEnum.values();
		List<AccountEntity> list = accountService.queryAccounts(query);
		map.put("mas", mas);
		map.put("states", states);
		map.put("list", list);
		return "manage/member/account";
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
	public @ResponseBody Map<String, String> editAccount(AccountEntity account) {
		System.out.println("test");
		accountService.modifyAccount(account);;
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOK", "ok");
		return map;
	}
	
	/**
	 * 修改子会员状态
	 * @param id
	 * @return
	 */
	@RequestMapping("editState")
	public @ResponseBody Map<String, String> editState(String id) {
		String state=accountService.editState(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOK", "ok");
		map.put("state", state);
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
		return map;
	}
}
