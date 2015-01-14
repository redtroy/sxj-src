package com.sxj.supervisor.service.impl.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sxj.supervisor.dao.member.IMemberRoleDao;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.entity.member.MemberRoleEntity;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
public class MemberRoleServiceImpl implements IMemberRoleService
{
    
    @Autowired
    private IMemberRoleDao roleDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<MemberFunctionEntity> getAllRoleFunction(String accountId)
            throws ServiceException
    {
        try
        {
            Assert.hasText(accountId);
            List<MemberFunctionEntity> list = roleDao.getAllRoleFunction(accountId);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员账户所有权限列表错误", e, this.getClass());
            throw new ServiceException("查询会员账户所有权限列表错误");
        }
    }
    
    @Override
    @Transactional
    public List<MemberFunctionModel> getRoleFunctions(String accountId)
            throws ServiceException
    {
        try
        {
            Assert.hasText(accountId);
            QueryCondition<MemberFunctionEntity> query = new QueryCondition<MemberFunctionEntity>();
            query.addCondition("parentId", "0");
            query.addCondition("accountId", accountId);
            List<MemberFunctionEntity> functionList = roleDao.getRoleFunctions(query);
            List<MemberFunctionModel> list = new ArrayList<MemberFunctionModel>();
            for (MemberFunctionEntity functionEntity : functionList)
            {
                if (functionEntity == null)
                {
                    continue;
                }
                QueryCondition<MemberFunctionEntity> childrenQuery = new QueryCondition<MemberFunctionEntity>();
                childrenQuery.addCondition("parentId", functionEntity.getId());
                childrenQuery.addCondition("accountId", accountId);
                List<MemberFunctionEntity> childrenList = roleDao.getRoleFunctions(childrenQuery);
                MemberFunctionModel model = new MemberFunctionModel();
                model.setFunction(functionEntity);
                model.setChildren(childrenList);
                list.add(model);
            }
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询权限菜单错误", e);
        }
    }
    
    @Override
    @Transactional
    public void addRoles(List<MemberRoleEntity> roles) throws ServiceException
    {
        try
        {
            Assert.notEmpty(roles);
            int size = roles.size();
            MemberRoleEntity[] roleArr = (MemberRoleEntity[]) roles.toArray(new MemberRoleEntity[size]);
            roleDao.addRoles(roleArr);
        }
        catch (Exception e)
        {
            SxjLogger.error("新增会员子账户权限错误", e, this.getClass());
            throw new ServiceException("新增会员子账户权限错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void removeRoles(String accountId) throws ServiceException
    {
        try
        {
            Assert.hasText(accountId);
            roleDao.deleteRolesByAccount(accountId);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除会员子账户权限错误", e, this.getClass());
            throw new ServiceException("删除会员子账户权限错误", e);
        }
        
    }
}
