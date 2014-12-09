package com.sxj.finance.manage.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	public static final String LOGIN = "manage/login";

	protected String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
	}

}
