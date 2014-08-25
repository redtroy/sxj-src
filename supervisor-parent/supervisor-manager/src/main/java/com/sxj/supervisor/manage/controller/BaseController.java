package com.sxj.supervisor.manage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {

	public static final String LOGIN = "manage/login";

	public static final String INDEX = "manage/index";

	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));

	}

	protected String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
	}

	// @RequestMapping("login")
	// public String ToLogin(String error, ModelMap model)
	// throws UnsupportedEncodingException {
	// if (StringUtils.isNotEmpty(error)) {
	// if ("no_validate".equals(error)) {
	// error = "请输入验证码";
	// } else if ("validate_error".equals(error)) {
	// error = "验证码错误，请重新输入";
	// }
	// }
	// model.addAttribute("error", error);
	// return LOGIN;
	// }

}
