package com.sxj.supervisor.rfid.login;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.supervisor.service.member.IMemberService;

public class SupervisorSiteShiroRealm extends AuthenticatingRealm {

	// 用于获取用户信息及用户权限信息的业务接口
	@Autowired
	private IAccountService accountService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IMemberRoleService roleService;

	@Autowired
	private IMemberFunctionService functionService;

	public static final String HASH_ALGORITHM = "MD5";

	public static final int HASH_INTERATIONS = 1;

	private static final int SALT_SIZE = 8;

	public SupervisorSiteShiroRealm() {
		// 认证
		super.setAuthenticationCachingEnabled(false);
		// 授权
		// super.setAuthorizationCacheName(GlobalStatic.authorizationCacheName);
	}

	// 获取认证信息
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		SupervisorSiteToken token = (SupervisorSiteToken) authcToken;
		// 通过表单接收的用户
		SupervisorPrincipal userBean = token.getUserBean();
		if (userBean == null) {
			return null;
		}
		if (userBean.getMember() == null && userBean.getAccount() != null) {
			return null;
		}
		if (userBean.getMember() != null && userBean.getAccount() == null) {
			MemberEntity member = userBean.getMember();
			if (member != null) {
				return new SimpleAuthenticationInfo(member,
						member.getPassword(), getName());
			}
		} else if (userBean.getMember() != null
				&& userBean.getAccount() != null) {
			AccountEntity account = userBean.getAccount();
			if (account != null) {
				return new SimpleAuthenticationInfo(account,
						account.getPassword(), getName());
			}
		}
		return null;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

}
