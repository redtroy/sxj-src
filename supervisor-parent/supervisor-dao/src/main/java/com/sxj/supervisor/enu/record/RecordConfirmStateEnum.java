package com.sxj.supervisor.enu.record;

/**
 * 备案审核状态
 * 
 * @author Administrator
 *
 */
public enum RecordConfirmStateEnum
{
    ACCEPTED("受理中", 0), UNCONFIRMED("未确认", 1), CONFIRMEDA("甲方确认", 2), CONFIRMEDB(
            "乙方确认", 3), HASRECORD("已备案", 4);
    // 成员变量
    private Integer id;
    
    private String name;
    
    private RecordConfirmStateEnum(String name, Integer id)
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
