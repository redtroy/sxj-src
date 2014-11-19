package com.sxj.supervisor.dao.rfid;

import java.sql.SQLException;

import com.sxj.supervisor.entity.rfid.RfidKeyEntity;

public interface IRfidKeyDao
{
    
    public void getKey(RfidKeyEntity entity) throws SQLException;
    
    public RfidKeyEntity getKeyEntity(String name) throws SQLException;
    
    public int updateKeyEntity(RfidKeyEntity entity) throws SQLException;
    
    public int insertKeyEntity(RfidKeyEntity entity) throws SQLException;
}
