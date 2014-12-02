package com.sxj.finance.typeHandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.finance.enu.member.MemberTypeEnum;


public class MemberTypeEnumTypeHandler extends
        EnumOrdinalTypeHandler<MemberTypeEnum>
{
    
    public MemberTypeEnumTypeHandler(Class<MemberTypeEnum> type)
    {
        super(type);
    }
    
}
