package com.sxj.finance.service.system;

import com.sxj.finance.entity.system.SystemAccountEntity;
import com.sxj.util.exception.ServiceException;

public interface ISystemAccountService
{
    
    /**
     * 
     * @param account
     * @throws ServiceException
     */
    public void addAccount(SystemAccountEntity account, String[] functionIds)
            throws ServiceException;
    
    public void modifyAccount(SystemAccountEntity account, String[] functionIds)
            throws ServiceException;
    
    public void updateLoginTime(String accountId) throws ServiceException;
    
    public String initPassword(String accountId) throws ServiceException;
    
    public void deleteAccount(String id) throws ServiceException;
    
    public SystemAccountEntity getAccount(String id) throws ServiceException;
    
    public SystemAccountEntity getAccountByAccount(String account)
            throws ServiceException;
    
    public SystemAccountEntity getAccountByAccountNo(String accountNo)
            throws ServiceException;
    
    public String edit_pwd(String id, String password) throws ServiceException;
}
