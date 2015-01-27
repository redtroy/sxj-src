package com.sxj.finance.typeHandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.finance.enu.member.MemberStatesEnum;

public class MemberStatesEnumTypeHandler extends
        EnumOrdinalTypeHandler<MemberStatesEnum>
{
    
    public MemberStatesEnumTypeHandler(Class<MemberStatesEnum> type)
    {
        super(type);
    }
    
}
