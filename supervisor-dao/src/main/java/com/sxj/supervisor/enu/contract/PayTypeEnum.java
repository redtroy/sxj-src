package com.sxj.supervisor.enu.contract;

public enum PayTypeEnum
{
    DEPOSIT("定金", 0), PAYMENT("货款", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private PayTypeEnum(String name, Integer id)
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
