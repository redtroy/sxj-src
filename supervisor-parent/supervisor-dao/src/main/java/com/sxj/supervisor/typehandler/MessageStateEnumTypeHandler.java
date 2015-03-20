package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.message.MessageStateEnum;

public class MessageStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<MessageStateEnum>
{
    
    public MessageStateEnumTypeHandler(Class<MessageStateEnum> type)
    {
        super(type);
        // TODO Auto-generated constructor stub
    }
    
}
