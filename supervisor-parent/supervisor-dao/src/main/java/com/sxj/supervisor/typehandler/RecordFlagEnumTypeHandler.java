package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.record.RecordFlagEnum;

public class RecordFlagEnumTypeHandler extends
        EnumOrdinalTypeHandler<RecordFlagEnum>
{
    
    public RecordFlagEnumTypeHandler(Class<RecordFlagEnum> type)
    {
        super(type);
    }
    
}
