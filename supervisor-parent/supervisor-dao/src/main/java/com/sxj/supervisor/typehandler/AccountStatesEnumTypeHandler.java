package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.member.AccountStatesEnum;

public class AccountStatesEnumTypeHandler extends
        EnumOrdinalTypeHandler<AccountStatesEnum>
{
    
    public AccountStatesEnumTypeHandler(Class<AccountStatesEnum> type)
    {
        super(type);
    }
    
}
