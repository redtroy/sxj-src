package com.sxj.supervisor.enu.contract;

public enum PayModeEnum
{
    CASH("现金支付", 0), FINANCING("融资支付", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private PayModeEnum(String name, Integer id)
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
