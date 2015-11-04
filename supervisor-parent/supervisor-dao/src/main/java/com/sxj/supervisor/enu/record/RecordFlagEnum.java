package com.sxj.supervisor.enu.record;

public enum RecordFlagEnum
{
    A("需方", 0), B("供方", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private RecordFlagEnum(String name, Integer id)
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
