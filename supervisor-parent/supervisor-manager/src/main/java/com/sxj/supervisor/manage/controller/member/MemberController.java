package com.sxj.supervisor.manage.controller.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberCheckStateEnum;
import com.sxj.supervisor.entity.member.MemberTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.member.IMemberService;
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
    
	@Autowired
	IMemberService memberService;
	/**
	 * 会员管理
	 * @param map
	 * @return
	 */
	@RequestMapping("memberList")
	public String memberList(MemberQuery query,ModelMap map) {
		MemberTypeEnum[] types = MemberTypeEnum.values();
		MemberCheckStateEnum[] state = MemberCheckStateEnum.values();
		List<MemberEntity> list = memberService.queryMembers(query);
		map.put("types", types);
		map.put("states", state);
		map.put("memberList", list);
		return "manage/member/member";
	}
}
