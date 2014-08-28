package com.sxj.supervisor.website.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Autowired
	IMemberService memberService;

	@RequestMapping("/memberInfo")
	public String memberList(ModelMap map) {
		String memberNo = "0000001";
		MemberEntity member = memberService.memberInfo(memberNo);
		map.put("member", member);
		return "site/member/member-profile";
	}
}
