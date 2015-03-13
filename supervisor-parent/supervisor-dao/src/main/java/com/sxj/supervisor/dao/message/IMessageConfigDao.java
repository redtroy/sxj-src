package com.sxj.supervisor.dao.message;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.supervisor.entity.message.MessageConfigEntity;

public interface IMessageConfigDao
{
    
    @BatchInsert
    public void addConfig(List<MessageConfigEntity> config);
    
    @BatchUpdate
    public void updateConfig(List<MessageConfigEntity> config);
    
    public void delConfig(String memberNo);
    
    public List<MessageConfigEntity> queryConfigList(String memberNo);
    
}
