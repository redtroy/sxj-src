package com.sxj.finance.manage.controller.member;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.finance.enu.member.MemberCheckStateEnum;
import com.sxj.finance.enu.member.MemberStatesEnum;
import com.sxj.finance.enu.member.MemberTypeEnum;
import com.sxj.finance.manage.controller.BaseController;
import com.sxj.finance.model.member.MemberQuery;
import com.sxj.finance.service.member.IMemberService;
import com.sxj.redis.advance.topic.RedisTopics;
import com.sxj.util.Constraints;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@RequestMapping("/member")
@Controller
public class MemberController extends BaseController {
	
	@Autowired
	private IMemberService memberService;
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
			List<MemberEntity> list = memberService.queryMembers(query);
			map.put("memberList", list);
			map.put("query", query);
			return "manage/member/memberList";
		} catch (Exception e) {
			SxjLogger.error("查询会员信息错误", e, this.getClass());
			throw new WebException("查询会员信息错误");
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
			SxjLogger.error("审核会员信息错误", e, this.getClass());
			throw new WebException("审核会员信息错误");
		}

	}
	/**
	 * 修改审核状态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("memberInfo")
	public String getMemberInfo(String id,ModelMap map) throws WebException {
		try {
			MemberEntity member = memberService.getMember(id);
			map.put("member", member);
			map.put("id", id);
			return "manage/member/memberInfo";
		} catch (Exception e) {
			SxjLogger.error("审核会员信息错误", e, this.getClass());
			throw new WebException("审核会员信息错误");
		}

	}
}
