package com.sxj.supervisor.enu.message;

public enum MessageStateEnum
{
    UNREAD("未读", 0), READ("已读", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private MessageStateEnum(String name, Integer id)
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
