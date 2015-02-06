package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.PayTypeEnum;

public class PayContentStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<PayTypeEnum>
{
    public PayContentStateEnumTypeHandler(Class<PayTypeEnum> type)
    {
        super(type);
    }
    
}
