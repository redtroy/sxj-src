package com.sxj.supervisor.dao.rfid.sale;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.rfid.sale.RfidSaleStatisticalEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRfidSaleStatisticalDao
{
    
    @Insert
    public void insert(RfidSaleStatisticalEntity entity) throws SQLException;
    
    public List<RfidSaleStatisticalEntity> queryList(
            QueryCondition<RfidSaleStatisticalEntity> query)
            throws SQLException;
}
