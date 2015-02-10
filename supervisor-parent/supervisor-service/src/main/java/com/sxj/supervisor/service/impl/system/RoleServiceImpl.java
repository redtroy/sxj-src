package com.sxj.supervisor.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sxj.cache.manager.CacheLevel;
import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.dao.system.IRoleDao;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.RoleEntity;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService
{
    
    @Autowired
    private IRoleDao roleDao;
    
    @Override
    public void addRoles(List<RoleEntity> roles) throws ServiceException
    {
        try
        {
            Assert.notEmpty(roles);
            int size = roles.size();
            RoleEntity[] roleArr = (RoleEntity[]) roles.toArray(new RoleEntity[size]);
            roleDao.addRoles(roleArr);
            HierarchicalCacheManager.evict(CacheLevel.REDIS,
                    "sys_role_menu",
                    roleArr[0].getAccountId());
        }
        catch (Exception e)
        {
            SxjLogger.error("新增系统会员权限失败", e, this.getClass());
            throw new ServiceException("新增系统会员权限失败", e.getMessage());
        }
        
    }
    
    @Override
    public void removeRoles(String accountId) throws ServiceException
    {
        try
        {
            roleDao.deleteRoles(accountId);
            HierarchicalCacheManager.evict(CacheLevel.REDIS,
                    "sys_role_menu",
                    accountId);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除系统会员权限失败", e, this.getClass());
            throw new ServiceException("删除系统会员权限失败", e.getMessage());
        }
        
    }
    
    @Override
    public List<RoleEntity> getRoles(String accountId) throws ServiceException
    {
        try
        {
            return roleDao.getRoles(accountId);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询系统会员权限失败", e, this.getClass());
            throw new ServiceException("查询系统会员权限失败", e.getMessage());
        }
        
    }
    
    @Override
    public List<FunctionModel> getRoleFunction(String accountId)
            throws ServiceException
    {
        try
        {
            Object cache = HierarchicalCacheManager.get(CacheLevel.REDIS,
                    "sys_role_menu",
                    accountId);
            if (cache instanceof List)
            {
                return (List<FunctionModel>) cache;
            }
            QueryCondition<FunctionEntity> query = new QueryCondition<FunctionEntity>();
            query.addCondition("parentId", 0);
            query.addCondition("accountId", accountId);
            List<FunctionEntity> functionList = roleDao.getRoleFunction(query);
            List<FunctionModel> list = new ArrayList<FunctionModel>();
            for (FunctionEntity functionEntity : functionList)
            {
                if (functionEntity == null)
                {
                    continue;
                }
                QueryCondition<FunctionEntity> childrenQuery = new QueryCondition<FunctionEntity>();
                childrenQuery.addCondition("parentId", functionEntity.getId());
                childrenQuery.addCondition("accountId", accountId);
                List<FunctionEntity> childrenList = roleDao.getRoleFunction(childrenQuery);
                FunctionModel model = new FunctionModel();
                model.setFunction(functionEntity);
                model.setChildren(childrenList);
                list.add(model);
            }
            HierarchicalCacheManager.set(CacheLevel.REDIS,
                    "sys_role_menu",
                    accountId,
                    list);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询权限菜单错误", e, this.getClass());
            throw new ServiceException("查询权限菜单错误", e);
        }
    }
    
    @Override
    public List<FunctionEntity> getAllRoleFunction(String accountId)
            throws ServiceException
    {
        try
        {
            List<FunctionEntity> list = roleDao.getAllRoleFunction(accountId);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询所有权限菜单错误", e, this.getClass());
            throw new ServiceException("查询所有权限菜单错误", e);
        }
    }
    
}
