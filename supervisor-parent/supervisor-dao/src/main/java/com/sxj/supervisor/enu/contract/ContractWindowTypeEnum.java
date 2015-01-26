package com.sxj.supervisor.enu.contract;

public enum ContractWindowTypeEnum
{
    TYPE0("C120 60B*120H", 0), TYPE1("C120 120B*120H", 1), TYPE2(
            "C120 150B*120H", 2), TYPE3("C150 60B*150H", 3), TYPE4(
            "C150 120B*150H", 4), TYPE5("C150 150B*150H", 5), TYPE6(
            "C150 180B*150H", 6), TYPE7("C180 60B*180H", 7), TYPE8(
            "C180 150B*180H", 8), TYPE9("C180 180B*180H", 9);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private ContractWindowTypeEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (ContractWindowTypeEnum c : ContractWindowTypeEnum.values())
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
