package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.member.MemberCheckStateEnum;

public class MemberCheckStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<MemberCheckStateEnum> {

	public MemberCheckStateEnumTypeHandler(Class<MemberCheckStateEnum> type) {
		super(type);
	}

}
