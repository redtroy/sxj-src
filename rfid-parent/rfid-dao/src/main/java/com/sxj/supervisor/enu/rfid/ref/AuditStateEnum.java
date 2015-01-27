package com.sxj.supervisor.enu.rfid.ref;

public enum AuditStateEnum
{
    NO_APPROVAL("未审核", 0), APPROVAL("已审核", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private AuditStateEnum(String name, Integer id)
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
