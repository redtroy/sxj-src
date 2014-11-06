package com.sxj.supervisor.website.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.spring.modules.util.Reflections;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

public class SupervisorSiteShiroRealm extends AuthorizingRealm {

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

	// 获取授权信息
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principals);
			SecurityUtils.getSubject().logout();
			return null;
		}
		Object current = principals.getPrimaryPrincipal();
		if (current == null) {
			return null;
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> permissions = new ArrayList<String>();
		if (current instanceof MemberEntity) {
			List<MemberFunctionEntity> functionList = functionService
					.queryChildrenFunctions(null, null);
			if (functionList != null && functionList.size() > 0) {
				for (MemberFunctionEntity functionEntity : functionList) {
					if (functionEntity == null) {
						continue;
					}
					if (StringUtils.isEmpty(functionEntity.getUrl())) {
						continue;
					}
					permissions.add(functionEntity.getUrl());
				}
			}
			info.addRole(((MemberEntity) current).getMemberNo());
		} else if (current instanceof AccountEntity) {
			AccountEntity account = (AccountEntity) current;
			List<MemberFunctionEntity> functionList = roleService
					.getAllRoleFunction(account.getId());
			if (functionList != null && functionList.size() > 0) {
				for (MemberFunctionEntity functionEntity : functionList) {
					if (functionEntity == null) {
						continue;
					}
					if (StringUtils.isEmpty(functionEntity.getUrl())) {
						continue;
					}
					permissions.add(functionEntity.getUrl());
				}
			}
			info.addRole(account.getId());
		}
		if (permissions.size() > 0) {
			info.addStringPermissions(permissions);
		}
		return info;
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

	private boolean isPermitted(Permission permission, AuthorizationInfo info) {
		Collection<Permission> perms = getPermissions(info);
		if (perms != null && !perms.isEmpty()) {
			for (Permission perm : perms) {
				if (perm.implies(permission)) {
					return true;
				}
			}
		}
		return false;
	}

	public AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {

		if (principals == null) {
			return null;
		}
		AuthorizationInfo info = null;
		SxjLogger.debug("Retrieving AuthorizationInfo for principals ["
				+ principals + "]", getClass());
		Cache<Object, AuthorizationInfo> cache = getAvailableAuthorizationCache();
		if (cache != null) {
			Object key = getAuthorizationCacheKey(principals);
			info = cache.get(key);
		}
		if (info == null) {
			info = doGetAuthorizationInfo(principals);
			if (info != null && cache != null) {
				Object key = getAuthorizationCacheKey(principals);
				cache.put(key, info);
			}
		}

		return info;
	}

	private Cache<Object, AuthorizationInfo> getAvailableAuthorizationCache() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache == null && isAuthorizationCachingEnabled()) {
			cache = getAuthorizationCacheLazy();
		}
		return cache;
	}

	private Cache<Object, AuthorizationInfo> getAuthorizationCacheLazy() {

		if (getAuthorizationCache() == null) {
			CacheManager cacheManager = getCacheManager();
			if (cacheManager != null) {
				String cacheName = getAuthorizationCacheName();
				Reflections.setFieldValue(this, "authorizationCache",
						cacheManager.getCache(cacheName));
			}
		}

		return getAuthorizationCache();
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals,
			Permission permission) {
		AuthorizationInfo info = getAuthorizationInfo(principals);
		return isPermitted(permission, info);
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		Permission p = getPermissionResolver().resolvePermission(permission);
		return this.isPermitted(principals, p);
	}

	private Collection<Permission> resolveRolePermissions(
			Collection<String> roleNames) {
		Collection<Permission> perms = Collections.emptySet();
		RolePermissionResolver resolver = getRolePermissionResolver();
		if (resolver != null && !CollectionUtils.isEmpty(roleNames)) {
			perms = new LinkedHashSet<Permission>(roleNames.size());
			for (String roleName : roleNames) {
				Collection<Permission> resolved = resolver
						.resolvePermissionsInRole(roleName);
				if (!CollectionUtils.isEmpty(resolved)) {
					perms.addAll(resolved);
				}
			}
		}
		return perms;
	}

	private Collection<Permission> resolvePermissions(
			Collection<String> stringPerms) {
		Collection<Permission> perms = Collections.emptySet();
		PermissionResolver resolver = getPermissionResolver();
		if (resolver != null && !CollectionUtils.isEmpty(stringPerms)) {
			perms = new LinkedHashSet<Permission>(stringPerms.size());
			for (String strPermission : stringPerms) {
				Permission permission = getPermissionResolver()
						.resolvePermission(strPermission);
				perms.add(permission);
			}
		}
		return perms;
	}

	private Collection<Permission> getPermissions(AuthorizationInfo info) {
		Set<Permission> permissions = new HashSet<Permission>();
		if (info != null) {
			Collection<Permission> perms = info.getObjectPermissions();
			if (!CollectionUtils.isEmpty(perms)) {
				permissions.addAll(perms);
			}
			perms = resolvePermissions(info.getStringPermissions());
			if (!CollectionUtils.isEmpty(perms)) {
				permissions.addAll(perms);
			}

			perms = resolveRolePermissions(info.getRoles());
			if (!CollectionUtils.isEmpty(perms)) {
				permissions.addAll(perms);
			}

			// =============================此处检查权限是否变动==============================//
			if (permissions.isEmpty()) {
				if (info.getRoles() != null && info.getRoles().size() > 0) {
					Iterator<String> iterator = info.getRoles().iterator();
					String accountId = iterator.next();
					if (StringUtils.isNotEmpty(accountId)) {
						List<MemberFunctionEntity> functionList = roleService
								.getAllRoleFunction(accountId);
						if (functionList != null && functionList.size() > 0) {
							for (MemberFunctionEntity functionEntity : functionList) {
								if (functionEntity == null) {
									continue;
								}
								if (StringUtils
										.isEmpty(functionEntity.getUrl())) {
									continue;
								}
								Permission p = new WildcardPermission(
										functionEntity.getUrl());
								permissions.add(p);
							}
						}
					}

				}
			}

			// =============================end==========================================//
		}

		if (permissions.isEmpty()) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableSet(permissions);
		}
	}
}
