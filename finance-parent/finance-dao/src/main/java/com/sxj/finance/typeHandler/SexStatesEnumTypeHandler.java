package com.sxj.finance.typeHandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import com.sxj.finance.enu.member.SexStatesEnum;

public class SexStatesEnumTypeHandler extends
		EnumOrdinalTypeHandler<SexStatesEnum> {
	public SexStatesEnumTypeHandler(Class<SexStatesEnum> type) {
		super(type);
	}
}
