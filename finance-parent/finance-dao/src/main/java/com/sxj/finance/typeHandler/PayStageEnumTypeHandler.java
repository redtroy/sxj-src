package com.sxj.finance.typeHandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.finance.enu.finance.PayStageEnum;

public class PayStageEnumTypeHandler extends
        EnumOrdinalTypeHandler<PayStageEnum>
{
    
    public PayStageEnumTypeHandler(Class<PayStageEnum> type)
    {
        super(type);
        // TODO Auto-generated constructor stub
    }
    
}
