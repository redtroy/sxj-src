package com.sxj.supervisor.dao.message;

import java.util.List;

import com.sxj.supervisor.model.message.TenderMessageModel;

public interface ITenderMessageDao
{
    public List<TenderMessageModel> queryMessageList(String memberNo);
}
