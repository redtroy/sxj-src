package com.sxj.supervisor.enu.rfid.apply;

public enum ReceiptStateEnum
{
    SHIPMENTS("发货中", 0), GOODS_RECRIPT("已收货", 1);
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private ReceiptStateEnum(String name, Integer id)
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
