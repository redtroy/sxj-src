package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.PayTypeEnum;

public class PayContractTypeEnumTypeHandler extends
        EnumOrdinalTypeHandler<PayTypeEnum>
{
    public PayContractTypeEnumTypeHandler(Class<PayTypeEnum> type)
    {
        super(type);
    }
    
}
