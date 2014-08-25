package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.member.MemberTypeEnum;

public class RecordTypeEnumTypeHandler  extends EnumOrdinalTypeHandler<MemberTypeEnum>{

	public RecordTypeEnumTypeHandler(Class<MemberTypeEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
