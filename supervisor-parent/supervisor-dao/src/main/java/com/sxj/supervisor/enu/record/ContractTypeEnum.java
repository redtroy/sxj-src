package com.sxj.supervisor.enu.record;

/**
 * 合同类型
 * @author Administrator
 *
 */
public enum ContractTypeEnum
{
    BIDDING("门窗招标", 0), GLASS("玻璃采购", 1), EXTRUSIONS("型材采购", 2);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private ContractTypeEnum(String name, Integer id)
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
