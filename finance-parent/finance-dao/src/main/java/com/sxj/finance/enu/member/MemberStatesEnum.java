package com.sxj.finance.enu.member;

/**
 * 会员帐号状态
 * 
 * @author Administrator
 *
 */
public enum MemberStatesEnum
{
    //normal("未冻结", 1), stop("已冻结", 0);
    normal("未冻结", 0), stop("已冻结", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private MemberStatesEnum(String name, Integer id)
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
    
    public static MemberStatesEnum getEnum(Integer id)
    {
        for (MemberStatesEnum c : MemberStatesEnum.values())
        {
            if (c.getId() == id)
            {
                return c;
            }
        }
        return null;
    }
}
