package com.sxj.supervisor.enu.rfid.logistics;

public enum LabelStateEnum
{
    UN_FILLED("标签未发货", 0), SHIPPED("标签已发货", 1), HAS_RECEIPT("标签已收货", 2), INSTALL(
            "标签已出库", 3), HAS_QUALITY("标签已验收", 4);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private LabelStateEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (LabelStateEnum c : LabelStateEnum.values())
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
