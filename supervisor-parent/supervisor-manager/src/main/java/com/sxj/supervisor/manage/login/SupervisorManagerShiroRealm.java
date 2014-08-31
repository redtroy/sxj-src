package com.sxj.supervisor.manage.login;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.util.common.StringUtils;

public class SupervisorManagerShiroRealm extends AuthorizingRealm
{
    
    // 用于获取用户信息及用户权限信息的业务接口
    @Autowired
    private ISystemAccountService accountService;
    
    @Autowired
    private IRoleService roleService;
    
    public static final String HASH_ALGORITHM = "MD5";
    
    public static final int HASH_INTERATIONS = 1;
    
    private static final int SALT_SIZE = 8;
    
    public SupervisorManagerShiroRealm()
    {
        super.setAuthenticationCachingEnabled(false);
    }
    
    // 获取授权信息
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals)
    {
        
        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated())
        {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }
        
        SystemAccountEntity current = (SystemAccountEntity) principals.getPrimaryPrincipal();
        
        String username = current.getAccount();
        
        if (username != null)
        {
            List<FunctionEntity> functionList = roleService.getAllRoleFunction(username);
            List<String> permissions = new ArrayList<String>();
            if (functionList != null && functionList.size() > 0)
            {
                for (FunctionEntity functionEntity : functionList)
                {
                    if (functionEntity == null)
                    {
                        continue;
                    }
                    if (StringUtils.isEmpty(functionEntity.getUrl()))
                    {
                        continue;
                    }
                    permissions.add(functionEntity.getUrl());
                }
            }
            if (permissions.size() > 0)
            {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                info.addStringPermissions(permissions);
                return info;
            }
        }
        
        return null;
    }
    
    // 获取认证信息
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 通过表单接收的用户名
        String username = token.getUsername();
        //
        if (username != null && !"".equals(username))
        {
            SystemAccountEntity account = accountService.getAccountByAccount(username);
            if (account != null)
            {
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
    public void initCredentialsMatcher()
    {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
                HASH_ALGORITHM);
        matcher.setHashIterations(HASH_INTERATIONS);
        
        setCredentialsMatcher(matcher);
    }
    
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection arg0)
    {
        super.clearCachedAuthorizationInfo(arg0);
    }
}
