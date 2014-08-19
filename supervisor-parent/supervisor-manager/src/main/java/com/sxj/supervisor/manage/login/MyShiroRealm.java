package com.sxj.supervisor.manage.login;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.member.IMemberService;

public class MyShiroRealm extends AuthorizingRealm {

	// 用于获取用户信息及用户权限信息的业务接口
	private IMemberService memberService;

	// 获取授权信息
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String) principals.fromRealm(getName()).iterator()
				.next();

//		if (username != null) {
//			// 查询用户授权信息
//			Collection<String> pers = businessManager
//					.queryPermissions(username);
//			if (pers != null && !pers.isEmpty()) {
//				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//				for (String each : pers)
//					info.addStringPermissions(each);
//
//				return info;
//			}
//		}

		return null;
	}

	// 获取认证信息
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 通过表单接收的用户名
		String username = token.getUsername();
//
//		if (username != null && !"".equals(username)) {
//			MemberQuery query=new MemberQuery();
//			
//			query.setMemberNo(username);
//			memberService.queryMembers(query);
//			LoginAccount account = businessManager.get(username);
//
//			if (account != null) {
//				return new SimpleAuthenticationInfo(account.getLoginName(),
//						account.getPassword(), getName());
//			}
//		}

		return null;
	}
}
