package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.PayContentStateEnum;

public class PayContentStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<PayContentStateEnum> {
	public PayContentStateEnumTypeHandler(Class<PayContentStateEnum> type) {
		super(type);
	}

}
