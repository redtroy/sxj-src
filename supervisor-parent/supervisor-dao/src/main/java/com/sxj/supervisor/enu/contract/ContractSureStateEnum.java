package com.sxj.supervisor.enu.contract;

/**
 * 确认状态
 * @author Administrator
 *
 */
public enum ContractSureStateEnum
{
    NOAFFIRM("未确认", 0), AAFFIRM("甲方确认", 1), BAFFIRM("乙方确认", 2), FILINGS("已备案",
            3);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private ContractSureStateEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (ContractSureStateEnum c : ContractSureStateEnum.values())
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
