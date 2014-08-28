package com.sxj.supervisor.manage.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Autowired
	IMemberService memberService;

	/**
	 * 会员管理列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("memberList")
	public String memberList(MemberQuery query, ModelMap map) {
		if (query != null) {
			query.setPagable(true);
		}
		MemberTypeEnum[] types = MemberTypeEnum.values();
		MemberCheckStateEnum[] checkStates = MemberCheckStateEnum.values();
		MemberStatesEnum[] states = MemberStatesEnum.values();
		query.setArea(null);
		List<MemberEntity> list = memberService.queryMembers(query);
		map.put("types", types);
		map.put("checkStates", checkStates);
		map.put("states", states);
		map.put("memberList", list);
		map.put("query", query);
		return "manage/member/member";
	}

	/**
	 * 初始化密码
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("initializePwd")
	public @ResponseBody Map<String, Object> initializePwd(String id) {
		String password = memberService.initializePwd(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isOK", "ok");
		map.put("password", password);
		return map;
	}

	/**
	 * 修改会员资料
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("editMember")
	public @ResponseBody Map<String, String> editMember(MemberEntity member) {
		memberService.modifyMember(member);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOK", "ok");
		return map;
	}

	/**
	 * 修改账户状态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("editState")
	public @ResponseBody Map<String, String> editState(String id, Integer state)
			throws WebException {
		try {
			memberService.editState(id, state);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}

	}

	/**
	 * 修改审核状态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("editCheckState")
	public @ResponseBody Map<String, String> editCheckState(String id,
			Integer state) throws WebException {
		try {
			memberService.editCheckState(id, state);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}

	}
}
