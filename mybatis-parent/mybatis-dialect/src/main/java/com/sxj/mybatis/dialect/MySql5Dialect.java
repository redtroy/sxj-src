package com.sxj.mybatis.dialect;

public class MySql5Dialect extends Dialect
{
    
    protected static final String SQL_END_DELIMITER = ";";
    
    public String getLimitString(String sql, boolean hasOffset)
    {
        return MySql5PageHepler.getLimitString(sql, -1, -1);
    }
    
    public String getLimitString(String sql, int offset, int limit)
    {
        return MySql5PageHepler.getLimitString(sql, offset, limit);
    }
    
    public boolean supportsLimit()
    {
        return true;
    }
    
    @Override
    public String getCountString(String sql)
    {
        return MySql5PageHepler.getCountString(sql);
    }
    
    @Override
    public String getSnString(SN sn)
    {
        StringBuffer sb = new StringBuffer("update ");
        sb.append(sn.getTableName());
        sb.append(" set ");
        sb.append(sn.getSn());
        sb.append(" = ");
        sb.append(sn.getSn());
        sb.append(" + ");
        sb.append(sn.getStep());
        sb.append(" where ");
        sb.append(sn.getStub());
        sb.append(" = '");
        sb.append(sn.getStubValue());
        sb.append("'");
        return sb.toString();
    }
    
    public MySql5Dialect()
    {
        super();
        setType(Dialect.Type.MYSQL);
    }
    
    @Override
    public String getSnInsertString(SN sn)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ");
        sb.append(sn.getTableName());
        sb.append(" (");
        sb.append(sn.getStub());
        sb.append(",");
        sb.append(sn.getSn());
        sb.append(") values ('");
        sb.append(sn.getStubValue());
        sb.append("',0");
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public String getSnSelectString(SN sn)
    {
        return "select 1 from " + sn.getTableName() + " where " + sn.getStub()
                + "='" + sn.getStubValue() + "'";
    }
    
}
