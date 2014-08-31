package com.sxj.supervisor.website.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.website.login.SupervisorPrincipal;

public class BaseController {

	public static final String LOGIN = "site/login";

	public static final String INDEX = "site/index";

	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
		binder.registerCustomEditor(MemberTypeEnum.class,
				new EnumPropertyEditorSupport<MemberTypeEnum>(
						MemberTypeEnum.class));
		binder.registerCustomEditor(RecordTypeEnum.class,
				new EnumPropertyEditorSupport<RecordTypeEnum>(
						RecordTypeEnum.class));
		binder.registerCustomEditor(ContractTypeEnum.class,
				new EnumPropertyEditorSupport<ContractTypeEnum>(
						ContractTypeEnum.class));
		binder.registerCustomEditor(MemberTypeEnum.class,
				new EnumPropertyEditorSupport<MemberTypeEnum>(
						MemberTypeEnum.class));
	}

	protected String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
	}

	protected SupervisorPrincipal getLoginInfo(HttpSession session) {
		Object object = session.getAttribute("userinfo");
		if (object != null) {
			SupervisorPrincipal userBean = (SupervisorPrincipal) object;
			return userBean;
		}
		return null;

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
