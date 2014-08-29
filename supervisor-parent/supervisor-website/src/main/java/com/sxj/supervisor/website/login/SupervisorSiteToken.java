package com.sxj.supervisor.website.login;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class SupervisorSiteToken implements HostAuthenticationToken,
		RememberMeAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7748781410357940194L;

	private SupervisorPrincipal userBean;

	private String password;

	private boolean rememberMe = false;

	private String host;

	public SupervisorSiteToken() {

	}

	public SupervisorSiteToken(final SupervisorPrincipal userBean,
			final String password, final boolean rememberMe, final String host) {
		this.userBean = userBean;
		this.password = password;
		this.rememberMe = rememberMe;
		this.host = host;
	}

	public SupervisorPrincipal getUserBean() {
		return userBean;
	}

	public void setUserBean(SupervisorPrincipal userBean) {
		this.userBean = userBean;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public Object getPrincipal() {
		return getUserBean();
	}

	@Override
	public Object getCredentials() {
		return getPassword();
	}

	@Override
	public boolean isRememberMe() {
		return rememberMe;
	}

	@Override
	public String getHost() {
		return host;
	}

}
