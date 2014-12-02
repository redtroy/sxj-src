package com.sxj.finance.website.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.util.LoginToken;

@Controller
public class BasicController extends BaseController {

	@RequestMapping("to_login")
	public String ToLogin() {
		return LOGIN;
	}

	@RequestMapping("/autoLogin")
	public String autoLogin(String member, String token,
			HttpServletRequest request) {
		try {
			String retUrl = request.getHeader("Referer");
			retUrl.toCharArray();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
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
