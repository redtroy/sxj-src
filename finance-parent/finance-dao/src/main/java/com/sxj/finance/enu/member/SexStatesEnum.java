package com.sxj.finance.enu.member;

public enum SexStatesEnum
{
    man("男", 0), woman("女", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private SexStatesEnum(String name, Integer id)
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
