package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.member.MemberTypeEnum;

public class ContractStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<MemberTypeEnum> {

	public ContractStateEnumTypeHandler(Class<MemberTypeEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
