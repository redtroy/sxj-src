package com.sxj.supervisor.dao.message;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;
import com.sxj.util.persistent.QueryCondition;

public interface ISystemInfoDao
{
    @Insert
    public void addMessageInfo(SystemMessageInfoEntity info);
    
    @Update
    public void updateMessageInfo(SystemMessageInfoEntity info);
    
    @Get
    public SystemMessageInfoEntity getMessageInfo(String id);
    
    public List<SystemMessageInfoEntity> queryMessageInfo(
            QueryCondition<SystemMessageInfoEntity> query);
    
}
