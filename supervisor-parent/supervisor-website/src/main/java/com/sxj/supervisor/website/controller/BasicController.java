package com.sxj.supervisor.website.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.website.login.SupervisorPrincipal;
import com.sxj.supervisor.website.login.SupervisorSiteToken;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

@Controller
public class BasicController extends BaseController {

	@Autowired
	private IMemberFunctionService functionService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IAccountService accountService;

	@RequestMapping("index")
	public String ToIndex(HttpServletRequest request) {
		return "redirect:" + getBasePath(request) + "member/memberInfo.htm";
	}

	@RequestMapping("to_login")
	public String ToLogin() {
		return LOGIN;
	}

	@RequestMapping("login")
	public String login(String memberName, String accountNo, String password,
			HttpSession session, HttpServletRequest request, ModelMap map) {
		map.put("account", accountNo);
		map.put("memberName", memberName);
		SupervisorSiteToken token = null;
		SupervisorPrincipal userBean = null;
		if (StringUtils.isNotEmpty(memberName)
				&& StringUtils.isNotEmpty(accountNo)) {
			AccountEntity account = accountService.getAccountByNo(accountNo);
			if (account == null) {
				map.put("message", "会员子账户不存在");
				return LOGIN;
			}
			String memberNo = account.getParentId();
			MemberEntity member = memberService.memberInfo(memberNo);
			if (member == null) {
				map.put("message", "会员不存在");
				return LOGIN;
			}
			if (!member.getName().equals(memberName)) {
				map.put("message", "会员名错误");
				return LOGIN;
			}
			userBean = new SupervisorPrincipal();
			userBean.setAccount(account);
			userBean.setMember(member);
			token = new SupervisorSiteToken(userBean, password);
		} else if (StringUtils.isNotEmpty(memberName)
				&& StringUtils.isEmpty(accountNo)) {
			MemberEntity member = memberService.getMemberByName(memberName);
			if (member == null) {
				map.put("message", "会员不存在");
				return LOGIN;
			}
			userBean = new SupervisorPrincipal();
			userBean.setMember(member);
			token = new SupervisorSiteToken(userBean, password);
		}
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
		} catch (AuthenticationException e) {
			SxjLogger.error("登陆失败", e, this.getClass());
			map.put("message", "密码错误");
			return LOGIN;

		}
		if (currentUser.isAuthenticated()) {
			session.setAttribute("userinfo", userBean);
			return "redirect:" + getBasePath(request) + "member/memberInfo.htm";
		} else {
			map.put("message", "登陆失败");
			return LOGIN;
		}
	}

	/**
	 * 左侧菜单
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("menu")
	public String ToMenu(ModelMap map) {
		// Subject user = SecurityUtils.getSubject();
		// SystemAccountEntity userName = (SystemAccountEntity) user
		// .getPrincipal();
		// if (userName == null) {
		// return "403";
		// }
		// List<MemberFunctionEntity> list = roleService
		// .getRoleFunction(userName.getId());
		List<MemberFunctionModel> list = functionService.queryFunctions();
		map.put("list", list);
		return "site/menu";
	}

}
