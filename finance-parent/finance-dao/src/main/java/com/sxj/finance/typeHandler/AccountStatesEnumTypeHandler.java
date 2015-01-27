package com.sxj.finance.typeHandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.finance.enu.member.AccountStatesEnum;

public class AccountStatesEnumTypeHandler extends
        EnumOrdinalTypeHandler<AccountStatesEnum>
{
    
    public AccountStatesEnumTypeHandler(Class<AccountStatesEnum> type)
    {
        super(type);
    }
    
}
