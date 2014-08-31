package com.sxj.supervisor.website.controller.member;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.supervisor.website.login.SupervisorPrincipal;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Autowired
	IMemberService memberService;

	/**
	 * 根据会员号获取会员信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/memberInfo")
	public String memberList(ModelMap map, HttpSession session) {
		SupervisorPrincipal info = getLoginInfo(session);
		if (info != null) {
			MemberEntity member = info.getMember();
			map.put("member", member);
			return "site/member/member-profile";
		}
		return LOGIN;

	}

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping("/edit_pwd")
	public @ResponseBody Map<String, String> edit_pwd(String id, String password) {
		Map<String, String> map = new HashMap<String, String>();
		memberService.edit_pwd(id, password);
		return map;
	}

	/**
	 * 修改会员信息界面
	 * 
	 * @return
	 */
	@RequestMapping("edit_member")
	public String edit_member(String id, ModelMap map) {
		MemberEntity member = memberService.getMember(id);
		map.put("member", member);
		return "site/member/edit-member";
	}

	@RequestMapping("save_member")
	public @ResponseBody Map<String, String> save_member(MemberEntity member) {
		memberService.modifyMember(member);
		return null;
	}

}
