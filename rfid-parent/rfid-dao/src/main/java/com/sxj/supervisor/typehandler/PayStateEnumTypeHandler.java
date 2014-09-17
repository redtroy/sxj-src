package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.Enum.rfid.base.PayStateEnum;

public class PayStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<PayStateEnum> {

	public PayStateEnumTypeHandler(Class<PayStateEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
