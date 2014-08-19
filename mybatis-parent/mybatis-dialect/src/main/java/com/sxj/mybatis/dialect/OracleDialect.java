package com.sxj.mybatis.dialect;

/**
 * 
 * @author tony.li
 * 
 */

public class OracleDialect extends Dialect
{
    
    public String getLimitString(String sql, int offset, int limit)
    {
        
        return OraclePageHelper.getLimitString(sql, offset, limit);
    }
    
    @Override
    public String getCountString(String sql)
    {
        return OraclePageHelper.getCountString(sql);
    }
}
