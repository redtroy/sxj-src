package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.ContractStateEnum;

public class ContractStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<ContractStateEnum>
{
    
    public ContractStateEnumTypeHandler(Class<ContractStateEnum> type)
    {
        super(type);
    }
    
}
