package com.sxj.mybatis.dialect;

public abstract class Dialect
{
    private Type type;
    
    public static enum Type
    {
        MYSQL, ORACLE
    }
    
    public abstract String getLimitString(String sql, int skipResults,
            int maxResults);
    
    public abstract String getCountString(String sql);
    
    public abstract String getSnString(SN sn);
    
    public abstract String getSnInsertString(SN sn);
    
    public abstract String getSnSelectString(SN sn);
    
    public Type getType()
    {
        return type;
    }
    
    public void setType(Type type)
    {
        this.type = type;
    }
    
}
