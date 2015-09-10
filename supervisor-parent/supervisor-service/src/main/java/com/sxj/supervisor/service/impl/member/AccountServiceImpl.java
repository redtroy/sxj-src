package com.sxj.supervisor.service.impl.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sxj.supervisor.dao.member.IAccountDao;
import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberRoleEntity;
import com.sxj.supervisor.enu.member.AccountStatesEnum;
import com.sxj.supervisor.model.member.AccountQuery;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService
{
    
    @Autowired
    private IAccountDao accountDao;
    
    @Autowired
    private IMemberDao memberDao;
    
    @Autowired
    private IMemberRoleService roleServce;
    
    @Autowired
    private IMemberService memberService;
    
    /**
     * 新增子会员
     */
    @Override
    @Transactional
    public void addAccount(AccountEntity account, String[] functionIds)
            throws ServiceException
    {
        try
        {
            AccountEntity oldAccount = getAccountByName(account.getAccountName(),
                    account.getParentId());
            if (oldAccount != null)
            {
                throw new ServiceException("用户账户已存在");
            }
            account.setState(AccountStatesEnum.NORMAL);
            account.setNoType(account.getParentId() + "-");
            account.setPassword(EncryptUtil.md5Hex(account.getPassword()));
            accountDao.addAccount(account);
            memberDao.addAccountNum(account.getParentId());
            if (functionIds != null && functionIds.length > 0)
            {
                List<MemberRoleEntity> roles = new ArrayList<MemberRoleEntity>();
                for (int i = 0; i < functionIds.length; i++)
                {
                    if (functionIds[i] != null
                            && !"none".equals(functionIds[i]))
                    {
                        MemberRoleEntity role = new MemberRoleEntity();
                        role.setAccountId(account.getId());
                        role.setFunctionId(functionIds[i]);
                        role.setMemberId(account.getParentId());
                        roles.add(role);
                    }
                }
                if (!roles.isEmpty())
                {
                    roleServce.addRoles(roles);
                }
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("添加会员子账户信息错误", e);
        }
    }
    
    /**
     * 更新子会员
     */
    @Override
    @Transactional
    public void modifyAccount(AccountEntity account, String[] functionIds)
            throws ServiceException
    {
        try
        {
            Assert.notNull(account);
            AccountEntity entity = accountDao.getAccount(account.getId());
            if (functionIds != null && functionIds.length > 0)
            {
                List<MemberRoleEntity> roles = new ArrayList<MemberRoleEntity>();
                for (int i = 0; i < functionIds.length; i++)
                {
                    if (functionIds[i] != null
                            && !"none".equals(functionIds[i]))
                    {
                        MemberRoleEntity role = new MemberRoleEntity();
                        role.setAccountId(entity.getId());
                        role.setFunctionId(functionIds[i]);
                        roles.add(role);
                    }
                }
                if (!roles.isEmpty())
                {
                    roleServce.removeRoles(account.getId());
                    roleServce.addRoles(roles);
                }
            }
            else
            {
                roleServce.removeRoles(account.getId());
            }
            account.setVersion(entity.getVersion());
            accountDao.updateAccount(account);
        }
        catch (Exception e)
        {
            SxjLogger.error("更新子账户信息错误", e, this.getClass());
            throw new ServiceException("更新子账户信息错误", e);
        }
        
    }
    
    /**
     * 查询子会员
     */
    @Override
    @Transactional(readOnly = true)
    public AccountEntity getAccount(String id) throws ServiceException
    {
        try
        {
            return accountDao.getAccount(id);
        }
        catch (Exception e)
        {
            SxjLogger.error("子会员查询错误", e, this.getClass());
            throw new ServiceException("子会员查询错误错误", e);
        }
        
    }
    
    /**
     * 子会员高级查询
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountEntity> queryAccounts(AccountQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<AccountEntity> condition = new QueryCondition<AccountEntity>();
            List<AccountEntity> aacountList = new ArrayList<AccountEntity>();
            if (query == null)
            {
                return aacountList;
            }
            condition.addCondition("name", query.getName());// 姓名
            condition.addCondition("parentId", query.getMemberNo());// 父会员号
            condition.addCondition("accountNo", query.getAccountNo());// 子会员
            condition.addCondition("accountName", query.getAccountName());// 子会员名称
            condition.addCondition("state", query.getState());// 子账户状态
            condition.addCondition("delstate", query.getDelstate());// 删除标记
            condition.addCondition("startDate", query.getStartDate());// 开始时间
            condition.addCondition("endDate", query.getEndDate());// 结束时间
            condition.addCondition("functionId", query.getFunctionId());// 权限ＩＤ
            condition.setPage(query);
            aacountList = accountDao.queryAccount(condition);
            query.setPage(condition);
            return aacountList;
        }
        catch (Exception e)
        {
            SxjLogger.error("子会员高级查询错误", e, this.getClass());
            throw new ServiceException("子会员高级查询错误错误", e);
        }
        
    }
    
    /**
     * 删除子会员
     */
    @Override
    @Transactional
    public void reomveAccount(String id)
    {
        try
        {
            AccountEntity account = getAccount(id);
            accountDao.updateAccount(account);
            MemberEntity member = memberService.memberInfo(account.getParentId());
            member.setAccountNum(member.getAccountNum() - 1);
            memberService.modifyMember(member, false);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除子账户信息错误", e, this.getClass());
            throw new ServiceException("删除子账户错误", e);
        }
    }
    
    /**
     * 更新子会员状态
     */
    @Override
    @Transactional
    public String editState(String id, Integer state) throws ServiceException
    {
        try
        {
            AccountEntity account = new AccountEntity();
            account.setId(id);
            account.setState(AccountStatesEnum.getEnum(state));
            account.setVersion(accountDao.getAccount(id).getVersion());
            accountDao.updateAccount(account);
            return AccountStatesEnum.getEnum(state).getName();
            
        }
        catch (Exception e)
        {
            SxjLogger.error("更新子账户状态错误", e, this.getClass());
            throw new ServiceException("更新子账户状态错误", e);
        }
        
    }
    
    /**
     * 初始化密码
     */
    @Override
    @Transactional
    public String initializePwd(String id) throws ServiceException
    {
        try
        {
            
            int rondom = NumberUtils.getRandomIntInMax(999999);
            String password = StringUtils.getLengthStr(rondom + "", 6, '0');
            AccountEntity account = new AccountEntity();
            account.setId(id);
            account.setPassword(EncryptUtil.md5Hex(password));
            account.setVersion(accountDao.getAccount(id).getVersion());
            accountDao.updateAccount(account);
            return password;
        }
        catch (Exception e)
        {
            SxjLogger.error("初始化密码错误", e, this.getClass());
            throw new ServiceException("初始化密码错误", e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public AccountEntity getAccountByName(String accountName, String memberNo)
            throws ServiceException
    {
        try
        {
            if (StringUtils.isEmpty(accountName))
            {
                return null;
            }
            QueryCondition<AccountEntity> condition = new QueryCondition<AccountEntity>();
            condition.addCondition("parentId", memberNo);// 父会员号
            condition.addCondition("accountName", accountName);// 子会员名称
            List<AccountEntity> list = accountDao.getAccountByAccountName(condition);
            if (list != null && list.size() > 0)
            {
                return list.get(0);
            }
            return null;
        }
        catch (Exception e)
        {
            SxjLogger.error("根据子账号查询信息错误", e, this.getClass());
            throw new ServiceException("根据子账号查询信息错误", e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public String editPwd(String id, String pwd) throws ServiceException
    {
        try
        {
            AccountEntity account = new AccountEntity();
            account.setId(id);
            account.setPassword(EncryptUtil.md5Hex(pwd));
            account.setVersion(accountDao.getAccount(id).getVersion());
            accountDao.updateAccount(account);
        }
        catch (Exception e)
        {
            SxjLogger.error("密码更新错误", e, this.getClass());
            throw new ServiceException("密码更新错误", e.getMessage());
        }
        return null;
    }
    
    @Override
    @Transactional
    public void edit_Login(String id) throws ServiceException
    {
        try
        {
            AccountEntity account = new AccountEntity();
            account.setId(id);
            account.setLastLogin(new Date());
            account.setVersion(accountDao.getAccount(id).getVersion());
            accountDao.updateAccount(account);
        }
        catch (Exception e)
        {
            SxjLogger.error("登陆时间更新错误", e, this.getClass());
            throw new ServiceException("登陆时间更新错误", e.getMessage());
        }
        
    }
}
