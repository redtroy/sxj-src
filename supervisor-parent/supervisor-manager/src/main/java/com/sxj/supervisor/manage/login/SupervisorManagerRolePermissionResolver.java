package com.sxj.supervisor.manage.login;

import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;

public class SupervisorManagerRolePermissionResolver implements
        RolePermissionResolver
{
    
    @Override
    public Collection<Permission> resolvePermissionsInRole(String arg0)
    {
        // TODO Auto-generated method stub
        System.out.println(arg0);
        return null;
    }
    
}
