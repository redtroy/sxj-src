package com.sxj.supervisor.dao.rfid.sale;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.sale.RfidPriceEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRfidPriceDao
{
    
    @Insert
    public void insertPrice(RfidPriceEntity entity) throws SQLException;
    
    @Update
    public void updatePrice(RfidPriceEntity entity) throws SQLException;
    
    public List<RfidPriceEntity> queryPrice(
            QueryCondition<RfidPriceEntity> query) throws SQLException;
    
}
