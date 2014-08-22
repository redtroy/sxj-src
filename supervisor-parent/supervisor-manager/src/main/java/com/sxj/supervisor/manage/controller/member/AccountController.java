package com.sxj.supervisor.manage.controller.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
