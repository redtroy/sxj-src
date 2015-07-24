package com.sxj.supervisor.enu.contract;

/**
 * 批次状态
 * @author An
 *
 */
public enum ItemTypeEnum
{
    NORMAL_ITEM("正常条目", 0), MODIFY_ITEM("变更条目", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private ItemTypeEnum(String name, Integer id)
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
