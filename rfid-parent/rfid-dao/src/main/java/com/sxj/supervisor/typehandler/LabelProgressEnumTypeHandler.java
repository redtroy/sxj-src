package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;

public class LabelProgressEnumTypeHandler extends
        EnumOrdinalTypeHandler<LabelProgressEnum>
{
    public LabelProgressEnumTypeHandler(Class<LabelProgressEnum> type)
    {
        super(type);
    }
    
}
