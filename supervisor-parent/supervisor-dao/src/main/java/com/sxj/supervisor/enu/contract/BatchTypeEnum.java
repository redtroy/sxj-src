package com.sxj.supervisor.enu.contract;

/**
 * 批次状态
 * @author An
 *
 */
public enum BatchTypeEnum
{
    NORMAL_BATCH("正常批次", 0), MODIFY_BATCH("变更批次", 1), REPLENISH_BATCH("补损批次", 2);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private BatchTypeEnum(String name, Integer id)
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
