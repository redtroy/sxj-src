package com.sxj.supervisor.enu.message;

public enum MessageTypeEnum
{
    CONTRACT("合同确认", 0), PAY("支付货款", 1), DELIVER("发货通知", 2), RECEIPT("收货通知", 3), SYSTEM(
            "系统信息", 4), TENDER("市场信息", 5), SMS("短信信息", 6);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private MessageTypeEnum(String name, Integer id)
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
