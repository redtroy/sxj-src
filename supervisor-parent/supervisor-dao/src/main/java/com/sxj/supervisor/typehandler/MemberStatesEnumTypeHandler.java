package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.member.MemberStatesEnum;

public class MemberStatesEnumTypeHandler extends
        EnumOrdinalTypeHandler<MemberStatesEnum>
{
    
    public MemberStatesEnumTypeHandler(Class<MemberStatesEnum> type)
    {
        super(type);
    }
    
}
