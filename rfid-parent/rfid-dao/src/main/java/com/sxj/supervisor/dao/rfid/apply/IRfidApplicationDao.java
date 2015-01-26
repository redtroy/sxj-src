package com.sxj.supervisor.dao.rfid.apply;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRfidApplicationDao
{
    /**
     * 查询RFID申请单
     */
    public List<RfidApplicationEntity> queryList(
            QueryCondition<RfidApplicationEntity> query) throws SQLException;
    
    /**
     * 更新
     */
    @Update
    public void updateRfidApplication(RfidApplicationEntity app)
            throws SQLException;
    
    /**
     * 增加
     */
    @Insert
    public void addRfidApplication(RfidApplicationEntity app)
            throws SQLException;
    
    @Get
    public RfidApplicationEntity getRfidApplication(String id)
            throws SQLException;
}
