package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.entity.member.MemberRoleEntity;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.util.exception.ServiceException;

public interface IMemberRoleService
{
    
    public void addRoles(List<MemberRoleEntity> roles) throws ServiceException;
    
    public void removeRoles(String accountId) throws ServiceException;
    
    public List<MemberFunctionEntity> getAllRoleFunction(String accountId)
            throws ServiceException;
    
    public List<MemberFunctionModel> getRoleFunctions(String accountId)
            throws ServiceException;
}
