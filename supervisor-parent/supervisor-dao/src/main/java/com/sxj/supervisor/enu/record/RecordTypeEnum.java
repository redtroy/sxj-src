package com.sxj.supervisor.enu.record;

/**
 * 备案类型
 * @author Administrator
 *
 */

public enum RecordTypeEnum
{
    CONTRACT("合同备案", 0), CHANGE("变更备案", 1), SUPPLEMENT("补损备案", 2);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private RecordTypeEnum(String name, Integer id)
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
