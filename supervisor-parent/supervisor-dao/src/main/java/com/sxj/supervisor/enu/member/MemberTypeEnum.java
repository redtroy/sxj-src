package com.sxj.supervisor.enu.member;

/**
 * 会员类型
 * 
 * @author Administrator
 *
 */
public enum MemberTypeEnum
{
    DAWP("门窗厂", 0), GLASSFACTORY("玻璃深加工厂", 1), GENRESFACTORY("型材厂", 2), DEVELOPERS(
            "开发商", 3), PRODUCTS("配件厂", 4), FRAMEFACTORY("附框厂", 5), AGENT("代理商",
            6), DISTRIBUTOR("经销商", 7);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private MemberTypeEnum(String name, Integer id)
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
