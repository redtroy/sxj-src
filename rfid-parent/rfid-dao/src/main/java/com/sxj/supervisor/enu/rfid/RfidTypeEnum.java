package com.sxj.supervisor.enu.rfid;

public enum RfidTypeEnum
{
    DOOR("门窗标签", 0), GLASS("玻璃标签", 1), EXTRUSIONS("型材标签", 2);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private RfidTypeEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (RfidTypeEnum c : RfidTypeEnum.values())
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
