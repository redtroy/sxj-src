package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.PayStageEnum;

public class PayStageEnumTypeHandler extends
        EnumOrdinalTypeHandler<PayStageEnum>
{
    
    public PayStageEnumTypeHandler(Class<PayStageEnum> type)
    {
        super(type);
    }
    
}
