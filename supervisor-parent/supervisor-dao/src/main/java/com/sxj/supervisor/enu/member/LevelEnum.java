package com.sxj.supervisor.enu.member;

public enum LevelEnum
{
    ONE("一级", 0), TWO("二级", 1), THREE("三级", 2);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private LevelEnum(String name, Integer id)
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
    
    public static LevelEnum getEnum(Integer id)
    {
        for (LevelEnum c : LevelEnum.values())
        {
            if (c.getId() == id)
            {
                return c;
            }
        }
        return null;
    }
}
