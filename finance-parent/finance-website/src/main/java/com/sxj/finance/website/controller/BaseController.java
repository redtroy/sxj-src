package com.sxj.finance.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sxj.finance.website.login.SupervisorPrincipal;

public class BaseController {

	public static final String LOGIN = "site/login";

	public static final String INDEX = "site/index";

	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	protected String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
	}

	protected SupervisorPrincipal getLoginInfo(HttpSession session) {
		Object object = session.getAttribute("userinfo");
		if (object != null) {
			SupervisorPrincipal userBean = (SupervisorPrincipal) object;
			return userBean;
		} else {
			return null;
		}

	}

}
