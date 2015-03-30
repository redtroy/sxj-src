package com.sxj.supervisor.dao.message;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IMessageConfigDao
{
    
    @BatchInsert
    public void addConfigBatch(List<MessageConfigEntity> config);
    
    @BatchUpdate
    public void updateConfigBatch(List<MessageConfigEntity> config);
    
    @Insert
    public void addConfig(MessageConfigEntity config);
    
    @Update
    public void updateConfig(MessageConfigEntity config);
    
    @Get
    public MessageConfigEntity getConfig(String id);
    
    public void delConfig(String memberNo);
    
    public List<MessageConfigEntity> queryConfigList(
            QueryCondition<MessageConfigEntity> query);
    
}
