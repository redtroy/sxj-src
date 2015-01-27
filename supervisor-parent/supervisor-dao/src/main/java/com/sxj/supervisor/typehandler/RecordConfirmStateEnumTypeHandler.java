package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;

public class RecordConfirmStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<RecordConfirmStateEnum>
{
    
    public RecordConfirmStateEnumTypeHandler(Class<RecordConfirmStateEnum> type)
    {
        super(type);
    }
    
}
