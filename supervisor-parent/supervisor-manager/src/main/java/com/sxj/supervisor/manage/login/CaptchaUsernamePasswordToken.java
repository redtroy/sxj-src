package com.sxj.supervisor.manage.login;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.context.WebApplicationContext;

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2569577809128055349L;

	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		
		this.captcha = captcha;
	}

}
