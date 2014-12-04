package com.sxj.finance.website.controller.member;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.finance.service.member.IMemberService;
import com.sxj.finance.website.controller.BaseController;
import com.sxj.finance.website.login.SupervisorPrincipal;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@RequestMapping("/member")
@Controller
public class MemberController extends BaseController {
	
	@Autowired
	private IMemberService memberService;
	
	/**
	 * 根据会员号获取会员信息
	 * 
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/info")
	public String memberList(ModelMap map, HttpSession session,
			HttpServletRequest request) throws WebException {
		try {
			
			SupervisorPrincipal info = getLoginInfo(session);
			if (info != null) {
				MemberEntity member = memberService.getMember(info.getMember()
						.getId());
				if (member.getAccountNum() == null) {
					member.setAccountNum(0);
				}
				map.put("member", member);
				return "site/member/member";
			}
			return LOGIN;
		} catch (Exception e) {
			SxjLogger.error("获取会员信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}

	}
}
