package com.sxj.supervisor.manage.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.supervisor.validator.hibernate.UpdateGroup;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IAreaService areaService;

	/**
	 * 会员管理列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("memberList")
	public String memberList(MemberQuery query, ModelMap map)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			if (StringUtils.isNotEmpty(query.getArea())) {
				String areaId = query.getArea();
				areaId = "32:江苏省," + areaId;
				query.setArea(areaId);
			}
			MemberTypeEnum[] types = MemberTypeEnum.values();
			MemberCheckStateEnum[] checkStates = MemberCheckStateEnum.values();
			MemberStatesEnum[] states = MemberStatesEnum.values();
			List<AreaEntity> cityList = areaService.getChildrenAreas("32");
			List<MemberEntity> list = memberService.queryMembers(query);
			map.put("types", types);
			map.put("checkStates", checkStates);
			map.put("states", states);
			map.put("memberList", list);
			map.put("cityList", cityList);
			map.put("query", query);
			return "manage/member/member";
		} catch (Exception e) {
			SxjLogger.error("查询会员信息错误", e, this.getClass());
			throw new WebException("查询会员信息错误");
		}

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
	 * @throws WebException
	 */
	@RequestMapping("editMember")
	public @ResponseBody Map<String, String> editMember(
			@Validated({ UpdateGroup.class }) MemberEntity member,
			BindingResult result) throws WebException {
		try {
			getValidError(result);
			memberService.modifyMember(member);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("修改会员信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}

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

	/**
	 * 验证会员是否注册过
	 */
	@RequestMapping("check_member")
	public @ResponseBody Map<String, String> check_member(String name, String id) {
		Map<String, String> map = new HashMap<String, String>();
		MemberEntity member = memberService.getMemberByName(name);
		if (member == null || member.getId().equals(id)) {
			return null;
		} else {
			map.put("text", "该会员已存在！");
			return map;
		}
	}
}
