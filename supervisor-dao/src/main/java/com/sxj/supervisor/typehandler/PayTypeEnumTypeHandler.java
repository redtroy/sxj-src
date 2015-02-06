package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.PayContractTypeEnum;

public class PayTypeEnumTypeHandler extends EnumOrdinalTypeHandler<PayContractTypeEnum>
{
    public PayTypeEnumTypeHandler(Class<PayContractTypeEnum> type)
    {
        super(type);
    }
    
}
