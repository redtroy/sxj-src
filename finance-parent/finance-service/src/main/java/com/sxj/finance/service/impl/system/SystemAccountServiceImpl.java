package com.sxj.finance.service.impl.system;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.finance.dao.system.ISystemAccountDao;
import com.sxj.finance.entity.system.SystemAccountEntity;
import com.sxj.finance.service.system.ISystemAccountService;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService
{
    
    @Autowired
    private ISystemAccountDao accountDao;
    
    @Override
    @Transactional
    public void addAccount(SystemAccountEntity account, String[] functionIds)
            throws ServiceException
    {
        try
        {
            SystemAccountEntity oldAccount = getAccountByAccount(account.getAccount());
            if (oldAccount != null)
            {
                throw new ServiceException("用户账户已存在");
            }
            if (StringUtils.isNotEmpty(account.getPassword()))
            {
                account.setPassword(EncryptUtil.md5Hex(account.getPassword()));
            }
            accountDao.addSystemAccount(account);
            
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
            if (account == null)
            {
                return;
            }
            if (StringUtils.isNotEmpty(account.getPassword()))
            {
                account.setPassword(EncryptUtil.md5Hex(account.getPassword()));
            }
            accountDao.updateSystemAccount(account);
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
            if (StringUtils.isNotEmpty(account.getPassword()))
            {
                account.setPassword(EncryptUtil.md5Hex(account.getPassword()));
            }
            accountDao.updateSystemAccount(account);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
