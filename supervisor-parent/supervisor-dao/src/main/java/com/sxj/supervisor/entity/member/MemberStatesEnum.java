package com.sxj.supervisor.entity.member;


/**
 * 会员帐号状态
 * @author Administrator
 *
 */
public enum MemberStatesEnum
{
    normal("冻结", 0), stop("解冻", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private MemberStatesEnum(String name, Integer id)
    {
        this.name = name;
        this.id = id;
    }
    
    // 普通方法
    public static String getName(Integer id)
    {
        for (MemberStatesEnum c : MemberStatesEnum.values())
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
