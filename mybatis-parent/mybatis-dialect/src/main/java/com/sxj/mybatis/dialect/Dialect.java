package com.sxj.mybatis.dialect;

public abstract class Dialect
{
    
    public static enum Type
    {
        MYSQL, ORACLE
    }
    
    public abstract String getLimitString(String sql, int skipResults,
            int maxResults);
    
    public abstract String getCountString(String sql);
    
}
