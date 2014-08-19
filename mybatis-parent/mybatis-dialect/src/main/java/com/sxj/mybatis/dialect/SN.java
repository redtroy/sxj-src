package com.sxj.mybatis.dialect;

public class SN
{
    private int step;
    
    private String tableName;
    
    private String stub;
    
    private long stubValue;
    
    public String getTableName()
    {
        return tableName;
    }
    
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    
    public String getStub()
    {
        return stub;
    }
    
    public void setStub(String stub)
    {
        this.stub = stub;
    }
    
    public int getStep()
    {
        return step;
    }
    
    public void setStep(int step)
    {
        this.step = step;
    }
    
    public long getStubValue()
    {
        return stubValue;
    }
    
    public void setStubValue(long stubValue)
    {
        this.stubValue = stubValue;
    }
}
