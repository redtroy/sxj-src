package com.sxj.finance.service.member;

import java.util.List;

import com.sxj.finance.entity.member.AccountEntity;
import com.sxj.finance.model.member.AccountQuery;
import com.sxj.util.exception.ServiceException;

public interface IAccountService
{
    
    public AccountEntity getAccount(String id) throws ServiceException;
    
    public AccountEntity getAccountByName(String accountName, String id)
            throws ServiceException;
    
    public List<AccountEntity> queryAccounts(AccountQuery query)
            throws ServiceException;
    
    public String edit_pwd(String id, String pwd) throws ServiceException;
    
    public void edit_Login(String id) throws ServiceException;
}
