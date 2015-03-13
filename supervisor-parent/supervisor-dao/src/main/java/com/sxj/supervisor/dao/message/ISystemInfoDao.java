package com.sxj.supervisor.dao.message;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;

public interface ISystemInfoDao
{
    @Insert
    public void addMessageInfo(SystemMessageInfoEntity info);
    
    @Update
    public void updateMessageInfo(SystemMessageInfoEntity info);
    
    @Get
    public SystemMessageInfoEntity getMessageInfo(String id);
    
}
