package com.sxj.supervisor.dao.message;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.message.TransMessageEntity;
import com.sxj.util.persistent.QueryCondition;

public interface ITransMessageDao
{
    @Insert
    public void addMessage(TransMessageEntity message);
    
    @Get
    public TransMessageEntity getMessage(String id);
    
    public List<TransMessageEntity> queryMessageList(
            QueryCondition<TransMessageEntity> query);
}
