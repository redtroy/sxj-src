package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.purchase.ImportStateEnum;

public class ImportStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<ImportStateEnum>
{
    public ImportStateEnumTypeHandler(Class<ImportStateEnum> type)
    {
        super(type);
    }
    
}
