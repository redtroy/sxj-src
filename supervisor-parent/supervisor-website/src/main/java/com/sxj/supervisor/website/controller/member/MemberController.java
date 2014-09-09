package com.sxj.supervisor.website.controller.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.supervisor.website.login.SupervisorPrincipal;
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
	 * 根据会员号获取会员信息
	 * 
	 * @param map
	 * @return
	 * @throws WebException 
	 */
	@RequestMapping("/memberInfo")
	public String memberList(ModelMap map, HttpSession session) throws WebException {
		try {
			SupervisorPrincipal info = getLoginInfo(session);
			if (info != null) {
				MemberEntity member = memberService.getMember(info.getMember()
						.getId());
				map.put("member", member);
				return "site/member/member-profile";
			}
			return LOGIN;
		} catch (Exception e) {
			SxjLogger.error("修改会员信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}

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
		List<AreaEntity> cityList = areaService.getChildrenAreas("32");
		map.put("cityList", cityList);
		map.put("member", member);
		return "site/member/edit-member";
	}

	@RequestMapping("save_member")
	public @ResponseBody Map<String, Object> save_member(MemberEntity member)
			throws WebException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			memberService.modifyMember(member);
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("修改会员信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}

	}

	/**
	 * 注册账户
	 */
	@RequestMapping("/regist")
	public String regist(ModelMap map) {
		MemberTypeEnum[] type = MemberTypeEnum.values();
		List<AreaEntity> list = areaService.getChildrenAreas("32");
		map.put("type", type);
		map.put("list", list);
		return "site/register";
	}

	/**
	 * 添加会员
	 */
	@RequestMapping("regist_save")
	public String regist_save(MemberEntity member, ModelMap map, String ms) {
		String message = (String) HierarchicalCacheManager.get(2, "checkMs",
				"checkMs");
		if (message.equals(ms)) {
			member.setState(MemberStatesEnum.stop);
			member.setCheckState(MemberCheckStateEnum.unaudited);
			member.setRegDate(new Date());
			memberService.addMember(member);
			return LOGIN;
		} else {
			MemberTypeEnum[] type = MemberTypeEnum.values();
			List<AreaEntity> list = areaService.getChildrenAreas("32");
			map.put("type", type);
			map.put("list", list);
			map.put("member", member);
			return "site/register";
		}
	}

	/**
	 * 验证会员是否注册过
	 */
	@RequestMapping("check_member")
	public @ResponseBody Map<String, String> check_member(String name) {
		Map<String, String> map = new HashMap<String, String>();
		MemberEntity member = memberService.getMemberByName(name);
		if (member == null) {
			return null;
		} else {
			map.put("text", "该会员已存在！");
			return map;
		}
	}

	/**
	 * 发送验证码
	 */
	@RequestMapping("send_ms")
	public @ResponseBody Map<String, String> send_ms(String phoneNo) {
		String message = "123456";
		message = memberService.createvalidata(phoneNo, message);
		Map<String, String> map = new HashMap<String, String>();
		HierarchicalCacheManager.set(2, "checkMs", "checkMs", message);
		return map;
	}
}
