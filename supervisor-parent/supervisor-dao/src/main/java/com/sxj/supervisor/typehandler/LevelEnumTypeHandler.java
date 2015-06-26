package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.member.LevelEnum;

public class LevelEnumTypeHandler extends EnumOrdinalTypeHandler<LevelEnum>
{
    public LevelEnumTypeHandler(Class<LevelEnum> type)
    {
        super(type);
    }
    
}
