package com.sxj.finance.manage.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.finance.entity.system.SystemAccountEntity;
import com.sxj.finance.service.system.ISystemAccountService;

@Controller
public class BasicController extends BaseController {

	@Autowired
	private ISystemAccountService accountService;

	@RequestMapping("to_login")
	public String ToLogin() {
		return LOGIN;
	}

	@RequestMapping("index")
	public String ToIndex(HttpServletRequest request) {
		Subject user = SecurityUtils.getSubject();
		SystemAccountEntity userName = (SystemAccountEntity) user
				.getPrincipal();
		if (userName == null) {
			return LOGIN;
		} else {
			SystemAccountEntity newAccount = accountService
					.getAccountByAccount(userName.getAccount());
			if (newAccount == null) {
				return LOGIN;
			}
			return "redirect:" + getBasePath(request)
					+ "member/memberList.htm?function=1";
		}
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:" + getBasePath(request) + "to_login.htm";
	}

	@RequestMapping("login")
	public String login(String account, String password, HttpSession session,
			HttpServletRequest request, ModelMap map) {
		SystemAccountEntity user = accountService.getAccountByAccount(account);
		if (user == null) {
			map.put("amessage", "用户名不存在");
			return LOGIN;
		}
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(account,
				password);
		try {
			currentUser.login(token);
		} catch (AuthenticationException e) {
			map.put("account", account);
			map.put("pmessage", "密码错误");
			return LOGIN;

		}
		if (currentUser.isAuthenticated()) {
			session.setAttribute("userinfo", user);
			user.setLastLogin(new Date());
			accountService.updateLoginTime(user.getId());
			return "redirect:" + getBasePath(request) + "index.htm";
		} else {
			map.put("account", account);
			map.put("pmessage", "登陆失败");
			return LOGIN;
		}
	}
}
