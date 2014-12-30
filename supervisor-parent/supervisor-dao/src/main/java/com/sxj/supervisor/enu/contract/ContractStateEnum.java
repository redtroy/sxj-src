package com.sxj.supervisor.enu.contract;

/**
 * 状态
 * @author Administrator
 *
 */
public enum ContractStateEnum
{
    approval("未审核", 0), noapproval("已审核", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private ContractStateEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (ContractStateEnum c : ContractStateEnum.values())
        {
            if (c.getId() == id)
            {
                return c.name;
            }
        }
        return null;
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
}
