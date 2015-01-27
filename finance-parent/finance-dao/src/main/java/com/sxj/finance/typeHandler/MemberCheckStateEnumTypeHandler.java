package com.sxj.finance.typeHandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.finance.enu.member.MemberCheckStateEnum;

public class MemberCheckStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<MemberCheckStateEnum>
{
    
    public MemberCheckStateEnumTypeHandler(Class<MemberCheckStateEnum> type)
    {
        super(type);
    }
    
}
