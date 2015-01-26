package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;

public class WindowTypeEnumTypeHandler extends
        EnumOrdinalTypeHandler<WindowTypeEnum>
{
    public WindowTypeEnumTypeHandler(Class<WindowTypeEnum> type)
    {
        super(type);
    }
    
}
