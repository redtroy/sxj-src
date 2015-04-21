package com.sxj.supervisor.dao.message;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.supervisor.entity.message.TenderMessageEntity;
import com.sxj.supervisor.model.message.TenderMessageModel;
import com.sxj.util.persistent.QueryCondition;

public interface ITenderMessageDao
{
    @BatchInsert
    public void addMessage(List<TenderMessageEntity> message);
    
    //@Update
    public Integer updateMessage(TenderMessageEntity message);
    
    @Get
    public TenderMessageEntity getMessage(String id);
    
    public List<TenderMessageModel> queryMessageList(
            QueryCondition<TenderMessageModel> query);
}
