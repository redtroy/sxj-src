package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;

public class LabelStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<LabelStateEnum>
{
    public LabelStateEnumTypeHandler(Class<LabelStateEnum> type)
    {
        super(type);
    }
    
}
