package com.sxj.supervisor.service.impl.system;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.sxj.supervisor.dao.system.ISystemAccountDao;
import com.sxj.supervisor.entity.system.RoleEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.system.SysAccountQuery;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;
import com.sxj.util.persistent.ResultListImpl;

@Service
@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService
{
    
    @Autowired
    private ISystemAccountDao accountDao;
    
    @Autowired
    private IRoleService roleServce;
    
    @Override
    @Transactional
    public void addAccount(SystemAccountEntity account, String[] functionIds)
            throws ServiceException
    {
        try
        {
            Assert.hasText(account.getPassword());
            SystemAccountEntity oldAccount = getAccountByAccount(account.getAccount());
            Assert.notNull(oldAccount, "用户账户已存在");
            //            if (oldAccount != null)
            //            {
            //                throw new ServiceException("用户账户已存在");
            //            }
            account.setPassword(EncryptUtil.md5Hex(account.getPassword()));
            accountDao.addSystemAccount(account);
            if (functionIds != null && functionIds.length > 0)
            {
                List<RoleEntity> roles = new ArrayList<RoleEntity>();
                for (int i = 0; i < functionIds.length; i++)
                {
                    //                    if (functionIds[i] == null)
                    //                    {
                    //                        continue;
                    //                    }
                    //                    if ("none".equals(functionIds[i]))
                    //                    {
                    //                        continue;
                    //                    }
                    if (functionIds[i] != null
                            && !"none".equals(functionIds[i]))
                    {
                        RoleEntity role = new RoleEntity();
                        role.setAccountId(account.getId());
                        role.setFunctionId(functionIds[i]);
                        roles.add(role);
                    }
                }
                Assert.notEmpty(roles);
                roleServce.addRoles(roles);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    @Override
    @Transactional
    public void modifyAccount(SystemAccountEntity account, String[] functionIds)
            throws ServiceException
    {
        try
        {
            Assert.notNull(account);
            Assert.hasText(account.getId());
            SystemAccountEntity systemAccount = accountDao.getSystemAccount(account.getId());
            Assert.notNull(systemAccount);
            //            if (account == null)
            //            {
            //                return;
            //            }
            if (functionIds != null && functionIds.length > 0)
            {
                List<RoleEntity> roles = new ArrayList<RoleEntity>();
                for (int i = 0; i < functionIds.length; i++)
                {
                    //                    if (functionIds[i] == null)
                    //                    {
                    //                        continue;
                    //                    }
                    //                    if ("none".equals(functionIds[i]))
                    //                    {
                    //                        continue;
                    //                    }
                    if (functionIds[i] != null
                            && !"none".equals(functionIds[i]))
                    {
                        RoleEntity role = new RoleEntity();
                        role.setAccountId(systemAccount.getId());
                        role.setFunctionId(functionIds[i]);
                        roles.add(role);
                    }
                }
                if (!CollectionUtils.isEmpty(roles))
                {
                    roleServce.removeRoles(systemAccount.getId());
                    roleServce.addRoles(roles);
                }
            }
            else
            {
                roleServce.removeRoles(systemAccount.getId());
            }
            if (StringUtils.isNotEmpty(account.getPassword()))
            {
                systemAccount.setPassword(EncryptUtil.md5Hex(account.getPassword()));
            }
            accountDao.updateSystemAccount(systemAccount);
        }
        catch (Exception e)
        {
            throw new ServiceException("修改系统用户信息错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void deleteAccount(String id) throws ServiceException
    {
        try
        {
            accountDao.deleteSystemAccount(id);
            roleServce.removeRoles(id);
        }
        catch (Exception e)
        {
            throw new ServiceException("删除系统用户信息错误", e);
        }
        
    }
    
    @Override
    public SystemAccountEntity getAccount(String id) throws ServiceException
    {
        try
        {
            return accountDao.getSystemAccount(id);
        }
        catch (Exception e)
        {
            throw new ServiceException("查询系统用户信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public SystemAccountEntity getAccountByAccount(String account)
            throws ServiceException
    {
        try
        {
            if (StringUtils.isEmpty(account))
            {
                return null;
            }
            QueryCondition<SystemAccountEntity> condition = new QueryCondition<SystemAccountEntity>();
            condition.addCondition("account", account);
            List<SystemAccountEntity> list = accountDao.querySystemAccount(condition);
            if (list != null && list.size() > 0)
            {
                return list.get(0);
            }
            return null;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询系统用户信息失败", e);
        }
    }
    
    @Override
    public SystemAccountEntity getAccountByAccountNo(String accountNo)
            throws ServiceException
    {
        try
        {
            if (StringUtils.isEmpty(accountNo))
            {
                return null;
            }
            QueryCondition<SystemAccountEntity> condition = new QueryCondition<SystemAccountEntity>();
            condition.addCondition("accountNo", accountNo);
            List<SystemAccountEntity> list = accountDao.querySystemAccount(condition);
            if (list != null && list.size() > 0)
            {
                return list.get(0);
            }
            return null;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询系统用户信息失败", e);
        }
    }
    
    @Override
    public ResultList<SystemAccountEntity> queryAccounts(SysAccountQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<SystemAccountEntity> condition = new QueryCondition<SystemAccountEntity>();
            if (query != null)
            {
                condition.addCondition("id", query.getId());
                condition.addCondition("accountNo", query.getAccountNo());
                condition.addCondition("name", query.getName());
                condition.addCondition("account", query.getAccount());
                condition.addCondition("functionId", query.getFunctionId());
                condition.setPage(query);
            }
            List<SystemAccountEntity> list = accountDao.querySystemAccount(condition);
            query.setPage(condition);
            ResultList<SystemAccountEntity> res = new ResultListImpl<SystemAccountEntity>();
            res.setResults(list);
            return res;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询系统用户错误", e);
        }
    }
    
    @Override
    @Transactional
    public String initPassword(String accountId) throws ServiceException
    {
        try
        {
            int rondom = NumberUtils.getRandomIntInMax(999999);
            String password = StringUtils.getLengthStr(rondom + "", 6, '0');
            SystemAccountEntity account = new SystemAccountEntity();
            account.setId(accountId);
            account.setPassword(EncryptUtil.md5Hex(password));
            accountDao.updateSystemAccount(account);
            return password;
        }
        catch (Exception e)
        {
            throw new ServiceException("初始化系统用户密码错误", e);
        }
    }
    
    @Override
    public void updateLoginTime(String accountId) throws ServiceException
    {
        try
        {
            SystemAccountEntity account = new SystemAccountEntity();
            account.setId(accountId);
            account.setLastLogin(new Date());
            accountDao.updateSystemAccount(account);
        }
        catch (Exception e)
        {
            throw new ServiceException("更新系統用戶登錄時間错误", e);
        }
        
    }
    
    @Override
    public String edit_pwd(String id, String password) throws ServiceException
    {
        SystemAccountEntity account = new SystemAccountEntity();
        account.setId(id);
        account.setPassword(password);
        try
        {
            Assert.hasText(id);
            Assert.hasText(password);
            if (StringUtils.isNotEmpty(account.getPassword()))
            {
                account.setPassword(EncryptUtil.md5Hex(account.getPassword()));
            }
            accountDao.updateSystemAccount(account);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
}
