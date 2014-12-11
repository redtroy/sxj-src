package com.sxj.finance.website.login;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.sxj.finance.model.finance.FinancePrincipal;

public class FinanceSiteToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7748781410357940194L;

	private FinancePrincipal userBean;

	public FinanceSiteToken() {

	}

	public FinanceSiteToken(final FinancePrincipal userBean,
			final String password, final boolean rememberMe, final String host) {
		this.userBean = userBean;
		super.setPassword(password.toCharArray());
		super.setRememberMe(rememberMe);
		super.setHost(host);
		if (this.userBean != null) {
			if (this.userBean.getMember() != null
					&& this.userBean.getAccount() != null) {
				super.setUsername(userBean.getAccount().getAccountNo());
			} else if (this.userBean.getMember() != null
					&& this.userBean.getAccount() == null) {
				super.setUsername(userBean.getMember().getMemberNo());
			}
		}
	}

	public FinanceSiteToken(final FinancePrincipal userBean,
			final String password) {
		this(userBean, password, false, null);
	}

	public FinancePrincipal getUserBean() {
		return userBean;
	}

	public void setUserBean(FinancePrincipal userBean) {
		this.userBean = userBean;
	}

	@Override
	public Object getPrincipal() {
		return getUserBean();
	}

	@Override
	public Object getCredentials() {
		return getPassword();
	}

}
