package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.ContractSureStateEnum;

public class ContractSureStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<ContractSureStateEnum>
{
    public ContractSureStateEnumTypeHandler(Class<ContractSureStateEnum> type)
    {
        super(type);
    }
}
