package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.message.MessageTypeEnum;

public class MessageTypeEnumTypehandler extends
        EnumOrdinalTypeHandler<MessageTypeEnum>
{
    
    public MessageTypeEnumTypehandler(Class<MessageTypeEnum> type)
    {
        super(type);
        // TODO Auto-generated constructor stub
    }
    
}
