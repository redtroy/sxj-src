package com.sxj.finance.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.finance.service.member.IMemberService;
import com.sxj.finance.website.login.SupervisorPrincipal;
import com.sxj.finance.website.login.SupervisorSiteToken;
import com.sxj.util.LoginToken;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

@Controller
public class BasicController extends BaseController {

	@Autowired
	private IMemberService memberService;

	@RequestMapping("index")
	public String ToIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userinfo") == null) {
			return LOGIN;
		} else {
			SupervisorPrincipal info = getLoginInfo(session);
			if (info.getMember() != null) {
				String function = request.getParameter("function");
				if (StringUtils.isNotEmpty(function)) {
					return "redirect:" + getBasePath(request)
							+ "member/memberInfo.htm?function=" + function;
				} else {
					return "redirect:" + getBasePath(request)
							+ "member/memberInfo.htm";
				}

			} else {
				return LOGIN;
			}

		}

	}

	@RequestMapping("to_login")
	public String ToLogin() {
		return LOGIN;
	}

	@RequestMapping("/autoLogin")
	public String autoLogin(String member, String token, HttpSession session,
			HttpServletRequest request, ModelMap map) {
		try {
			String retUrl = request.getHeader("Referer");
			MemberEntity memberInfo = memberService.memberInfo(member);
			if (memberInfo == null) {
				return LOGIN;
			}
			LoginToken newToken = new LoginToken();
			newToken.setMemberNo(memberInfo.getMemberNo());
			newToken.setMemberName(memberInfo.getName());
			newToken.setPassword(memberInfo.getPassword());
			String tokenMd5 = EncryptUtil.md5Hex(newToken.toString());
			if (!tokenMd5.equals(token)) {
				return LOGIN;
			}
			SupervisorPrincipal userBean = new SupervisorPrincipal();
			userBean.setMember(memberInfo);
			SupervisorSiteToken siteToken = new SupervisorSiteToken(userBean,
					memberInfo.getPassword());
			Subject currentUser = SecurityUtils.getSubject();
			try {
				currentUser.login(siteToken);
			} catch (AuthenticationException e) {
				SxjLogger.error("登陆失败", e, this.getClass());
				map.put("pmessage", "密码错误");
				return LOGIN;

			}
			if (currentUser.isAuthenticated()) {
				session.setAttribute("userinfo", userBean);
				return "redirect:" + getBasePath(request) + "index.htm";
			} else {
				map.put("message", "登陆失败");
				return LOGIN;
			}
		} catch (Exception e) {
			map.put("message", "登陆失败");
			return LOGIN;
		}
	}

	@RequestMapping("/login")
	public String login(String id, HttpServletRequest request) {
		try {
			String retUrl = request.getHeader("Referer");
			LoginToken token = (LoginToken) HierarchicalCacheManager.get(2,
					"login_cache_token", id);

			retUrl.toCharArray();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
