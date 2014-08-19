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
    
    @Override
    public String getSnString(SN sn)
    {
        StringBuffer sb = new StringBuffer("update ");
        sb.append(sn.getTableName());
        sb.append(" set ");
        sb.append(sn.getStub());
        sb.append("=");
        sb.append(sn.getStub());
        sb.append("+");
        sb.append(sn.getStep());
        sb.append("where");
        sb.append(sn.getStub());
        sb.append("=");
        sb.append(sn.getStubValue());
        return sb.toString();
    }
    
    public OracleDialect()
    {
        super();
        setType(Dialect.Type.ORACLE);
    }
}
