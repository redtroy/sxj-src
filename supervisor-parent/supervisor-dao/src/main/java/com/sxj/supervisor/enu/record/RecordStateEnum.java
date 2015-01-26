package com.sxj.supervisor.enu.record;

/**
 * 备案绑定状态
 * 
 * @author Administrator
 *
 */
public enum RecordStateEnum
{
    NOBINDING("未绑定", 0), BINDING("已绑定", 1);
// NOCHANGE("未变更", 2), CHANGE("已变更", 3), NOSUPPLEMENT(
      //      "未补损", 4), SUPPLEMENT("已补损", 5);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private RecordStateEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (RecordStateEnum c : RecordStateEnum.values())
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
