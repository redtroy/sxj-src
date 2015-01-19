package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.purchase.PayStateEnum;

public class P_PayStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<PayStateEnum> {

	public P_PayStateEnumTypeHandler(Class<PayStateEnum> type) {
		super(type);
	}

}
