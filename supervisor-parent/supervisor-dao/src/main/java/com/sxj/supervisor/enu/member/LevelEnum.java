package com.sxj.supervisor.enu.member;

public enum LevelEnum
{
	SUPER("特级", 0),ONE("一级", 1), TWO("二级", 2), THREE("三级",3),A("甲",4),B("乙",5),C("丙",6);
    
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
