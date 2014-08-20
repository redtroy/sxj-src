package com.sxj.mybatis.dialect;

public class SN
{
    private int step;
    
    private long current;
    
    private String tableName;
    
    private String stub;
    
    private String stubValue;
    
    private String sn;
    
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
    
    public String getStubValue()
    {
        return stubValue;
    }
    
    public void setStubValue(String stubValue)
    {
        this.stubValue = stubValue;
    }
    
    public String getSn()
    {
        return sn;
    }
    
    public void setSn(String sn)
    {
        this.sn = sn;
    }
    
    public long getCurrent()
    {
        return current;
    }
    
    public void setCurrent(long current)
    {
        this.current = current;
    }
    
}
