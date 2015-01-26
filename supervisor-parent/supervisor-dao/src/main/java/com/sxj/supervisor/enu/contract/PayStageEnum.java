package com.sxj.supervisor.enu.contract;

public enum PayStageEnum
{
    STAGE1("未支付", "支付中"), STAGE2_0("融资受理中", "支付中"), STAGE2_1("融资已放款", "支付中"), STAGE2_2(
            "融资已搁置", "支付中"), STAGE3("乙方确认中", "确认收款"), STAGE4("已完成支付", "已完成支付");
    private String nameA;
    
    private String nameB;
    
    private PayStageEnum(String nameA, String nameB)
    {
        this.nameA = nameA;
        this.nameB = nameB;
    }
    
    public String getNameA()
    {
        return nameA;
    }
    
    public void setNameA(String nameA)
    {
        this.nameA = nameA;
    }
    
    public String getNameB()
    {
        return nameB;
    }
    
    public void setNameB(String nameB)
    {
        this.nameB = nameB;
    }
    
}
