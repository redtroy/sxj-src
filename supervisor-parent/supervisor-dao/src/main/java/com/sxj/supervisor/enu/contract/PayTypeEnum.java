package com.sxj.supervisor.enu.contract;

public enum PayTypeEnum
{
    GLASS("玻璃", 0), EXTRUDERS("型材", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private PayTypeEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (PayTypeEnum c : PayTypeEnum.values())
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
