package com.sxj.supervisor.enu.member;

/**
 * 会员子帐号状态
 * 
 * @author Administrator
 *
 */
public enum AccountStatesEnum
{
    STOP("已冻结", 0), NORMAL("未冻结", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private AccountStatesEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public static AccountStatesEnum getEnum(Integer id)
    {
        for (AccountStatesEnum c : AccountStatesEnum.values())
        {
            if (c.getId() == id)
            {
                return c;
            }
        }
        return null;
    }
}
