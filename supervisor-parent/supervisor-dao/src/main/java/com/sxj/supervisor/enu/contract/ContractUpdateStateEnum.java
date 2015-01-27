package com.sxj.supervisor.enu.contract;

/**
 * 变更状态
 * @author Administrator
 *
 */
public enum ContractUpdateStateEnum
{
    NOTCHANGE("未变更", 0), HAVECHANGED("已变更", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private ContractUpdateStateEnum(String name, Integer id)
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
}
