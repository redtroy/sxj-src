package com.sxj.supervisor.entity.system;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.system.IRoleDao;

/**
 * 子账户关联系统功能
 * @author AnShaoshuai
 *
 */
@Entity(mapper = IRoleDao.class)
@Table(name = "M_SYS_ROLE")
public class RoleEntity extends Pagable implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -3954228102127813375L;
    
    /**
     * 主键标识
    **/
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 系统功能ID
    **/
    @Column(name = "FUNCTION_ID")
    private String functionId;
    
    /**
     * 账户ID
    **/
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getFunctionId()
    {
        return functionId;
    }
    
    public void setFunctionId(String functionId)
    {
        this.functionId = functionId;
    }
    
    public String getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }
}
