package com.sxj.supervisor.manage.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.util.common.StringUtils;

@Controller
public class BaseController {

	public static final String LOGIN = "page/login";

	public static final String INDEX = "page/index";

	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));

	}

	@RequestMapping("login")
	public String ToLogin(String error, ModelMap model)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(error)) {
			if ("no_validate".equals(error)) {
				error = "请输入验证码";
			} else if ("validate_error".equals(error)) {
				error = "验证码错误，请重新输入";
			}
		}
		model.addAttribute("error", error);

		return LOGIN;
	}

	@RequestMapping("index")
	public String ToIndex() {
		return INDEX;
	}

	@RequestMapping("header")
	public String ToHeader() {
		return "page/main/header1";
	}

	@RequestMapping("content")
	public String ToContent() {
		return "page/main/content1";
	}

	@RequestMapping("footer")
	public String ToFooter() {
		return "page/main/footer1";
	}

	@RequestMapping("middel")
	public String ToMiddel() {
		return "page/main/middel1";
	}

	@RequestMapping("default")
	public String ToDefault() {
		return "page/default";
	}

	@RequestMapping("accessdenied")
	public String ToAccessdenied() {
		return "page/accessdenied";
	}

}
